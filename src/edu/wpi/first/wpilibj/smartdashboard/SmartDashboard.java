/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * The {@link SmartDashboard} class is the bridge between robot programs and the SmartDashboard on the
 * laptop.
 *
 * <p>When a value is put into the SmartDashboard here, it pops up on the SmartDashboard on the laptop.
 * Users can put values into and get values from the SmartDashboard</p>
 * 
 * @author Joe Grinstead
 */
public class SmartDashboard {

    /** The {@link NetworkTable} used by {@link SmartDashboard} */
    private static final NetworkTable table = NetworkTable.getTable("SmartDashboard");
    /** 
     * A table linking tables in the SmartDashboard to the {@link SmartDashboardData} objects
     * they came from.
     */
    private static final Hashtable tablesToData = new Hashtable();

    /**
     * Maps the specified key to the specified value in this table.
     * The key can not be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public static void putData(String key, SmartDashboardData value) {
        NetworkTable type = new NetworkTable();
        type.putString("~TYPE~", value.getType());
        type.putSubTable("Data", value.getTable());
        table.putSubTable(key, type);
        tablesToData.put(type, key);
    }

    /**
     * Maps the specified key (where the key is the name of the {@link SmartDashboardNamedData}
     * to the specified value in this table.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public static void putData(SmartDashboardNamedData value) {
        putData(value.getName(), value);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a {@link SmartDashboardData}
     * @throws IllegalArgumentException if the key is null
     */
    public static SmartDashboardData getData(String key) throws NetworkTableKeyNotDefined {
        NetworkTable subtable = table.getSubTable(key);
        Object data = tablesToData.get(subtable);
        if (data == null) {
            throw new IllegalArgumentException("Value at \"" + key + "\" is not a boolean");
        } else {
            return (SmartDashboardData) data;
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
    public static void putBoolean(String key, boolean value) {
        table.putBoolean(key, value);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a boolean
     * @throws IllegalArgumentException if the key is null
     */
    public static boolean getBoolean(String key) throws NetworkTableKeyNotDefined {
        return table.getBoolean(key);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue returned if the key doesn't exist
     * @return the value
     * @throws IllegalArgumentException if the value mapped to by the key is not a boolean
     * @throws IllegalArgumentException if the key is null
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return table.getBoolean(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
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
    public static void putInt(String key, int value) {
        table.putInt(key, value);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not an int
     * @throws IllegalArgumentException if the key is null
     */
    public static int getInt(String key) throws NetworkTableKeyNotDefined {
        return table.getInt(key);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue the value returned if the key is undefined
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not an int
     * @throws IllegalArgumentException if the key is null
     */
    public static int getInt(String key, int defaultValue) {
        try {
            return table.getInt(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
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
    public static void putDouble(String key, double value) {
        table.putDouble(key, value);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NoSuchEleNetworkTableKeyNotDefinedmentException if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a double
     * @throws IllegalArgumentException if the key is null
     */
    public static double getDouble(String key) throws NetworkTableKeyNotDefined {
        return table.getDouble(key);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue the value returned if the key is undefined
     * @return the value
     * @throws NoSuchEleNetworkTableKeyNotDefinedmentException if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a double
     * @throws IllegalArgumentException if the key is null
     */
    public static double getDouble(String key, double defaultValue) {
        try {
            return table.getDouble(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     * The value can be retrieved by calling the get method with a key that is equal to the original key.
     * @param key the key
     * @param value the value
     * @throws IllegalArgumentException if key or value is null
     */
    public static void putString(String key, String value) {
        table.putString(key, value);
    }

    /**
     * Returns the value at the specified key.
     * @param key the key
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a string
     * @throws IllegalArgumentException if the key is null
     */
    public static String getString(String key) throws NetworkTableKeyNotDefined {
        return table.getString(key);
    }

        /**
     * Returns the value at the specified key.
     * @param key the key
     * @param defaultValue  The value returned if the key is undefined
     * @return the value
     * @throws NetworkTableKeyNotDefined if there is no value mapped to by the key
     * @throws IllegalArgumentException if the value mapped to by the key is not a string
     * @throws IllegalArgumentException if the key is null
     */
    public static String getString(String key, String defaultValue) {
        try {
            return table.getString(key);
        } catch (NetworkTableKeyNotDefined ex) {
            return defaultValue;
        }
    }

     /**
     * Initializes the SmartDashboard by injecting it into the DriverStation
     * class such that it is polled for data instead of the default
     * Dashboard instance.
     *
     * This must be called before using any static method of
     * SmartDashboard for the data to be sent across the network.
      *
      * @deprecated no longer necessary
     */
    public static void init() {
    }

    /**
     * Send the given byte value to the client as the field with the given name.
     * @param value The value to be displayed on the client.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)}
     */
    public static int log(byte value, String name) {
        putInt(name, value);
        return 0;
    }

    /**
     * Send the given UTF-16 char value to the client as the field with the given name.
     * @param value The value to be displayed on the client.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)}
     */
    public static int log(char value, String name) {
        putInt(name, value);
        return 0;
    }

    /**
     * Sends the given int value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)}
     */
    public static int log(int value, String name) {
        putInt(name, value);
        return 0;
    }


    /**
     * Sends the given long value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)}
     */
    public static int log(long value, String name) {
        putInt(name, (int) value);
        return 0;
    }


    /**
     * Sends the given short value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)}
     */
    public static int log(short value, String name) {
        putInt(name, value);
        return 0;
    }

    /**
     * Sends the given float value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putDouble(java.lang.String, double)}
     */
    public static int log(float value, String name) {
        putDouble(name, value);
        return 0;
    }

     /**
     * Sends the given double value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
      * @deprecated use {@link SmartDashboard#putDouble(java.lang.String, double)}
     */
    public static int log(double value, String name) {
        putDouble(name, value);
        return 0;
    }

    /**
     * Sends the given String value to the client as the field with the given name.
     * @param value The value to send. This may be at most 63 characters in length.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putString(java.lang.String, java.lang.String)}
     */
    public static int log(String value, String name) {
        putString(name, value);
        return 0;
    }

    /**
     * Sends the given boolean value to the client as the field with the given name.
     * @param value The value to send.
     * @param name The name of the field.
     * @return An integer status code.
     * @deprecated use {@link SmartDashboard#putInt(java.lang.String, int)} instead
     */
    public static int log(boolean value, String name) {
        putBoolean(name, value);
        return 0;
    }

    /**
     * Does Nothing.  This has no equivalent in the new SmartDashboard.
     * @deprecated 
     */
    public static int useProfile(String name) {
        return 0;
    }

    /**
     * Does nothing. There is no equivalent in the new SmartDashboard
     * @deprecated
     */
    public static String diagnoseErrorCode(int code) {
        return "diagnoseErrorCode(int code) is deprecated";
    }

    /**
     * Does nothing.  There is no equivalent in the new SmartDashboard
     * @deprecated
     */
    public static void useBlockingIO(boolean value) {
    }
}
