/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.assertions;

import edu.stuy.commands.CommandBase;
import static org.junit.Assert.*;

/**
 *
 * @author 694
 */
public class AcquirerAssertions {

    public static void assertAcquirerIsRunning() {
        assertEquals(1, CommandBase.acquirer.getRollerSpeed(), 0.01);
    }

    public static void assertAcquirerIsNotRunning() {
        assertEquals(0, CommandBase.acquirer.getRollerSpeed(), 0.01);
    }
}
