package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import models.TexturedModel;

public class StaticEntity extends Entity {
	
	private TexturedModel model;
	
	
	public StaticEntity(TexturedModel model) {
		this.model = model;
	}
	
	public StaticEntity(TexturedModel model,Vector3f position, Vector3f rotation) {
		this.model = model;
		this.setPosition(position.x, position.y, position.z);
		this.setRotation(rotation.x, rotation.y, rotation.z);
	}
	
	public TexturedModel getModel() {
		return model;
	}
	
	public Matrix4f getModelMatrix() {
		//generate the model matrix
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(this.getPosition());
		matrix.rotate((float) Math.toRadians(this.getRotation().z),new Vector3f(0,0,1));
		matrix.rotate((float) Math.toRadians(this.getRotation().x),new Vector3f(1,0,0));
		matrix.rotate((float) Math.toRadians(this.getRotation().y),new Vector3f(0,1,0));
		matrix.scale(this.getScale());
		return matrix;
	}
}
