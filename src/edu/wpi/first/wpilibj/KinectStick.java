/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import com.sun.cldc.jna.Pointer;
import com.sun.cldc.jna.Structure;
import edu.wpi.first.wpilibj.communication.FRCControl;

/**
 *
 * @author bradmiller
 */
public class KinectStick extends GenericHID {

    private final static byte kJoystickDataID = 24;
    private final static byte kJoystickDataSize = 18;
    private int m_recentPacketNumber;

    private int m_id;

    static class JoystickDataBlock extends Structure {

        byte joystick1[] = new byte[6];
        short button1;
        byte joystick2[] = new byte[6];
        short button2;

        public static final int size = kJoystickDataSize - 2;

        JoystickDataBlock(Pointer backingMemory) {
            useMemory(backingMemory);
        }

        public void read() {
            backingNativeMemory.getBytes(0, joystick1, 0, 6);
            button1 = backingNativeMemory.getShort(6);
            backingNativeMemory.getBytes(8, joystick2, 0, 6);
            button2 = backingNativeMemory.getShort(14);
        }

        public void write() {
            backingNativeMemory.setBytes(0, joystick1, 0, 6);
            backingNativeMemory.setShort(6, button1);
            backingNativeMemory.setBytes(8, joystick2, 0, 6);
            backingNativeMemory.setShort(14, button2);
        }

        public int size() {
            return size;
        }
    }

    class JoystickData extends FRCControl.DynamicControlData {

        JoystickDataBlock data;

        {
            allocateMemory();
            data = new JoystickDataBlock(
                    new Pointer(backingNativeMemory.address().toUWord().toPrimitive() + 2,
                                JoystickDataBlock.size));
        }

        public void read() {
            data.read();
        }

        public void write() {
            data.write();
        }

        public int size() {
            return kJoystickDataSize;
        }

        public void copy(JoystickData dest) {
            write();
            Pointer.copyBytes(backingNativeMemory, 0, dest.backingNativeMemory, 0, size());
            dest.read();
        }
    }
    JoystickData tempOutputData = new JoystickData();

    public KinectStick(int id) {
        m_id = id;
    }

    private void getData() {
        if (m_recentPacketNumber !=  DriverStation.getInstance().getPacketNumber()){
            m_recentPacketNumber = DriverStation.getInstance().getPacketNumber();
            int retVal = FRCControl.getDynamicControlData(kJoystickDataID, tempOutputData, tempOutputData.size(), 5);
            if (retVal != 0) {
                System.err.println("Bad retval: " + retVal);
            }
        }
    }

    private double normalize(byte rawValue) {
        if(rawValue >= 0)
            return rawValue / 127.0;
        else
            return rawValue / 128.0;
    }

    public double getX(Hand hand) {
        getData();
        return getRawAxis(Joystick.kDefaultXAxis);
    }

    public double getY(Hand hand) {
        getData();
        return getRawAxis(Joystick.kDefaultYAxis);
    }

    public double getZ(Hand hand) {
        getData();
        return getRawAxis(Joystick.kDefaultZAxis);
    }

    public double getTwist() {
        getData();
        return getRawAxis(Joystick.kDefaultTwistAxis);
    }

    public double getThrottle() {
        getData();
        return getRawAxis(Joystick.kDefaultThrottleAxis);
    }

    public double getRawAxis(int axis) {
        if (axis < 1 || axis > DriverStation.kJoystickAxes)
            return 0.0;

        getData();
        if (m_id == 1) {
            return normalize(tempOutputData.data.joystick1[axis-1]);
        } else {
            return normalize(tempOutputData.data.joystick2[axis-1]);
        }
    }

    public boolean getTrigger(Hand hand) {
		getData();
        return (tempOutputData.data.button1 & (short) Joystick.kDefaultTriggerButton) != 0;
    }

    public boolean getTop(Hand hand) {
		getData();
        return (tempOutputData.data.button1 & (short) Joystick.kDefaultTopButton) != 0;
    }

    public boolean getBumper(Hand hand) {
		getData();
        return (tempOutputData.data.button1 & (short) 4) != 0;
    }

    public boolean getRawButton(int button) {
		getData();
        return (tempOutputData.data.button1 & (short) (1 << (button - 1))) != 0;
    }
}
