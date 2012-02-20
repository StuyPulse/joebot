/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands.util;
import edu.stuy.commands.*;
/**
 *
 * @author Q
 */
public class ConveyorTools {
    public static void lowerSensorDoesNotSense() {
        CommandBase.conveyor.lowerSensor.value = false;
    }

    public static void lowerSensorDoesSense() {
        CommandBase.conveyor.lowerSensor.value = true;
    }
}
