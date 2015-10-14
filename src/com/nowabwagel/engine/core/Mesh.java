package com.nowabwagel.engine.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.nowabwagel.util.Util;

public class Mesh {
	// Pointer for OpenGL, vertex buffer object
	private int vbo;
	// Pointer for OpenGL, index buffer object
	private int ibo;
	// How much data in pointer
	private int size;

	public Mesh() {
		vbo = GL15.glGenBuffers();
		ibo = GL15.glGenBuffers();
		size = 0;
	}

	public void addVertices(Vertex[] vertices, int[] indices) {
		size = indices.length;// * Vertex.SIZE;

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL15.GL_STATIC_DRAW);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}

	public void draw() {
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 0);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
	}
}
