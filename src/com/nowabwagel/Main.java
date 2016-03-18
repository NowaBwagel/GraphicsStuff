package com.nowabwagel;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Main {

	public static void main(String[] args) {

		String vertex = "#version 450 core \n" + "void main(){ \n"
				+ " gl_Position = vec4(0.0, 0.0, 0.5, 1.0); \n" + "} \n";

		String fragment = "#version 450 core \n" + "out vec4 color \n"
				+ "void main(){ \n" + " color = vec4(0.0, 0.8, 1.0, 1.0); \n"
				+ "} \n";

		int shaderProgram = compileShaderProgram(vertex, fragment);
		
		Application app = new Application() {

			@Override
			public void startup() {
				// TODO Auto-generated method stub

			}

			@Override
			public void render(double currentTime) {
				FloatBuffer red = createFlippedFloatBuffer(
						(float) (Math.sin(currentTime) * 0.5f + 0.5f),
						(float) (Math.cos(currentTime) * 0.5f + 0.5f), 0.0f,
						1.0f);

				GL30.glClearBufferfv(GL11.GL_COLOR, 0, red);
			}

		};

		app.start();
	}

	public static int compileShaderProgram(String vertex, String fragment) {
		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader, vertex);
		GL20.glCompileShader(vertexShader);

		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader, fragment);
		GL20.glCompileShader(fragmentShader);

		int program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertexShader);
		GL20.glAttachShader(program, fragmentShader);
		GL20.glLinkProgram(program);

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		return program;
	}

	public static FloatBuffer createFlippedFloatBuffer(float... data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length + 1);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
