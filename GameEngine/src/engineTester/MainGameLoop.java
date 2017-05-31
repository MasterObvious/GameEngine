package engineTester;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;



public class MainGameLoop {

	private long window;
	
	
	public void run() {
		System.out.println("Using LWJGL " + Version.getVersion());
		
		init();
		loop();
		kill();
		
	}
	
	private void init() {
		//Setup an error callback.
		//This will print any error message in System.err
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Initialise GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialise GLFW");
		}
		
		//Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		//Create the window
		window = glfwCreateWindow(1280, 720, "First OpenGL", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		//Setup a key callback. It will be called every time a key is pressed, repeated or released
		glfwSetKeyCallback(window,(window,key,scancode,action,mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window,true);
			}
		});
		
		//Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(2);
			
			//Get the window size passed to glfwCreateWindow()
			glfwGetWindowSize(window,pWidth,pHeight);
			
			//Get the resolution of primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			//Centre the window
			glfwSetWindowPos(window, (vidmode.width() -pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
			
		} //the stack frame is popped automatically
		
		//Make the OpenGL context current
		glfwMakeContextCurrent(window);
		//Enable v-sync
		glfwSwapInterval(1);
		
		//Make the window visible
		glfwShowWindow(window);
		
	}
	
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	
	private void kill() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	
	public static void main(String args[]) {
		new MainGameLoop().run();
	}
	
}
