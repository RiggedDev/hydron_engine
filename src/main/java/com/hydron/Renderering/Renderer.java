package com.hydron.Renderering;

import com.hydron.Data.Texture;
import com.hydron.Shaders.Shader;
import com.hydron.Utils.TimeUtils;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Renderer {

    public Shader shader;
    public Texture texture;

    private float[] verticesArray = {
            100f, 0f,   0.0f,
            0f,   100f, 0.0f,
            100f, 100f, 0.0f,
            0f,   0f,   0.0f
    };

    private float[] textureCoordinates = {
            1, 1,
            0, 0,
            1, 0,
            0, 1
    };

    private int[] indicesArray = {
            2, 1, 0,
            0, 1, 3
    };

    public int vaoID;
    public int vboID;
    public int vboID1;
    public int eboID;

    public Camera camera;

    public Renderer(){

        camera = new Camera(new Vector2f(0.0f, 0.0f));

        shader = new Shader("Resources\\Shaders\\vertexShader.vert", "Resources\\Shaders\\fragmentShader.frag");
        shader.compile();

        texture = new Texture("Resources\\Textures\\diamond_block.png");

        setupQuad();
    }

    public void setupQuad(){
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(verticesArray.length);
        vertexBuffer.put(verticesArray).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(textureCoordinates.length);
        textureBuffer.put(textureCoordinates).flip();

        vboID1 = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID1);
        glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indicesArray.length);
        elementBuffer.put(indicesArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesArray, GL_STATIC_DRAW);

        int positionSize = 3;
        int textureCoordinatesSize = 2;
        int verticesSizeBytes = positionSize * Float.BYTES;
        int textureCoordinatesSizeBytes = textureCoordinatesSize * Float.BYTES;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, verticesSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, textureCoordinatesSize, GL_FLOAT, false, textureCoordinatesSizeBytes, 0);
        glEnableVertexAttribArray(1);

    }

    public void render(){
        shader.useShader();
        glActiveTexture(GL_TEXTURE0);
        shader.loadTexture("textureSampler", 0);
        texture.bindTexture();
        shader.loadMatrix4f("projectionMatrix", camera.getProjectionMatrix());
        shader.loadMatrix4f("viewMatrix", camera.getViewMatrix());
        shader.loadFloat("time", TimeUtils.time);
        shader.loadFloat("deltaTime", TimeUtils.deltaTime);

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
        camera.update();
    }

    public void fixedUpdate(){

    }

}
