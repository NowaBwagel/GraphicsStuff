package com.nowabwagel.engine.core;

import static org.lwjgl.glfw.Callbacks.glfwSetCallback;
import static org.lwjgl.glfw.GLFW.GLFWWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.glfw.GLFWWindowSizeCallback.SAM;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.nowabwagel.engine.core.input.CursorPosHandler;
import com.nowabwagel.engine.core.input.KeyboardHandler;
import com.nowabwagel.engine.core.input.MouseButtonHandler;

/**
 * This class will be used by all any program using this to create windows, this
 * class will manage the window and Key Callback for now.
 * 
 * @author Noah Bergl
 * @version 1.0
 */
public class Window {
	/**
	 * Value for title of window in this class.
	 */
	private String title;
	/**
	 * Value of width on the window.
	 */
	private int width;
	/**
	 * Value of height of the window.
	 */
	private int height;
	/**
	 * If the window has been resized between bufferSwaps then the viewport size
	 * needs to change.
	 */
	private boolean resized;

	/**
	 * Native C functions that through errors in LWJGL / GLFW will throw their
	 * errors to the callback
	 */
	private GLFWErrorCallback errorCallback;

	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;

	/**
	 * Pointer for GLFW / LWJGL for the window
	 */
	private long window;

	/**
	 * Creates a new Windows object, initializing all private fields not related
	 * to GLFW / LWJGL.
	 * 
	 * @param title
	 *            The title of the Window.
	 * @param width
	 *            The width of the Window.
	 * @param height
	 *            The height of the Window.
	 */
	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	/**
	 * Used to initialize GLFW / LWJGL.
	 * Called by {@link #start()}
	 * 
	 * @throws IllegalStateException
	 * @throws RuntimeException
	 */
	public void init() throws IllegalStateException, RuntimeException {
		// Set Error Callback for GLFW and make errorCallback point to it.
		GLFW.glfwSetErrorCallback((errorCallback = Callbacks.errorCallbackPrint(System.err)));

		// If GLFW fails to initialize then I will throw an error at you.
		if (GLFW.glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Sorry, I failed at initializing GLFW.");

		// Now that GLFW is init. We can make the Window!
		// First we can't GLFW to know that we don't want the window to show up
		// till its fully loaded.
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);

		// Now that GLFW know what we want the window to do we will create the
		// window.
		window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);

		// However if the window fails to be made it will just return
		// MemoryUtil.NULL.
		if (window == MemoryUtil.NULL)
			throw new RuntimeException("Sorry, I failed at making a window.");

		GLFW.glfwSetKeyCallback(window, (keyCallback = new KeyboardHandler()));
		GLFW.glfwSetCursorPosCallback(window, (cursorPosCallback = new CursorPosHandler()));
		GLFW.glfwSetMouseButtonCallback(window, (mouseButtonCallback = new MouseButtonHandler()));
		
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetCallback(window, GLFWWindowSizeCallback(new SAM() {
			@Override
			public void invoke(long window, int w, int h) {
				resized = true;
				width = w;
				height = h;
			}
		}));

		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		GL.createCapabilities();
		glClearColor(0f, 0f, 0.5f, 1f);
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwSwapBuffers(window);
	}

	public void dispose() {
		GLFW.glfwTerminate();
		errorCallback.release();
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getWindow() {
		return window;
	}

	public boolean getResized() {
		return resized;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}
}
