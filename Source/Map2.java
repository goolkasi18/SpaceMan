package Spaceman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The second map you play in the game.  Inherits from the Level class.
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Map2 extends Level
{
    public Map2() //overrides the Level array and changes the position of the switch
    {
        leveldata[60] = leveldata[60]+16; //adds the key to a location
    }
    
    public int getControls(int key)  //overrides the game controls 
    {
        int result = key;
        if (key == KeyEvent.VK_LEFT) {
            result = 2;
        } else if (key == KeyEvent.VK_RIGHT) {
            result = 1;
        } else if (key == KeyEvent.VK_UP) {
            result = 4;
        } else if (key == KeyEvent.VK_DOWN) {
            result = 3;
        }
        return result; //1=left, 2=right, 3=up, 4=down
    }

}