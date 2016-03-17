package old.com.nowabwagel.engine.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import old.com.nowabwagel.engine.core.math.Vector3f;

public class ResourceLoader {
	public static final String SHADER_LOCATION = "./res/shaders/";
	public static final String MESH_LOCATION = "./res/obj/";

	enum FileType {
		OBJ_FILE
	}

	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader;

		try {
			shaderReader = new BufferedReader(new FileReader(SHADER_LOCATION
					+ fileName));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		return shaderSource.toString();
	}

	public static Mesh loadMesh(String fileName, FileType type) {
		Mesh mesh = new Mesh();

		if (type == FileType.OBJ_FILE)
			return loadOBJ(fileName);

		return null;
	}

	private static Mesh loadOBJ(String fileName) {
		Mesh mesh = new Mesh();

		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		BufferedReader meshReader;
		try {
			meshReader = new BufferedReader(new FileReader(MESH_LOCATION
					+ fileName));
			String line;

			while ((line = meshReader.readLine()) != null) {
				String[] tokens = line.split(" ");

				if (tokens.length == 0 || tokens[0].equals("#"))
					continue;

				if (tokens[0].equals("v")) {
					positions.add(new Vector3f(Float.valueOf(tokens[1]), Float
							.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"));//FIXME: lol i broken

			}

			meshReader.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		return mesh;
	}
}
