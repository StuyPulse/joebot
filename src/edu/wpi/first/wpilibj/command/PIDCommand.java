/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.command;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;

/**
 * This class defines a {@link Command} which interacts heavily with a PID loop.
 * 
 * <p>It provides some convenience methods to run an internal {@link PIDController}.
 * It will also start and stop said {@link PIDController} when the {@link PIDCommand} is
 * first initialized and ended/interrupted.</p>
 *
 * @author Joe Grinstead
 */
public abstract class PIDCommand extends Command {

    /** The max setpoint value */
    private double max = Double.MAX_VALUE;
    /** The min setpoint value */
    private double min = Double.MIN_VALUE;
    /** The internal {@link PIDController} */
    private SendablePIDController controller;
    /** An output which calls {@link PIDCommand#usePIDOutput(double)} */
    private PIDOutput output = new PIDOutput() {

        public void pidWrite(double output) {
            usePIDOutput(output);
        }
    };
    /** A source which calls {@link PIDCommand#returnPIDInput()} */
    private PIDSource source = new PIDSource() {

        public double pidGet() {
            return returnPIDInput();
        }
    };

    /**
     * Instantiates a {@link PIDCommand} that will use the given p, i and d values.
     * @param name the name of the command
     * @param p the proportional value
     * @param i the integral value
     * @param d the derivative value
     */
    public PIDCommand(String name, double p, double i, double d) {
        super(name);
        controller = new SendablePIDController(p, i, d, source, output);
    }

    /**
     * Instantiates a {@link PIDCommand} that will use the given p, i and d values.  It will also space the time
     * between PID loop calculations to be equal to the given period.
     * @param name the name
     * @param p the proportional value
     * @param i the integral value
     * @param d the derivative value
     * @param period the time (in seconds) between calculations
     */
    public PIDCommand(String name, double p, double i, double d, double period) {
        super(name);
        controller = new SendablePIDController(p, i, d, source, output, period);
    }

    /**
     * Instantiates a {@link PIDCommand} that will use the given p, i and d values.
     * It will use the class name as its name.
     * @param p the proportional value
     * @param i the integral value
     * @param d the derivative value
     */
    public PIDCommand(double p, double i, double d) {
        controller = new SendablePIDController(p, i, d, source, output);
    }

    /**
     * Instantiates a {@link PIDCommand} that will use the given p, i and d values.
     * It will use the class name as its name..
     * It will also space the time
     * between PID loop calculations to be equal to the given period.
     * @param p the proportional value
     * @param i the integral value
     * @param d the derivative value
     * @param period the time (in seconds) between calculations
     */
    public PIDCommand(double p, double i, double d, double period) {
        controller = new SendablePIDController(p, i, d, source, output, period);
    }

    /**
     * Returns the {@link PIDController} used by this {@link PIDCommand}.
     * Use this if you would like to fine tune the pid loop.
     *
     * <p>Notice that calling {@link PIDController#setSetpoint(double) setSetpoint(...)} on the controller
     * will not result in the setpoint being trimmed to be in
     * the range defined by {@link PIDCommand#setSetpointRange(double, double) setSetpointRange(...)}.</p>
     *
     * @return the {@link PIDController} used by this {@link PIDCommand}
     */
    protected PIDController getPIDController() {
        return controller;
    }

    void _initialize() {
        controller.enable();
    }

    void _end() {
        controller.disable();
    }

    void _interrupted() {
        _end();
    }

    /**
     * Adds the given value to the setpoint.
     * If {@link PIDCommand#setRange(double, double) setRange(...)} was used,
     * then the bounds will still be honored by this method.
     * @param deltaSetpoint the change in the setpoint
     */
    public void setSetpointRelative(double deltaSetpoint) {
        setSetpoint(getSetpoint() + deltaSetpoint);
    }

    /**
     * Sets the setpoint to the given value.  If {@link PIDCommand#setRange(double, double) setRange(...)}
     * was called,
     * then the given setpoint
     * will be trimmed to fit within the range.
     * @param setpoint the new setpoint
     */
    protected void setSetpoint(double setpoint) {
        controller.setSetpoint(setpoint);
    }

    /**
     * Returns the setpoint.
     * @return the setpoint
     */
    protected double getSetpoint() {
        return controller.getSetpoint();
    }

    /**
     * Returns the current position
     * @return the current position
     */
    protected double getPosition() {
        return returnPIDInput();
    }

    /**
     * Sets the range that the setpoint may be in.  If this method is called, all subsequent calls to
     * {@link PIDCommand#setSetpoint(double) setSetpoint(...)} will force the setpoint within the given values.
     * @param a the first value (can be the min or max, it doesn't matter)
     * @param b the second value (can be the min or max, it doesn't matter)
     */
    protected void setSetpointRange(double a, double b) {
        if (a <= b) {
            this.min = a;
            this.max = b;
        } else {
            this.min = b;
            this.max = a;
        }
    }

    /**
     * Returns the input for the pid loop.
     *
     * <p>It returns the input for the pid loop, so if this command was based
     * off of a gyro, then it should return the angle of the gyro</p>
     *
     * <p>All subclasses of {@link PIDCommand} must override this method.</p>
     *
     * <p>This method will be called in a different thread then the {@link Scheduler} thread.</p>
     *
     * @return the value the pid loop should use as input
     */
    protected abstract double returnPIDInput();

    /**
     * Uses the value that the pid loop calculated.  The calculated value is the "output" parameter.
     * This method is a good time to set motor values, maybe something along the lines of <code>driveline.tankDrive(output, -output)</code>
     *
     * <p>All subclasses of {@link PIDCommand} must override this method.</p>
     *
     * <p>This method will be called in a different thread then the {@link Scheduler} thread.</p>
     *
     * @param output the value the pid loop calculated
     */
    protected abstract void usePIDOutput(double output);

    public String getType() {
        return "PIDCommand";
    }

    public NetworkTable getTable() {
        return controller.getTable();
    }
}
