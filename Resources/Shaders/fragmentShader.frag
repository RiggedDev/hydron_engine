#version 330 core

in vec2 textureCoords;
out vec4 output_color;

uniform sampler2D textureSampler;

void main(void){
    output_color = texture(textureSampler, textureCoords);
}