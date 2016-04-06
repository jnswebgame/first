/*
 * This is supposed to represent four tiles, in a two by two grid.
 * ___ ___
 *|_0_|_1_|
 *|_2_|_3_|
 */

import java.awt.*;
import javax.swing.*;

public class ShiftScreen 
{
    private int width = 500;
    private int height = 500;
    private int playerWidth = 20;
    private int playerHeight = 20;
    public JPanel[] tileAry = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};
    public JPanel currentTile;
    private JPanel tiles;
    //private  CardLayout tiles = new CardLayout();    

    private CardLayout cardLayout = (CardLayout) tiles.getLayout();
        
    
    public ShiftScreen()
    {   
     //   for(int i =0 ; i< tileAry.length;i++)
     //   {
     //       tiles.add(tileAry[i]);           
      //  }
    }
    public void shift(int x, int y, int t)
    {        
        currentTile = tileAry[checkTile(x, y, t)];
    }
    public int[] playerShift(int x, int y, int t)
    {
        int currentTile = t;
        int[] ia = new int[2]; //the new x and y position and the new tile
        int newX = checkEdgeX(x);
        int newY = checkEdgeX(y);
        ia[0] = newX;
        ia[1] = newY;
        
        return ia;
    }
    
    public int[] NonPlayerShift(int x, int y)
    {      
        int[] coordinates = {0,0};
        
        x = x + width;
        y = y + height;
        coordinates[0]=x;
        coordinates[1]=y;
        
        return coordinates;
    }
    public int checkTile(int x, int y, int t)
    {
        int tile = 0;
        int rightEdge=width-30;
        int leftEdge = 30;
        int topEdge=30;
        int bottomEdge = height - 30;
        
        if(t==0)
        {
            if(x==rightEdge)
            {                                                     
                tile=1;
            }     
            if(y==bottomEdge)
            {
                tile=2;
            }
        }
        if(t==1)
        {
            if(x<leftEdge)        
            {                
                tile=0;
            }     
            if(y==bottomEdge)
            {
                tile=3;
            }
        }
        
        if(t==2)
        {
            if(x==rightEdge)        
            {                
                tile=3;
            }     
            if(y==topEdge)
            {
                tile=0;
            }
        }
        
        if(t==3)
        {
            if(x<leftEdge)        
            {                
                tile=2;
            }     
            if(y==topEdge)
            {
                tile=1;
            }
        }
        return tile;
    }
    public int checkEdgeX(int x)
    {
        
        int rightEdge=width-30;
        int leftEdge = 30;
                int newX = x+playerWidth; //the right side of the player
        
        //System.out.println(":"+newX);
        //sends the player to the left edge when they go too far right
        if (newX>rightEdge)                    
            x = leftEdge;          
        //sends the player to right edge when they go too far left
        if(x<leftEdge)        
            x = rightEdge-playerWidth;  

        return x;
    }
    public int checkEdgeY(int y, int tile)
    {
        int newY = y+playerHeight;
        int topEdge=30;
        int bottomEdge = height - 30;
        //System.out.println(":"+newX);
        //sends the player to upper edge when they go too low
        if (newY>bottomEdge)                    
        {
            y = topEdge; 
            tile += 2;
        }
        //sends the player to the lower edge when they go too high
        if(y<topEdge)
        {
            y = bottomEdge-playerHeight;  
            tile -= 2;
        }
        return y;
    }
    /*
    public int checkEdgeX(int x)
    {
        int newX = x+playerWidth; //the left side of the player
        int rightEdge=width-30;
        int leftEdge = 30;
        //System.out.println(":"+newX);
        //sends the player to the left edge when they go too far right
        if (newX>rightEdge)                    
            x = leftEdge;          
        //sends the player to right edge when they go too far left
        if(x<leftEdge)        
            x = rightEdge-playerWidth;  
        
        return x;
    }
    public int checkEdgeY(int y)
    {
        int newY = y+playerHeight;
        int topEdge=30;
        int bottomEdge = height - 30;
        //System.out.println(":"+newX);
        //sends the player to upper edge when they go too low
        if (newY>bottomEdge)                    
            y = topEdge;          
        //sends the player to the lower edge when they go too high
        if(y<topEdge)        
            y = bottomEdge-playerHeight;  
        return y;
    }
    
    */
}
