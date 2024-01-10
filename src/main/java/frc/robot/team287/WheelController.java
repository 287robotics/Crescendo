package frc.robot.team287;

public class WheelController {

	public double delta = 0;
	public double wheelDirTarget = 0;
	public double wheelDirActual = 0;
	public double wheelPosition = 0;
	public double wheelSign = 1;
	public double normalizedPosition = 0;
	public double angleDiff = 0;

	public WheelController() {
		wheelDirTarget = Math.PI / 2;
		wheelPosition = Math.PI / 2;
	}
  
	private static double mod(double a, double n) {
		  return a - Math.floor(a / n) * n;
	}
  
	public void updateInput(Vec2 motorVector) {
		wheelDirTarget = motorVector.toRadians();
  
		normalizedPosition = Math.IEEEremainder(wheelPosition, Math.PI * 2);
		angleDiff = Math.PI - Math.abs(Math.abs(wheelDirTarget - normalizedPosition) - Math.PI);
  
		if(angleDiff > Math.PI / 2) {
			if(wheelDirTarget > 0) {
				wheelDirActual = wheelDirTarget - Math.PI;
			} else {
				wheelDirActual = wheelDirTarget + Math.PI;
			}
			wheelSign = -1;
		} else {
			wheelSign = 1;
			wheelDirActual = wheelDirTarget;
		}
			
		delta = mod(((wheelDirActual - normalizedPosition) + Math.PI), Math.PI * 2) - Math.PI;
	}
  
	public void update() {
		wheelPosition += delta;
  
		delta = 0;
	}
	
}