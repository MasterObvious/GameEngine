package entities;

import org.joml.Vector3f;

public abstract class Entity {
	
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f rotation = new Vector3f(0,0,0);
	private float scale = 1;
	
	
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(float x, float y, float z) {
		position = new Vector3f(x,y,z);
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(float x, float y, float z) {
		rotation = new Vector3f(x,y,z);
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public void incrementPosition(float dx, float dy, float dz) {
		position.add(new Vector3f(dx,dy,dz));
	}
	public void incrementRotation(float dx, float dy, float dz) {
		rotation.add(new Vector3f(dx,dy,dz));
	}
	
	

}
