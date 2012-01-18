/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.parsing.IDeviceController;

/**
 * IFI Victor Speed Controller
 */
public class Victor extends SafePWM implements SpeedController, IDeviceController {

    /**
     * Common initialization code called by all constructors.
     *
     * Note that the Victor uses the following bounds for PWM values.  These values were determined
     * empirically through experimentation during the 2008 beta testing of the new control system.
     * Testing during the beta period revealed a significant amount of variation between Victors.
     * The values below are chosen to ensure that teams using the default values should be able to
     * get "full power" with the maximum and minimum values.  For better performance, teams may wish
     * to measure these values on their own Victors and set the bounds to the particular values
     * measured for the actual Victors they were be using.
     *   - 210 = full "forward"
     *   - 138 = the "high end" of the deadband range
     *   - 132 = center of the deadband range (off)
     *   - 126 = the "low end" of the deadband range
     *   - 56 = full "reverse"
     */
    private void initVictor() {
        setBounds(210, 138, 132, 126, 56);
        setPeriodMultiplier(PeriodMultiplier.k2X);
        setRaw(m_centerPwm);
    }

    /**
     * Constructor that assumes the default digital module.
     *
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     */
    public Victor(final int channel) {
        super(channel);
        initVictor();
    }

    /**
     * Constructor that specifies the digital module.
     *
     * @param slot The slot in the chassis that the digital module is plugged into.
     * @param channel The PWM channel on the digital module that the Victor is attached to.
     */
    public Victor(final int slot, final int channel) {
        super(slot, channel);
        initVictor();
    }

    /**
     * Set the PWM value.
     *
     * @deprecated For compatibility with CANJaguar
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately
     * scaling the value for the FPGA.
     *
     * @param speed The speed to set.  Value should be between -1.0 and 1.0.
     * @param syncGroup The update group to add this Set() to, pending UpdateSyncGroup().  If 0, update immediately.
     */
    public void set(double speed, byte syncGroup) {
        setSpeed(speed);
    }

    /**
     * Set the PWM value.
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately
     * scaling the value for the FPGA.
     *
     * @param speed The speed value between -1.0 and 1.0 to set.
     */
    public void set(double speed) {
        setSpeed(speed);
    }

    /**
     * Get the recently set value of the PWM.
     *
     * @return The most recently set value for the PWM between -1.0 and 1.0.
     */
    public double get() {
        return getSpeed();
    }

    /**
     * Write out the PID value as seen in the PIDOutput base object.
     *
     * @param output Write out the PWM value as was found in the PIDController
     */
    public void pidWrite(double output) {
        set(output);
    }
}
