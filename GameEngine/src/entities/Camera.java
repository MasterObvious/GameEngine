package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Entity {
	
	private float FOV;
	private float NEAR_PLANE;
	private float FAR_PLANE;
	
	public Camera() {
		//use default values for the FOV and planes
		FOV = 45;
		NEAR_PLANE = 0;
		FAR_PLANE = 10000;
	}
	
	public Camera(float fov, float near, float far) {
		//use cutom values
		FOV = fov;
		NEAR_PLANE = near;
		FAR_PLANE = far;
	}
	
	public Matrix4f getProjectionMatrix(int width, int height) {
		//generate the projection matrix
		Matrix4f matrix = new Matrix4f();
		float aspectRatio = (float) width / height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;
		
		matrix.m00(x_scale);
		matrix.m11(y_scale);
		matrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustrum_length));
		matrix.m23(-1);
		matrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustrum_length));
		matrix.m33(0);
		
		return matrix;
	}
	
	public Matrix4f getViewMatrix() {
		//generate the view matrix
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.scale(1 / this.getScale());
		matrix.rotate((float) -Math.toRadians(this.getRotation().y),new Vector3f(0,1,0));
		matrix.rotate((float) -Math.toRadians(this.getRotation().x),new Vector3f(1,0,0));
		matrix.rotate((float) -Math.toRadians(this.getRotation().z),new Vector3f(0,0,1));
		matrix.translate(-this.getPosition().x,-this.getPosition().y,-this.getPosition().z);
		
		return matrix;
	}
	
}
