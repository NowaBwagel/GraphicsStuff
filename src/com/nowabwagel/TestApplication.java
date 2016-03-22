package com.nowabwagel;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class TestApplication extends Application {

	int shaderProgram;
	int vao;
	double lastTime;
	double passedTime;
	double frameTime = 1 / 60D;
	int frameCount;

	@Override
	public void startup() {
		System.out.println("Starting");
		String vertex;
		String fragment;

		if (GL.getCapabilities().OpenGL45) {
			System.out.println("Going to use Shaders for 450");
			vertex = Utils.getFileAsString("vertex450.txt");
			fragment = Utils.getFileAsString("fragment450.txt");
		} else {
			System.out.println("Going to use Shaders for 330");
			vertex = Utils.getFileAsString("vertex330.txt");
			fragment = Utils.getFileAsString("fragment330.txt");
		}

		shaderProgram = Utils.compileShaderProgram(vertex, fragment);
		vao = GL30.glGenVertexArrays();
		// GL45.glCreateVertexArrays(1, vao);
		GL30.glBindVertexArray(vao);

		System.out.println("Start finished: " + shaderProgram);

	}

	@Override
	public void render(double currentTime) {
		FloatBuffer clearColor = Utils.createFlippedFloatBuffer((float) (Math.sin(currentTime) * 0.5f + 0.5f),
				(float) (Math.cos(currentTime) * 0.5f + 0.5f), 0.0f, 1.0f);

		GL30.glClearBufferfv(GL11.GL_COLOR, 0, clearColor);

		GL20.glUseProgram(shaderProgram);

		FloatBuffer attrib = Utils.createFlippedFloatBuffer((float) (Math.sin(currentTime) * 0.5f),
				(float) (Math.cos(currentTime) * 0.6f), 0.0f, 0.0f);

		GL20.glVertexAttrib4fv(0, attrib);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

	}

	@Override
	public void shutdown() {
		System.out.println("Shuting down");

		GL30.glDeleteVertexArrays(vao);
		GL20.glDeleteProgram(shaderProgram);

		System.out.println("Shutdown");
	}

}
