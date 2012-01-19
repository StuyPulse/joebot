/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

/**
 * This interface allows for PIDController to automatically read from this
 * object
 * @author dtjones
 */
public interface PIDSource {

    /**
     * Get the result to use in PIDController
     * @return the result to use in PIDController
     */
    public double pidGet();
}
