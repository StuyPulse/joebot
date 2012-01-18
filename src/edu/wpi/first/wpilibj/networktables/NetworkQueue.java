/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import edu.wpi.first.wpilibj.networktables.LinkedList.Link;
import java.util.Hashtable;

/**
 * This class is a queue filled with key value pairs
 * and will only contain the most recent value for any key.

 * @author Joe Grinstead
 */
class NetworkQueue {

    /** Maps a key to the last linked list element in the queue with that key */
    Hashtable table = new Hashtable();
    /** An ordered linked list of things in the queue */
    LinkedList queue = new LinkedList();
    private boolean inTransaction = false;

    synchronized void offer(Data value) {
        if (value instanceof Entry) {
            if (inTransaction) {
                queue.add(value);
            } else {
                Entry entry = (Entry) value;
                if (table.containsKey(entry.getKey())) {
                    ((Link) table.get(entry.getKey())).data = entry;
                } else {
                    table.put(entry.getKey(), queue.add(entry));
                }
            }
        } else if (value instanceof Confirmation) {
            if (!queue.isEmpty() && queue.last.data instanceof Confirmation) {
                queue.last.data = Confirmation.combine((Confirmation) queue.last.data, (Confirmation) value);
            } else {
                queue.add(value);
            }
        } else if (value instanceof Denial) {
            if (!queue.isEmpty() && queue.last.data instanceof Denial) {
                queue.last.data = Denial.combine((Denial) queue.last.data, (Denial) value);
            } else {
                queue.add(value);
            }
        } else if (value == Data.TRANSACTION_START) {
            inTransaction = true;
            queue.add(value);
        } else if (value == Data.TRANSACTION_END) {
            inTransaction = false;
            queue.add(value);
        } else {
            queue.add(value);
        }
    }

    synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    synchronized boolean containsKey(Key key) {
        return key == null ? false : table.containsKey(key);
    }

    synchronized Data poll() {
        if (isEmpty()) {
            return null;
        }
        Link link = queue.first;
        Data data = (Data) link.data;
        if (data instanceof Entry) {
            table.remove(((Entry) data).getKey());
        }
        link.detach();
        return data;
    }

    synchronized void clear() {
        queue.clear();
        table.clear();
    }

    synchronized Data peek() {
        if (isEmpty()) {
            return null;
        }
        return (Data) queue.first.data;
    }
}
