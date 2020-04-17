
package com.Game.Objects;

import com.Game.Framework.GameObject;
import com.Game.Framework.ObjectId;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Coin extends GameObject{
    private float width, height;
    
    public Coin(int x, int y,int width,int heiht, ObjectId id) 
    {
        super(x, y, id);
        this.width = width;
        this.height = heiht;
    }

    public void render(Graphics g) 
    {
        g.setColor(Color.YELLOW);
        g.fillRect((int )x,(int) y,(int) width,(int) height);
    }

    public Rectangle getBounds() 
    {
        return new Rectangle((int )x,(int) y,(int) width,(int) height);
    }
    public void tick(LinkedList<GameObject> objects) 
    {
        
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
