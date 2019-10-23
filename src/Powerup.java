/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Powerup
 */

package testproject;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Powerup extends Projectile {

    private Random generator = new Random();

    @Override
    public boolean newInstance(Graphics g, int xIn, int yIn, boolean space, int directionXIn) {
        if (draw == false) {
            multiplier = generator.nextBoolean();//decides what the powerup will be

        }
        return super.newInstance(g, xIn, yIn, space, directionXIn); //uses super class
    }

    @Override
    public void fire(Graphics g) {
        g.setColor(themeColour);
        g.fillRect(xPos, yPos, 50, 20);
        g.setColor(Color.black);
        if (multiplier == true) {
            g.drawString("+LIFE", xPos + 10, yPos + 15);
        } else {
            g.drawString("LASER", xPos + 5, yPos + 15);
        }
        update();

    }

    @Override
    public void update() {
        yPos += 5;

    }

    public boolean getPowerValue() {
        return multiplier;
    }

}
