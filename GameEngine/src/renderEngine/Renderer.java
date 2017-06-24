package renderEngine;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import models.TexturedModel;


public class Renderer {
	
	public static void render(TexturedModel model) {
		//bind the vao of the model
		glBindVertexArray(model.getModelID());
		//enable the vertex attributes
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		//activate the texture in texture bank 0
		glActiveTexture(GL_TEXTURE0);
		//Bind the model's texture
		glBindTexture(GL_TEXTURE_2D , model.getTextureID());
		//draw each triangle using the element array buffer
		glDrawElements(GL_TRIANGLES,model.getVertexCount(),GL_UNSIGNED_INT,0);
		//disable the attribute
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		//unbind the vao
		glBindVertexArray(0);
	}
	
}
