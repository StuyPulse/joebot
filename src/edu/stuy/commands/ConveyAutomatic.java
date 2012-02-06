/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kevin Wang
 */
public class ConveyAutomatic extends CommandBase {
    
    private double timer, begintime;

    public ConveyAutomatic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
        requires(shooter);
        begintime = Timer.getFPGATimestamp();
        timer = 90000;
    }

    public ConveyAutomatic(double time) {
        this();
        timer = time;
    }


    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("shooter is at speed? " + shooter.isSpeedGood());
        System.out.println("ball at top? " + conveyor.ballAtTop());
        if (shooter.isSpeedGood() || !conveyor.ballAtTop() || Timer.getFPGATimestamp() - begintime < timer) {
            conveyor.convey();
        }
        else {
            conveyor.stop();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
