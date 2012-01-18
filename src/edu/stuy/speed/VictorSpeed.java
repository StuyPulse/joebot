/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.speed;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Prog
 */
public class VictorSpeed implements JoeSpeed {
    private static final double KP = 0.0;
    private static final double KI = 0.0;
    private static final double KD = 0.0;

    private Victor victor;
    private Encoder encoder;
    private PIDController controller;

    public VictorSpeed(int victorPort, int encoderA, int encoderB) {
        victor = new Victor(victorPort);

        encoder = new Encoder(encoderA, encoderB);

        controller = new PIDController(KP, KI, KD, encoder, victor);


    }

    public int getRPM() {
    }

    public void setRPM(int rpm) {
    }

}
