package com.nowabwagel.engine.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.nowabwagel.engine.core.input.events.KeyEvent;
import com.nowabwagel.util.FIFOBuffer;

public class KeyboardHandler extends GLFWKeyCallback {

	public static boolean[] keyStates = new boolean[65536];

	public static FIFOBuffer<KeyEvent> fifoKeyEvents = new FIFOBuffer<KeyEvent>(32);

	// The GLFWKeyCallback class is an abstract method that
	// can't be instantiated by itself and must instead be extended
	//

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		fifoKeyEvents.add(new KeyEvent(window, key, scancode, action, mods));
		//keyStates[key] = action != GLFW.GLFW_RELEASE;
	}

	// boolean method that returns true if a given key
	// is pressed.
	public static boolean isKeyDown(int keycode) {
		return keyStates[keycode];
	}

	// FIXME: Like make it work with getPressed, isReleased and stuffs.
}
