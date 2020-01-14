/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileWriter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ReverseSolenoid;
import frc.robot.commands.SetSolenoid;


/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  private PowerDistributionPanel pdp = new PowerDistributionPanel(1);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax leftController1 = null;
  CANSparkMax leftController2 = null;
  CANSparkMax rightController1 = null;
  CANSparkMax rightController2 = null;

  SpeedControllerGroup leftDrive = null;
  SpeedControllerGroup rightDrive = null;

  DifferentialDrive drive = null;

  DoubleSolenoid gearShift = null;

  FileWriter file = null;
  String temperature = "Time, Port 1 Temperature, Port 2 Temperature, Port 3 Temperature, Port 4 Temperature, Port 1 Speed, Port 2 Speed, Port 3 Speed, Port 4 Speed, PDP Voltage\n";

  public Drive() {
    leftController1 = new CANSparkMax(1, MotorType.kBrushless);
    leftController2 = new CANSparkMax(2, MotorType.kBrushless);
    rightController1 = new CANSparkMax(3, MotorType.kBrushless);
    rightController2 = new CANSparkMax(4, MotorType.kBrushless);

    leftDrive = new SpeedControllerGroup(leftController1, leftController2);
    rightDrive = new SpeedControllerGroup(rightController1, rightController2);

    drive = new DifferentialDrive(leftDrive, rightDrive);

    gearShift = new DoubleSolenoid(0, 1);

    try {
    } catch (Exception e) {
      System.out.println("No file found!");
    }

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void init() {
    drive.setSafetyEnabled(true);
    drive.setExpiration(0.5);
  }

  public void log() {
    try {
      file = new FileWriter("/home/lvuser/temperature.csv");
      file.write(temperature);
      file.flush();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private int logCounter = 0;

  public void periodic() {
    SmartDashboard.putNumber("Left Motor 1: Output Current", leftController1.getOutputCurrent());
    SmartDashboard.putNumber("Left Motor 1: Input Voltage", leftController1.getBusVoltage());
    SmartDashboard.putNumber("Left Motor 1: Temperature Celsius", leftController1.getMotorTemperature());

    
    SmartDashboard.putNumber("Left Motor 2: Output Current", leftController2.getOutputCurrent());
    SmartDashboard.putNumber("Left Motor 2: Input Voltage", leftController2.getBusVoltage());
    SmartDashboard.putNumber("Left Motor 2: Temperature Celsius", leftController2.getMotorTemperature());

    
    SmartDashboard.putNumber("Right Motor 1: Output Current", rightController1.getOutputCurrent());
    SmartDashboard.putNumber("Right Motor 1: Input Voltage", rightController1.getBusVoltage());
    SmartDashboard.putNumber("Right Motor 1: Temperature Celsius", rightController1.getMotorTemperature());

    
    SmartDashboard.putNumber("Right Motor 2: Output Current", rightController2.getOutputCurrent());
    SmartDashboard.putNumber("Right Motor 2: Input Voltage", rightController2.getBusVoltage());
    SmartDashboard.putNumber("Right Motor 2: Temperature Celsius", rightController2.getMotorTemperature());

    logCounter++;
    if (logCounter == 5) {
      temperature += DriverStation.getInstance().getMatchTime() + "," + leftController1.getMotorTemperature() +  "," + leftController2.getMotorTemperature() + "," + rightController1.getMotorTemperature() + "," + rightController2.getMotorTemperature() + "," + leftController1.get() + "," + leftController2.get() + "," + rightController1.get() + "," + rightController2.get() + "," + pdp.getVoltage() + "\n";
      logCounter = 0;
    }
  }
  
  public void drive(double speed, double turn, boolean squareInputs) {
    drive.arcadeDrive(speed, turn, squareInputs);
  }

  public void gearShift() {
    new ReverseSolenoid(gearShift).reverse();
  }

  public void setHigh() {
    new SetSolenoid(gearShift, Value.kForward).set();
  }

  public void setLow() {
    new SetSolenoid(gearShift, Value.kReverse).set();
  }
}
