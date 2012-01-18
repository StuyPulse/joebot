/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

/**
 *
 * @author Joe
 */
class OldData implements Data {
    
    final Entry entry;

    public OldData(Entry data) {
        this.entry = data;
    }

    public void encode(Buffer buffer) {
        buffer.writeByte(Data.OLD_DATA);
        entry.encode(buffer);
    }

}
