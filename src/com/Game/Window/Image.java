
package com.Game.Window;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
    private BufferedImage image;
    public Image(String imageDir)
    {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imageDir));
        }
        catch (Exception ex) 
        {
            System.out.println("A fájl nem található: " + imageDir);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String imageDir) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imageDir));
        }
        catch (Exception ex) 
        {
            System.out.println("A fájl nem található: " + imageDir);
        }
    }
    
}
