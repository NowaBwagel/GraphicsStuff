package com.nowabwagel.engine.core;

import java.io.BufferedReader;
import java.io.FileReader;

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
			shaderReader = new BufferedReader(new FileReader(SHADER_LOCATION + fileName));
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
		BufferedReader meshReader;
		try {
			meshReader = new BufferedReader(new FileReader(MESH_LOCATION + fileName));
			String line;
			
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		return mesh;
	}
}
