/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Character
 */
package testproject;

import java.awt.Color;

public class Character {

    int baseX;
    int baseY;
    boolean alive = true;
    static Color themeColour;
    //more efficient to store this once and for all sprites
    

    public void moveLeft(int move) {

        if (baseX >= -20) {
            baseX -= move;
        }
    }

    public void moveRight(int move) {
        if (baseX <= 990) {
            baseX += move;
        }
    }

    public void moveDown(int move) {
        baseY += move;
    }

    public void kill() {
        alive = (!(alive));
    }

    public boolean getAlive() {
        return alive;//returns whether the sprite is alive or not
    }

    public static void setThemeColour(Color in) {
        themeColour = in;
    }//sets the theme colour for the sprites upon instantiation
}
