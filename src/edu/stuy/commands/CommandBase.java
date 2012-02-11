package edu.stuy.commands;

import edu.stuy.Devmode;
import edu.stuy.OI;
import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {
    public static OI oi;
    
    // Create a single static instance of all of your subsystems
    public static Drivetrain drivetrain;
    public static Shooter shooter;
    public static Tusks tusks;
    public static Acquirer acquirer;
    public static Conveyor conveyor;

    static {
        drivetrain = new Drivetrain();
        conveyor = new Conveyor();
        shooter = new Shooter();
        acquirer = new Acquirer();
        if (!Devmode.DEV_MODE) {
            tusks = new Tusks();
        }
    }

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        if (!Devmode.DEV_MODE) {
            // Show what command your subsystem is running on the SmartDashboard
            SmartDashboard.putData(drivetrain);
            SmartDashboard.putData(shooter);
            SmartDashboard.putData(tusks);
            SmartDashboard.putData(acquirer);
            SmartDashboard.putData(conveyor);
        }

        System.out.println("drivetrain: " + drivetrain);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }

    public void cancel() {
        end();
        super.cancel();
    }
}
