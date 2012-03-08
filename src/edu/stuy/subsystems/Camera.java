/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Yulli
 */
public class Camera extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Servo camServo;
    
    public Camera(){
       camServo = new Servo(2, RobotMap.CAMERA_SERVO); 
       
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void switchView(boolean down){
        if(down){
            camServo.setAngle(0); // TODO: find out which angle is what
        }
        else {
            camServo.setAngle(180);
        }
        System.out.println(camServo.getAngle());
    }
    
    
}
