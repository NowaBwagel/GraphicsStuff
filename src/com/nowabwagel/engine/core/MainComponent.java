package com.nowabwagel.engine.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.nowabwagel.engine.core.callbacks.KeyCallback;
import com.nowabwagel.engine.core.callbacks.MouseButtonCallback;
import com.nowabwagel.engine.core.callbacks.WindowPosCallback;
import com.nowabwagel.engine.core.events.types.KeyEvent;
import com.nowabwagel.engine.core.events.types.MouseEvent;
import com.nowabwagel.engine.core.layers.TestLayer;

public class MainComponent {
	private double FRAME_CAP;

	private Window display;
	private long window;
	private boolean isRunning;
	private Game game;

	public MainComponent(String title, int width, int height, double FRAME_CAP) {
		display = new Window(title, width, height);
		display.init();

		System.out.println("Currently Running OpenGL version: " + RenderUtil.getOpenGLVersion());

		game = new Game();
		this.FRAME_CAP = FRAME_CAP;

		window = display.getWindow();
		//display.addLayer(new TestLayer());
		
		RenderUtil.initGraphics();
		
		isRunning = false;
	}

	public void start() {
		if (isRunning)
			return;
		
		run();
	}

	public void stop() {
		if (!isRunning)
			return;

		isRunning = false;
	}

	private void run() {
		isRunning = true;

		int frames = 0;
		long frameCounter = 0;

		final double frameTime = 1.0 / FRAME_CAP;

		long lastTime = Time.getTime();
		double unProcessedTime = 0;

		while (isRunning) {
			boolean render = false;

			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;

			unProcessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;

			while (unProcessedTime > frameTime) {
				render = true;

				unProcessedTime -= frameTime;

				Time.setDelta(frameTime);

				game.input();
				game.update();
				display.updateWindow();

				if (display.getIsCloseRequested())
					stop();

				GLFW.glfwPollEvents();

				KeyEvent keyEvent;

				while ((keyEvent = KeyCallback.fifoKeyEvents.get()) != null) {
					if (GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE
							|| keyEvent.getKey() == GLFW.GLFW_KEY_ESCAPE
							&& keyEvent.getAction() == GLFW.GLFW_RELEASE)
						stop();
				}

				MouseEvent mouseEvent;

				while ((mouseEvent = MouseButtonCallback.fifoBuffer.get()) != null) {
					if (mouseEvent.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT
							&& mouseEvent.getAction() == GLFW.GLFW_RELEASE)
						System.out.println("Left Mouse Button Released");
				}

				if (WindowPosCallback.getMoved())
					System.out.println(WindowPosCallback.getPos());

				if (display.getResized()) {
					GL11.glViewport(0, 0, display.getWidth(),
							display.getHeight());
					display.setResized(false);
				}

				if (frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;

				}

			}
			if (render) {
				onRender();
				frames++;
			} else
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		cleanup();
	}

	private void onRender() {
		RenderUtil.clear();
		game.render();
		display.onRender();
	}

	private void cleanup() {
		display.dispose();
	}

	public static void main(String[] args) {
		MainComponent game = new MainComponent("Im a test m8", 800, 600, 300.0);

		game.start();
	}
}
