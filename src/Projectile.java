/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Projectile
 */

package testproject;

import java.awt.Color;
import java.awt.Graphics;


public class Projectile {

    int xPos;
    int yPos; //used in a sublass so not private
    static Color themeColour;
    
    private int directionX;
    boolean draw,collision,multiplier;
    
 public void projectile(){multiplier = false;}
    public boolean newInstance(Graphics g, int xIn, int yIn,boolean space, int directionXIn) {
        if (draw == false) {
            collision = false;
            xPos = xIn;
            yPos = yIn;
            directionX = directionXIn;
            fire(g);
            draw = true;
            
            //if a player has not already fired
        } else {
            space = requestUpdate(g, space); //updates appeareance of laser
        }
        return space;
    }

    public void fire(Graphics g) {
        g.setColor(themeColour);
        update();//updates the coordinates of the shot
        g.fillRect(xPos, yPos, 3, 25*(1+Boolean.compare(multiplier,false)));//if multipler is true it will lengthen the shot
        
        
    }

    public void update() {
        yPos += (10*directionX*(1+Boolean.compare(multiplier,false)));
       // System.out.println(Boolean.compare(multiplier,false));
    }
    public int getX(){
    return xPos;
    }
    public int getY(){
    return yPos;
    }

    public boolean requestUpdate(Graphics g, boolean space) {
        if (yPos <= -12 || yPos >= 768||collision == true) {

            draw = false;
            space = false;
            //shot has reached end of screen
            //user able to fire again

        } else {
            fire(g);
            //updates where the projectile if it hasnt 
            //reached the end of the screen
        }
        return space;
        //space = true if shot is moving
        //space = false if shot has left screen
    }
    
    public void collisionDetetct(){
    collision = true;
    yPos = -12; //off board
    }
    public void killProj(){
    draw = false;
    }
    public void togglePowerup(Boolean in){
    multiplier = in;
    }
    public boolean returnPowerup(){
    return multiplier;
    }
    public static void setThemeColour(Color in){
    themeColour = in;
    }
}
    
