/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: PowerEnemy
 */

package testproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

/**
 *
 * @author mashe
 */
public class PowerEnemy extends Character {

    private boolean direction = true;
    private boolean fire = false;
    private Powerup laser = new Powerup();
    private static int[] xPoints = {0,0,5,5,10,10,25,25,30,30,35,35,25,25,20,20,15,15,10,10,0};
    private static int[] yPoints = {0,10,10,15,15,20,20,15,15,10,10,0,0,5,5,0,0,5,5,0,0};

    public PowerEnemy() {
        baseX = -100;
        baseY = 25;
        alive = false;

    }

    public void Appearence(Graphics g) {
        if (fire == true) {
            fire = fire(g, themeColour, fire);

        }
        if (alive == true) {

            GeneralPath Player = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);

            Player.moveTo(baseX + xPoints[0], baseY - yPoints[0]);
            for (int i = 0; i < (xPoints.length); i++) {
                Player.lineTo(baseX + xPoints[i], baseY - yPoints[i]);
            }
            
            
            Graphics2D pen = (Graphics2D) g;
            pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(themeColour);
            pen.fill(Player);
            pen.draw(Player);
            g.setColor(Color.black);
            g.fillRect(baseX+10, baseY -13, 5, 5);
            g.fillRect(baseX+20, baseY-13, 5, 5);
            g.setColor(themeColour);
           update();

        } else {
            baseX = -100;
            baseY = 45;
        }

    }

    public boolean fire(Graphics g, Color laserColour, boolean space) {
        space = laser.newInstance(g, baseX, baseY, space, 1);//instantiates a new powerup
                                                             //or updates an existing one
        return space;
    }

    private void update() {
        if (alive == true) {
            if (direction == true) {
                moveRight(5);
                if (baseX > 979) {
                    direction = false;
                }
            } else {
                moveLeft(5);
                if (baseX < 1) {
                    direction = true;
                } 
            }
        }
    } //updates the positioning on the screen with each clock cycle

    @Override
    public void kill() {

        if (alive == true) {
            fire = true;

           
        }
        alive = !alive;

    }

    public boolean tellProjCol() {
laser.collisionDetetct();
return laser.getPowerValue(); //once the powerup collides with the player
    }

    public int getProjLoc(boolean choice) {
        if (choice == true) {
            return laser.getX();
        } else {
            return laser.getY();
        }
    }
}
