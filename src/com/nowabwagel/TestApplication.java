package com.nowabwagel;

import java.nio.FloatBuffer;

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
		String vertex = Utils.getFileAsString("vertex.txt");
		// "#version 450 core \n" + "void main(void){ \n"
		// + "const vec4 vertices[3] = vec4[3](vec4(0.25,-0.25,0.5,1.0),
		// vec4(-0.25,-0.25,0.5,1.0),vec4(0.25, 0.25, 0.5, 1.0));\n"
		// + " gl_Position = vertices[gl_VertexID]; \n" + "} \n";

		String fragment = Utils.getFileAsString("fragment.txt");
		// "#version 450 core \n" + "out vec4 color; \n" + "void main(void){ \n"
		// + " color = vec4(0.0, 0.8, 1.0, 1.0); \n" + "} \n";

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
