package com.nowabwagel.engine.core.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPosHandler extends GLFWCursorPosCallback {
	private static double xpos = 0;
	private static double ypos = 0;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		CursorPosHandler.xpos = xpos;
		CursorPosHandler.ypos = ypos;
	}

	public static double getXpos() {
		return xpos;
	}

	public static double getYpos() {
		return ypos;
	}

	public static String asString() {
		return "CursorPosHandler [xpos=" + xpos + ", ypos=" + ypos + "]";
	}

	// FIXME: Add return vector2d
}
