#version 400 core

in vec3 frag_colour;

out vec4 out_colour;

void main(void) {
	
	out_colour = vec4(frag_colour,1.0);
}