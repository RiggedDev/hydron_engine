package com.hydron.Inputs;

import org.joml.Math;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseInput {

    private static final boolean[] pressedButtons = new boolean[5];
    private static final Vector2f scrollAmount = new Vector2f(0);
    public static Vector2f position = new Vector2f(0);
    private static final Vector2f lastPosition = new Vector2f(0);
    private static boolean mouseDragging = false;

    public static void mousePositionCallback(long window, double xpos, double ypos){
        lastPosition.x = position.x;
        lastPosition.y = position.y;
        position.x = (float) (xpos);
        position.y = (float) (ypos);
        mouseDragging = pressedButtons[0] || pressedButtons[1] || pressedButtons[2] || pressedButtons[3] || pressedButtons[4];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        int limitedButton = Math.clamp(button, 0, pressedButtons.length);
        if(action == GLFW_PRESS) {
            pressedButtons[limitedButton] = true;
        }
        else if(action == GLFW_RELEASE) {
            pressedButtons[limitedButton] = true;
            mouseDragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        scrollAmount.x = (float) xOffset;
        scrollAmount.y = (float) yOffset;
    }

    public static void frameEnd(){

        scrollAmount.x = 0;
        scrollAmount.y = 0;

        lastPosition.x = position.x;
        lastPosition.y = position.y;

    }

    public static Vector2f getDrag(){
        return new Vector2f(lastPosition.x - position.x, lastPosition.y - position.y);
    }

    public static Vector2f getScrollAmount(){
        return scrollAmount;
    }

    public static boolean isMouseDragging(){
        return mouseDragging;
    }

    public static boolean MouseButtonPressed(int button){
        return pressedButtons[Math.clamp(button, 0, pressedButtons.length)];
    }

}
