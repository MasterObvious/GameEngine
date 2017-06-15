package renderEngine;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import models.Mesh;


public class Renderer {
	
	public static void render(Mesh model) {
		//bind the vao of the model
		glBindVertexArray(model.getVaoID());
		//enable the vertex attributes
		glEnableVertexAttribArray(0);
		//draw the array with the vertex count
		glDrawArrays(GL_TRIANGLES,0,model.getVertexCount());
		//dsiable the attribute
		glDisableVertexAttribArray(0);
		//unbind the vao
		glBindVertexArray(0);
	}
	
}
