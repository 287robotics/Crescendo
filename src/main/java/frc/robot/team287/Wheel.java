package frc.robot.team287;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Wheel {

    private static final double MAX_SPEED = 0.1;
    private static final double NEO_CONVERSION_FACTOR = -13.71;
    private static final double NEO_DRIVE_CONVERSION_FACTOR = 7.36;

    private final CANcoder absEncoder;
    private final CANSparkMax swivel;
    private final CANSparkMax drive;

    private final WheelController controller;
    private final PIDController swivelPIDController;

    private Vec2 motorVector;

    public Wheel(int cancoderId, int swivelId, int driveId, double offset, boolean reversed) {
        this.absEncoder = new CANcoder(cancoderId);
        this.swivel = new CANSparkMax(swivelId, MotorType.kBrushless);
        this.drive = new CANSparkMax(driveId, MotorType.kBrushless);
        this.drive.setInverted(reversed);
        this.controller = new WheelController();
        this.swivelPIDController = new PIDController(swivel, NEO_CONVERSION_FACTOR, 0.1, offset);
    }

    public double getEncoderRotation() {
        return absEncoder.getAbsolutePosition().getValue();
    }

    public double getEncoderRotation2() {
        return swivelPIDController.getPosition();
    }

    public void calibrate() {
        swivelPIDController.setReferencePositionNoOffset(absEncoder.getAbsolutePosition().getValue());
    }

    public void setVector(Vec2 vector) {
        this.motorVector = vector.limitLength(MAX_SPEED);
    }

    public void update() {
        controller.updateInput(motorVector);
        controller.update();
        drive.set(-motorVector.getLength() * controller.wheelSign);//this was originally above the update
        // drive.set(0);
        swivelPIDController.setPosition(controller.wheelPosition / Math.PI / 2);
    }
    
}
