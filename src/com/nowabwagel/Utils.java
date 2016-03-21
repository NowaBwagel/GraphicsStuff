package com.nowabwagel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL40;

public class Utils {

	private static int compileShader(String src, int type) {
		int shader;
		if ((shader = GL20.glCreateShader(type)) == 0) {
			System.err.println("Vertex Shader creation failed: Could not fin valid memory when adding shader");
			System.exit(1);
		}

		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);

		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
			System.err.println(GL20.glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}

		return shader;
	}

	private static int createProgram(int... shaders) {
		int program = GL20.glCreateProgram();
		for (int i = 0; i < shaders.length; i++)
			GL20.glAttachShader(program, shaders[i]);

		GL20.glLinkProgram(program);

		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0) {
			System.err.println(GL20.glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(program);

		if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0) {
			System.err.println(GL20.glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
		
		for (int i = 0; i < shaders.length; i++)
			GL20.glDeleteShader(shaders[i]);

		return program;
	}

	public static int compileShaderProgram(String vertex, String fragment) {
		int vertexShader = compileShader(vertex, GL20.GL_VERTEX_SHADER);
		int fragmentShader = compileShader(fragment, GL20.GL_FRAGMENT_SHADER);

		int program = createProgram(vertexShader, fragmentShader);
		return program;
	}

	public static FloatBuffer createFlippedFloatBuffer(float... data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length + 1);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * Files should be kept in the res directory
	 * 
	 * @param location
	 *            Path to file in res folder
	 * @return String text of file
	 */
	public static String getFileAsString(String location) {
		String fileText = "";

		File fileLoc = new File("res/" + location);
		try (BufferedReader br = new BufferedReader(new FileReader(fileLoc))) {
			String line;
			while ((line = br.readLine()) != null) {
				fileText += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileText;
	}
}
