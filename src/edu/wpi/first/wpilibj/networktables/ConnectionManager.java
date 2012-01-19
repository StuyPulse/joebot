/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import com.sun.squawk.platform.posix.natives.Socket;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Joe
 */
class ConnectionManager {

    private static ConnectionManager m_instance;

    public static synchronized ConnectionManager getInstance() {
        if(m_instance == null)
            m_instance = new ConnectionManager();
        return m_instance;
    }
    
    
    final boolean IS_SERVER = true;
    /** The port number to use */
    private final int PORT = 1735;
    /** The set of all active connections */
    private Set connections = new Set();
    
    private ConnectionManager(){
        new Thread() {

            public void run() {
                acceptConnections();
            }
        }.start();
    }

    /**
     * This will constantly look for and accept connections.
     * This should be given a dedicated thread, and should only be called once.
     * Will be called in the initialize method if called for the first time.
     */
    private void acceptConnections() {
        ServerSocketConnection server = null;

        // Open the server
        while (true) {
            try {
                server = (ServerSocketConnection) Connector.open("socket://:" + PORT);
                break;
            } catch (IOException ex) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex1) {
                }
            }
        }

        try {
            while (true) {
                // Wait for a connection
                SocketConnection socket = (SocketConnection) server.acceptAndOpen();

                socket.setSocketOption(SocketConnection.LINGER, 0);

                addConnection(new Connection(socket));
            }
        } catch (IOException ex) {
            System.out.println("NetworkingTable: LOST SERVER!");
        }
    }

    private synchronized void addConnection(Connection connection) {
        if(!connections.add(connection)){
            System.err.println("ResourceAlreadyAllocated: Connection object already exists");
            return;
        }
        connection.start();
    }

    synchronized void removeConnection(Connection connection) {
        connections.remove(connection);
    }
}
