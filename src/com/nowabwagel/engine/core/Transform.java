package com.nowabwagel.engine.core;

import com.nowabwagel.engine.core.math.Matrix4f;
import com.nowabwagel.engine.core.math.Vector3f;

public class Transform {
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;

	public Transform() {
		translation = new Vector3f();
		rotation = new Vector3f();
		scale = new Vector3f(1, 1, 1);
	}

	public Matrix4f getTransformation() {
		Matrix4f translationmMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(),
				translation.getZ());
		Matrix4f roationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());

		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return translationmMatrix.mul(roationMatrix.mul(scaleMatrix));
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public void setTranslation(float x, float y, float z) {
		this.translation = new Vector3f(x, y, z);
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setRotaion(float x, float y, float z) {
		this.rotation = new Vector3f(x, y, z);
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void setScale(float x, float y, float z) {
		this.scale = new Vector3f(x, y, z);
	}
}
