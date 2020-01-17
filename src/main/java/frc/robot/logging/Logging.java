package frc.robot.logging;

import java.io.FileWriter;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;

public class Logging {

    Drive drive;

    FileWriter file = null;
    String data = "Time, Port 1 Temperature, Port 2 Temperature, Port 3 Temperature, Port 4 Temperature, Port 1 Speed, Port 2 Speed, Port 3 Speed, Port 4 Speed, PDP Voltage\n";

    public Logging(Drive drive) {
        this.drive = drive;
    }

    public void record() {
        data += drive.data();
    }

    public void write() {
        try {
            file = new FileWriter("/home/lvuser/temperature.csv");
            file.write(data);
            file.flush();
          } catch (Exception e) {
            System.out.println(e);
          }
    }

    public static void put(String key, double value) {
        if (true) {
            // key.setDouble(value);
            SmartDashboard.putNumber(key, value);
        }
    }
    
    public static void put(String key, String value) {
        if (true) {
            // key.setString(value);
            SmartDashboard.putString(key, value);
        }
    }
    
    public static void put(String key, boolean value) {
        if (true) {
            // key.setBoolean(value);
            SmartDashboard.putBoolean(key, value);
        }
    }

    public static void log(String str) {
        if (true) {
            System.out.println(str);
        }
    }
    
    public static void logf(String format, Object... args) {
        if (true) {
            System.out.printf(format + "\n", args);
        }
    }
}