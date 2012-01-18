/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import edu.wpi.first.wpilibj.networktables.Entry.BooleanEntry;
import edu.wpi.first.wpilibj.networktables.Entry.DoubleEntry;
import edu.wpi.first.wpilibj.networktables.Entry.IntegerEntry;
import edu.wpi.first.wpilibj.networktables.Entry.StringEntry;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 *
 * @author Joe Grinstead
 */
public class NetworkTable {

    /** Links names to tables */
    private static Hashtable tables;
    /** The currently available id */
    private static int currentId = 1;
    /** Links ids to currently active NetworkingTables */
    private static Hashtable ids = new Hashtable();

    /**
     * Opens up the connection stream.  Note that this method will be called
     * automatically when {@link NetworkTable#getTable(java.lang.String)} is
     * called.  This will only have an effect the first time this is called.
     */
    public synchronized static void initialize() {
        if (tables == null) {
            tables = new Hashtable();
            ConnectionManager.getInstance();//initialize ConnectionManager
        }
    }

    /**
     * Returns the table with the given name.  The table will automatically be connected
     * by clients asking for the same table.
     * @param name the name of the table
     * @return the table
     * @throws NoTeamNumberException if setTeam(...) has not yet been called
     */
    public synchronized static NetworkTable getTable(String name) {
        initialize();
        if (tables.containsKey(name)) {
            return (NetworkTable) tables.get(name);
        } else {
            NetworkTable table = new NetworkTable();
            tables.put(name, table);
            return table;
        }
    }

    static NetworkTable getTable(Integer id) {
        return (NetworkTable) ids.get(id);
    }

    /**
     * Returns an id that can be used.
     * @return and id
     */
    private synchronized Integer grabId() {
        return new Integer(currentId++);
    }
    /** The connections this table has */
    private Set connections;
    /** Set of NetworkingListeners who register for everything */
    private Set listenToAllListeners;
    /** Links names to NetworkingListeners */
    private Hashtable listeners;
    /** Set of addition listeners */
    private Set additionListeners;
    /** Set of connection listeners */
    private Set connectionListeners;
    /** The lock for listener modification */
    private final Object listenerLock = new Object();
    /** The actual data */
    private Hashtable data;
    /** The id of this table */
    private final Integer id;
    /** The queue of the current transaction */
    private NetworkQueue transaction;
    /** The number of times begin transaction has been called without a matching end transaction */
    private int transactionCount;
    /** A list of values which need to be signaled */
    private NetworkQueue hasChanged;
    /** A list of values which has been added */
    private NetworkQueue hasAdded;

    /**
     * Creates a new NetworkTable.
     */
    public NetworkTable() {
        data = new Hashtable();
        id = grabId();
        transaction = new NetworkQueue();
        transactionCount = 0;
        hasChanged = new NetworkQueue();
        hasAdded = new NetworkQueue();
    }

    /**
     * Begins a transaction.  Note that you must call endTransaction() afterwards.
     */
    public void beginTransaction() {
        synchronized (this) {
            transactionCount++;
        }
    }

    public void endTransaction() {
        synchronized (this) {
            if (transactionCount == 0) {
                throw new RuntimeException("End transaction called too many times...");
            } else if (--transactionCount == 0) {
                processTransaction(true, transaction);
            }
        }
    }

    void processTransaction(boolean confirmed, NetworkQueue transaction) {
        if (transaction.isEmpty()) {
            return;
        }

        Connection source = ((Entry) transaction.peek()).getSource();

        synchronized (this) {
            if (isConnected()) {
                for (int i = 0; i < connections.size(); i++) {
                    Connection connection = (Connection) connections.get(i);
                    if (connection != source) {
                        connection.offerTransaction(transaction);
                    }
                }
            }

            while (!transaction.isEmpty()) {
                Entry entry = (Entry) transaction.poll();
                Entry oldEntry = entry.getKey().setEntry(entry);
                if (oldEntry == null) {
                    hasAdded.offer(entry);
                } else if (!entry.equals(oldEntry)) {
                    hasChanged.offer(entry);
                }
            }
            while (!hasAdded.isEmpty()) {
                Entry entry = (Entry) hasAdded.poll();
                alertListeners(true, confirmed, entry.getKey().getName(), entry);
            }
            while (!hasChanged.isEmpty()) {
                Entry entry = (Entry) hasChanged.poll();
                alertListeners(true, confirmed, entry.getKey().getName(), entry);
            }
        }
    }

