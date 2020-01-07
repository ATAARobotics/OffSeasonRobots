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

    public void teleopCyc() {
        double speed = controller.getY(Hand.kLeft);
        double turn = controller.getX(Hand.kRight);
        drive.drive(speed, turn, true); 
    }
}