/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.assertions;

import static org.junit.Assert.*;
import edu.stuy.commands.CommandBase;
import edu.stuy.subsystems.Conveyor;

/**
 *
 * @author 694
 */
public class ConveyorAssertions {
    public static void assertConveyorIsRunning() {
        assertEquals(Conveyor.FORWARD , CommandBase.conveyor.getRoller(), 0.01);
    }

    public static void assertConveyorIsNotRunning() {
        assertEquals(0, CommandBase.conveyor.getRoller(), 0.01);
    }

    public static void assertConveyorIsRunningBackwards(){
        assertEquals(Conveyor.BACKWARD, CommandBase.conveyor.getRoller(), 0.01);
    }

}
