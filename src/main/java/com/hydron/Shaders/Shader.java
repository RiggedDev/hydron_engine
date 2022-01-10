package com.hydron.Shaders;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public String vertexShader = "#version 330 core\n" +
            "\n" +
            "layout (location=0) in vec3 position;\n" +
            "layout (location=1) in vec4 color;\n" +
            "out vec4 fragment_color;\n" +
            "\n" +
            "void main(void){\n" +
            "    fragment_color = color;\n" +
            "    gl_Position = vec4(position, 1.0);\n" +
            "}";

    public String fragmentShader = "#version 330 core\n" +
            "\n" +
            "in vec4 fragment_color;\n" +
            "out vec4 output_color;\n" +
            "\n" +
            "void main(void){\n" +
            "    output_color = fragment_color;\n" +
            "}";

    public int vertexShaderID;
    public int fragmentShaderID;
    public int shaderProgramID;

    public void compile(){
        compileShaders();
        compileShaderProgram();
    }

    private void compileShaderProgram(){
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexShaderID);
        glAttachShader(shaderProgramID, fragmentShaderID);
        glLinkProgram(shaderProgramID);

        int result = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(result == GL_FALSE){
            int length = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("Shader Program Linking Failed On You");
            System.out.println(glGetProgramInfoLog(shaderProgramID, length));
            assert false : "";
        }

    }

    private void compileShaders(){
        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexShaderID, vertexShader);
        glCompileShader(vertexShaderID);

        int result = glGetShaderi(vertexShaderID, GL_COMPILE_STATUS);
        if(result == GL_FALSE){
            int length = glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH);
            System.out.println("Vertex Shader Compilation Failed On You");
            System.out.println(glGetShaderInfoLog(vertexShaderID, length));
            assert false : "";
        }

        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentShaderID, fragmentShader);
        glCompileShader(fragmentShaderID);

        result = glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS);
        if(result == GL_FALSE){
            int length = glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH);
            System.out.println("Fragment Shader Compilation Failed On You");
            System.out.println(glGetShaderInfoLog(fragmentShaderID, length));
            assert false : "";
        }

    }

    public void useShader(){
        glUseProgram(shaderProgramID);
    }

    public void stop(){
        glUseProgram(0);
    }

}
