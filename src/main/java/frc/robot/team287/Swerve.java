package frc.robot.team287;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.XboxController;

public class Swerve {

    Wheel wheel1 = new Wheel(15, 1, 2, .3 - .5);
    Wheel wheel2 = new Wheel(16, 3, 4, .1413 + .5);
    Wheel wheel3 = new Wheel(17, 7, 8, .0231);
    Wheel wheel4 = new Wheel(14, 5, 6, .9772 - .5);

    // private final Pigeon2 pigeon = new Pigeon2(10);

    public void setVector(Vec2 vector) {
        wheel1.setVector(vector);
        wheel2.setVector(vector);
        wheel3.setVector(vector);
        wheel4.setVector(vector);
    }

    public void update(XboxController controller) {
        this.setVector(new Vec2(controller.getRightX(), controller.getRightY()));

        wheel1.update();
        wheel2.update();
        wheel3.update();
        wheel4.update();
    }

}
