package old.com.nowabwagel.engine.core.callbacks;

import old.com.nowabwagel.engine.core.math.Vector2f;

import org.lwjgl.glfw.GLFWWindowPosCallback;

public class WindowPosCallback extends GLFWWindowPosCallback {
	// TODO: Im static
	private static boolean moved = false;
	private static Vector2f pos = new Vector2f();

	@Override
	public void invoke(long window, int xpos, int ypos) {
		moved = true;
		pos = new Vector2f(xpos, ypos);
	}

	public static boolean getMoved() {
		if (moved) {
			moved = false;
			return true;
		}
		return moved;
	}

	public static Vector2f getPos() {
		return pos;
	}

}
