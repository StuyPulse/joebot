package edu.stuy;

import crio.hardware.CRIO;
import java.io.*;

public class InitTests {
    public static void main(String[] args) throws IOException {
        setUpTests();
    }

    public static void setUpTests() throws IOException {
        //File f = new File("C:/Users/Kevin Wang/Documents/FRC/VIRSYSJ/VIRSYS_alpha/VIRSYS");
        //System.out.println(f.getAbsolutePath());
        //Runtime.getRuntime().exec("virsys.exe", null, f);
        CRIO.init();
        Devmode.DEV_MODE = true;
    }

    public static void tearDownTests() {
        CRIO.end();
    }
}
