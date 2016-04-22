package Spaceman;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Makes the board and runs controls and animation of the game.
 * 
 * @author William Goolkasian and Austin Moehnke
 * @version 04.18.2015
 */
public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;

    private boolean inGame = false;

    private final int blockSize = 24; //size of each block
    private final int nrOfBlocks = 20; //number of blocks as a square
    private final int screenSize = nrOfBlocks * blockSize; //sets screensize
    private int speed;  //speed you can move
    private int score;  //increases every level you get through
    private Image UpImage, DownImage, LeftImage, RightImage; //shows where the spaceship is pointed
    private int spacemanx, spacemany, spacemandx, spacemandy; //tells where the ship is located
    private int reqdx, reqdy, viewdx, viewdy; //slope(rate) of movement for the character
    private boolean stopped = true; //whether the ship is moving or not
    private int[] screendata; //copy of the leveldata array for editing
    private Timer timer;
    private int moveCount;
    private int level = 0; //sets the level to 0 as default
    private Level Map = new Map1();  //makes the first map as default

    public Board() { //main constructor of the board that loads instance variables and sets default board

        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
    }

    private void initVariables() { //iniates values to the instance variables
        screendata = new int[nrOfBlocks * nrOfBlocks];
        d = new Dimension(400, 400);
        timer = new Timer(40, this);
        timer.start();

    }

    private void playGame(Graphics2D g2d) { //called on repeat while the game is active better translated 'game is being played'
        moveSpaceman(); //moves the spaceman in the specified direction
        drawSpaceman(g2d); //draws the spaceman at the newly specified values
        checkMaze(); //checks to see if the maze has been completed
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48)); //draws a square for welcoming screen
        g2d.fillRect(50, screenSize / 2 - 150, screenSize - 100, 340);
        g2d.setColor(Color.white);
        g2d.drawRect(50, screenSize / 2 - 150, screenSize - 100, 340);

        String Welcome = "Welcome to SpaceMan!";
        String Goal = "The Goal of the game is to collect the";
        String Goal2 = "dot on each level, opening a gate and";
        String Goal3 = "allowing you to reach the goal!";
        String care = "Watch out, as you can only move in one";
        String care2 = "direction at a time until you reach";
        String care3 = "a wall in that direction!";
        String care4 = "Some levels might also change controls!";
        String goal = "The goal for each level is the closed";
        String goal2 = "box in the middle of each level. Get";
        String goal3 = "the dot in order to open it!";
        String s = "Press s to start or if you ever get stuck.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white); //draws the message in the box
        g2d.setFont(small);
        g2d.drawString(Welcome, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2-120);
        g2d.drawString(Goal, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2-80);
        g2d.drawString(Goal2, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2-60);
        g2d.drawString(Goal3, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2-40);
        g2d.drawString(care, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2);
        g2d.drawString(care2, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 20);
        g2d.drawString(care3, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 40);
        g2d.drawString(goal, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 80);
        g2d.drawString(goal2, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 100);
        g2d.drawString(goal3, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 120);
        g2d.drawString(s, (screenSize - metr.stringWidth(s)) / 2, screenSize / 2+ 160);
    }

    private void drawScore(Graphics2D g) { //keeeps track of the level and draws it in corner
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Level: " + score + "   Moves: " + moveCount;
        g.drawString(s, screenSize - 160, screenSize + 16);
    }

    private void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < nrOfBlocks * nrOfBlocks && finished) { //for every element in the array

            if ((screendata[i] & 48) != 0) { //checks to see if all dots are gone or not
                finished = false; //if any spot has a dot, it is not done.
            }

            i++;
        }

        if (finished) { //if you have finished a particular level
            screendata[190] = 0; //opens the "door" allowing you to get to the finish zone.
            if(spacemanx == 10*blockSize && spacemany == 8*blockSize) //if the spaceman got to the finish zone
            {
                level++;  //goes to next level
                initLevel(); //redraw and call the now new level
                stopped = true;//allows the user to type another movement command
            }
        }
    }

    private void moveSpaceman() {

        int pos;
        int ch;

        if (reqdx == -spacemandx && reqdy == -spacemandy) { //checks bounds and resets if out of them
            spacemandx = reqdx;
            spacemandy = reqdy;
            viewdx = spacemandx;
            viewdy = spacemandy;
        }

        if (spacemanx % blockSize == 0 && spacemany % blockSize == 0) {//make sure that the spaceman moves only in the grid we  want
            pos = spacemanx / blockSize + nrOfBlocks * (int) (spacemany / blockSize);
            ch = screendata[pos];

            if ((ch & 16) != 0) { //if the player got to the dot
                screendata[pos] = (int) (ch & 15);
                score++;
            }

            if (reqdx != 0 || reqdy != 0) {//moves the position of the spaceman
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                    || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                    || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                    || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    spacemandx = reqdx;
                    spacemandy = reqdy;
                    viewdx = spacemandx;
                    viewdy = spacemandy;
                }
            }

            // Check for standstill (if the spaceman hits a wall after moving
            if ((spacemandx == -1 && spacemandy == 0 && (ch & 1) != 0)
            || (spacemandx == 1 && spacemandy == 0 && (ch & 4) != 0)
            || (spacemandx == 0 && spacemandy == -1 && (ch & 2) != 0)
            || (spacemandx == 0 && spacemandy == 1 && (ch & 8) != 0)) {
                spacemandx = 0;
                spacemandy = 0;

            }
        }
        spacemanx = spacemanx + speed * spacemandx; //moves the position of the spaceman with element of speed
        spacemany = spacemany + speed * spacemandy;
        if (level == 2)
        {
            Map.sendX(spacemanx);
            Map.sendY(spacemany);
        }
        if (spacemandx == 0 && spacemandy == 0) //if the person isnt moving
        {
            stopped = true; //let them make a new move
        }

    }

    private void drawSpaceman(Graphics2D g2d) {  //draws thespaceship in the correct direction

        if (viewdx == -1) {
            drawSpacemanLeft(g2d);
        } else if (viewdx == 1) {
            drawSpacemanRight(g2d);
        } else if (viewdy == -1) {
            drawSpacemanUp(g2d);
        } else { //view dy would equal 1 but this will also allow any other direction just drawn down. avoids errors
            drawSpacemanDown(g2d);
        }

    }

    private void drawSpacemanUp(Graphics2D g2d) { //draws the spaceman with the up image at the saved location
        g2d.drawImage(UpImage, spacemanx + 1, spacemany + 1, this);
    }

    private void drawSpacemanDown(Graphics2D g2d) { //draws the spaceman with the down image at the saved location
        g2d.drawImage(DownImage, spacemanx + 1, spacemany + 1, this);
    }

    private void drawSpacemanLeft(Graphics2D g2d) { //draws the spaceman with the left image at the saved location
        g2d.drawImage(LeftImage, spacemanx + 1, spacemany + 1, this);
    }

    private void drawSpacemanRight(Graphics2D g2d) { //draws the spaceman with the right image at the saved location
        g2d.drawImage(RightImage, spacemanx + 1, spacemany + 1, this);
    }

    private void drawMaze(Graphics2D g2d) { //draws the walls and key as the map object instructs

        int i = 0;
        int x, y;

        for (y = 0; y < screenSize; y += blockSize) {
            for (x = 0; x < screenSize; x += blockSize) { //for each element in the array

                g2d.setColor(Map.getMazeColor());
                g2d.setStroke(new BasicStroke(2));
                 /////////////////////////   draws the lines on the left, top, bottomr, or right of the block based on integer in array
                if ((screendata[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + blockSize - 1);
                }

                if ((screendata[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + blockSize - 1, y);
                }

                if ((screendata[i] & 4) != 0) { 
                    g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,
                        y + blockSize - 1);
                }

                if ((screendata[i] & 8) != 0) { 
                    g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1,
                        y + blockSize - 1);
                }

                if ((screendata[i] & 16) != 0) {  //draws the dot wherever the element is above 16
                    g2d.setColor(Map.getDotColor());
                    g2d.fillRect(x + 8, y + 8, 5, 5);
                }

                i++;
            }
        }
    }

    private void initGame() { //iniates the game only runs at begining and not again until restart
        score = 0;
        initLevel(); //initiates the level data
    }

    private void initLevel() { //gets and sets level data
        if (level == 0)
        {
            Map = new Map1();    
        }
        if (level == 1)
        {
            Map = new Map2();
        }
        if (level == 2)
        {
            Map = new Map3();
        }

        if (level == 3)
        {
            level = -1;
        }
        screendata = Map.getLevelData();
        speed = Map.getSpeed();

        continueLevel();
    }

    private void continueLevel() {
        /////////// places character in starting position /////////////
        spacemanx = 0; 
        spacemany = 19 * blockSize;
        spacemandx = 0;
        spacemandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
    }

    private void loadImages() { // gets the images for the selected level
        UpImage = Map.getUpImage();
        DownImage = Map.getDownImage();
        LeftImage = Map.getLeftImage();
        RightImage = Map.getRightImage();
    }

    public void paintComponent(Graphics g) {  //pain component
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) { //draws the game onto the screen

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            int result = Map.getControls(key);//1=left, 2=right, 3=up, 4=down

            if (inGame && stopped == true) {
                if (result == 1) { //moves the character left
                    reqdx = -1;
                    reqdy = 0;
                    stopped = false;
                    moveCount ++;
                } else if (result == 2) { //moves the character right
                    reqdx = 1;
                    reqdy = 0;
                    stopped = false;
                    moveCount ++;
                } else if (result == 3) { //moves the character up
                    reqdx = 0;
                    reqdy = -1;
                    stopped = false;
                    moveCount ++;
                } else if (result == 4) { //moves the character down
                    reqdx = 0;
                    reqdy = 1;
                    stopped = false;
                    moveCount ++;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        public void keyReleased(KeyEvent e) {  //when you stop pressing a key

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
            || key == Event.UP || key == Event.DOWN) {
                reqdx = 0;
                reqdy = 0;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint(); //redraw the game with new variables
    }
}