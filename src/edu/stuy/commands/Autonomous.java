/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.stuy.commands.CommandBase;

/**
 *
 * @author Kevin Wang
 */
public class Autonomous extends CommandGroup {

    /* For inches to fender */
    private static final int DIST_FENDER_TO_KEY = 110;
    private static final double BUMPER_EDGE_TO_WHEEL_CENTER = 9.5;
    private static final int BOT_LENGTH_WITH_BUMPERS = 44;
    private static final double DIST_ALLIANCESTATION_TO_BRIDGE = 280.4;
    public static final double FENDER_DEPTH = 38.5;
    private static final double TOLERANCE = 2;
    public static final double INCHES_TO_FENDER = DIST_FENDER_TO_KEY - (BOT_LENGTH_WITH_BUMPERS - BUMPER_EDGE_TO_WHEEL_CENTER);

    /* For inches to bridge from fender */
    public static final double INCHES_TO_BRIDGE = DIST_ALLIANCESTATION_TO_BRIDGE - FENDER_DEPTH - BOT_LENGTH_WITH_BUMPERS -TOLERANCE;

    public static final double RAMPING_DISTANCE = 5;
    public static final double RAMPING_CONSTANT = 10/9;

    public Autonomous() {
        addSequential(new DrivetrainSetGear(true));
        if(CommandBase.oi.getAutonSetting() == 0){
            addSequential(new AutonSetting1());
        }
        else if(CommandBase.oi.getAutonSetting() == 1){
            addSequential(new AutonSetting2());
        }
        else if(CommandBase.oi.getAutonSetting() == 2){
            addSequential(new AutonSetting3());
        }
        else if(CommandBase.oi.getAutonSetting() == 3){
            addSequential(new AutonSetting4());
        }
        else if(CommandBase.oi.getAutonSetting() == 4){
            addSequential(new AutonSetting5());
        }
        else{
            addSequential(new AutonSetting6());
        }
    }
}
