package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent {
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;

	public FreeMove(float speed) {
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LSHIFT);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}

	@Override
	public void input(float delta) {
		float movAmt = speed * delta;

		if (Input.getKey(forwardKey))
			move(getTransform().getRot().getForward(), movAmt);
		if (Input.getKey(backKey))
			move(getTransform().getRot().getForward(), -movAmt);
		if (Input.getKey(leftKey))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Input.getKey(rightKey))
			move(getTransform().getRot().getRight(), movAmt);
		if (Input.getKey(upKey))
			move(getTransform().getRot().getUp(), movAmt);
		if (Input.getKey(downKey))
			move(getTransform().getRot().getDown(), movAmt);
	}

	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
