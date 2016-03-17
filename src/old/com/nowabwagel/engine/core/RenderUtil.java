package old.com.nowabwagel.engine.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class RenderUtil {
	public static void clear() {
		// TODO: Stencil Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public static void initGraphics() {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		GL11.glFrontFace(GL11.GL_CW);
		GL11.glCullFace(GL11.GL_BACK);
		//GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		//TODO: Depth Clamp
		
		GL11.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
	}
	
	public static String getOpenGLVersion(){
		return GL11.glGetString(GL11.GL_VERSION);
	}
}
