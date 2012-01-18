/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import java.util.Hashtable;

/**
 * The {@link SendableChooser} class is a useful tool for presenting a selection of options
 * to the {@link SmartDashboard}.
 *
 * <p>For instance, you may wish to be able to select between multiple autonomous modes.
 * You can do this by putting every possible {@link Command} you want to run as an autonomous into
 * a {@link SendableChooser} and then put it into the {@link SmartDashboard} to have a list of options
 * appear on the laptop.  Once autonomous starts, simply ask the {@link SendableChooser} what the selected
 * value is.</p>
 *
 * @author Joe Grinstead
 */
public class SendableChooser implements SmartDashboardData {

    /** The key for the default value */
    private static final String DEFAULT = "default";
    /** The key for the number of options */
    private static final String COUNT = "count";
    /** The key for the selected option */
    private static final String SELECTED = "selected";

    /** The default choice */
    private Object defaultChoice;
    /** A table linking strings to the objects the represent */
    private Hashtable choices;
    /** A table linking objects to their representative strings */
    private Hashtable ids;
    /** The {@link NetworkTable} used internally */
    private NetworkTable table;
    /** The number of items in the table */
    private int count = 0;

    /**
     * Instantiates a {@link SendableChooser}.
     */
    public SendableChooser() {
        table = new NetworkTable();
        choices = new Hashtable();
        ids = new Hashtable();
    }

    /**
     * Adds the given object to the list of options.  On the {@link SmartDashboard} on the desktop,
     * the object will appear as the given name.
     * @param name the name of the option
     * @param object the option
     */
    public void addObject(String name, Object object) {
        Object last = choices.put(name, object);
        if (last == null) {
            String id = String.valueOf(count);
            ids.put(object, id);
            table.putString(id, name);
            table.putInt(COUNT, ++count);
        } else {
            String id = (String) ids.get(last);
            ids.remove(last);
            ids.put(object, id);
            table.putString(id, name);
        }
    }

    /**
     * Add the given object to the list of options and marks it as the default.
     * Functionally, this is very close to {@link SendableChooser#addObject(java.lang.String, java.lang.Object) addObject(...)}
     * except that it will use this as the default option if none other is explicitly selected.
     * @param name the name of the option
     * @param object the option
     */
    public void addDefault(String name, Object object) {
        defaultChoice = object;
        addObject(name, object);
        table.putString(DEFAULT, name);
    }

    /**
     * Returns the selected option.  If there is none selected, it will return the default.  If there is none selected
     * and no default, then it will return {@code null}.
     * @return the option selected
     */
    public Object getSelected() {
        try {
            return table.containsKey(SELECTED) ? choices.get(table.getString(SELECTED)) : defaultChoice;
        } catch (NetworkTableKeyNotDefined ex) {
            return null;
        }
    }

    public String getType() {
        return "String Chooser";
    }

    public NetworkTable getTable() {
        return table;
    }
}
