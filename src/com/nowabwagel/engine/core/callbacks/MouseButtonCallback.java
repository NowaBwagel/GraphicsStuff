package com.nowabwagel.engine.core.callbacks;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.nowabwagel.engine.core.events.types.MouseEvent;
import com.nowabwagel.util.FiFoBuffer;

public class MouseButtonCallback extends GLFWMouseButtonCallback {
	// TODO: Im static
	public static FiFoBuffer<MouseEvent> fifoBuffer = new FiFoBuffer<MouseEvent>(MouseEvent.class, 16);

	@Override
	public void invoke(long window, int button, int action, int mods) {
		fifoBuffer.add(new MouseEvent(window, button, action, mods));
	}
}
