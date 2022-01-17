#version 330 core

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoordinates;

out vec2 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){
    textureCoords = textureCoordinates;
    gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);
}