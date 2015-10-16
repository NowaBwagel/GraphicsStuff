package com.nowabwagel.engine.core;

import com.nowabwagel.engine.core.math.Vector3f;

public class Game {
	private Window window;
	private Mesh mesh;
	private Shader shader;
	private Transform transform;

	public Game(Window window) {
		this.window = window;
		window.setGame(this);
		mesh = new Mesh();
		shader = new Shader();

		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)),
				new Vertex(new Vector3f(0, 1, 0)),
				new Vertex(new Vector3f(1, -1, 0)),
				new Vertex(new Vector3f(0, -1, 1)) };

		int[] indices = new int[] { 0, 1, 3, 3, 1, 2, 2, 1, 0, 0, 2, 3 };

		mesh.addVertices(vertices, indices);

		transform = new Transform();

		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();

		shader.addUniform("transform");

	}

	public void input() {
		// FIXME: Make input class and use events and stuff
	}

	float temp = 0.0f;

	public void update() {
		temp += Time.getDelta() / 15;

		transform.setTranslation((float) Math.sin(temp), 0, 0);
		transform.setRotaion(0, (float) Math.sin(temp) * 180, 0);
		// transform.setScale((float) Math.sin(temp), (float) Math.sin(temp),
		// (float) Math.sin(temp));
	}

	public void render() {
		shader.bind();
		postBindUniformUpdate();
		mesh.draw();
	}

	private void postBindUniformUpdate() {
		shader.setUniform("transform", transform.getTransformation());
	}
}
