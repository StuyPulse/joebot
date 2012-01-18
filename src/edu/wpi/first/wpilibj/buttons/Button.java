/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData;

/**
 * This class provides an easy way to link commands to inputs.
 *
 * It is very easy to link a button to a command.  For instance, you could
 * link the trigger button of a joystick to a "score" command.
 *
 * It is encouraged that teams write a subclass of Button if they want to have
 * something unusual (for instance, if they want to react to the user holding
 * a button while the robot is reading a certain sensor input).  For this, they
 * only have to write the {@link Button#get()} method to get the full functionality
 * of the Button class.
 *
 * @author Joe Grinstead
 */
public abstract class Button implements SmartDashboardData {

    /**
     * Returns whether or not the button is pressed.
     *
     * This method will be called repeatedly a command is linked to the Button.
     *
     * @return whether or not the button is pressed.
     */
    public abstract boolean get();

    /**
     * Returns whether get() return true or the internal table for SmartDashboard use is pressed.
     * @return  whether get() return true or the internal table for SmartDashboard use is pressed
     */
    private boolean grab() {
        return get() || (table != null && table.isConnected() && table.getBoolean("pressed", false));
    }

    /**
     * Starts the given command whenever the button is newly pressed.
     * @param command the command to start
     */
    public void whenPressed(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.start();
                    }
                } else {
                    pressedLast = false;
                }
            }
        }.start();
    }

    /**
     * Constantly starts the given command while the button is held.
     *
     * {@link Command#start()} will be called repeatedly while the button is held,
     * and will be canceled when the button is released.
     *
     * @param command the command to start
     */
    public void whileHeld(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    pressedLast = true;
                    command.start();
                } else {
                    if (pressedLast) {
                        pressedLast = false;
                        command.cancel();
                    }
                }
            }
        }.start();
    }

    /**
     * Starts the command when the button is released
     * @param command the command to start
     */
    public void whenReleased(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    pressedLast = true;
                } else {
                    if (pressedLast) {
                        pressedLast = false;
                        command.start();
                    }
                }
            }
        }.start();
    }

    /**
     * An internal class of {@link Button}.  The user should ignore this, it is
     * only public to interface between packages.
     */
    public abstract class ButtonScheduler {
        public abstract void execute();

        protected void start() {
            Scheduler.getInstance().addButton(this);
        }
    }

    public String getType() {
        return "Button";
    }

    private NetworkTable table;
    public NetworkTable getTable() {
        if (table == null) {
            table = new NetworkTable();
            table.putBoolean("pressed", get());
        }
        return table;
    }
}
