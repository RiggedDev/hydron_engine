package com.hydron.Core;

import com.hydron.Inputs.KeyInput;
import com.hydron.Inputs.MouseInput;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    public int width;
    public int height;
    public String title;

    public static long window;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void initialize(){

        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

    }

    public void LaunchWindow(){
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new IllegalStateException("Failed to create the GLFW window");

        glfwSetCursorPosCallback(window, MouseInput::mousePositionCallback);
        glfwSetMouseButtonCallback(window, MouseInput::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseInput::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyInput::keyCallback);

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();
    }

}
