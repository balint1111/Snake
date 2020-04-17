/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Game.Window;

import com.Game.Framework.GameObject;
import com.Game.Framework.KeyInput;
import com.Game.Framework.ObjectId;
import com.Game.Objects.Coin;
import com.Game.Objects.Player;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 *
 * @author Balint
 */
public class Game extends Canvas implements Runnable{
    //private static final long serialversionUID = -6261436164361361187L;
    private static final int    coinWidth=20;
    private static final int    coinHeight=20;
    public boolean running = false;
    private Thread thread;
    
    public static int WIDTH, HEIGHT; //szélesség magasság a külső 
    private static Game game;
    private static Handler handler;
    
    //Object
    
    Random rand = new Random(); //random a newCoin függvényhez
    
    private void init()
    {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        game = this;
        
        handler = new Handler();
        
        handler.addObject(new Player(100,100,ObjectId.Player)); //player hozzáadása a handlerhez

        handler.createLevel();///create level

        handler.addObject(newCoin()); //első coin spawn //// utoljára kell hozzáadni a handlerhez!
        this.addKeyListener(new KeyInput(handler));
    }
    
    public void restart()
    {
        init();
    }
    
    
    public synchronized void start()
    {
        if(running)
        {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    

    public void run()
    {
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running)
        {
            
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    private void tick()
    {
        
        handler.tick();
     }
    private void render()
    {
        BufferStrategy bs =this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        /////////////////////////////////////
        //Draw here
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        handler.render(g);
        
        
        /////////////////////////////////////
        g.dispose();
        bs.show();
    }
    Window window;
    public static void main(String args[])
    {
        Window window = new Window(1000, 600, "Game", new Game());
    }
    public Coin newCoin()
    {
        int coinX = rand.nextInt(WIDTH-200);
        int coinY = rand.nextInt(HEIGHT);
        Coin coin = new Coin(coinX, coinY, coinWidth, coinHeight, ObjectId.Coin);
        for( int i = 0; i < handler.objects.size();i++)
        {
            if(coin.getBounds().intersects(handler.objects.get(i).getBounds()))
            {
                return newCoin();
            }

        }
        for( int i = 0; i<handler.snakeParts.size();i++)
        {
            if(coin.getBounds().intersects(handler.snakeParts.get(i).getBounds()))
            {
                return newCoin();
            }
        }
        
        return coin;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        Game.game = game;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        Game.handler = handler;
    }
    
}
