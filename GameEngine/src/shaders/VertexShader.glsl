#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 frag_textureCoords;

uniform mat4 modelViewProjection;

void main(void) {
	
	gl_Position = modelViewProjection * vec4(position,1.0);
	
	frag_textureCoords = textureCoords;
}