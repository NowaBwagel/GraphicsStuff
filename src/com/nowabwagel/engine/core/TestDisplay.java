package com.nowabwagel.engine.core;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class TestDisplay {

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long window;

	private String title;
	private int width;
	private int height;

	@SuppressWarnings("unused")
	private float dTime = 0;

	public TestDisplay(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public void start() {
		System.out.println("LWJGL: " + Sys.getVersion());

		try {
			init();
			run();

			glfwDestroyWindow(window);
			keyCallback.release();
		} finally {
			glfwTerminate();
			errorCallback.release();
		}

	}

	private void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		if (glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create GLFW window");

		// TODO: move glfwKeyCallback to own class
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, GL_TRUE);
			}
		});

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	public void run() {
		GL.createCapabilities();

		glClearColor(0f, 0f, 0f, 0f);
		long sTime = 0;

		while (glfwWindowShouldClose(window) == GL_FALSE) {
			sTime = System.nanoTime();

			render();
			glfwPollEvents();
			glfwSwapBuffers(window);

			dTime = (System.nanoTime() - sTime) / 1000000000.0f;
		}

	}

	private void render() {
		float mul = (float) Math
				.abs(Math.sin(System.nanoTime() / 1000000000.0));

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GL11.glBegin(GL11.GL_TRIANGLES);
		// Top & Red
		GL11.glColor3f(1.0f * mul, 0.0f, 0.0f);
		GL11.glVertex2f(0.0f, 1.0f);

		// Right & Green
		GL11.glColor3f(0.0f, 1.0f * mul, 0.0f);
		GL11.glVertex2f(1.0f, -1.0f);

		// Left & Blue
		GL11.glColor3f(0.0f, 0.0f, 1.0f * mul);
		GL11.glVertex2f(-1.0f, -1.0f);
		GL11.glEnd();
	}

	public long getWindow() {
		return window;
	}
}
