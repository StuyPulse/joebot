// Copyright (c) National Instruments 2009.  All Rights Reserved.
// Do Not Edit... this file is generated!

package edu.wpi.first.wpilibj.fpga;

import com.ni.rio.*;

public class tSysWatchdog extends tSystem
{

   public tSysWatchdog()
   {
      super();

   }

   protected void finalize()
   {
      super.finalize();
   }

   public static final int kNumSystems = 1;








//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Command
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSysWatchdog_Command_Address = 0x8134;

   public static void writeCommand(final int value)
   {

      NiFpga.writeU32(m_DeviceHandle, kSysWatchdog_Command_Address, value, status);
   }
   public static int readCommand()
   {

      return (int)((NiFpga.readU32(m_DeviceHandle, kSysWatchdog_Command_Address, status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Challenge
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSysWatchdog_Challenge_Address = 0x8138;

   public static short readChallenge()
   {

      return (short)((NiFpga.readU32(m_DeviceHandle, kSysWatchdog_Challenge_Address, status)) & 0x000000FF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Active
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSysWatchdog_Active_Address = 0x8140;

   public static void writeActive(final boolean value)
   {

      NiFpga.writeU32(m_DeviceHandle, kSysWatchdog_Active_Address, (value ? 1 : 0), status);
   }
   public static boolean readActive()
   {

      return ((NiFpga.readU32(m_DeviceHandle, kSysWatchdog_Active_Address, status)) != 0 ? true : false);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Timer
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSysWatchdog_Timer_Address = 0x813C;

   public static long readTimer()
   {

      return (long)((NiFpga.readU32(m_DeviceHandle, kSysWatchdog_Timer_Address, status)) & 0xFFFFFFFFl);
   }




}
