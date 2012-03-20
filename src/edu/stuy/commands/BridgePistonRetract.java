/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author admin
 */
public class BridgePistonRetract extends CommandBase {
    public BridgePistonRetract() {
        requires(bridgePiston);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
        bridgePiston.retract();
    }
    
    protected boolean isFinished() {
        return !bridgePiston.isExtended();
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
    
}
