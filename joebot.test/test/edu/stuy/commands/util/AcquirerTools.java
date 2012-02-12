/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands.util;

import edu.stuy.commands.*;
/**
 *
 * @author English
 */
public class AcquirerTools {

    public static void stopAcquirer(){
        CommandBase.acquirer.stop();
    }

    public static void startAcquirerForward(){
        CommandBase.acquirer.acquire();
    }

    public static void startAcquirerBackwards(){
        CommandBase.acquirer.acquireReverse();
    }

}
