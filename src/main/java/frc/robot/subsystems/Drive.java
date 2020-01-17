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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
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

  TalonSRX talon = null;
  Faults faults = new Faults();

  CANEncoder encoder = null;

  SpeedControllerGroup leftDrive = null;
  SpeedControllerGroup rightDrive = null;

  DifferentialDrive drive = null;

  DoubleSolenoid gearShift = null;

  public Drive() {
    leftController1 = new CANSparkMax(1, MotorType.kBrushless);
    leftController2 = new CANSparkMax(2, MotorType.kBrushless);
    rightController1 = new CANSparkMax(3, MotorType.kBrushless);
    rightController2 = new CANSparkMax(4, MotorType.kBrushless);

    talon = new TalonSRX(2);
    talon.configFactoryDefault();
    talon.setSensorPhase(false);

    // encoder = leftController1.getAlternateEncoder(AlternateEncoderType.kQuadrature, 4096);

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

  public String data() {
    String data = DriverStation.getInstance().getMatchTime() + "," + leftController1.getMotorTemperature() +  "," + leftController2.getMotorTemperature() + "," + rightController1.getMotorTemperature() + "," + rightController2.getMotorTemperature() + "," + leftController1.get() + "," + leftController2.get() + "," + rightController1.get() + "," + rightController2.get() + "," + pdp.getVoltage() + "\n";
    return data;
  }
  
  public void drive(double speed, double turn, boolean squareInputs) {
    talon.set(ControlMode.PercentOutput, 0);
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

  public void printEncoder() {
    System.out.println("Sensor Vel:" + talon.getSelectedSensorVelocity());
    System.out.println("Sensor Pos:" + talon.getSelectedSensorPosition());
    System.out.println("Out %" + talon.getMotorOutputPercent());
    System.out.println("Out Of Phase:" + faults.SensorOutOfPhase);
  }
}
