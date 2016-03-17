package old.com.nowabwagel.engine.core;

import org.lwjgl.glfw.GLFW;

public class Time {
	public static final long SECOND = 1000000000L;
	
	private static double delta;

	public static long getTime() {
		return System.nanoTime();
	}

	public static double getDelta() {
		return delta;
	}

	public static void setDelta(double delta) {
		Time.delta = delta;
	}
}
