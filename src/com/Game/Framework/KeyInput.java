
package com.Game.Framework;

import com.Game.Objects.Player;
import com.Game.Objects.directin;
import com.Game.Window.Handler;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
    
    Handler handler;
    
    public KeyInput(Handler handler)
    {
        this.handler = handler;
    }
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        for(int i = 0 ; i < handler.objects.size() ; i++)
        {
            GameObject tempObhect = handler.objects.get(i);
            
            if(tempObhect.getId() == ObjectId.Player)
            {
                Player playerObj = (Player) tempObhect;
                if(playerObj.getDirection() == directin.Stop || playerObj.getDirection() == directin.Up || playerObj.getDirection() == directin.Down)
                {
                    if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
                    {
                        playerObj.setDirection(directin.Right);
                    }
                    if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
                    {
                        playerObj.setDirection(directin.Left);
                    }
                }
                
                if(playerObj.getDirection() == directin.Stop || playerObj.getDirection() == directin.Left || playerObj.getDirection() == directin.Right)
                {
                    if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
                    {
                        playerObj.setDirection(directin.Up);
                    }
                    if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
                    {
                        playerObj.setDirection(directin.Down);
                    }
                }
            }
        }
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        for(int i = 0 ; i < handler.objects.size() ; i++)
        {
            GameObject tempObhect = handler.objects.get(i);
            
            if(tempObhect.getId() == ObjectId.Player)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObhect.setVelX(0);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObhect.setVelX(0);
                }
            }
        }
    }
}
