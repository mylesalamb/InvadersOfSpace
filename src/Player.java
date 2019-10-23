/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Player
 */

package testproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

public class Player extends Character {

    Projectile laser = new Projectile();
    private int[] xPoints = {0, 0, 7, 7, 14, 14, 21, 21, 28, 28, 35, 35, 42, 42, 49, 49, 0};
    private int[] yPoints = {0, 7, 7, 14, 14, 21, 21, 28, 28, 21, 21, 14, 14, 7, 7, 0, 0};
    //X and Y coordinates of the player
    private int ammo;

    public Player() {
        baseX = 462;
        baseY = 718;//placement on board at the start of the game
        ammo = 0;
        Projectile.setThemeColour(themeColour);
    }

    public void Appearence(Graphics g) {
        if (alive == true) {

            GeneralPath Player = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);

            Player.moveTo(baseX, baseY);
            for (int i = 0; i < (xPoints.length); i++) {
                Player.lineTo(baseX + xPoints[i], baseY - yPoints[i]);
            }
            //moves the 'pen' to each position respective to the baseX and baseY

            Graphics2D pen = (Graphics2D) g;//must be declared for genereal path to be used
            pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(themeColour);
            pen.fill(Player);
            pen.draw(Player);

        }
    }

    public boolean fire(Graphics g, boolean space) {
        if (alive == true) {
            space = laser.newInstance(g, baseX + 23, baseY - 53, space, -1);
            //updates the position of the laser or creates a new one 
        } else {
            space = false;
        }
        if (space == false && ammo != 0) {
            ammo--;
            if (ammo == 0) {
                laser.togglePowerup(false);
            }
            //laser no longer in play -> subtrcts from ammo
            //if powerup is active
        }
        return space;
    }

    public void tellProjCol() {
        laser.collisionDetetct();//stops the laser from drawing
    }

    public void applyEffects(Boolean in) {
        laser.togglePowerup(in);
        if (in == true) {
            ammo = 10;
        }
    }

    @Override
    public void kill() {
        alive = false;
        laser.togglePowerup(false);
        laser.killProj();
    }

    public int getProjLoc(boolean choice) {
        if (choice == true) {
            return laser.getX();
        } else {
            return laser.getY();
        }

    }

}
