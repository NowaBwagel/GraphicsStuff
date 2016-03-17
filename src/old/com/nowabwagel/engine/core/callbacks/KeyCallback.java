package old.com.nowabwagel.engine.core.callbacks;

import old.com.nowabwagel.engine.core.events.types.KeyEvent;
import old.com.nowabwagel.util.FiFoBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyCallback extends GLFWKeyCallback {
	// TODO: Im static
	
	public static FiFoBuffer<KeyEvent> fifoKeyEvents = new FiFoBuffer<KeyEvent>(KeyEvent.class, 32);

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		fifoKeyEvents.add(new KeyEvent(window, key, scancode, action, mods));
	}
}
