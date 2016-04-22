package Spaceman;

import java.awt.EventQueue;
import javax.swing.JFrame;
/**
 * Creates an instance of the Spaceman game
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Spaceman extends JFrame {
    int nrOfBlocks = 20; //used to calculate screen size
    public Spaceman() { 
        initUI(); //calls to iniate the ui
    }

    private void initUI() { //initiates the user interface

        add(new Board()); //adds a new board to the instance
        setTitle("Spaceman");
        setDefaultCloseOperation(EXIT_ON_CLOSE); //sets close operation
        setSize(nrOfBlocks*25, nrOfBlocks*28);  //sets size of the screen
        setLocationRelativeTo(null); //opens the game in the middle of the screen
        setVisible(true);  //makes it visible
    }

    public static void main(String[] args) {  //runs the game

        EventQueue.invokeLater(new Runnable() 
            {
                public void run() {
                    Spaceman ex = new Spaceman(); //makes the new instance of the game
                    ex.setVisible(true); //makes the instance visible
                }
            }
        );
    }
}