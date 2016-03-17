package old.com.nowabwagel.engine.core;

import old.com.nowabwagel.engine.core.math.Vector3f;

public class Vertex {
	// Will tell how many integers are held in the class (go GPU knows how much data in it)
	public static final int SIZE = 3;
	//!!!!!!!!!!!!!!!!!!!!! IF MORE DATA IS ADDED TO THIS CLASS CHANGE METHOD IN UTIL CLASS
	// createFlippedBuffer()
	private Vector3f pos;
	
	public Vertex(Vector3f pos){
		this.pos = pos;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
}
