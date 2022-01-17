package com.hydron.Shaders;

import com.hydron.Utils.Utility;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.Scanner;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public int vertexShaderID;
    public int fragmentShaderID;
    public int shaderProgramID;

    public String vertexShaderPath;
    public String fragmentShaderPath;

    public boolean alreadyUsed = false;

    public Shader(String vertexShaderPath, String fragmentShaderPath){
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
    }

    public void compile(){
        compileShaders();
        compileShaderProgram();
    }

    public void compileShaderProgram(){
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexShaderID);
        glAttachShader(shaderProgramID, fragmentShaderID);
        glLinkProgram(shaderProgramID);
        glValidateProgram(shaderProgramID);

        int result = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(result == GL_FALSE){
            int length = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("Shader Program Linking Failed On You");
            System.out.println(glGetProgramInfoLog(shaderProgramID, length));
            assert false : "";
        }

    }

    public void compileShaders(){
        fragmentShaderID = CreateShader(LoadShaderContents(vertexShaderPath), GL_VERTEX_SHADER, "Vertex Shader Compilation Failed");
        vertexShaderID = CreateShader(LoadShaderContents(fragmentShaderPath), GL_FRAGMENT_SHADER, "Fragment Shader Compilation Failed");
    }

    public int CreateShader(String shader, int shaderType, String errorMessage){
        int shaderID = glCreateShader(shaderType);

        glShaderSource(shaderID, shader);
        glCompileShader(shaderID);

        int result = glGetShaderi(shaderID, GL_COMPILE_STATUS);

        if(result == GL_FALSE){
            int length = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
            System.out.println(errorMessage);
            System.out.println(glGetShaderInfoLog(shaderID, length));
            assert false : "";
        }

        return shaderID;

    }

    public void useShader(){
        if(!alreadyUsed) {
            glUseProgram(shaderProgramID);
            alreadyUsed = true;
        }
    }

    public void loadInteger(String name, int value){
        int location = getUniformLocation(name);
        GL20.glUniform1i(location, value);
    }

    public void loadFloat(String name, float value){
        int location = getUniformLocation(name);
        GL20.glUniform1f(location, value);
    }

    public void loadVector2f(String name, Vector2f value){
        int location = getUniformLocation(name);
        GL20.glUniform2f(location, value.x, value.y);
    }

    public void loadVector3f(String name, Vector3f value){
        int location = getUniformLocation(name);
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    public void loadVector4f(String name, Vector4f value){
        int location = getUniformLocation(name);
        GL20.glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void loadBoolean(String name, boolean value){
        int location = getUniformLocation(name);
        float loadValue = 0;
        if(value)
            loadValue = 1;

        GL20.glUniform1f(location, loadValue);
    }

    public void loadMatrix4f(String name, Matrix4f value){
        int location = getUniformLocation(name);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        value.get(matrixBuffer);
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    private int getUniformLocation(String name){
        useShader();
        return GL20.glGetUniformLocation(shaderProgramID, name);
    }

    public void loadTexture(String name, int slot){
        int location = getUniformLocation(name);
        glUniform1i(location, slot);
    }

    public void stop(){
        glUseProgram(0);
        alreadyUsed = false;
    }

    private String LoadShaderContents(String shaderFilePath){
        StringBuilder contents = new StringBuilder();
        File shaderFile = new File(shaderFilePath);
        try {
            Scanner shaderReader = new Scanner(shaderFile);

            while(shaderReader.hasNext()){
                contents.append(shaderReader.nextLine()).append("\n");
            }

            shaderReader.close();

        } catch (FileNotFoundException e) {
            System.err.println("File was not found.");
            System.err.println(shaderFile.getAbsolutePath());
            System.exit(-1);
        }
        return contents.toString();
    }

}
