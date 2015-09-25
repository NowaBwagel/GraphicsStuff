package com.nowabwagel.engine.core;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Display {

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long window;

	public void start() {
		System.out.println("LWJGL: " + Sys.getVersion());
	}
	
	public void run(){
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
	}

	private void init() {

	}

	public void loop() {

	}

	public long getWindow() {
		return window;
	}
}