    /**
     * Returns the id of this NetworkTable.  It is guaranteed
     * to be different than any other NetworkTable.
     * @return the id
     */
    Integer getId() {
        return id;
    }

    /**
     * Adds the given connection to this table.
     * @param connection the connection
     */
    void addConnection(Connection connection) {
        synchronized (this) {
            if (connections == null) {
                connections = new Set();
            }
            if (connections.add(connection)) {
                Enumeration keys = data.elements();
                while (keys.hasMoreElements()) {
                    Key key = (Key) keys.nextElement();
                    connection.offer(key);
                    if (key.hasEntry()) {
                        connection.offer(key.getEntry());
                    }
                }
                if (connections.size() == 1) {
                    ids.put(id, this);
                    if (connectionListeners != null) {
                        for (int i = 0; i < connectionListeners.size(); i++) {
                            ((NetworkConnectionListener) connectionListeners.get(i)).connected();
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes the given connection from this table
     * @param connection the connection
     */
    void removeConnection(Connection connection) {
        synchronized (this) {
            connections.remove(connection);
            if (connections.size() == 0) {
                ids.remove(id);
                if (connectionListeners != null) {
                    for (int i = 0; i < connectionListeners.size(); i++) {
                        ((NetworkConnectionListener) connectionListeners.get(i)).disconnected();
                    }
                }
            }
        }
    }

    /**
     * Returns a vector of the keys for this table.
     * The vector will not stay updated, and is in fact completely detached.
     * It is a Vector of strings.
     * from the table.
     * @return a vector of the keys for this table
     */
    public Vector getKeys() {
        synchronized (this) {
            Enumeration elements = data.elements();
            Vector result = new Vector();
            while (elements.hasMoreElements()) {
                Key key = (Key) elements.nextElement();
                if (key.hasEntry()) {
                    result.addElement(key.getName());
                }
            }
            return result;
        }
    }

    /**
     * Returns the key that the name maps to.  This should
     * never fail, if their is no key for that name, then one should be made.
     * @param name the name
     * @return the key
     */
    Key getKey(String name) {
        synchronized (this) {
            Key key = (Key) data.get(name);
            if (key == null) {
                key = new Key(this, name);
                data.put(name, key);
                if (connections != null) {
                    for (int i = 0; i < connections.size(); i++) {
                        ((Connection) connections.get(i)).offer(key);
                    }
                }
            }
            return key;
        }
    }

    /**
     * Maps the specified key to the specified value in this table.
     * The key can not be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public void putInt(String key, int value) {
        put(key, new IntegerEntry(value));
    }

    /**
     * Maps the specified key to the specified value in this table.
     * The key can not be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public void putBoolean(String key, boolean value) {
        put(key, new BooleanEntry(value));
    }

    /**
     * Maps the specified key to the specified value in this table.
     * The key can not be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public void putDouble(String key, double value) {
        put(key, new DoubleEntry(value));
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key or value is null
     */
    public void putString(String key, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Given null value");
        }

        put(key, new StringEntry(value));
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key or value is null
     */
    public void putSubTable(String key, NetworkTable value) {
        if (value == null) {
            throw new IllegalArgumentException("Given null value");
        }

        put(key, new Entry.TableEntry(value));
    }

    private void put(String field, Entry value) {
        if (field == null) {
            throw new IllegalArgumentException("Given null key");
        }

        synchronized (this) {
            Key key = getKey(field);
            value.setKey(key);

            if (transactionCount == 0) {
                got(true, key, value);
            } else {
                transaction.offer(value);
            }
        }
    }

    private void send(Entry entry) {
        synchronized (this) {
            if (isConnected()) {
                for (int i = 0; i < connections.size(); i++) {
                    Connection connection = (Connection) connections.get(i);
                    if (connection != entry.getSource()) {
                        connection.offer(entry);
                    }
                }
            }
        }
    }

    /**
     * Adds a NetworkListener to listen to the specified element.
     * @param key the key to listen to
     * @param listener the listener
     * @see NetworkListener
     */
    public void addListener(String key, NetworkListener listener) {
        synchronized (listenerLock) {
            if (listeners == null) {
                listeners = new Hashtable();
            }
            Set list = (Set) listeners.get(key);

            if (list == null) {
                list = new Set();
                listeners.put(key, list);
            }

            list.add(listener);
        }
    }

    public void addListenerToAll(NetworkListener listener) {
        synchronized (listenerLock) {
            if (listenToAllListeners == null) {
                listenToAllListeners = new Set();
            }
            listenToAllListeners.add(listener);
        }
    }

    /**
     * Removes the given NetworkListener from the specified element.
     * Note that setting the key to NetworkTable.ALL will only remove the listener
     * from listening in the all category.  It will not remove that listener from
     * every category it is listening to
     * @param key the key to no longer listen to (or NetworkTable.ALL).
     * @param listener the listener to remove
     * @see NetworkListener
     */
    public void removeListener(String key, NetworkListener listener) {
        synchronized (listenerLock) {
            if (listeners != null) {
                Set list = (Set) listeners.get(key);

                if (list != null) {
                    list.remove(listener);
                }
            }
        }
    }

    public void removeListenerFromAll(NetworkListener listener) {
        synchronized (listenerLock) {
            if (listenToAllListeners != null) {
                listenToAllListeners.remove(listener);
            }
        }
    }

    /**
     * Adds the NetworkAdditionListener to the table.
     * @param listener the listener to add
     * @see NetworkAdditionListener
     */
    public void addAdditionListener(NetworkAdditionListener listener) {
        synchronized (listenerLock) {
            if (additionListeners == null) {
                additionListeners = new Set();
            }
            additionListeners.add(listener);
        }
    }

    /**
     * Removes the given NetworkAdditionListener from the set of listeners.
     * @param listener the listener to remove
     * @see NetworkAdditionListener
     */
    public void removeAdditionListener(NetworkAdditionListener listener) {
        synchronized (listenerLock) {
            if (additionListeners != null) {
                additionListeners.remove(listener);
            }
        }
    }

    /**
     * Adds a NetworkConnectionListener to this table.
     * @param listener the listener to add
     * @param immediateNotify whether to tell the listener of the current connection status
     * @see NetworkConnectionListener
     */
    public void addConnectionListener(NetworkConnectionListener listener, boolean immediateNotify) {
        synchronized (listenerLock) {
            if (connectionListeners == null) {
                connectionListeners = new Set();
            }
            connectionListeners.add(listener);
            if (immediateNotify) {
                if (isConnected()) {
                    listener.connected();
                } else {
                    listener.disconnected();
                }
            }
        }
    }

    /**
     * Removes the given NetworkConnectionListener from the table.
     * @param listener the listener to remove
     * @see NetworkConnectionListener
     */
    public void removeConnectionListener(NetworkConnectionListener listener) {
        synchronized (listenerLock) {
            if (connectionListeners != null) {
                connectionListeners.remove(listener);
            }
        }
    }

    /**
     * Returns the value at the specified key.  It will be an object wrapper around the actual value.
     *
     * For instance, if there is a double at the position, this will return a Double.
     *
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the key is null
     */
    public Object getValue(String key) throws NetworkTableKeyNotDefined {
        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            return entry.getValue();
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not an int
     * @throws IllegalArgumentException if the key is null
     */
    public int getInt(String key) throws NetworkTableKeyNotDefined {
        if (key == null) {
            throw new IllegalArgumentException("given null key");
        }

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            if (entry instanceof IntegerEntry) {
                return entry.getInt();
            } else {
                throw new IllegalArgumentException("Value at \"" + key + "\" is not an int");
            }
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a double
     * @throws IllegalArgumentException if the key is null
     */
    public double getDouble(String key) throws NetworkTableKeyNotDefined {
        if (key == null) {
            throw new IllegalArgumentException("given null key");
        }

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            if (entry instanceof DoubleEntry) {
                return entry.getDouble();
            } else {
                throw new IllegalArgumentException("Value at \"" + key + "\" is not a double");
            }
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a string
     * @throws IllegalArgumentException if the key is null
     */
    public String getString(String key) throws NetworkTableKeyNotDefined {
        if (key == null) {
            throw new IllegalArgumentException("given null key");
        }

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            if (entry instanceof StringEntry) {
                return entry.getString();
            } else {
                throw new IllegalArgumentException("Value at \"" + key + "\" is not a string");
            }
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a string
     * @throws IllegalArgumentException if the key is null
     */
    public NetworkTable getSubTable(String key) throws NetworkTableKeyNotDefined {
        if (key == null) {
            throw new IllegalArgumentException("given null key");
        }

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            if (entry instanceof Entry.TableEntry) {
                return entry.getTable();
            } else {
                throw new IllegalArgumentException("Value at \"" + key + "\" is not a table");
            }
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a boolean
     * @throws IllegalArgumentException if the key is null
     */
    public boolean getBoolean(String key) throws NetworkTableKeyNotDefined {
        if (key == null) {
            throw new IllegalArgumentException("given null key");
        }

        Entry entry = getEntry(key);

        if (entry == null) {
            throw new NetworkTableKeyNotDefined();
        } else {
            if (entry instanceof BooleanEntry) {
                return entry.getBoolean();
            } else {
                throw new IllegalArgumentException("Value at \"" + key + "\" is not a boolean");
            }
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue The value returned if the key is undefined
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not an int
     * @throws IllegalArgumentException if the key is null
     */
    public int getInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue  the value returned if the key is undefined
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a double
     * @throws IllegalArgumentException if the key is null
     */
    public double getDouble(String key, double defaultValue) {
        try {
            return getDouble(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue  the value returned if the key is undefined
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a string
     * @throws IllegalArgumentException if the key is null
     */
    public String getString(String key, String defaultValue) {
        try {
            return getString(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a boolean
     * @throws IllegalArgumentException if the key is null
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return getBoolean(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

    /**
     * Returns whether or not this table is connected to the robot.
     * @return whether or not this table is connected to the robot
     */
    public boolean isConnected() {
        return connections != null && connections.size() > 0;
    }

    /**
     * Returns whether or not there is a value mapped to the given key
     * @param key the key
     * @return if there is a value
     */
    public boolean containsKey(String key) {
        synchronized (this) {
            Key k = (Key) data.get(key);
            return k != null && k.hasEntry();
        }
    }

    /**
     * Internally used to get at the underlying Entry
     * @param key the key
     * @return the entry at that position (or null if no entry)
     */
    Entry getEntry(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Can not be given null");
        }
        synchronized (this) {
            Key k = (Key) data.get(key);
            return k == null ? null : k.getEntry();
        }
    }

    /**
     * This method should be called by children when they want to add a new value.
     * It will notify listeners of the value
     * @param confirmed whether or not this value was confirmed or received
     * @param key the key
     * @param value the value
     */
    void got(boolean confirmed, Key key, Entry value) {
        Entry old;
        synchronized (this) {
            old = key.setEntry(value);
        }

        if (value.equals(old)) {
            return;
        }

        send(value);

        alertListeners(old == null, confirmed, key.getName(), value);
    }

    private void alertListeners(boolean isNew, boolean confirmed, String key, Entry value) {
        synchronized (listenerLock) {

            if (isNew && additionListeners != null) {
                for (int i = 0; i < additionListeners.size(); i++) {
                    ((NetworkAdditionListener) additionListeners.get(i)).fieldAdded(key, value.getValue());
                }
            }

            Set list = null;

            if (listeners != null) {
                list = (Set) listeners.get(key);
            }

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    NetworkListener listener = (NetworkListener) list.get(i);
                    if (confirmed) {
                        listener.valueConfirmed(key, value.getValue());
                    } else {
                        listener.valueChanged(key, value.getValue());
                    }
                }
            }

            list = listenToAllListeners;

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    NetworkListener listener = (NetworkListener) list.get(i);
                    if (confirmed) {
                        listener.valueConfirmed(key, value.getValue());
                    } else {
                        listener.valueChanged(key, value.getValue());
                    }
                }
            }
        }
    }

    void encodeName(Buffer buffer) {
        buffer.writeTableId(id.intValue());
    }
}
