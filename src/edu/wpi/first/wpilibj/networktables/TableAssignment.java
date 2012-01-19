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
class TableAssignment implements Data {

    private final NetworkTable table;
    private final Integer alteriorId;

    public TableAssignment(NetworkTable table, Integer alteriorId) {
        this.table = table;
        this.alteriorId = alteriorId;
    }

    public void encode(Buffer buffer) {
        buffer.writeByte(Data.TABLE_ASSIGNMENT);
        buffer.writeTableId(alteriorId.intValue());
        table.encodeName(buffer);
    }

}
