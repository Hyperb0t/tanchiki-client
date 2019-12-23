package ru.itis.tanchiki.client.window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TextureManager {

    public BufferedImage[] getWallTextures() {
        try {
             BufferedImage[] wallTextures = {
                    ImageIO.read(new File("textures/Wall.png")),
                    ImageIO.read(new File("textures/Wall2.png")),
                    ImageIO.read(new File("textures/Wall3.png"))
            };
             return wallTextures;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public BufferedImage getFloorTexture() {
        try {
            return ImageIO.read(new File("textures/Floor.png"));
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public BufferedImage[] getBlueTankTextures() {
        try {
            BufferedImage[] tankTextures = {
                    ImageIO.read(new File("textures/BlueTankUP.png")),
                    ImageIO.read(new File("textures/BlueTankDOWN.png")),
                    ImageIO.read(new File("textures/BlueTankLEFT.png")),
                    ImageIO.read(new File("textures/BlueTankRIGHT.png")),
                    ImageIO.read(new File("textures/DestroyedTankUP.png")),
                    ImageIO.read(new File("textures/DestroyedTankDOWN.png")),
                    ImageIO.read(new File("textures/DestroyedTankLEFT.png")),
                    ImageIO.read(new File("textures/DestroyedTankRIGHT.png"))
            };
            return tankTextures;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public BufferedImage[] getGreenTankTextures() {
        try {
            BufferedImage[] tankTextures = {
                    ImageIO.read(new File("textures/GreenTankUP.png")),
                    ImageIO.read(new File("textures/GreenTankDOWN.png")),
                    ImageIO.read(new File("textures/GreenTankLEFT.png")),
                    ImageIO.read(new File("textures/GreenTankRIGHT.png")),
                    ImageIO.read(new File("textures/DestroyedTankUP.png")),
                    ImageIO.read(new File("textures/DestroyedTankDOWN.png")),
                    ImageIO.read(new File("textures/DestroyedTankLEFT.png")),
                    ImageIO.read(new File("textures/DestroyedTankRIGHT.png"))
            };
            return tankTextures;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
