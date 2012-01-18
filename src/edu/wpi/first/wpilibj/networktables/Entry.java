/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

/**
 * This class locks in well with the NetworkTable class.
 * It is used to contain data.
 * @author Joe
 */
abstract class Entry implements Data {

    /** The Key that this belongs to */
    private Key key;
    /** The source of this entry (or null if from Robot) */
    private Connection source;

    private Object value;

    /**
     * Sets the key for this entry
     * @param key the key for this entry
     */
    void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the key for this object (note that multiple entries are
     * allowed to reference the same key, but only one entry in the key).
     * @return the key
     */
    Key getKey() {
        return key;
    }


    abstract int getType();

    /**
     * Sets the source of this Entry
     * @param source the source of this Entry
     */
    void setSource(Connection source) {
        this.source = source;
    }

    /**
     * Returns the source of this entry (or null if from Robot).
     * @return the source of this entry (or null if from Robot)
     */
    Connection getSource() {
        return source;
    }

    /**
     * Returns the value for this object.
     * @return the value
     */
    abstract Object grabValue();

    public Object getValue() {
        return value == null ? value = grabValue() : value;
    }

    public void encode(Buffer buffer) {
        getKey().encodeName(buffer);
    }

    int getInt() {
        throw new UnsupportedOperationException();
    }

    double getDouble() {
        throw new UnsupportedOperationException();
    }

    boolean getBoolean() {
        throw new UnsupportedOperationException();
    }

    String getString() {
        throw new UnsupportedOperationException();
    }

    NetworkTable getTable() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        return obj instanceof Entry && grabValue().equals(((Entry) obj).grabValue());
    }

    public int hashCode() {
        return grabValue().hashCode();
    }

    static class DoubleEntry extends Entry {

        private double value;

        public DoubleEntry(double value) {
            this.value = value;
        }

        Object grabValue() {
            return new Double(value);
        }

        double getDouble() {
            return value;
        }

        int getType() {
            return NetworkTypes.DOUBLE;
        }

        public void encode(Buffer buffer) {
            super.encode(buffer);
            buffer.writeByte(Data.DOUBLE);
            buffer.writeDouble(value);
        }

        public String toString() {
            return "[Double:" + value + "]";
        }
    }

    static class IntegerEntry extends Entry {

        private int value;

        public IntegerEntry(int value) {
            this.value = value;
        }

        Object grabValue() {
            return new Integer(value);
        }

        int getInt() {
            return value;
        }

        int getType() {
            return NetworkTypes.INT;
        }

        public void encode(Buffer buffer) {
            super.encode(buffer);
            buffer.writeByte(Data.INT);
            buffer.writeInt(value);
        }

        public String toString() {
            return "[int:" + value + "]";
        }
    }

    static class BooleanEntry extends Entry {

        private boolean value;

        public BooleanEntry(boolean value) {
            this.value = value;
        }

        Object grabValue() {
            return value ? Boolean.TRUE : Boolean.FALSE;
        }

        boolean getBoolean() {
            return value;
        }

        int getType() {
            return NetworkTypes.BOOLEAN;
        }

        public void encode(Buffer buffer) {
            super.encode(buffer);
            buffer.writeByte(value ? Data.BOOLEAN_TRUE : Data.BOOLEAN_FALSE);
        }

        public String toString() {
            return "[boolean:" + value + "]";
        }
    }

    static class StringEntry extends Entry {

        private String value;

        public StringEntry(String value) {
            this.value = value;
        }

        Object grabValue() {
            return value;
        }

        String getString() {
            return value;
        }

        int getType() {
            return NetworkTypes.STRING;
        }

        public void encode(Buffer buffer) {
            super.encode(buffer);
            buffer.writeByte(Data.STRING);
            buffer.writeString(value);
        }

        public String toString() {
            return "[String:" + value + "]";
        }
    }

    static class TableEntry extends Entry {

        private NetworkTable value;

        public TableEntry(NetworkTable value) {
            this.value = value;
        }

        Object grabValue() {
            return value;
        }

        NetworkTable getTable() {
            return value;
        }

        int getType() {
            return NetworkTypes.TABLE;
        }

        public void encode(Buffer buffer) {
            super.encode(buffer);
            value.encodeName(buffer);
        }

        public String toString() {
            return "[Table]";
        }
    }

    public static class UnsupportedOperationException extends RuntimeException {
        
    }
}
