package models;

import textures.ModelTexture;

public class TexturedModel {
	
	private Mesh modelMesh;
	private ModelTexture modelTexture;
	
	public TexturedModel(Mesh mesh, ModelTexture texture) {
		modelMesh = mesh;
		modelTexture = texture;
	}
	
	public int getModelID() {
		return modelMesh.getVaoID();
	}
	
	public int getVertexCount() {
		return modelMesh.getVertexCount();
	}
	
	public int getTextureID() {
		return modelTexture.getTextureID();
	}

}
