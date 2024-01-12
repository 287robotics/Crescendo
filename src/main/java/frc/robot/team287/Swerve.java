package frc.robot.team287;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Swerve {

	private XboxController controller;

	private Wheel wheel1 = new Wheel(15, 1, 2, -0.2021, false);
	private Wheel wheel2 = new Wheel(16, 3, 4, 0.1428, true);
	private Wheel wheel3 = new Wheel(17, 7, 8, -0.4802, true);
	private Wheel wheel4 = new Wheel(14, 5, 6, 0.4827, false);

	private double botDriveAngle = 0;
	private double botSpeedTarget = 0;
	private double botSpeedRamp = 0;

	private final Pigeon2 pigeon = new Pigeon2(10);
	private double startPigeon;

	private RotationController rotationController;
	private double botRotationTarget;

	public Swerve(XboxController controller) {
		this.controller = controller;
	}

	public void sharedInit() {
		calibrateAll();
		startPigeon = pigeon.getAngle() * Math.PI / 180;
		botRotationTarget = 0;
		rotationController = new RotationController(startPigeon, pigeon);
	}

	public void calibrateAll() {
		wheel1.calibrate();
		wheel2.calibrate();
		wheel3.calibrate();
		wheel4.calibrate();
	}

	public void robotPeriodic() {
		SmartDashboard.putNumber("wheel1", wheel1.getEncoderRotation());
		SmartDashboard.putNumber("wheel2", wheel2.getEncoderRotation());
		SmartDashboard.putNumber("wheel3", wheel3.getEncoderRotation());
		SmartDashboard.putNumber("wheel4", wheel4.getEncoderRotation());
		SmartDashboard.putNumber("wheel1a", wheel1.getEncoderRotation2());
		SmartDashboard.putNumber("wheel2a", wheel2.getEncoderRotation2());
		SmartDashboard.putNumber("wheel3a", wheel3.getEncoderRotation2());
		SmartDashboard.putNumber("wheel4a", wheel4.getEncoderRotation2());
	}

	public void update() {
		Vec2 translate = new Vec2(controller.getRightX(), controller.getRightY());
		botRotationTarget += controller.getLeftX() * 0.1;
		rotationController.setRotationTarget(botRotationTarget);
		double rotationOutput = controller.getAButton() ? rotationController.getRotationOutput() : 0;
		SmartDashboard.putNumber("rotationOutput", rotationOutput);

		this.botSpeedTarget = translate.getLengthSquared();

		if(translate.getLengthSquared() > 0.02) {
			botDriveAngle = Math.atan2(translate.x, translate.y);//add gyro
		}

		this.botSpeedRamp = this.botSpeedTarget * 0.2 + this.botSpeedRamp * 0.8;

		double pigeonAngle = pigeon.getAngle() * Math.PI / 180;
		SmartDashboard.putNumber("pigeonAngle", pigeonAngle);

		Vec2 targetVector = new Vec2(botSpeedRamp * Math.cos(botDriveAngle + (pigeonAngle - startPigeon)), botSpeedRamp * Math.sin(botDriveAngle + (pigeonAngle - startPigeon)));

		// get individual vectors based on rotation and add the translational movement to each.
		// you may think "adam, what if the vector is longer than 1 and it overdrives the drive motors?"
		// but worry not carp/keira/vinny/whoever else, Wheel.setVector(Vec2) caps the length to 1
		Vec2 r1 = new Vec2(rotationOutput, rotationOutput).add(targetVector);
		Vec2 r2 = new Vec2(-rotationOutput, rotationOutput).add(targetVector);
		Vec2 r3 = new Vec2(-rotationOutput, -rotationOutput).add(targetVector);
		Vec2 r4 = new Vec2(rotationOutput, -rotationOutput).add(targetVector);

		//apply the vectors to the wheels
		wheel1.setVector(r1);
		wheel2.setVector(r2);
		wheel3.setVector(r3);
		wheel4.setVector(r4);
		wheel1.update();
		wheel2.update();
		wheel3.update();
		wheel4.update();
	}

}