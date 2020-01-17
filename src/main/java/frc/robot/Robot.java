/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.logging.Logging;
import frc.robot.subsystems.Drive;
import frc.robot.teleop.Teleop;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  

  private Drive drive = new Drive();
  private Teleop teleop = null;
  private Logging logging = null;

  Ultrasonic ultrasonic = new Ultrasonic(0, 1);
  Ultrasonic ultrasonic2 = new Ultrasonic(2, 3);
  ColorSensorV3 color = new ColorSensorV3(I2C.Port.kOnboard);
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    teleop = new Teleop(drive);
    logging = new Logging(drive);
    ultrasonic.setAutomaticMode(true);
    ultrasonic2.setAutomaticMode(true);
    Shuffleboard.getTab("Example tab").add(ultrasonic);
    Shuffleboard.getTab("Example tab").add(ultrasonic2);
  }

  @Override
  public void robotPeriodic() {
    // drive.periodic();
    Color detectingColor = color.getColor();

    double IR = color.getIR();
    int proximity = color.getProximity();

    drive.printEncoder();

    SmartDashboard.putNumber("Red", detectingColor.red);
    SmartDashboard.putNumber("Green", detectingColor.green);
    SmartDashboard.putNumber("Blue", detectingColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Proximity", proximity);


  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    teleop.teleopInit();
  }

  int i = 0;
  @Override
  public void teleopPeriodic() {
    teleop.teleopCyc();
    if (i < 10) {
      i++;
    } else {
      logging.record();
      i = 0;
    }
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    logging.write();
  }

}
