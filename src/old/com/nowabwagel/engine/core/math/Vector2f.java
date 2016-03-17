package old.com.nowabwagel.engine.core.math;

public class Vector2f {
	private float x;
	private float y;

	public Vector2f() {
		new Vector2f(0f, 0f);
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float dot(Vector2f v) {
		return x * v.x + y * v.y;
	}

	/**
	 * Vector will lose length component as length will now become one.
	 * 
	 * @return Itself
	 */
	public Vector2f normalize() {
		float length = length();
		x /= length;
		y /= length;
		return this;
	}

	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		return new Vector2f(x * cos - y * sin, x * sin + y * cos);
	}

	public Vector2f add(Vector2f v) {
		return new Vector2f(x + v.x, y + v.y);
	}

	public Vector2f add(float v) {
		return new Vector2f(x + v, y + v);
	}

	public Vector2f sub(Vector2f v) {
		return new Vector2f(x - v.x, y - v.y);
	}

	public Vector2f sub(float v) {
		return new Vector2f(x - v, y - v);
	}

	public Vector2f mul(Vector2f v) {
		return new Vector2f(x * v.x, y * v.y);
	}

	public Vector2f mul(float v) {
		return new Vector2f(x * v, y * v);
	}

	public Vector2f div(Vector2f v) {
		return new Vector2f(x / v.x, y / v.y);
	}

	public Vector2f div(float v) {
		return new Vector2f(x / v, y / v);
	}

	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f set(Vector2f v) {
		set(v.x, v.y);
		return this;
	}

	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2f))
			return false;
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
}
