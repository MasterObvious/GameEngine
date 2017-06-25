package shaders;

public class EntityShader extends ShaderProgram {
	
	public static final String vertexLocation = "src/shaders/VertexShader.glsl";
	public static final String fragmentLocation = "src/shaders/FragmentShader.glsl";
	
	public static int MVP_MATRIX;
	
	public EntityShader() {
		//create the shader
		super(vertexLocation,fragmentLocation);
	}
	
	@Override
	public void bindAttributes() {
		//bind the position
		bindAttribute(0,"position");
		//bind the texture coordinates
		bindAttribute(1,"textureCoords");
	}

	@Override
	public void getAllUniformLocations() {
		//load the mvp location
		MVP_MATRIX = super.getUniformLocation("modelViewProjection");
	}

}
