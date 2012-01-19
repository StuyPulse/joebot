/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.util.Hashtable;

/**
 * The Key class represents a single field in a NetworkTable.
 * @author Joe Grinstead
 */
class Key implements Data {


    private static Hashtable ids = new Hashtable();
    private static int currentId = 0;

    private synchronized static Integer grabId() {
        return new Integer(currentId++);
    }

    static Key getKey(Integer id) {
        return (Key) ids.get(id);
    }

    /** The table this field belongs to */
    private final NetworkTable table;
    /** The key of this field */
    private final String key;
    /** The Entry in this field */
    private Entry entry;
    /** The id of this field */
    private final Integer id;

    /**
     * Creates a NetworkingField within the given table for the given key.
     * @param table the table
     * @param key the key
     */
    public Key(NetworkTable table, String key) {
        this.table = table;
        this.key = key;
        this.id = grabId();

        table.addConnectionListener(new NetworkConnectionListener() {

            public void connected() {
                ids.put(id, Key.this);
            }

            public void disconnected() {
                ids.remove(Key.this);
            }
        }, true);
    }

    public NetworkTable getTable() {
        return table;
    }

    /**
     * Returns the type of data stored in this field.
     * @return the type of data stored in this field
     */
    public synchronized int getType() {
        return entry == null ? NetworkTypes.NONE : entry.getType();
    }

    /**
     * Returns the entry of this field.
     * @return the entry of this field
     */
    public synchronized Entry getEntry() {
        return entry;
    }

    /**
     * Returns the name of the field.
     * @return the name of the field
     */
    public String getName() {
        return key;
    }

    /**
     * Sets the entry of this field.
     * Will alert the listeners.
     * @param entry the new entry
     */
    synchronized Entry setEntry(Entry entry) {
        Entry old = this.entry;
        this.entry = entry;
        this.entry.setKey(this);
        return old;
    }

    boolean hasEntry() {
        return entry != null;
    }

    Integer getId() {
        return id;
    }

    void encodeName(Buffer buffer) {
        buffer.writeId(id.intValue());
    }

    // This encodes the assignment
    public void encode(Buffer buffer) {
        buffer.writeByte(Data.ASSIGNMENT);
        table.encodeName(buffer);
        buffer.writeString(key);
        buffer.writeId(id.intValue());
    }

    public String toString() {
        return "[(" + table.getId() + ") \"" + getName() + "\"->" + entry + "]";
    }
}
