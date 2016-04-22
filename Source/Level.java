package Spaceman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The class that Map1, Map2, and Map3 inherit from.  It contains the Level array that 
 * sets up the location of the walls, the switch, and the exit portal.  Also defines the colors, 
 * speed of the spaceship, the spaceship graphics, the location of the ship (X and Y), and 
 * the keyboard controls that make the ship move.
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Level
{
    int leveldata[] = {
            3, 10, 10, 10, 10, 10, 10, 2, 6, 3, 2, 2, 2, 2, 2, 10, 10, 2, 10, 6,
            5, 3, 2, 2, 2, 2, 2, 0, 0, 0, 8, 0, 0, 0, 0, 6, 7, 1, 6, 5,
            5, 1, 0, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 8, 0, 0, 4, 1, 0, 4,
            5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 7, 1, 0, 4, 9, 0, 4,
            5, 1, 0, 0, 0, 8, 8, 8, 0, 0, 0, 4, 11, 12, 1, 0, 0, 2, 0, 12,
            5, 1, 0, 0, 4, 11, 10, 14, 1, 0, 8, 0, 2, 2, 0, 0, 0, 8, 8, 6,
            5, 1, 0, 0, 0, 2, 2, 2, 0, 12, 7, 9, 0, 4, 9, 0, 12, 3, 6, 5,
            1, 0, 0, 0, 8, 0, 0, 0, 12, 3, 8, 6, 9, 0, 2, 4, 11, 0, 12, 5,
            5, 1, 0, 12, 7, 1, 0, 4, 11, 12, 7, 9, 14, 1, 0, 0, 6, 5, 3, 4,
            5, 1, 4, 3, 12, 1, 0, 8, 2, 2, 2, 2, 2, 8, 0, 0, 4, 13, 1, 4,
            5, 1, 4, 13, 3, 0, 4, 15, 1, 4, 1, 8, 0, 6, 9, 0, 0, 2, 8, 4,
            1, 0, 0, 10, 0, 0, 8, 10, 8, 0, 4, 15, 9, 0, 2, 4, 9, 4, 15, 5,
            1, 0, 4, 3, 0, 4, 11, 10, 14, 1, 0, 6, 15, 1, 0, 0, 6, 1, 2, 4,
            9, 0, 4, 1, 0, 0, 2, 2, 10, 0, 8, 0, 2, 0, 0, 0, 0, 0, 0, 4,
            7, 1, 4, 9, 0, 0, 8, 8, 6, 5, 7, 1, 4, 1, 8, 12, 1, 4, 9, 4,
            1, 0, 0, 2, 0, 4, 11, 14, 5, 5, 5, 9, 8, 12, 7, 3, 0, 0, 6, 5,
            1, 0, 0, 4, 1, 0, 2, 2, 4, 5, 9, 10, 10, 10, 12, 1, 0, 0, 8, 4,
            1, 0, 0, 0, 0, 0, 0, 0, 4, 1, 2, 2, 2, 2, 10, 8, 0, 12, 7, 5,
            1, 0, 0, 0, 0, 0, 0, 0, 4, 9, 0, 0, 0, 12, 3, 2, 4, 11, 12, 5,
            9, 8, 8, 8, 8, 8, 12, 9, 8, 10, 8, 12, 9, 10, 8, 8, 8, 10, 10, 12
        }; //default level data (no key)
    Color dotColor = new Color(192, 192, 0); //default dot color
    Color mazeColor = new Color(5, 100, 5); //default maze color
    int spacemanSpeed = 24; //default speed

    /////////////////  default images  //////////////
    Image spaceman4up = new ImageIcon("images/up3.png").getImage();
    Image spaceman4down = new ImageIcon("images/down3.png").getImage();
    Image spaceman4left = new ImageIcon("images/left3.png").getImage();
    Image spaceman4right = new ImageIcon("images/right3.png").getImage();

    
    /////////////////////////////////////// getters //////////////////////////////////////
    public int[] getLevelData()
    {
        return leveldata;
    }

    public Color getDotColor()
    {
        return dotColor;
    }

    public Color getMazeColor()
    {
        return mazeColor;
    }

    public int getSpeed()
    {
        return spacemanSpeed;
    }

    public Image getUpImage()
    {
        return spaceman4up;
    }

    public Image getDownImage()
    {
        return spaceman4down;
    }

    public Image getRightImage()
    {
        return spaceman4right;
    }

    public Image getLeftImage()
    {
        return spaceman4left;
    }

    public void sendX(int spacemanx){}

    public void sendY(int spacemany) {}
    
    public int getControls(int key)
    {
        int result = key;
        if (key == KeyEvent.VK_LEFT) {
             result = 1;
        } else if (key == KeyEvent.VK_RIGHT) {
             result = 2;
        } else if (key == KeyEvent.VK_UP) {
             result = 3;
        } else if (key == KeyEvent.VK_DOWN) {
             result = 4;
        }
        return result; //1=left, 2=right, 3=up, 4=down
    }
}
