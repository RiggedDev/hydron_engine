package com.hydron.Data;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    private String path;

    private int textureID;

    public Texture(String filepath){
        this.path = filepath;

        createTexture();

    }

    private void createTexture(){
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(path, width, height, channels, 0);

        if(image != null){
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            System.out.println("Width: " + width.get(0));
            System.out.println("Height: " + height.get(0));
        }
        else{
            assert false : "Couldn't Load Image";
        }

        stbi_image_free(image);

    }

    public void bindTexture(){
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public String getTexturePath() {
        return path;
    }

    public String getAbsoluteTexturePath(){
        return new File(path).getAbsolutePath();
    }

    public File getTextureFile(){
        return new File(path);
    }

    public int getTextureID(){
        return textureID;
    }

}
