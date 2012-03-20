/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author admin
 */
public class BridgePistonExtend extends CommandBase {
    public BridgePistonExtend() {
        requires(bridgePiston);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
        bridgePiston.extend();
    }
    
    protected boolean isFinished() {
        return bridgePiston.isExtended();
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
}
