package com.nowabwagel.engine.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonHandler extends GLFWMouseButtonCallback {

	public static boolean[] buttons = new boolean[128];

	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW.GLFW_RELEASE;
	}

	public static boolean getPress(int button) {
		return buttons[button];
	}
	// FIXME: Make other things work like getpressed and stuff
}
