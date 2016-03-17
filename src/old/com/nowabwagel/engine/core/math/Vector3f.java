package old.com.nowabwagel.engine.core.math;

public class Vector3f {
	private float x;
	private float y;
	private float z;

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float length() {
		return Math.abs(x * x + y * y + z * z);
	}

	public float dot(Vector3f v) {
		return x * v.x + y * v.y + z * v.z;
	}

	public Vector3f cross(Vector3f v) {
		float x_ = y * v.z - z * v.y;
		float y_ = z * v.x - x * v.z;
		float z_ = x * v.y - y * v.x;

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Vector will lose length component as length will now become one.
	 * 
	 * @return Itself
	 */
	public Vector3f normalized() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}

	public Vector3f rotate(float angle) {
		return null; // TODO: Im Special
	}

	public Vector3f add(Vector3f v) {
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}

	public Vector3f add(float v) {
		return new Vector3f(x + v, y + v, z + v);
	}

	public Vector3f sub(Vector3f v) {
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}

	public Vector3f sub(float v) {
		return new Vector3f(x - v, y - v, z - v);
	}

	public Vector3f mul(Vector3f v) {
		return new Vector3f(x * v.x, y * v.y, z * v.z);
	}

	public Vector3f mul(float v) {
		return new Vector3f(x * v, y * v, z * v);
	}

	public Vector3f div(Vector3f v) {
		return new Vector3f(x / v.x, y / v.y, z / v.z);
	}

	public Vector3f div(float v) {
		return new Vector3f(x / v, y / v, z / v);
	}

	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
