package com.nowabwagel.engine.core;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.nowabwagel.engine.core.callbacks.CursorPosCallback;
import com.nowabwagel.engine.core.callbacks.KeyCallback;
import com.nowabwagel.engine.core.callbacks.MouseButtonCallback;
import com.nowabwagel.engine.core.callbacks.WindowCloseCallback;
import com.nowabwagel.engine.core.callbacks.WindowPosCallback;
import com.nowabwagel.engine.core.callbacks.WindowSizeCallback;
import com.nowabwagel.engine.core.layers.Layer;

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

	// FIXME: Make all callbacks for events non static so multiple windows can
	// be created at once.
	private KeyCallback keyCallback;
	private CursorPosCallback cursorPosCallback;
	private MouseButtonCallback mouseButtonCallback;
	private WindowPosCallback windowPosCallback;
	private WindowSizeCallback windowSizeCallback;
	private WindowCloseCallback windowCloseCallback;

	/**
	 * Pointer for GLFW / LWJGL for the window
	 */
	private long window;

	/**
	 * Every different layer is a new layer for rendering and events. IE. layer
	 * for world, and a layer for a GUI
	 */
	private List<Layer> layerStack;

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

	private Game game;

	public void setGame(Game game) {
		this.game = game;
	}

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.layerStack = new ArrayList<Layer>();
	}

	/**
	 * Used to initialize GLFW / LWJGL. Called by {@link #start()}
	 * 
	 * @throws IllegalStateException
	 * @throws RuntimeException
	 */
	public void init() throws IllegalStateException, RuntimeException {
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

		// So our callbacks don't go through a nasty garbage collection
		keyCallback = new KeyCallback();
		cursorPosCallback = new CursorPosCallback();
		mouseButtonCallback = new MouseButtonCallback();
		windowPosCallback = new WindowPosCallback();
		windowSizeCallback = new WindowSizeCallback();
		windowCloseCallback = new WindowCloseCallback();

		GLFW.glfwSetKeyCallback(window, keyCallback);
		GLFW.glfwSetCursorPosCallback(window, cursorPosCallback);
		GLFW.glfwSetMouseButtonCallback(window, mouseButtonCallback);
		GLFW.glfwSetWindowPosCallback(window, windowPosCallback);
		GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
		GLFW.glfwSetWindowCloseCallback(window, windowCloseCallback);

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		GL.createCapabilities();
		glClearColor(0f, 0f, 0f, 1f);
	}

	public void updateWindow() {
		int w = windowSizeCallback.getWidth();
		int h = windowSizeCallback.getHeight();
		if (w != width || h != height) {
			width = w;
			height = h;
			GL11.glViewport(0, 0, w, h);
		}
	}

	public void onRender() {

		for (int i = layerStack.size() - 1; i >= 0; i--)
			layerStack.get(i).onRender();

		game.render();
		GLFW.glfwSwapBuffers(window);
	}

	public void onUpdate(float delta) {
		game.update();

		for (int i = 0; i < layerStack.size(); i++)
			layerStack.get(i).onUpdate(delta);
	}

	public void dispose() {
		GLFW.glfwTerminate();
		errorCallback.release();
	}

	public void addLayer(Layer l) {
		layerStack.add(l);
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

	public boolean getIsCloseRequested() {
		return windowCloseCallback.isCloseRequested();
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
