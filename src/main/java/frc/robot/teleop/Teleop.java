package frc.robot.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.Drive;

public class Teleop {
    private Drive drive = null;

    private XboxController controller = new XboxController(0);

    public Teleop(Drive drive) {
        this.drive = drive;
    }

    public void teleopInit() {
        drive.init();
    }

    private double previousSpeed = 0;
    private double previousTurn = 0;

    public void teleopCyc() {
        double speed;
        if (Math.abs(controller.getY(Hand.kLeft)) < 0.05) {
            speed = 0;
        } else {
            speed = controller.getY(Hand.kLeft) * 0.1 + previousSpeed * 0.9;
        }
        previousSpeed = speed;
        double turn;
        if (Math.abs(controller.getX(Hand.kRight)) < 0.05) {
            turn = 0;
        } else {
            turn = controller.getX(Hand.kRight) * 0.1 + previousTurn * 0.9;
        }
        previousTurn = turn;
        drive.drive(speed, turn, true); 

        if (controller.getStickButtonPressed(Hand.kRight)) {
            drive.gearShift();
        }
    }
}