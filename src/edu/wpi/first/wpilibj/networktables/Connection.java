/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import edu.wpi.first.wpilibj.networktables.LinkedList.Link;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.SocketConnection;

/**
 * The Connection class is the direct link between the server and the clients.
 * @author Joe Grinstead
 */
class Connection {

    /** The maximum delay between pings */
    private static final long WRITE_DELAY = 250;
    /** The maximum wait time before closing connection */
    private static final long TIMEOUT = 1000;
    /** The Socket of the connection */
    private final SocketConnection socket;
    /** Connects the other side's ids for tables to this side's tables */
    private final Hashtable tables = new Hashtable();
    /** Connects the oher side's ids for fields to this side's fields */
    private final Hashtable fields = new Hashtable();
    /** The semaphore for data */
    private final Object dataLock = new Object();
    /** The queue of things that need to be written */
    private final NetworkQueue queue = new NetworkQueue();
    /** The queue of confirmations */
    private final CountingQueue confirmations = new CountingQueue();
    /** The transaction that is currently being read */
    private final NetworkQueue transaction = new NetworkQueue();
    /** Whether or not actually connected */
    private boolean connected = true;
    /** Whether or not currently processing a transaction */
    private boolean inTransaction = false;
    /** Whether or not the transaction should be denied */
    private boolean denyTransaction = false;
    /** The watchdog */
    private Watchdog watchdog;

    Connection(SocketConnection socket) {
        this.socket = socket;
    }

    Object getLock() {
        return dataLock;
    }

    void offerTransaction(NetworkQueue transaction) {
        synchronized (dataLock) {
            // Add tables first
            for (Link link = transaction.queue.first; link != null; link = link.next) {
                if (link.data instanceof Entry.TableEntry) {
                    NetworkTable table = ((Entry) link.data).getTable();
                    table.addConnection(this);
                }
            }
            offer(Data.TRANSACTION_START);
            for (Link link = transaction.queue.first; link != null; link = link.next) {
                queue.offer((Data) link.data);
            }
            offer(Data.TRANSACTION_END);
            dataLock.notify();
        }
    }

    void offer(Data data) {
        if (data != null) {
            synchronized (dataLock) {
                if (data instanceof Entry.TableEntry) {
                    NetworkTable table = ((Entry) data).getTable();
                    table.addConnection(this);
                }

                queue.offer(data);
                dataLock.notify();
            }
        }
    }

    void start() {
        watchdog = new Watchdog();
        watchdog.start();

        new Thread() {

            public void run() {
                try {
                    read(socket.openDataInputStream());
                } catch (IOException e) {
//                    e.printStackTrace();
                }

                close();
            }
        }.start();

        new Thread() {

            public void run() {
                try {
                    write(socket.openDataOutputStream());
                } catch (IOException e) {
//                    e.printStackTrace();
                }

                close();
            }
        }.start();
    }

