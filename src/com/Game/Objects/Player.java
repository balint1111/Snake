
package com.Game.Objects;

import com.Game.Framework.GameObject;
import com.Game.Framework.ObjectId;
import com.Game.Window.Game;
import static com.Game.Window.Game.HEIGHT;
import static com.Game.Window.Game.WIDTH;
import com.Game.Window.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

public class Player extends GameObject{
    
    private directin            direction = directin.Stop;
    private static final double  deltaTail=10,               ///tail változása coinonként
                                maxSpeed=3,                 ///max sebesség
                                deltaSpeed=0.05f;           ///speed változása coinoncént
    private static final int    width =15,                  ///player szélesség
                                height=15,                  ///player magasság
                                bodyBlockWidth=15,          ///bodyBlokkok szélessége   ///(futásidőben generálódnak)
                                bodyBlockHeight=15,         ///bodyBlokkok magassága
                                tailNothingMinimum=15;
    private int                 taillength=50;              ///(kezdőérték) //tail aktuális hossza
    private int                 score=0;                    ///(kezdőérték) //aktuális score
    private int                 tailNothing=25;             ///(kezdőérték) //az első tailNothing db taildarab amivel nem ütközhet a player 
    private double               speed=1.5f;                 ///(kezdőérték) //sebesség 
    
    
    public Player(float x, float y, ObjectId id) 
    {
        super(x, y, id);
    }

    public void render(Graphics g) 
    {
        g.setColor(Color.black);
        g.fillRect((int )x,(int) y, width, height);
        g.drawString("Score:   " + Integer.toString(score), 850, 50);
        g.drawString("Hossz:   " + Integer.toString(taillength), 850, 100);
        if(Double.toString(speed).length() == 1)
        {
            g.drawString("Sebesség:   " + Double.toString(speed).charAt(0), 850, 150);
        }
        else if(Double.toString(speed).length() == 3)
        {
            g.drawString("Sebesség:   " + Double.toString(speed).charAt(0)
                                        + Double.toString(speed).charAt(1)
                                        + Double.toString(speed).charAt(2)
                                        , 850, 150);
        }
        else if(Double.toString(speed).length() >= 4)
        {
            g.drawString("Sebesség:   " + Double.toString(speed).charAt(0)
                                        + Double.toString(speed).charAt(1)
                                        + Double.toString(speed).charAt(2)
                                        + Double.toString(speed).charAt(3)
                                        , 850, 150);
        }
        
        
    }

    public Rectangle getBounds() 
    {
        return new Rectangle((int )x,(int) y, width, height);
    }

    public void tick(LinkedList<GameObject> objects) 
    {
        ///Mozgaás
        if(direction == directin.Up) y-=speed;
        if(direction == directin.Down) y+=speed;
        if(direction == directin.Left) x-=speed;
        if(direction == directin.Right) x+=speed;
        
        tailManege();       ///tail spawn nothing id-vel ///tail eltüntetés ///a hátsó elemek id-je ->Block
        
        Collision();        ///
    }
    private void Collision ()
    {
        /////snakeParts Collison Check ///végigfut a snakeParts összes elemén 
        for( int i = 0; i<Game.getHandler().snakeParts.size();i++)  
        {
            gamoveCheck(Game.getHandler().snakeParts.get(i));       ///GameOver check       ///ütközik e a player az aktuális blockal
        }
        /////objects Collison Check /// veégigfut az objects összes elemén
        for( int i = 0; i < Game.getHandler().objects.size();i++)
        {
            gamoveCheck(Game.getHandler().objects.get(i));          ///GameOver check       ///ütközik e a player az aktuális Gameobjectel
            coinCollison(Game.getHandler().objects.get(i));         ///Coinfelvétel check   ///ütközik e a player az aktuális Gameobjectel   (Coin)
        }
    }

    
    private void gamoveCheck(GameObject tempObject)
    {
        if(tempObject.getId() == ObjectId.Block) ///Game over check ///aktuális object Block típusú e
            {
                if(getBounds().intersects(tempObject.getBounds())) /// az object colleiderei egymásba vannak e
                {
                    gameOver();///játék végi utasítások
                }
            }
    }
    private void coinCollison (GameObject tempObject)
    {
        if(tempObject.getId() == ObjectId.Coin)                             ///ha id=coin
        {
            if(getBounds().intersects(tempObject.getBounds()))              ///ha ütközik a playerel
            {
                Game.getHandler().removeObject(tempObject);                 ///törli a coint
                Game.getHandler().addObject(Game.getGame().newCoin());      ///új coin spawnol
                variableUpdate();                                           ///frissíti a változókat
            }
        }
    }
    private void tailManege()
    {
        if(direction != directin.Stop)                                      ///ha nem áll a player
        {
                                                                            ///létrehoz egy bodyt(tail) a player közepén ///Nothing id-vel ///hozzáadja a snakerészek töbhöz
            Game.getHandler().addSnakePart(new Body((int)x+((width-bodyBlockWidth)/2) ,(int)y+((height-bodyBlockHeight)/2), bodyBlockWidth, bodyBlockHeight, ObjectId.Nothing));
        }
        if(Game.getHandler().snakeParts.size()>taillength)                  ///ha a snakerészek tömb nagysága nagyobb mint farokhossz
        {
            Game.getHandler().snakeParts.remove(0);                         ///kitörli a snakerészek első elemét (ez a farok végén lévő elem mindíg)
            ///beállítja a tail végén lévő bodyblokkok id-jét Block-ra
            for( int i=0;i<taillength-tailNothing;i++)
            {
                Game.getHandler().snakeParts.get(i).setId(ObjectId.Block);
            }
        }
    }
    private void gameOver()
    {
        setDirection(direction.Stop);
        System.out.println("Game Over! Score: " + score);
        Game.getGame().restart();
    }
    private void variableUpdate()
    {
        taillength+=deltaTail;                                      
        score++;
        if(tailNothing > tailNothingMinimum)
        {
            tailNothing--;

        }
        if(speed < maxSpeed)
        {
            speed+=deltaSpeed;
        }
        if (speed > maxSpeed)
        {
            speed=maxSpeed;
        }
        System.out.println("tailnothing : " + tailNothing);
        System.out.println("score : " + score);
        System.out.println("snake size: " + taillength);
        System.out.println("speed : " + speed);
    }
    /////////////////////////
    //// Getterek és Setterek
    public directin getDirection() {
        return direction;
    }

    public void setDirection(directin direction) {
        this.direction = direction;
    }

    public int getTaillength() {
        return taillength;
    }

    public void setTaillength(int taillength) {
        this.taillength = taillength;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
