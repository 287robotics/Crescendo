package frc.robot.team287;

import com.ctre.phoenix6.hardware.Pigeon2;

public class RotationPID {

    private Pigeon2 gyro;

    private double p;
    private double i;
    private double d;

    private double target;

    public RotationPID(double p, double i, double d, double startingRotation, Pigeon2 gyro) {
        this.p = p;
        this.i = i;
        this.d = d;

        this.target = 0;
    }
    
}
