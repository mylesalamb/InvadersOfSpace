/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: Application window
 */
package testproject;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ApplicationWindow extends JFrame {

    private final int WINDOW_X = 1024;
    private final int WINDOW_Y = 768;//fixed dimensions of the window
    private JPanel cardHolder = new JPanel(); //hold the menus and the gameboard
    private CardLayout layout = new CardLayout();//layout capable of holding multiple jpanels
    private PageContent content;
    private GameBoard game;//declaring menu objects
    private ProgSounds sound = new ProgSounds("song.wav");

    public ApplicationWindow() {
        initial();
    }

    public void initial() {
        File fileLoc = new File("");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(fileLoc.getAbsolutePath()+"\\src\\testproject\\icon1.png"));
        this.setTitle("Invaders of Space");
        sound.start(10);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                content.packUp();//writes to file when 'x' is pushed
            }
        });//overrides the windows close operation allowing user to save as
        //window closes allows novice users not lose scores
        JButton button = new JButton("Start Game");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.kill();//annoying menu music stops
                layout.show(cardHolder, "2");//to show game
                game.focus(content.getThemeColour(), content.news.diffSet + 1, content.returnLowHS());
                //passes in colour for sprites, difficulty setting as well as lowest high score for 
                //game end screen
            }
        }); //button to switch cards to game, must be created in application window where it has acess
        //to both menus
        cardHolder.setLayout(layout);
        content = new PageContent(button);
        JButton button2 = new JButton("Main menu");
        button2.setPreferredSize(content.BUTTONSIZE);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.start(10);
                layout.show(cardHolder, "1");
                content.news.update(game.getPlayerName(),content.returnDiffName(), game.getPlayerScore());
                content.initial();
                //switching to the main menu
            }
        });
        game = new GameBoard(button2);//parameter to get back to main
        cardHolder.setLayout(layout);
        cardHolder.add(content, "1");
        cardHolder.add(game, "2");
        add(cardHolder);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WINDOW_X, WINDOW_Y));
        setPreferredSize(new Dimension(WINDOW_X, WINDOW_Y));
        setLocationRelativeTo(null); //centers the window
        setResizable(false);
        pack();
        setVisible(true);//methods called that relate to the appearence of the
        //JFrame itself
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                ApplicationWindow newWindow = new ApplicationWindow();
                //initializes the object
            }
        });
    }
}
