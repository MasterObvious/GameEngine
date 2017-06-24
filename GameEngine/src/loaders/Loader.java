package loaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import models.Mesh;
import textures.ModelTexture;


public class Loader {
	
	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> textures = new ArrayList<>();
	
	public Mesh loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
		//generate vao id and then bind that vao
		int vaoID = createVAO();
		//store the indices list in the element array buffer
		bindIndicesBuffer(indices);
		//store the positions into a vbo
		storeDataInVBO(0,3,positions);
		storeDataInVBO(1,2,textureCoords);
		//unbind the vao
		unbindVAO();
		//create a new mesh model
		return new Mesh(vaoID,indices.length);
	}
	
	public ModelTexture loadTexture(String fileName) {
		BufferedImage texture = null;
		//load the texture into a buffered image
		try {
			texture = TextureLoader.loadImage(new FileInputStream("res/" + fileName + ".png"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//if the image isn't found then use the default texture
		if (texture == null) {
			try {
				texture = TextureLoader.loadImage(new FileInputStream("res/DEFAULT.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("The DEFAULT texture was not found!");
			}
		}
		
		//generate a texture id
		int textureID = TextureLoader.getTextureID(texture);
		textures.add(textureID);
		//return a new model texture
		return new ModelTexture(textureID);
		
	}

	private int createVAO() {
		//generate a vao id
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		//bind the vao
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInVBO(int attributeNumber, int dimensions, float[] data) {
		//generate a vbo id
		int vboID = glGenBuffers();
		vbos.add(vboID);
		//bind this vbo as an array buffer
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		//create a float buffer for the data
		FloatBuffer buffer = arrayToBuffer(data);
		//put this data into a glBuffer
		glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
		//establish a pointer to this data with the relevant attribute number
		glVertexAttribPointer(attributeNumber,dimensions,GL11.GL_FLOAT,false,0,0);
		//unbind this buffer
		glBindBuffer(GL_ARRAY_BUFFER,0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		//generate a vbo id
		int vboID = glGenBuffers();
		//bind this vbo as an element array buffer
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboID);
		//put the indicies into an int buffer
		IntBuffer buffer = arrayToBuffer(indices);
		//put this data into a glbuffer
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
		//unbind the buffer
		glBindBuffer(GL_ARRAY_BUFFER,0);
	}
	
	private FloatBuffer arrayToBuffer(float[] data) {
		//create a float buffer with the same length as the data
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		//put the data into the buffer
		buffer.put(data);
		//flip the buffer from write to read mode
		buffer.flip();
		return buffer;
		
	}
	
	private IntBuffer arrayToBuffer(int[] data) {
		//create a float buffer with the same length as the data
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		//put the data into the buffer
		buffer.put(data);
		//flip the buffer from write to read mode
		buffer.flip();
		return buffer;
	}
	
	private void unbindVAO() {
		//unbind the vao
		glBindVertexArray(0);
	}
	
	public void cleanUp() {
		for (int i : vaos) {
			glDeleteVertexArrays(i);
		}
		
		for (int i : vbos) {
			glDeleteBuffers(i);
		}
		
		for (int i : textures) {
			glDeleteTextures(i);
		}
	}

}
