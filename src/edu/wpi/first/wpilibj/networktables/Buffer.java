/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is just an array of bytes with an adjustable size but fixed capacity.
 * It is NOT thread safe.
 * @author Joe Grinstead
 */
class Buffer {

    /** The internal buffer */
    private byte[] buffer;
    /** The number of bytes currently in buffer */
    private int size;
    /** The output stream this writes to */
    private OutputStream stream;

    public Buffer(int capacity, OutputStream output) {
        buffer = new byte[capacity];
        stream = output;
    }

    public void writeString(String entry) {
        if (entry.length() >= Data.BEGIN_STRING) {
            writeByte(Data.BEGIN_STRING);
            writeBytes(entry.getBytes());
            writeByte(Data.END_STRING);
        } else {
            writeByte(entry.length());
            writeBytes(entry.getBytes());
        }
    }

    public void writeDouble(double entry) {
        long l = Double.doubleToLongBits(entry);
        writeByte((int) ((l >> 56) & 0xff));
        writeByte((int) ((l >> 48) & 0xff));
        writeByte((int) ((l >> 40) & 0xff));
        writeByte((int) ((l >> 32) & 0xff));
        writeByte((int) ((l >> 24) & 0xff));
        writeByte((int) ((l >> 16) & 0xff));
        writeByte((int) ((l >> 8) & 0xff));
        writeByte((int) (l & 0xff));
    }

    public void writeInt(int entry) {
        writeByte((entry >> 24) & 0xff);
        writeByte((entry >> 16) & 0xff);
        writeByte((entry >> 8) & 0xff);
        writeByte(entry & 0xff);
    }

    public void writeId(int id) {
        writeVariableSize(Data.ID, id);
    }

    public void writeTableId(int id) {
        writeVariableSize(Data.TABLE_ID, id);
    }

    private void writeVariableSize(int tag, int id) {
        if (id < tag - 4) {
            writeByte(tag | id);
        } else {
            int fullTag = (tag | (tag - 1)) ^ 3;
            if (id < (1 << 8)) {
                writeByte(fullTag);
                writeByte(id);
            } else if (id < (1 << 16)) {
                writeByte(fullTag | 1);
                writeByte(id >> 8);
                writeByte(id);
            } else if (id < (1 << 24)) {
                writeByte(fullTag | 2);
                writeByte(id >> 16);
                writeByte(id >> 8);
                writeByte(id);
            } else {
                writeByte(fullTag | 3);
                writeByte(id >> 24);
                writeByte(id >> 16);
                writeByte(id >> 8);
                writeByte(id);
            }
        }
    }

    public void writeBytes(byte[] entries) {
        for (int i = 0; i < entries.length; i++) {
            writeByte(entries[i]);
        }
    }

    public void writeByte(int entry) {
        if (size >= buffer.length) {
            throw new BufferOverflowException("Attempted to write beyond the " + buffer.length + " byte limit.");
        }
        buffer[size++] = (byte) entry;
    }

    public void flush() throws IOException {
        stream.write(buffer, 0, size);
        stream.flush();
        clear();
    }

    public void clear() {
        size = 0;
    }

    public String toString() {
        String string = "[";
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string += "-";
            }
            string += buffer[i] & 0xff;
        }
        return string + "]";
    }

    public static class BufferOverflowException extends IndexOutOfBoundsException {

        public BufferOverflowException(String message) {
            super(message);
        }
    }
}
