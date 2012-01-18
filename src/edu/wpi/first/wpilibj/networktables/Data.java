/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

/**
 * Data defines the interface for everything which can be sent over the network.
 * @author Joe Grinstead
 */
interface Data {

    public static final Data TRANSACTION_START = new Data() {

        public void encode(Buffer buffer) {
            buffer.writeByte(TRANSACTION);
        }
    };

    public static final Data TRANSACTION_END = new Data() {

        public void encode(Buffer buffer) {
            buffer.writeByte(TRANSACTION);
        }
    };

    public void encode(Buffer buffer);

    // Important Constants
    public static final int STRING = 0;
    public static final int BEGIN_STRING = 0xff;
    public static final int END_STRING = 0;
    public static final int INT = 1;
    public static final int DOUBLE = 2;
    public static final int TABLE = 3;
    public static final int TABLE_ASSIGNMENT = TABLE;
    public static final int BOOLEAN_FALSE = 4;
    public static final int BOOLEAN_TRUE = 5;
    public static final int ASSIGNMENT = 6;
    public static final int EMPTY = 7;
    public static final int DATA = 8;
    public static final int OLD_DATA = 9;
    public static final int TRANSACTION = 10;
    public static final int REMOVAL = 11;
    public static final int TABLE_REQUEST = 12;
    public static final int ID = 1 << 7;
    public static final int TABLE_ID = 1 << 6;
    public static final int CONFIRMATION = 1 << 5;
    public static final int CONFIRMATION_MAX = CONFIRMATION - 1;
    public static final int PING = CONFIRMATION;
    public static final int DENIAL = 1 << 4;
}
