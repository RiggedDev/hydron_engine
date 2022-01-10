package com.hydron.Renderering;

import com.hydron.Objects.Renderering.ObjectRenderer;
import com.hydron.Shaders.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Renderer {

    public Shader shader;

    private float[] vertexArray = {
             0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 0.0f,
            -0.5f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,
             0.5f,  0.5f, 0.0f,       1.0f, 0.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f,
    };

    private int[] indicesArray = {
            2, 1, 0,
            0, 1, 3
    };

    public int vaoID;
    public int vboID;
    public int eboID;

    public Renderer(){

        shader = new Shader();
        shader.compile();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indicesArray.length);
        elementBuffer.put(indicesArray).flip();
        eboID = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesArray, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

    }

    public void render(){
        shader.useShader();

        glBindVertexArray(vaoID);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, indicesArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        shader.stop();
    }

    public void update(){


    }

    public void fixedUpdate(){

    }

}
