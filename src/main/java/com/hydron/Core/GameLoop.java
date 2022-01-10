package com.hydron.Core;

import com.hydron.Renderering.Renderer;
import com.hydron.Utils.TimeUtils;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

public class GameLoop{

    public double updates = 1.0d/60.0d;
    public boolean loaded = false;
    private int displayFps;
    private int displayUps;
    public int fps;
    public int fixedUps;
    public int ups;

    private Renderer renderer;

    public GameLoop(Renderer renderer){
        this.renderer = renderer;
    }

    public void startGameLoop() {
        loop();
    }

    public void loop(){

        double lastTime = System.nanoTime();
        double nextFixedUpdateTime = 0.0;
        double time = System.currentTimeMillis() + 1000;
        while(!glfwWindowShouldClose(Window.window)){
            double currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime);
            double deltaTimeInSeconds = deltaTime / 1000000000.0d;
            TimeUtils.deltaTime = (float) (deltaTimeInSeconds);
            nextFixedUpdateTime += deltaTimeInSeconds;
            doWindowEvents();
            while (nextFixedUpdateTime >= updates)
            {
                renderer.fixedUpdate();
                nextFixedUpdateTime -= updates;
            }
            renderer.update();
            if (System.currentTimeMillis() >= time){
                displayFps = fps;
                displayUps = fixedUps;
                fps = 0;
                ups++;
                fixedUps = 0;
                time += 1000;
            }
            renderer.render();
            lastTime = currentTime;
        }
        glfwFreeCallbacks(Window.window);
        glfwDestroyWindow(Window.window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void doWindowEvents(){
        glfwPollEvents();
        glClearColor( (30f/255f), (30f/255f), (30f/255f), 1.0f);

        glClear(GL_COLOR_BUFFER_BIT);

        glfwSwapBuffers(Window.window);
    }




}
