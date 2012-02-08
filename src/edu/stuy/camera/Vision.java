/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.stuy.camera;
import com.sun.squawk.io.BufferedReader;
import edu.wpi.first.wpilibj.IterativeRobot;
import java.io.*;
import javax.microedition.io.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Vision extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    SocketConnection socketConnect;
    InputStream myInputStream;
    DataInputStream data;
    BufferedReader in;
    public void robotInit() {
        try {
            //This IP should be the computer's IP, in the from 10.XX.YY.Z
            //XX.YY is your team number (e.g. ours in 1011)
            //Z is whatever your computer is on
            // TODO: Fill in computer IP for competition
            socketConnect = (SocketConnection) Connector.open("socket://10.6.94.xxx:1180");
            System.out.println("Socket opened to Dashboard");
            myInputStream = socketConnect.openInputStream();
            System.out.println("Input stream opened from socket");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        try {
            int b;
            while ((b = myInputStream.read()) != -1) {
                System.out.println(b);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Teleop code goes here
    }
}

