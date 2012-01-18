/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.communication;

import com.sun.cldc.jna.*;
import com.sun.squawk.VM;

/**
 * Class for obtaining a RIO handle.
 * @author jhersh
 */
public class BumARioHandle
{

    // UINT32 FRC_NetworkCommunication_nBumARioHandle_bum(INT32 *status);
    private static Function bumFn;
    private static Function invalidateFn;

    static {
        try {
            bumFn = NativeLibrary.getDefaultInstance().getFunction("FRC_NetworkCommunication_nBumARioHandle_bum");
            invalidateFn = NativeLibrary.getDefaultInstance().getFunction("FRC_NetworkCommunication_nBumARioHandle_invalidate");
        } catch (Exception e){
            bumFn = null;
            invalidateFn = null;
            throw new RuntimeException("Communication functions are missing. Make sure that you are using the latest version of the communication library and FPGA, downloaded from file releases on sourceforge.wpi.edu");
        }
    }

    /**
     * Obtain a RIO handle.
     * @param pStatus The current status.
     * @return A RIOHandle
     */
    public static int bum(int pStatus)
    {
        return bumFn.call1(pStatus);
    }

    static {
        invalidateFn.call0();
        VM.addShutdownHook(new Thread(new Runnable() {
            public void run() {
                invalidateFn.call0();
            }
        }));
    }
}
