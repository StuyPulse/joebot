/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.util.Vector;

/**
 * A simple set object.
 * @author Joe Grinstead
 */
class Set {

    private final Vector elements = new Vector();

    public synchronized boolean add(Object o) {
        if (!elements.contains(o)) {
            elements.addElement(o);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean remove(Object o) {
        return elements.removeElement(o);
    }

    public synchronized Object get(int i) {
        return elements.elementAt(i);
    }

    public synchronized int size() {
        return elements.size();
    }
}
