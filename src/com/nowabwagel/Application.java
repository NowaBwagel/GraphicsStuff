package com.nowabwagel;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.ByteBuffer;

import old.com.nowabwagel.engine.core.callbacks.WindowCloseCallback;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public abstract class Application {

	private String title;
	private int width;
	private int height;

	private GLFWErrorCallback errorCallback;
	private WindowCloseCallback windowCloseCallback;

	private long window;

	public abstract void startup();

	public abstract void render(long currentTime);

	public Application() {
		this("unnamed application", 800, 800);
	}

	public Application(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public void start() {
		// Set Error Callback for GLFW and make errorCallback point to it.
		GLFW.glfwSetErrorCallback((errorCallback = Callbacks
				.errorCallbackPrint(System.err)));

		// If GLFW fails to initialize then I will throw an error at you.
		if (GLFW.glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException(
					"Sorry, I failed at initializing GLFW.");

		// Now that GLFW is init. We can make the Window!
		// First we can't GLFW to know that we don't want the window to show up
		// till its fully loaded.
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);

		// Now that GLFW know what we want the window to do we will create the
		// window.
		window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL,
				MemoryUtil.NULL);

		// However if the window fails to be made it will just return
		// MemoryUtil.NULL.
		if (window == MemoryUtil.NULL)
			throw new RuntimeException("Sorry, I failed at making a window.");

		windowCloseCallback = new WindowCloseCallback();

		GLFW.glfwSetWindowCloseCallback(window, windowCloseCallback);

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		GL.createCapabilities();
		glClearColor(0f, 0f, 0f, 1f);

		startup();
		run();
	}

	private void onRender() {
		render(System.nanoTime());
		GLFW.glfwSwapBuffers(window);
	}

	private void run() {
		while (true) {
			onRender();
			
			GLFW.glfwPollEvents();
			
			if (windowCloseCallback.isCloseRequested())
				dispose();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void dispose() {
		GLFW.glfwTerminate();
		errorCallback.release();
		windowCloseCallback.release();
	}
}
