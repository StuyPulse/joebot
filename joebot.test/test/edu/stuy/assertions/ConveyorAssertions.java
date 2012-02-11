/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.assertions;

import static org.junit.Assert.*;
import edu.stuy.commands.CommandBase;


/**
 *
 * @author 694
 */
public class ConveyorAssertions {
    public static void assertConveyorIsRunning() {
        assertEquals(1, CommandBase.conveyor.getRoller(), 0.01);
    }

    public static void assertConveyorIsNotRunning() {
        assertEquals(0, CommandBase.conveyor.getRoller(), 0.01);
    }

}
