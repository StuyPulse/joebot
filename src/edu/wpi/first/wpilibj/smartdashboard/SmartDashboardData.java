/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Anything that implements the {@link SmartDashboardData} interface can be sent
 * to the SmartDashboard using {@link SmartDashboard#putData(java.lang.String, edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData) putData(...)}.
 *
 * <p>Examples include {@link SendablePIDController}, {@link SendableGyro} and {@link SendableChooser}.</p>
 *
 * <p>{@link SmartDashboardData} works by having an internal {@link NetworkTable} that it should both
 * keep up-to-date and respond to.  Also, {@link SmartDashboardData} requires a string to identify what type it is 
 * (ie. "Gyro", "PIDController", "Command", "Subsystem", etc...)</p>
 *
 * <p>Users can define their own {@link SmartDashboardData}, but they will have to also make an extension to the SmartDashboard
 * which can handle their new type</p>
 * @author Joe Grinstead
 * @see SmartDashboard
 */
public interface SmartDashboardData {

    /**
     * Returns the type of the data.
     *
     * This is used by the SmartDashboard on the desktop to determine
     * what to do with the table returned by {@link SmartDashboardData#getTable() getTable()}.
     * 
     * <p>For instance, if the type was "Button", then the SmartDashboard would show the data
     * as a button on the desktop and would know to look at and modify the "pressed" field in the
     * {@link NetworkTable} returned by {@link SmartDashboardData#getTable() getTable()}.</p>
     *
     * @return the type of the data
     */
    public String getType();

    /**
     * Returns the {@link NetworkTable} associated with the data.
     *
     * The table should contain all the information the desktop version of SmartDashboard needs
     * to interact with the object.  The data should both keep the table up-to-date and also react
     * to changes that the SmartDashboard might make.
     * 
     * <p>For instance, the {@link SendablePIDController} will put its p, i and d values into its table
     * and will change them if the table receives new values.</p>
     *
     * <p>This method should return the same table between calls</p>
     *
     * @return the table that represents this data
     */
    public NetworkTable getTable();
}
