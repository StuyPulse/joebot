 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;


/**
 * abstract class for making a common API for the Jaguars and the victors
 * @author admin
 */
public interface JoeSpeed {
    final int ENCODER_CODES_PER_REV    = 360;
    final double ENCODER_RPM_PER_PULSE = 60.0 / ENCODER_CODES_PER_REV;
    final double ROLLER_RADIUS         = 2.375;

    /**
     * Returns the RPM of the speed controller.
     * @return RPM of the speed controller
     */
    double getRPM();

    /**
     * Sets the target RPM of the speed controller.
     * @param rpm The target RPM of the speed controller
     */
    void setRPM(double rpm);

    /**
     * Checks if the current RPM is within a certain
     * range of the desired RPM.
     * @return atSetPoint
     */
    boolean isAtSetPoint();

    public void setPID(String prefix);

}
