package frc.robot.team287;

public class Vec2 {
	public double x;
	public double y;

	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vec2() {
	}

	public String toString() {
		return "" + x + ", " + y;
	}

	public Vec2 add(Vec2 other) {
		Vec2 res = new Vec2();

		res.x = this.x + other.x;
		res.y = this.y + other.y;

		return res;
	}

	public double toAngle() {
		return Math.atan2(y, x) * 180 / Math.PI;
	}

	public double toRadians() {
		return Math.atan2(y, x);
	}

	public double getLength() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public double getLengthSquared() {
		return this.x * this.x + this.y * this.y;
	}

	public Vec2 multiply(double scalar) {
		Vec2 res = new Vec2();

		res.x = this.x * scalar;
		res.y = this.y * scalar;

		return res;
	}

	public Vec2 limitLength(double max) {
		double lenSquared = this.getLengthSquared();
		if (lenSquared > max * max) {
			this.x = this.x / Math.sqrt(lenSquared) * max;
			this.y = this.y / Math.sqrt(lenSquared) * max;
		}

		return this;
	}
}
