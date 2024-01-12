package frc.robot.team287;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotationController {

	private Pigeon2 pigeon;
	private double startPigeon;

	private double kP = 1.8;
	private double kI = 0.0004;
	private double kD = 0;

	private double lastP = 0;
	private double p = 0;	
	private double i = 0;
	private double d = 0;

	private double rotation;
	private double rotationSpeed = 0.05;
	private double rotationTarget;

	private long lastTime;
	private long currentTime;

	public RotationController(double startPigeon, Pigeon2 pigeon) {
		this.startPigeon = startPigeon;
		this.pigeon = pigeon;
		this.rotationTarget = startPigeon;
		this.rotation = startPigeon;
		this.currentTime = System.currentTimeMillis();
		this.lastTime = System.currentTimeMillis();
	}

	public void setStartPigeon(double startPigeon) {
		this.startPigeon = startPigeon;
	}

	private static double mod(double a, double n) {
		return a - Math.floor(a / n) * n;
  	}
  
	public void setRotationTarget(double angle) {
		double nt = Math.IEEEremainder(angle + startPigeon, Math.PI * 2);
        double np = Math.IEEEremainder(rotationTarget, Math.PI * 2);
        double a = nt - np;
        a = mod(a + Math.PI, Math.PI * 2) - Math.PI;
        rotationTarget += a;
	}

	public double getRotationOutput() {
		double delta = (currentTime - lastTime) * 0.001;

		if (rotation < rotationTarget) {
            rotation += rotationSpeed;
        } else if (rotation > rotationTarget) {
            rotation -= rotationSpeed;
        }
        p = rotation - pigeon.getAngle() * Math.PI / 180;
        i += p * delta;
        d = (p - lastP) / delta;
        double rotationOutput = -Math.min(1, Math.max(p * kP + i * kI + d * kD, -1));

		SmartDashboard.putNumber("rawOutputPID", p * kP + i * kI + d * kD);
		SmartDashboard.putNumber("rotationOutputPID", rotationOutput);
		SmartDashboard.putNumber("rotationTargetPID", rotationTarget);
		SmartDashboard.putNumber("rotationPID", rotation);

		lastTime = currentTime;
        currentTime = System.currentTimeMillis();

		return rotationOutput;
	}
    
}
