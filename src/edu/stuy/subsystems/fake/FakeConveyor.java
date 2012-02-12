/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.stuy.subsystems.*;
import edu.stuy.RobotMap;
import edu.stuy.speed.VictorSpeed;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Danny
 */
public class FakeConveyor extends Subsystem {


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public FakeConveyor() {
 
    }
    
    public void roll(double upperSpeed, double lowerSpeed) {
   
    }

    public void convey() {
     
    }

    public void conveyReverse() {
   
    }

    public void stop() {
        System.out.println("Conveyor stop");
      
    }

    public boolean ballAtTop() {
        return false;
    }

    public boolean ballAtBottom() {
        return false;
    }

    public double getUpperRoller() {
        return 0;
    }

    public double getLowerRoller() {
        return 0;
    }
}
