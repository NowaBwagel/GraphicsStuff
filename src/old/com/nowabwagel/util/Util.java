package old.com.nowabwagel.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import old.com.nowabwagel.engine.core.Vertex;
import old.com.nowabwagel.engine.core.math.Matrix4f;

import org.lwjgl.BufferUtils;

public class Util {
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
		}
		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				buffer.put(value.get(i, j));
			}
		}

		buffer.flip();

		return buffer;
	}

	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);

		for (int v : values)
			buffer.put(v);
		buffer.flip();
		
		return buffer;
	}
}
