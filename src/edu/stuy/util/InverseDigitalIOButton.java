/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author admin
 */
public class InverseDigitalIOButton extends DigitalIOButton {
    
    public InverseDigitalIOButton(int port) {
        super(port);
    }
    
    private NetworkTable table;
    public NetworkTable getTable(String key) {
        if (table == null) {
            table = NetworkTable.getTable(key);
            table.putBoolean("pressed", get());
        }
        return table;
    }
    private boolean grab() {
        return !(get() || (table != null && table.isConnected() && table.getBoolean("pressed", false)));
    }
    
}
