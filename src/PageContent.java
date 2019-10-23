/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: PageContent
 */

package testproject;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PageContent extends JPanel {

    final Dimension BUTTONSIZE = new Dimension(150, 50);
    private final String[] COLOURID = {"red", "blue", "green", "magenta", "orange", "cyan","white"}; //colours for jcombobox
    DataReading news = new DataReading(); //where data from file is read
    private Color themeColor = new Color(0, 0, 0);//RGB of theme colour
    private final JButton START_BUTTON; //button that switches to main
    private FlowLayout mainMenuLayout = new FlowLayout();//to organise components in pause menu and end screen
    private JLabel mainTitle = makeLabel("Invaders of Space", true); //title of associated menu
    private JButton mainB = makeButton(1, "Main");
    private JButton settingsB = makeButton(2, "Settings");
    private JButton highScoresB = makeButton(4, "HighScores");
    private JLabel[] highScores = new JLabel[3];
    private JSlider diffSlider;
    private final String[] DIFF = {"Peaceful", "Easy", "Medium", "Hard", "Meme"};
    private JLabel content = new JLabel("Difficulty: " + DIFF[news.diffSet]);
    private JComboBox colourChooser = new JComboBox(COLOURID);
    //private Field field;

    public PageContent(JButton m) {

        colourChooser.setBackground(Color.black);
        colourChooser.setSelectedItem((Object) news.themeColour);
        stringToColour();
        colourChooser.setForeground(themeColor);
        colourChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stringToColour();//
                removeAll();     //allows for the appearence of the menu
                settings();      //to change as the user makes a decision
                revalidate();    //
                repaint();       //
                colourChooser.setPopupVisible(true);
                colourChooser.requestFocus();
            }
        });

        diffSlider = new JSlider(0, 4, news.diffSet);//difficulty slider
        START_BUTTON = m;
        START_BUTTON.setPreferredSize(BUTTONSIZE);
        mainMenuLayout.setVgap(40);
        mainMenuLayout.setHgap(500);
        setLayout(mainMenuLayout);
        setBackground(Color.BLACK);
        content.setForeground(Color.white);//text colour
        content.setFont(new Font("Terminal Bold", Font.PLAIN, 20));
        diffSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                removeAll();
                news.diffSet = diffSlider.getValue();
                content.setText("Difficulty: " + DIFF[diffSlider.getValue()]);
                settings();
                revalidate();
                repaint();
            }
        });
        initial();
    }

    public void initial() {
        removeAll();
        mainTitle = makeLabel("Invaders of Space", true);
        add(mainTitle); //main title
        add(START_BUTTON);//button to switch to view the game board
        add(highScoresB);
        add(settingsB);
        mainTitle.requestFocus();
        revalidate();
        repaint();
        //how the main menu appears at start up, m posesses action listener to switch cards
        //has to be passed in as it doesnt have acess within the menu
    }

    private void highScores() {
        mainTitle.setText("HighScores");
        add(mainTitle);
        for (int i = 0; i < news.names.length; i++) {
            highScores[i] = new JLabel(news.names[i] + ": " + news.scores[i]);
            highScores[i].setFont(new Font("Terminal Regular", Font.PLAIN, 25));
            highScores[i].setForeground(Color.white);
            add(highScores[i]); //adds the highscores to the settings screen
        }
        add(mainB);
        mainB.requestFocus(); //for keyboad operating menu
    } //prints the current high scores to the highscores menu

    private void settings() {
        mainTitle = makeLabel("Settings", true);
        add(mainTitle);
        add(content);
        add(diffSlider);
        add(colourChooser);
        add(mainB);//button to main menu
        diffSlider.requestFocus();
    }

    private JButton makeButton(int in, String title) {
        JButton button = new JButton(title);
        button.setPreferredSize(BUTTONSIZE);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //when button is pressed
                removeAll();
                switch (in) {
                    case 1:
                        initial();
                        button.setToolTipText("To main menu");
                        break;
                    case 2:
                        settings();
                        button.setToolTipText("To settings");
                        break;
                    default:
                        highScores();
                        button.setToolTipText("To high scores");
                        break;
                }
                revalidate();
                repaint();
            }
        });
        return button;
    } //method made to simplify the appearence and actins of buttons

    private JLabel makeLabel(String title, boolean h1) {
        JLabel label = new JLabel(title);
        if (h1 == true) {//if the label is to be a title
            label.setForeground(themeColor);
            label.setFont(new Font("Terminal Bold", Font.PLAIN, 48));
        } else {
            label.setFont(new Font("Terminal Bold", Font.PLAIN, 25));
            label.setForeground(Color.white);
        }
        return label;

    } //method made to simplify making changes to appearence of text

    public void packUp() {
        news.themeColour = (String) colourChooser.getSelectedItem(); //gets the currently selected colour
        news.write(); //writes high scores and selected colour to file
    }

    public Color getThemeColour() {
        return themeColor;

    }

    public int returnLowHS() {
        return news.scores[2];
    }

    public String returnDiffName() {
        return DIFF[news.diffSet];
    }

    private void stringToColour() {
        if (themeColor == null) {
            themeColor = new Color(0, 0, 0);
        }
        try {
            Field field = Color.class.getField(COLOURID[colourChooser.getSelectedIndex()]);//gets the colour constant with the name of the selected string
            themeColor = (Color) field.get(null); //casts field as the colour
            colourChooser.setForeground(themeColor);
        } catch (Exception ex) {
            Logger.getLogger(PageContent.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
