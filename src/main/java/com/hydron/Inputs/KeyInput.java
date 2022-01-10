package com.hydron.Inputs;

import org.joml.Math;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyInput {
    private static boolean[] pressedKeys = new boolean[400];
    private static boolean[] pressedKeySingle = new boolean[400];
    public static void keyCallback(long window, int key, int scancode, int action, int mods){

        if(action == GLFW_PRESS) {
            pressedKeys[key] = true;
            pressedKeySingle[key] = true;
        }
        else if(action == GLFW_RELEASE){
            pressedKeys[key] = false;
            pressedKeySingle[key] = false;
        }
    }

    public static boolean KeyPressed(int key){
        return pressedKeys[key];
    }

}
