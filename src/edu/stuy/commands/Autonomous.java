/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
    
    public static final double INCHES_FROM_EDGE_TO_SONAR = 13;
    
    public static final double RAMPING_DISTANCE = 5;
    public static final double RAMPING_CONSTANT = 10/9;
    
    public static final double CONVEY_AUTO_TIME = 4;

    public Autonomous() {
        addSequential(new DrivetrainSetGear(true));
        switch(CommandBase.oi.getAutonSetting()){
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
