package loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
	
	//Allows RGBA 
	public static final int BYTES_PER_PIXEL = 4;
	
	public static int getTextureID(BufferedImage image) {
		
		//create an array for the image
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		//create a buffer for each byte
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);
		
		//put the array data into the byte
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				 buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                 buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                 buffer.put((byte) (pixel & 0xFF));               // Blue component
                 buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}
		
		//put the buffer into read mode
		buffer.flip();
		
		int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
        //Unbind the texture
        glBindTexture(GL_TEXTURE_2D, textureID);
        //Return the texture ID so we can bind it later again
        return textureID;
      
   }
	
	public static BufferedImage loadImage(FileInputStream f) {
		//load a file into a buffered image
		try {
			return ImageIO.read(f);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
		
}

