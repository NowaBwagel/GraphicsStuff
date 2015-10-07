package com.nowabwagel.engine.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.nowabwagel.engine.core.input.CursorPosHandler;
import com.nowabwagel.engine.core.input.KeyboardHandler;

public class MainComponent {
	private double FRAME_CAP;

	private Window display;
	private long window;
	private boolean isRunning;
	private Game game;

	public MainComponent(String title, int width, int height, double FRAME_CAP) {
		display = new Window(title, width, height);
		game = new Game();
		this.FRAME_CAP = FRAME_CAP;

		isRunning = false;
	}

	public void start() {
		if (isRunning)
			return;

		display.init();
		window = display.getWindow();
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
				
				GLFW.glfwPollEvents();

				if (GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE || KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
					stop();
				
				if (display.getResized()) {
					GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
					display.setResized(false);
				}

				if (frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
					
				}

			}
			if (render) {
				render();
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

	private void render() {
		game.render();
		display.render();
	}

	private void cleanup() {
		display.dispose();
	}

	public static void main(String[] args) {
		MainComponent game = new MainComponent("Im a test m8", 800, 600, 120.0);

		game.start();
	}
}
