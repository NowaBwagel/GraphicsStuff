package com.nowabwagel.engine.core.callbacks;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class WindowSizeCallback extends GLFWWindowSizeCallback {

	private int width;
	private int height;

	@Override
	public void invoke(long window, int width, int height) {
		this.width = width;
		this.height = height;

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
