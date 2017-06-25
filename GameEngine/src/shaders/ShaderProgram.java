package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		//load the vertex shader
		vertexShaderID = loadShader(vertexFile,GL_VERTEX_SHADER);
		//load the fragment shader
		fragmentShaderID = loadShader(fragmentFile,GL_FRAGMENT_SHADER);
		//create a program
		programID = glCreateProgram();
		//bind both shaders
		glAttachShader(programID,vertexShaderID);
		glAttachShader(programID,fragmentShaderID);
		//bind the attributes
		bindAttributes();
		//link and validate the program
		glLinkProgram(programID);
		glValidateProgram(programID);
		//get all uniform locations
		getAllUniformLocations();
	}
	
	public abstract void bindAttributes();
	
	public abstract void getAllUniformLocations();
	
	public int getUniformLocation(String uniformName) {
		//get a specific location of a uniform
		return glGetUniformLocation(programID,uniformName);
	}
	
	
	public void loadUniform(int location, Matrix4f matrix) {
		//loads a 4x4 matrix into the specified locatiton
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	public void bindAttribute(int attributeNumber, String variableName) {
		//associate an attribute number with a variable name
		glBindAttribLocation(programID,attributeNumber,variableName);
	}
	
	public void start() {
		//start the program
		glUseProgram(programID);
	}
	
	public void stop() {
		//stop the program
		glUseProgram(0);
	}
	
	public void cleanUp() {
		//detach and delete shaders
		glDetachShader(programID,vertexShaderID);
		glDetachShader(programID,fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		//delete the program
		glDeleteProgram(programID);
	}
	
	
	private int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		
		//try and read the file line by line
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				//this ensures there's a return at the end of every line
				shaderSource.append(line).append("\n");
			}
			reader.close();
			
		}catch(IOException e){
			System.err.println("Could not read shader file");
			e.printStackTrace();
			System.exit(-1);
		}
		
		//create the shader
		int shaderID = glCreateShader(type);
		//bind the shader source code
		glShaderSource(shaderID, shaderSource);
		//compile the shader
		glCompileShader(shaderID);
		
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			//print out any errors during compile time
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader");
			System.exit(-1);
		}
		
		return shaderID;
	}

}
