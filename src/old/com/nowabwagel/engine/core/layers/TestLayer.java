package old.com.nowabwagel.engine.core.layers;

import old.com.nowabwagel.engine.core.Mesh;
import old.com.nowabwagel.engine.core.ResourceLoader;
import old.com.nowabwagel.engine.core.Shader;
import old.com.nowabwagel.engine.core.Time;
import old.com.nowabwagel.engine.core.Transform;
import old.com.nowabwagel.engine.core.Vertex;
import old.com.nowabwagel.engine.core.events.Event;
import old.com.nowabwagel.engine.core.math.Vector3f;

public class TestLayer extends Layer {
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Vector3f color;
	private float time;

	public TestLayer(Vector3f color, float time) {
		this.color = color;
		this.time = time;
		mesh = new Mesh();

		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)),
				new Vertex(new Vector3f(0, 1, 0)),
				new Vertex(new Vector3f(1, -1, 0)),
				new Vertex(new Vector3f(0, -1, 1)) };

		int[] indices = new int[] { 0, 1, 3, 3, 1, 2, 2, 1, 0, 0, 2, 3 };

		mesh.addVertices(vertices, indices);

		shader = new Shader();

		transform = new Transform();

		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();

		shader.addUniform("transform");
		shader.addUniform("unicolor");
	}

	@Override
	public void onRender() {
		shader.bind();
		postShaderBindUpdate();
		mesh.draw();
	}

	float temp = 0.0f;

	public void onUpdate(float delta) {
		temp += Time.getDelta() * time;

		transform.setTranslation((float) Math.sin(temp), 0, 0);
		//transform.setRotaion(0, -(float) Math.sin(temp) * 180, 0);
		// transform.setScale((float) Math.sin(temp), (float) Math.sin(temp),
		// (float) Math.sin(temp));
	}

	private void postShaderBindUpdate() {
		shader.setUniform("transform", transform.getTransformation());
		shader.setUniform("unicolor", color);
	}

	@Override
	public void onEvent(Event e) {

	}
}
