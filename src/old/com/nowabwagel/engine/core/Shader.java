package old.com.nowabwagel.engine.core;

import java.util.HashMap;

import old.com.nowabwagel.engine.core.math.Matrix4f;
import old.com.nowabwagel.engine.core.math.Vector3f;
import old.com.nowabwagel.util.Util;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public class Shader {
	// Pointer
	private int program;
	// Because uniform locations are just pointers we use a string to identify
	// them
	private HashMap<String, Integer> uniforms;

	public Shader() {
		program = GL20.glCreateProgram();
		uniforms = new HashMap<String, Integer>();

		if (program == 0) {
			System.err.println("Shader creation failed: Could not fin valid memory location in constructor");
			System.exit(1);
		}

	}

	public void bind() {
		GL20.glUseProgram(program);
	}

	public void addUniform(String uniform) {
		int uniformLocation = GL20.glGetUniformLocation(program, uniform);

		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error: Could not find uniform " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}

		uniforms.put(uniform, uniformLocation);
	}

	public void addVertexShader(String s) {
		addProgram(s, GL20.GL_VERTEX_SHADER);
	}

	public void addGeometryShader(String s) {
		addProgram(s, GL32.GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String s) {
		addProgram(s, GL20.GL_FRAGMENT_SHADER);

	}

	public void compileShader() {
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
	}

	public void addProgram(String s, int type) {
		int shader = GL20.glCreateShader(type);

		if (shader == 0) {
			System.err.println("Shader creation failed: Could not fin valid memory when adding shader");
			System.exit(1);

		}

		GL20.glShaderSource(shader, s);
		GL20.glCompileShader(shader);

		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
			System.err.println(GL20.glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}

		GL20.glAttachShader(program, shader);
	}

	public void setUniformi(String uniformName, int value) {
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUnformf(String uniformName, float value) {
		GL20.glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Vector3f value) {
		GL20.glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}

	public void setUniform(String uniformName, Matrix4f value) {
		GL20.glUniformMatrix4fv(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
}
