/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Jaguar motor controller with speed methods
 * @author admin
 */
public class JaguarSpeed implements JoeSpeed{
    CANJaguar jaguar;
    public JaguarSpeed (int id) {
        jaguar = new CANJaguar(id,CANJaguar.ControlMode.kSpeed);
        jaguar.configEncoderCodesPerRev(ENCODER_RPM_PER_REV);
        jaguar.enableControl();
    } 
    public double getRPM() {
        return jaguar.getSpeed();
    }

    public void setRPM(int rpm) {
        
    }
    
    
}
