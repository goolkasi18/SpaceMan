package Spaceman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The first map you play in the game.  Inherits from the Level class. 
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Map1 extends Level
{

    public Map1() //default constructor that edits an element in the array to add the "key"
    {
        leveldata[373] = leveldata[373]+16; //adds the key to a location
    }

}