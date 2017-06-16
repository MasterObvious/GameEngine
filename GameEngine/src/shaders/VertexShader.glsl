#version 400 core

in vec3 position;

out vec3 frag_colour;

void main(void) {
	
	gl_Position = vec4(position,1.0);
	
	frag_colour = vec3(position.x + 0.5, 1, position.y + 0.5);
}