    private void read(InputStream stream) throws IOException {
        Reader input = new Reader(stream);

        while (connected) {
            watchdog.feed();
            watchdog.activate();

            int value = input.read();

            if (value >= Data.ID || value == Data.OLD_DATA) {
                boolean oldData = value == Data.OLD_DATA;

                Integer id = input.readId(!oldData);
                Key key = Key.getKey((Integer) fields.get(id));

                value = input.read();

                if (key == null) {
                    throw new IOException("Corrupted Data: No assignment for id:" + id);
                }

                if (ConnectionManager.getInstance().IS_SERVER && confirmations.containsKey(key)) {
                    if (inTransaction) {
                        denyTransaction = true;
                    } else {
                        offer(Denial.ONE);
                    }
                    if (value >= Data.TABLE_ID) {
                        input.readTableId(true);
                    } else {
                        input.readEntry(true);
                    }
                } else if (value >= Data.TABLE_ID) {
                    Integer tableId = input.readTableId(true);
                    if (oldData && key.hasEntry()) {
                        offer(Denial.ONE);
                    } else {
                        NetworkTable table = getTable(false, tableId);
                        Entry entry = new Entry.TableEntry(table);
                        entry.setSource(this);
                        entry.setKey(key);
                        if (inTransaction) {
                            transaction.offer(entry);
                        } else {
                            key.getTable().got(false, key, entry);
                            offer(Confirmation.ONE);
                        }
                    }
                } else {
                    Entry entry = input.readEntry(true);

                    if (entry == null) {
                        throw new IOException("Corrupted Data: Could not interpret entry");
                    } else if (oldData && key.hasEntry()) {
                        offer(Denial.ONE);
                    } else {
                        entry.setSource(this);
                        entry.setKey(key);
                        if (inTransaction) {
                            transaction.offer(entry);
                        } else {
                            key.getTable().got(false, key, entry);
                            offer(Confirmation.ONE);
                        }
                    }
                }
            } else if (value >= Data.CONFIRMATION) {
                int count = input.readConfirmations(true);
                while (count-- > 0) {
                    if (confirmations.isEmpty()) {
                        throw new IOException("Corrupted Data: Excess Confirmations");
                    }
                    Entry entry = confirmations.poll();
                    if (entry == null) {
                        if (ConnectionManager.getInstance().IS_SERVER) {
                            while (confirmations.poll() != null);
                        } else {
                            while ((entry = confirmations.poll()) != null) {
                                transaction.offer(entry);
                            }
                            if (!transaction.isEmpty()) {
                                ((Entry) transaction.peek()).getKey().getTable().processTransaction(true, transaction);
                            }
                        }
                    } else if (!ConnectionManager.getInstance().IS_SERVER) {
                        entry.getKey().getTable().got(true, entry.getKey(), entry);
                    }
                }
            } else if (value >= Data.DENIAL) {
                if (ConnectionManager.getInstance().IS_SERVER) {
                    throw new IOException("Corrupted Data: The Server can not be denied");
                }
                int count = input.readDenials(connected);
                while (count-- > 0) {
                    if (confirmations.isEmpty()) {
                        throw new IOException("Corrupted Data: Excess Denial");
                    } else if (confirmations.poll() == null) {
                        while (confirmations.poll() != null);
                    }
                }
            } else if (value == Data.TABLE_REQUEST) {
                if (!ConnectionManager.getInstance().IS_SERVER) {
                    throw new IOException("Corrupted Data: Server requesting table");
                }

                String name = input.readString();
                Integer id = input.readTableId(false);

                NetworkTable table = NetworkTable.getTable(name);

                synchronized (dataLock) {
                    offer(new TableAssignment(table, id));
                    table.addConnection(this);
                }

                tables.put(id, table.getId());
            } else if (value == Data.TABLE_ASSIGNMENT) {
                NetworkTable table = getTable(true, input.readTableId(false));
                Integer id = input.readTableId(false);
                tables.put(id, table.getId());
            } else if (value == Data.ASSIGNMENT) {
                NetworkTable table = getTable(false, input.readTableId(false));
                Key key = table.getKey(input.readString());
                Integer id = input.readId(false);
                fields.put(id, key.getId());
            } else if (value == Data.TRANSACTION) {
                if (!(inTransaction = !inTransaction)) {
                    if (denyTransaction) {
                        offer(Denial.ONE);
                    } else {
                        if (!transaction.isEmpty()) {
                            ((Entry) transaction.peek()).getKey().getTable().processTransaction(false, transaction);
                        }
                        offer(Confirmation.ONE);
                    }
                    denyTransaction = false;
                }
                continue;
            } else {
                throw new IOException("Corrupted Data: Don't know how to interpret marker byte (" + value + ")");
            }
        }
    }

    private NetworkTable getTable(boolean local, Integer id) throws IOException {
        NetworkTable table;
        if (local) {
            table = NetworkTable.getTable(id);
        } else {
            Integer localId = (Integer) tables.get(id);
            if (localId == null) {
                table = new NetworkTable();
                tables.put(id, table.getId());

                synchronized (dataLock) {
                    offer(new TableAssignment(table, id));
                    table.addConnection(this);
                }
            } else {
                table = NetworkTable.getTable(localId);
            }
        }
        if (table == null) {
            throw new IOException("Corrupted Data: No " + (local ? "local" : "offboard") + " table found with id:" + id);
        } else {
            return table;
        }
    }

    private void write(OutputStream output) throws IOException {
        Buffer buffer = new Buffer(2048, output);
        boolean sentData = true;
        Data data = null;

        while (connected) {

            synchronized (dataLock) {
                data = queue.poll();

                // Check if there is data
                if (data == null) {
                    // Ping if necessary
                    if (sentData) {
                        sentData = false;
                    } else {
                        buffer.writeByte(Data.PING);
                        buffer.flush();
                    }

                    // Wait for data
                    try {
                        dataLock.wait(WRITE_DELAY);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }
            }

            // If there is data, send it
            sentData = true;

            if (data instanceof Entry) {
                confirmations.offer((Entry) data);
            } else if (data instanceof OldData) {
                confirmations.offer(((OldData) data).entry);
            } else if (data == Data.TRANSACTION_START || data == Data.TRANSACTION_END) {
                confirmations.offer(null);
            }

            data.encode(buffer);

            buffer.flush();
        }
    }

    private synchronized void close() {
        if (connected) {
            connected = false;
            try {
                socket.close();
            } catch (IOException e) {
            }
            Enumeration tableIds = tables.elements();
            while (tableIds.hasMoreElements()) {
                NetworkTable.getTable((Integer) tableIds.nextElement()).removeConnection(this);
            }
            ConnectionManager.getInstance().removeConnection(this);
        }
    }

    private class Watchdog extends Thread {

        private final Object lock = new Object();
        boolean active = false;
        boolean fed = false;

        public void run() {
            synchronized (lock) {
                while (connected) {
                    while (!active) {
                        try {
                            lock.wait();
                        } catch (InterruptedException ex) {
                        }
                    }
                    fed = false;
                    try {
                        lock.wait(TIMEOUT);
                        if (!fed) {
                            break;
                        }
                    } catch (InterruptedException ex) {
                    }
                }

                close();
            }
        }

        private void activate() {
            synchronized (lock) {
                if (!active) {
                    active = true;
                    lock.notify();
                }
            }
        }

        private void feed() {
            synchronized (lock) {
                active = false;
                fed = true;
                lock.notify();
            }
        }
    }
}
