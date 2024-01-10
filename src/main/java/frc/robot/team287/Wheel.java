package frc.robot.team287;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Wheel {

    private static final double NEO_CONVERSION_FACTOR = -13.71;
    private static final double NEO_DRIVE_CONVERSION_FACTOR = 7.36;

    private final CANcoder absEncoder;
    private final CANSparkMax swivel;
    private final CANSparkMax drive;

    private final WheelController controller;
    private final PIDController swivelPIDController;

    private Vec2 motorVector;

    public Wheel(int cancoderId, int swivelId, int driveId, double offset) {
        this.absEncoder = new CANcoder(cancoderId);
        this.swivel = new CANSparkMax(swivelId, MotorType.kBrushless);
        this.drive = new CANSparkMax(driveId, MotorType.kBrushless);
        this.controller = new WheelController();
        this.swivelPIDController = new PIDController(swivel, NEO_CONVERSION_FACTOR, 1, offset);
    }

    public void setVector(Vec2 vector) {
        this.motorVector = vector;
    }

    public void update() {
        controller.updateInput(motorVector);
        controller.update();
        drive.set(-motorVector.getLength() * controller.wheelSign);//this was originally above the update
        swivelPIDController.setPosition(-controller.wheelPosition / Math.PI / 2 + 0.25);
    }
    
}
