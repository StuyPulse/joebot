/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.util;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;

/**
 * Reads and parses text files on the cRIO. This is useful for quickly adjusting tuning values using FTP, such as for PID.
 * Place the '#' character before comments.
 *
 * @author Kevin Wang
 */
public class FileIO {
    private static StringBuffer log = new StringBuffer("");

    /**
     * Returns the contents of a file on the root directory of the cRIO, getting rid of comments and trimming whitespace.
     * @param filename The name of the file to read.
     * @return The contents of the file.
     */
    private static String getFileContents(String filename) {
        String url = "file:///values/" + filename;
        String contents = "";
        try {
            FileConnection c = (FileConnection) Connector.open(url);
            BufferedReader buf = new BufferedReader(new InputStreamReader(c.openInputStream()));
            String line;

            boolean lineRead;
            while ((line = buf.readLine()) != null) {
                lineRead = false;
                if (line.charAt(0) != '#') {
                    for (int i = 0; i < line.toCharArray().length; i++) {
                        if (line.toCharArray()[i] == '#') {
                            contents += line.substring(0, i).trim() + "\n";
                            lineRead = true;
                            break;
                        }
                    }
                    if (lineRead == false) {
                        contents += line + "\n";
                    }
                }
            }
            c.close();
        }
        catch (IOException e) {
        }
        return contents;
    }

    /**
     * Create an array of doubles using values in a text file. Each line in the file corresponds to one element in the array.
     * @param filename The name of the file to read.
     * @return The array of values in the file.
     */
    public static double[] getArray(String filename) {
        String raw = getFileContents(filename);
        StringTokenizer st = new StringTokenizer(raw);
        double[] array = new double[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            array[i++] = Double.parseDouble(st.nextToken());
        }
        return array;
    }

}