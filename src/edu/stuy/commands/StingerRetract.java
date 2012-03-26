/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author admin
 */
public class StingerRetract extends CommandBase {
    public StingerRetract() {
        requires(stinger);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
        stinger.retract();
    }
    
    protected boolean isFinished() {
        return !stinger.isExtended();
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
    
}
