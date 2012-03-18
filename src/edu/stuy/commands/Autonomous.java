/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Kevin Wang
 */
public class Autonomous extends CommandGroup {

    public static final double FENDER_DEPTH = 38.5;

    public static final double CONVEY_AUTO_TIME = 6;



    //TODO: update with testing
    //Time to delay is [(distance to travel)/rate + 0.2] * 1.25
    // 0.2 is a guess for the amount of time to reach max speed
    // 1.25:  add 25% more time to account for friction
    //    drive train speed is based on free speed; friction will make it slower
    
    // distance is ~50" or ~4', rate is ~10 ft/s. Time is 0.4s, Your Mileage May Vary
    //public static final double t_farKeyToBridge = 0.75;
    public static final double t_farKeyToBridge = 2.0; // lower speed, try this (untested)

    // distance is ~136" or ~12', rate is 10 ft/s. Time is 1.2s, Your Mileage May Vary
    public static final double t_closeKeyToBridge = 5.0;  // lower speed, try this (untested)


    public Autonomous() {
        addSequential(new DrivetrainSetGear(true));
        addSequential(new WaitCommand(CommandBase.oi.getDelayTime()));
        switch (CommandBase.oi.getAutonSetting()) {
            case 0:
                addSequential(new AutonSetting0());
                break;
            case 1:
                addSequential(new AutonSetting1());
                break;
            case 2:
                addSequential(new AutonSetting2());
                break;
            case 3:
                addSequential(new AutonSetting3());
                break;
            case 4:
                addSequential(new AutonSetting4());
                break;
            case 5:
                addSequential(new AutonSetting5());
                break;
            default:
                break;
        }
    }
}
