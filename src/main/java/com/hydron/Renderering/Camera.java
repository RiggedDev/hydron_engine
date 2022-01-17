package com.hydron.Renderering;

import com.hydron.Inputs.KeyInput;
import com.hydron.Inputs.MouseInput;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.event.MouseEvent;

public class Camera {

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Vector2f position;

    private float previousX, previousY;

    public Camera(Vector2f position){
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection(){
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f ,-1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();

        viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f), cameraUp);

        return viewMatrix;

    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }

    public void update(){
        if (MouseInput.MouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_3)) {
            float dx = (MouseInput.position.x + MouseInput.getDrag().x - previousX);
            float dy = (MouseInput.position.y + MouseInput.getDrag().y - previousY);
            position.x -= dx / 2;
            position.y += dy / 2;
        }
        previousX = MouseInput.position.x + MouseInput.getDrag().x;
        previousY = MouseInput.position.y + MouseInput.getDrag().y;

    }

}
