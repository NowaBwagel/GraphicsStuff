package com.nowabwagel.engine.core;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback.SAM;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

import com.nowabwagel.engine.core.input.KeyboardHandler;

public class Display {
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long window;
	int width = 600;
	int height = 600;

	private boolean running;

	public void init() {
		if (glfwInit() != GL11.GL_TRUE) {
			System.err.println("Failed to inizialize GLFW.");
			System.exit(-1);
		}
		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);

		window = glfwCreateWindow(width, height, "2D Pong", MemoryUtil.NULL,
				MemoryUtil.NULL);

		if (window == MemoryUtil.NULL) {
			System.err.println("Could not create our window");
		}

		glfwSetKeyCallback(window, (keyCallback = new KeyboardHandler()));

		// creates a bytebuffer object 'vidmode' which then queries
		// to see what the primary monitor is.
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Sets the initial position of our game window.
		glfwSetWindowPos(window, 100, 100);
		// Sets the context of GLFW, this is vital for our program to work.
		glfwMakeContextCurrent(window);
		// finally shows our created window in all it's glory.
		glfwShowWindow(window);

		GL.createCapabilities();

		GL11.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		System.out.println("OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
		running = true;
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}

	public void update() {
		glfwPollEvents();

		if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE))
			System.out.println("Space is down");
	}

	public void run() {
		init();
		while (running) {
			update();
			render();

			if (glfwWindowShouldClose(window) == GL11.GL_TRUE) {
				running = false;
			}
		}

	}

	public static void main(String[] args) {
		Display dis = new Display();
		dis.run();
	}
}
