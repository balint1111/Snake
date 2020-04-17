/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Game.Window;

import com.Game.Framework.GameObject;
import com.Game.Framework.ObjectId;
import com.Game.Objects.Block;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Balint
 */
public class Handler {
    public LinkedList<GameObject> objects = new LinkedList<GameObject>();
    public LinkedList<GameObject> snakeParts = new LinkedList<GameObject>();
    
    private GameObject tempObject;
    private GameObject player;
    
    public void tick()
    {
        for(int i = 0; i < objects.size() ; i++)
        {
            tempObject = objects.get(i);
            
            tempObject.tick(objects);
        }
         for(int i = 0; i < snakeParts.size() ; i++)
        {
            tempObject = snakeParts.get(i);
            
            tempObject.tick(snakeParts);
        }
    }
    public void render(Graphics g)
    {
        for(int i = 0; i < objects.size() ; i++)
        {
            tempObject = objects.get(i);
            if(tempObject.getId() != ObjectId.Player)
            {
                tempObject.render(g);
            }
            else
            {
                player = tempObject;
            }
        }
        
        for(int i = 0; i < snakeParts.size() ; i++)
        {
            tempObject = snakeParts.get(i);
            
            tempObject.render(g);

        }
        player.render(g);
    }
    public void addObject(GameObject object)
    {
        this.objects.add(object);
    }
    public void removeObject(GameObject object)
    {
        this.objects.remove(object);
    }
    public void addSnakePart(GameObject snakePart)
    {
        this.snakeParts.add(snakePart);
    }
    public void removeSnakePart(GameObject snakePart)
    {
        this.snakeParts.remove(snakePart);
    }
    public void createLevel()
    {
        Integer blockWidth=30, blockHeight=20;
        //alsó
        for(int x=0; x < Game.WIDTH; x+=blockWidth)
        {
        addObject(new Block( x, Game.HEIGHT-blockHeight, blockWidth, blockHeight, ObjectId.Block));
        }
        //felső
        for(int x=0; x < Game.WIDTH; x+=blockWidth)
        {
            addObject(new Block( x, 0, blockWidth, blockHeight, ObjectId.Block));
        }
        //////90 fokos forgatással
        int temp;
        temp=blockWidth;
        blockWidth = blockHeight;
        blockHeight = temp;
        ///////
        //bal
        for(int x=blockWidth; x < Game.HEIGHT-blockWidth; x+=blockHeight)
        {
            addObject(new Block( 0, x, blockWidth, blockHeight, ObjectId.Block));
        }
        //jobb belső
        for(int x=blockWidth; x < Game.HEIGHT-blockWidth; x+=blockHeight)
        {
            addObject(new Block( Game.WIDTH-blockWidth-200, x, blockWidth, blockHeight, ObjectId.Block));
        }
        //jobb szélső
        for(int x=blockWidth; x < Game.HEIGHT-blockWidth; x+=blockHeight)
        {
            addObject(new Block( Game.WIDTH-blockWidth, x, blockWidth, blockHeight, ObjectId.Block));
        }
    }
}
