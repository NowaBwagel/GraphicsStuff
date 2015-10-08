package com.nowabwagel.engine.core.callbacks;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.nowabwagel.engine.core.input.events.KeyEvent;
import com.nowabwagel.util.FiFoBuffer;

public class KeyCallback extends GLFWKeyCallback {
	// TODO: Im static
	
	public static FiFoBuffer<KeyEvent> fifoKeyEvents = new FiFoBuffer<KeyEvent>(32);

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		fifoKeyEvents.add(new KeyEvent(window, key, scancode, action, mods));
	}
}
