/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.io.IOException;
import java.io.InputStream;

/**
 * The reader class provides a wrapper around an InputStream.
 * The InputStream must be called in an expected manner.
 * This is NOT thread safe.
 * @author Joe Grinstead
 */
class Reader {

    /** The input stream */
    private InputStream stream;
    /** The last read value (-2 if nothing has been read) */
    private int lastByte;

    public Reader(InputStream input) {
        stream = input;
        lastByte = -2;
    }

    public int read() throws IOException {
        lastByte = stream.read();
        if (lastByte == -1) {
            throw new IOException("End Of Stream");
        }
        return lastByte;
    }

    private int check(boolean useLastValue) throws IOException {
        return useLastValue ? lastByte : read();
    }

    public String readString() throws IOException {
        read();
        StringBuffer buffer;

        if (lastByte == Data.BEGIN_STRING) {
            buffer = new StringBuffer(360);
            while (read() != Data.END_STRING) {
                buffer.append((char) lastByte);
            }
        } else {
            int length = lastByte;
            buffer = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                buffer.append((char) read());
            }
        }

        return buffer.toString();
    }

    public Integer readId(boolean useLastValue) throws IOException {
        return readVariableSize(useLastValue, Data.ID);
    }

    public Integer readTableId(boolean useLastValue) throws IOException {
        return readVariableSize(useLastValue, Data.TABLE_ID);
    }

    private Integer readVariableSize(boolean useLastValue, int tag) throws IOException {
        int value = check(useLastValue);
        value ^= tag;
        if (value < tag - 4) {
            return new Integer(value);
        } else {
            int bytes = (value & 3) + 1;
            int id = 0;
            for (int i = 0; i < bytes; i++) {
                id = (id << 8) | read();
            }
            return new Integer(id);
        }
    }

    public int readInt() throws IOException {
        return (read() << 24) | (read() << 16) | (read() << 8) | read();
    }

    public double readDouble() throws IOException {
        long l = read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        l = (l << 8) | read();
        return Double.longBitsToDouble(l);
    }

    public int readConfirmations(boolean useLastValue) throws IOException {
        return check(useLastValue) ^ Data.CONFIRMATION;
    }

    public int readDenials(boolean useLastValue) throws IOException {
        return check(useLastValue) ^ Data.DENIAL;
    }

    public Entry readEntry(boolean useLastValue) throws IOException {
        switch (check(useLastValue)) {
            case Data.BOOLEAN_FALSE:
                return new Entry.BooleanEntry(false);
            case Data.BOOLEAN_TRUE:
                return new Entry.BooleanEntry(true);
            case Data.INT:
                return new Entry.IntegerEntry(readInt());
            case Data.DOUBLE:
                return new Entry.DoubleEntry(readDouble());
            case Data.STRING:
                return new Entry.StringEntry(readString());
            default:
                return null;
        }
    }
}
