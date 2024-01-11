package frc.robot.team287;

import edu.wpi.first.wpilibj.XboxController;

public class Swerve {

	private XboxController controller;

	private Wheel wheel1 = new Wheel(15, 1, 2, .3 - .5);
	private Wheel wheel2 = new Wheel(16, 3, 4, .1413 + .5);
	private Wheel wheel3 = new Wheel(17, 7, 8, .0231);
	private Wheel wheel4 = new Wheel(14, 5, 6, .9772 - .5);

	// private final Pigeon2 pigeon = new Pigeon2(10);

	public Swerve(XboxController controller) {
		this.controller = controller;
	}

	public void sharedInit() {
		calibrateAll();
	}

	public void calibrateAll() {
		wheel1.calibrate();
		wheel2.calibrate();
		wheel3.calibrate();
		wheel4.calibrate();
	}

	public void update() {
		Vec2 translate = new Vec2(controller.getRightX(), controller.getRightY());
		double rotationOutput = controller.getLeftX();

		// get individual vectors based on rotation and add the translational movement to each.
		// you may think "adam, what if the vector is longer than 1 and it overdrives the drive motors?"
		// but worry not carp/keira/vinny/whoever else, Wheel.setVector(Vec2) caps the length to 1
		Vec2 r1 = new Vec2(rotationOutput, rotationOutput).add(translate);
		Vec2 r2 = new Vec2(rotationOutput, -rotationOutput).add(translate);
		Vec2 r3 = new Vec2(-rotationOutput, -rotationOutput).add(translate);
		Vec2 r4 = new Vec2(-rotationOutput, rotationOutput).add(translate);

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