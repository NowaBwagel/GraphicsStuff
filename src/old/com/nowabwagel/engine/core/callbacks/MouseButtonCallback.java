package old.com.nowabwagel.engine.core.callbacks;

import old.com.nowabwagel.engine.core.events.types.MouseEvent;
import old.com.nowabwagel.util.FiFoBuffer;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonCallback extends GLFWMouseButtonCallback {
	// TODO: Im static
	public static FiFoBuffer<MouseEvent> fifoBuffer = new FiFoBuffer<MouseEvent>(MouseEvent.class, 16);

	@Override
	public void invoke(long window, int button, int action, int mods) {
		fifoBuffer.add(new MouseEvent(window, button, action, mods));
	}
}
