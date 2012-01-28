/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author admin
 */
public class AutonSetting1 extends CommandGroup {

    /* For inches to fender */
    private static final int DIST_FENDER_TO_KEY = 110;
    private static final double BUMPER_EDGE_TO_WHEEL_CENTER = 9.5;
    private static final int BOT_LENGTH_WITH_BUMPERS = 44;
    public static final double INCHES_TO_FENDER = DIST_FENDER_TO_KEY - (BOT_LENGTH_WITH_BUMPERS - BUMPER_EDGE_TO_WHEEL_CENTER);

    /* For inches to bridge from fender */
    public static final double INCHES_TO_BRIDGE = 5.0;
    
    public AutonSetting1() {
        addSequential(new AutonDriveToFender(INCHES_TO_FENDER));
        addSequential(new ShooterShoot(2.0));
        
        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addParallel(new TusksExtend());
        
        addSequential(new AutonBackUpToBridge(INCHES_TO_BRIDGE));
        addSequential(new TusksRetract());
        
    }
}
