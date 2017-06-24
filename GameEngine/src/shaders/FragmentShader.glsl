#version 400 core

in vec2 frag_textureCoords;

out vec4 out_colour;

uniform sampler2D textureSampler;

void main(void) {
	
	out_colour = texture(textureSampler,frag_textureCoords);
	
}