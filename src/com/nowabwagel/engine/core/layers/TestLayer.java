package com.nowabwagel.engine.core.layers;

import org.lwjgl.opengl.GL11;

public class TestLayer extends Layer {
	@Override
	public void onRender() {
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4f(1f, 0f, 0f, 1f);
		GL11.glVertex2f(-1f, -1f);
		GL11.glColor4f(0f, 1f, 0f, 1f);
		GL11.glVertex2f(0f, 1f);
		GL11.glColor4f(0f, 0f, 1f, 1f);
		GL11.glVertex2f(1f, -1f);
		GL11.glEnd();
	}
}
