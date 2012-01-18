/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;

/**
 * abstract class for making a common API for the jaguars and the victors
 * @author admin
 */
public interface JoeSpeed {
    final double ENCODER_CODES_PER_REV = 360.0;
    final double ENCODER_RPM_PER_PULSE = 60 / ENCODER_CODES_PER_REV;
    int getRPM ();
    void setRPM (int rpm);
}
