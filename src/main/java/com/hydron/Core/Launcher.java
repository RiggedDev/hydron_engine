package com.hydron.Core;

import com.hydron.Renderering.Renderer;

public class Launcher {

    public static void main(String[] args){
        Window window = new Window(1280, 720, "Hydron Engine 2D");

        window.initialize();
        window.LaunchWindow();

        GameLoop gameLoop = new GameLoop(new Renderer());
        gameLoop.startGameLoop();

    }

}
