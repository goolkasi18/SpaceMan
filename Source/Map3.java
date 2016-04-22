package Spaceman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The third map you play in the game.  Inherits from the Level class.
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Map3 extends Level
{
    int x = 0;
    int y = 0;
    public Map3()
    {
        leveldata[399] = leveldata[399]+16; //puts the key in the given location
        
    }
    
    public Color getMazeColor() //changes the color of the maze based on your x and y coordinates
    {
        mazeColor = new Color(x/2,(y + x)/4, x/2);
        return mazeColor;
    }
    
    public void sendX(int spacemanx) //gets the X coordinate
    {
         x = spacemanx;
    }

    public void sendY(int spacemany)  //gets the Y coordinate
    {
         y = spacemany;
    }


}