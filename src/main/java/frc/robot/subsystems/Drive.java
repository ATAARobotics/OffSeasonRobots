/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  CANSparkMax leftController1 = null;
  CANSparkMax leftController2 = null;
  CANSparkMax rightController1 = null;
  CANSparkMax rightController2 = null;

  SpeedControllerGroup leftDrive = null;
  SpeedControllerGroup rightDrive = null;

  DifferentialDrive drive = null;

  public Drive() {
    leftController1 = new CANSparkMax(0, MotorType.kBrushless);
    leftController2 = new CANSparkMax(1, MotorType.kBrushless);
    rightController1 = new CANSparkMax(2, MotorType.kBrushless);
    rightController2 = new CANSparkMax(3, MotorType.kBrushless);

    leftDrive = new SpeedControllerGroup(leftController1, leftController2);
    rightDrive = new SpeedControllerGroup(rightController1, rightController2);

    drive = new DifferentialDrive(leftDrive, rightDrive);
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
  
  public void drive(double speed, double turn, boolean squareInputs) {
    drive.arcadeDrive(speed, turn, squareInputs);
  }
}
