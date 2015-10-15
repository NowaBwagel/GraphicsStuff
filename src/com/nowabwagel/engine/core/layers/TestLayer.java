package com.nowabwagel.engine.core.layers;

import com.nowabwagel.engine.core.Mesh;
import com.nowabwagel.engine.core.ResourceLoader;
import com.nowabwagel.engine.core.Shader;
import com.nowabwagel.engine.core.Transform;
import com.nowabwagel.engine.core.Vertex;
import com.nowabwagel.engine.core.events.Event;
import com.nowabwagel.engine.core.math.Vector3f;

public class TestLayer extends Layer {
	private Mesh mesh;
	private Shader shader;
	private Transform transform;

	public TestLayer() {

		mesh = new Mesh();

		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)),
				new Vertex(new Vector3f(0, 1, 0)),
				new Vertex(new Vector3f(1, -1, 0)) };
		int[] indices = new int[] { 0, 1, 2 };

		mesh.addVertices(vertices, indices);

		shader = new Shader();

		transform = new Transform();

		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();

		shader.addUniform("transform");

	}

	@Override
	public void onRender() {
		shader.bind();
		postShaderBindUpdate();
		mesh.draw();
	}

	private void postShaderBindUpdate() {
		shader.setUniform("transform", transform.getTransformation());
	}

	@Override
	public void onEvent(Event e) {

	}
}
