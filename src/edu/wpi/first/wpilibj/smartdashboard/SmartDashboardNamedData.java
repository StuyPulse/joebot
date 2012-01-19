/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

/**
 * This class is a minor extension to {@link SmartDashboardData} that describes a {@link SmartDashboardNamedData#getName() getName()}
 * method that allows the {@link SmartDashboard} class to automatically assign the field of the data in its
 * {@link SmartDashboard#putData(edu.wpi.first.wpilibj.smartdashboard.SmartDashboardNamedData) putData(...)} method.
 * @author Joe Grinstead
 */
public interface SmartDashboardNamedData extends SmartDashboardData {

    /**
     * Returns the name of this object.
     * @return the name of this object
     */
    public String getName();
}
