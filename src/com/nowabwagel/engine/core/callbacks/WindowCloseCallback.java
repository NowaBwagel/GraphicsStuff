package com.nowabwagel.engine.core.callbacks;

import org.lwjgl.glfw.GLFWWindowCloseCallback;

public class WindowCloseCallback extends GLFWWindowCloseCallback {
	private boolean isCloseRequested;

	public WindowCloseCallback() {
		isCloseRequested = false;
	}

	@Override
	public void invoke(long window) {
		isCloseRequested = true;
	}

	public boolean isCloseRequested() {
		return isCloseRequested;
	}

}
