#version 330 core

in vec4 fragment_color;
out vec4 output_color;

void main(void){
    output_color = fragment_color;
}