/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Enemy
 */

package testproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class Enemy extends Character {

    private boolean fire, pTF;
    private final Random GENERATOR = new Random();
    private static boolean directionX, hasEnd = true;
    private Projectile laser = new Projectile();
    private boolean mirror = true;
    private static int counter, animationInt, difficulty = 0;
    private static boolean animation = false;
    private static ProgSounds sound = new ProgSounds("song(1).wav"); //one shared sound clip
    private static int[] xPoints = {0, 0, 5, 5, 10, 10, 25, 25, 30, 30, 35, 35, 30, 30, 25, 25, 10, 10, 5, 5, 0}; //held statically 
    private static int[] yPoints = {0, 5, 5, 25, 25, 30, 30, 25, 25, 5, 5, 0, 0, 5, 5, 10, 10, 5, 5, 0, 0};       //as more efficient to 2 arrays for each alien

    public Enemy(int xIn, int yIn) {
        baseX = xIn;
        baseY = yIn;
        fire = false;
        pTF = false;

    }

    private boolean fire(Graphics g,boolean firing) {
        firing = laser.newInstance(g, baseX + 17, baseY + 10, firing, 1);
        //+ ints to center shot
        return firing; //false if collision or left screen
    }

    //@Override
    public void Appearence(Graphics g) {

        if (alive == true) {
            GeneralPath drawLine = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
            drawLine.moveTo(baseX + xPoints[0], baseY - yPoints[0]);
            for (int i = 0; i < (xPoints.length); i++) {
                drawLine.lineTo(baseX + xPoints[i], baseY - yPoints[i]);
            }
//arranges points in the xpoints and ypoints array
            Graphics2D pen = (Graphics2D) g;
            pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(themeColour);
            pen.fill(drawLine);
            pen.draw(drawLine);//connects the points on the general path drawing them out
            pen.setColor(Color.black);
            pen.fillRect(baseX + 10, baseY - 20, 5, 5);
            pen.fillRect(baseX + 20, baseY - 20, 5, 5);//draws eyes on the aliens

            if (fire == false && pTF == true) {
                if (GENERATOR.nextInt(300) < Math.pow(1.5, difficulty + 1)) { //set to 300
                    fire = true;
                }
            }
        }
        if (counter == 1) {
            update(); //moves each town the animation state changes
        }
        if (fire == true) {
            fire = fire(g, fire);
        }
    }

    private void update() {
        if (alive == true) {
            if (!(mirror == directionX)) {
                moveDown(4 * (difficulty + 1));
                mirror = directionX;
            }
            if (directionX == true) {
                if (baseX >= 970) {
                    hasEnd = false;

                }
                moveRight(10 + 2 * difficulty);

            }
            if (directionX == false) {
                if (baseX <= 10) {
                    hasEnd = true;

                }
                moveLeft(10 + 2 * difficulty);
            }
        }
    }

    public static void animate() {
        counter++;
        if (counter % 61 == 0) {
            animation = (!(animation));
            if (animation == true) {
                animationInt = 5;

            } else {
                animationInt = 0;
            }
            xPoints = new int[]{animationInt,animationInt, 5, 5, 10, 10, 25, 25, 30, 30, 35 + animationInt * -1, 35 + animationInt * -1, 30 + animationInt * -1, 30 + animationInt * -1, 25, 25, 10, 10, 5 + animationInt, 5 + animationInt, 0 + animationInt};
            //gives the alien its two appearences of movement
            counter = 1;//only to occur once per second
        }

    }

    public static void changeDirection() {
        directionX = hasEnd; //if the aliens on one side has reached the end of the screen
    }

    public void kill() {
        alive = false;
        baseX = -20; //places alien off screen
        baseY = -20; //
        sound.start();//places death sound
    }

    public void tellProjCol() {
        laser.collisionDetetct();
    }

    public void ptf() {
        pTF = true;
    }

    public int getProjLoc(boolean choice) {
        if (choice == true) {
            return laser.getX();
        } else {
            return laser.getY();
        }
    }

    public static void diffSetting(int in) {
        difficulty = in;
    }
}
