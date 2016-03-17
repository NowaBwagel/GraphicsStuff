package com.nowabwagel;

import org.lwjgl.opengl.GL11;

public class Main {

	public static void main(String[] args) {
		Application app = new Application() {

			@Override
			public void startup() {
				// TODO Auto-generated method stub

			}

			@Override
			public void render(long currentTime) {
				GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			}

		};
		
		app.start();
	}
}
