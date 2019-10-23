/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: GameBoard
 */

package testproject;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;

import java.awt.event.ActionListener;

public class GameBoard extends JPanel {

    private final int DELAY = 16; //delay is milli between clock ticks ~60fps@16
    private int lives = 3;
    private Player playerSprite = new Player(); //player sprite
    private Enemy[][] aliens = new Enemy[5][5];
    private PowerEnemy powerEnemy = new PowerEnemy();
    private TimerToDo Schedule; //what the timer performs
    private boolean a_Down, d_Down, space_Down, gameEnd, gamePause, validate;//checks if the user is pressing a key
    private int score, difficulty, lowHS;
    private JButton start, startNoScore,resume;
    private ScheduledExecutorService ex = Executors.newScheduledThreadPool(1);//thread that plays the game loop
    private ScheduledFuture<?> future; //allows timer to be stopped without stalling program
    private JLabel endScore,validateString = new JLabel();
    private JTextField playerScoreName = new JTextField(15);
    private ProgSounds sound = new ProgSounds("Song(2).wav");
    private Color themeColor;

    public GameBoard(JButton START) {
        gameEnd = true;//restarts the game
        start = new JButton(START.getText());
        start.setPreferredSize(START.getPreferredSize());//button to main
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePause = false;
                gameEnd = true;
                START.doClick();
            }
        });
        resume = new JButton("Resume");
        resume.setPreferredSize(START.getPreferredSize());
        resume.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        future = ex.scheduleAtFixedRate(Schedule, 0, DELAY, TimeUnit.MILLISECONDS);
        gamePause = false;
        }
        });
        validateString = new JLabel("enter a name");
        validateString.setForeground(Color.white);
        setBackground(Color.black);
        Schedule = new TimerToDo();
        start.setPreferredSize(START.getPreferredSize());
        start.setToolTipText("Back to main menu");
        playerScoreName.setFocusable(true);
        FlowLayout boardMenuLayout = new FlowLayout();
        boardMenuLayout.setVgap(40);
        boardMenuLayout.setHgap(700);
        setLayout(boardMenuLayout);
        playerScoreName.setPreferredSize(new Dimension(200, 50));
        playerScoreName.setToolTipText("Enter a name");
        startNoScore = new JButton("Enter new score");
        startNoScore.setPreferredSize(START.getPreferredSize());
        startNoScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("calls");
                if (playerScoreName.getText().length() <= 6) {
                    start.doClick();
                    // DataReading.update(playerScoreName.getText(), score - 1);
                    validate = false;
                } else {
                    validate = true;//whether the user needs to be notified of a user error
                    gameEnd(score > lowHS);

                }
            }
        });
        this.addKeyListener(new KeyListener() { //wether the user is pressing certain buttons each clock cycle
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D:
                        d_Down = true;
                        break;
                    case KeyEvent.VK_A:
                        a_Down = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        if (space_Down == false) {
                            sound.start();
                        }
                        space_Down = true;
                        break;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D:
                        d_Down = false;
                        break;
                    case KeyEvent.VK_A:
                        a_Down = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        //do nothing, space boolean to validate hits
                        break;
                    case KeyEvent.VK_P:
                        if(!gameEnd){//if game end is false
                        pause();
                        }
                        System.err.println("pause button");

                        break;

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {/*do nothing*/ }
        });//end of listener

    }//end constructor

    private void initial() {
        Character.setThemeColour(themeColor);//sets the theme colour for the sprites
        for (int i = 0; i < aliens.length; i++) {
            for (int r = 0; r < aliens[0].length; r++) {
                aliens[i][r] = new Enemy(((170 * i) + 100), ((80 * r) + 100));

            }//aliens palcement on board initialising
        }
        if (powerEnemy.getAlive()) {
            powerEnemy.kill();
            powerEnemy.tellProjCol();
        }//if th powerenenym is alive from the last game
        playerSprite = new Player();
        lives = 3;
        score = 0;
        gameEnd = false;
        gamePause = false;
        grantpTF(0, 0, 0, true, false);//so only the bottom row fires
        future = ex.scheduleAtFixedRate(Schedule, 0, DELAY, TimeUnit.MILLISECONDS);//starts game loop

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (gameEnd == false) {
            g.setColor(Color.white);
            g.drawString("Lives:" + String.valueOf(lives), 30, 20);
            g.drawString("Score:" + String.valueOf(score), 80, 20);
            playerSprite.Appearence(g);//updates player appearence
            Enemy.animate();//adds to the counter for changing enemy appearence

            powerEnemy.Appearence(g);//udates powerenemy appearence on the board

            for (int i = 0; i < aliens.length; i++) {
                for (int r = 0; r < aliens[0].length; r++) {

                    aliens[i][r].Appearence(g); //each aliens appearence on the board

                }
            }
            if (space_Down == true) {
                space_Down = playerSprite.fire(g, space_Down);
            } //if the laseer is in play updates the appearence of the laser

            Enemy.changeDirection();//changes direction if end has been reached
            if (gamePause == true) {
                g.setColor(Color.white);
                g.fillRect(250, 30, 500, 600); //draws the white menu if the game is paused
            }

        }

    }

    public void focus(Color in, int diff, int highscoreLow) {
        themeColor = in;
        lowHS = highscoreLow;
        difficulty = diff;
        setFocusable(true); //focuses on jpanel fixes issue with key listener not working
        requestFocusInWindow();
        if (gameEnd == true) {
            initial();
        }
        Enemy.diffSetting(diff); //called at th start of each game so difficulty is set her
        if (diff == 5) {
            reignOfDeath(); //allow all of the enemies to fire

        }

    }

    private void reignOfDeath() {
        for (int i = 0; i < aliens.length; i++) {
            for (int r = 0; r < aliens[0].length; r++) {
                aliens[i][r].ptf();//allows all the enemies to fire
            }

        }

    }

    private void pause() {
        if (gamePause == false) {
            future.cancel(false);//pauses game cycle

            removeAll();
            add(new JLabel("Paused"));
            add(resume);
            add(start);//button to main menu, will end game
            start.requestFocusInWindow();
            revalidate();
            repaint();
            // gameEnd = true;
            gamePause = true;
        } else {

            gamePause = false;//will unpause when p is pressed again
            removeAll();
            future = ex.scheduleAtFixedRate(Schedule, 0, DELAY, TimeUnit.MILLISECONDS);
            repaint();

        }

    }

    private class TimerToDo extends TimerTask {

        @Override
        public void run() {

            removeAll();

            if (d_Down == true) {
                playerSprite.moveRight(5);
            }
            if (a_Down == true) {
                playerSprite.moveLeft(5);
            }  //between each clock cycle ran in timer task
            //checks whether the key has been pressed
            
            if (score % (100 * difficulty) == 0 && powerEnemy.getAlive() == false && score != 0) {
                powerEnemy.kill();//powerup enemy will only appear for every ten kills
            }
            //getProjectileLoc(true) returns the x position of the entity or its laser
            //getProjectileLoc(false) returns the y position of the entity or its laser
                
                for (int col = 0; col < aliens.length; col++) {
                    for (int row = 0; row < aliens[0].length; row++) {

                        if (aliens[col][row].getProjLoc(false) >= playerSprite.baseY - 28 && aliens[col][row].getProjLoc(false) <= playerSprite.baseY) {
                            //checks if the projectile is in the same y region as the player
                            if (aliens[col][row].getProjLoc(true) >= playerSprite.baseX && aliens[col][row].getProjLoc(true) <= playerSprite.baseX + 49) {
                                //checks if the x's are similar
                                aliens[col][row].tellProjCol();//stops drawing the enmy laser
                                die(1);
                            }
                        }
                        if (playerSprite.getProjLoc(false) >= aliens[col][row].baseY - 30 && playerSprite.getProjLoc(false) <= aliens[col][row].baseY) {
                            //checks if the player projectile y position 
                            if (playerSprite.getProjLoc(true) >= aliens[col][row].baseX && playerSprite.getProjLoc(true) <= aliens[col][row].baseX + 30) {
                                aliens[col][row].kill();
                                enemyDeath();//adds to score
                                grantpTF(0, 0, 0, true, false);//executes to assign permission to fire to bottom line
                                playerSprite.tellProjCol();//stops ddrawing the pkayer laser
                            }

                        }

                        if (aliens[col][row].baseY > 738) {
                            die(lives); //ends the game if the aliens reach the bottom of the screen

                        }

                    }
                }
                if (playerSprite.getProjLoc(false) >= powerEnemy.baseY - 30 && playerSprite.getProjLoc(false) <= powerEnemy.baseY) {
                    //if the player shot is in the same y region as the power enemy
                    if (playerSprite.getProjLoc(true) >= powerEnemy.baseX && playerSprite.getProjLoc(true) <= powerEnemy.baseX + 30) {
                        //if the x position is also in the same position as the powerup
                        powerEnemy.kill();
                        enemyDeath();
                        playerSprite.tellProjCol();
                    }

                }

                if (powerEnemy.getProjLoc(false) >= playerSprite.baseY - 20 && powerEnemy.getProjLoc(false) <= playerSprite.baseY) {
                    //checks if the projectile is in the same y region as the player
                    if (powerEnemy.getProjLoc(true) >= playerSprite.baseX-50 && powerEnemy.getProjLoc(true) <= playerSprite.baseX + 50) {
                        //9checks if the x's are similar
                        if (powerEnemy.tellProjCol() == true) {
                            lives++; //if powerup is an add life
                        } else {
                            playerSprite.applyEffects(true); //if powerup is to alter laser
                        }

                    }
                }
          

            repaint();

        }
    }

    private void die(int in) {
        lives -= in;
        if (lives == 0) {
            lives--;
            playerSprite.kill();
            future.cancel(false); //stops the game loop
            endScore = new JLabel("You died Score: " + (score));
            endScore.setFont(new Font("Terminal Bold", Font.PLAIN, 48));
            endScore.setForeground(themeColor);
            gameEnd(score > lowHS);
            gameEnd = true;
            gamePause = true;

        }

    }

    private void enemyDeath() {
        score += 10 * (difficulty);//score to add * difficulty setting
    }

    private void grantpTF(int col, int row, int lastAlive, boolean recurse, boolean flag) { //grants permission to fire
        try {
            if (recurse == true) {
                if (aliens[col][row].getAlive() == true) {
                    lastAlive = row; //sets the last alive alien in each colum
                    row++;
                    flag = true;
                } else {
                    row++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            if (col == aliens.length) {//ran out of columms
                recurse = false;
                if (flag == false) {
                    for (int i = 0; i < aliens.length; i++) {
                        for (int r = 0; r < aliens[0].length; r++) {
                            aliens[i][r] = new Enemy(((170 * i) + 100), ((80 * r) + 100));
                            if(difficulty == 5 ){this.reignOfDeath();}
                        }//aliens palcement on board initialising if all aliens are dead
                    }
                    grantpTF(0, 0, 0, true, false); //sets so that only bottom line will fire
                }
            } else { //the row number has thrown the exception
                aliens[col][lastAlive].ptf();//sets the last alien to fire
                col++;//to next colum
                grantpTF(col, 0, 0, recurse, flag);//operates on the next colum
                recurse = false;
            }
        } finally {
            if (recurse == true) {
                grantpTF(col, row, lastAlive, true, flag);//operates on thee next row of the colum
            }

        }

    }

    private void gameEnd(boolean button) {
       //parameter button determines whether a high score has been set
       //thus different components will be added
        removeAll();
        gameEnd = true;
        add(endScore);

        if (button == true) {
            if (validate) {
                validateString.setText("enter a name with less than six characters");
                add(validateString);
                playerScoreName.setText("");
                //if the user enters an invalid username
            } else {
                validateString.setText("enter a name");
                add(validateString);
            }
            add(playerScoreName);
            add(startNoScore);
        } else {
            add(start);
        }
        revalidate();
        repaint();

    }

    public int getPlayerScore() {
        return score;
    } //just for more readability elsewhere

    public String getPlayerName() {
        return playerScoreName.getText();
    }//just for readability elsewhere
}
