/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: ProgSounds
 */

package testproject;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;


public class ProgSounds {

    private File FileLoc;
    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
    private String fileLoc;

    public ProgSounds(String in) {
        try {
            FileLoc = new File("");
            FileLoc = new File(FileLoc.getAbsolutePath()+"\\src\\testproject\\"+in);
            System.out.print(FileLoc);
            stream = AudioSystem.getAudioInputStream(FileLoc);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info); //loads the data in the buffer
            clip.setLoopPoints(0, clip.getFrameLength()); //loop point at the end of the file

            

        } catch (Exception e) {
System.out.println("Error setting up sound file");
System.out.print(FileLoc);

        }
        
    }

    public void start(int in) {
        try {
            if (clip.isOpen()) {
                clip.stop();
                clip.setMicrosecondPosition(0);
                clip.loop(in);
                System.out.print("attempting start");
            } else {
                clip.open(stream);
                clip.loop(in);
    //sound used for main menu
            }

        } catch (Exception e) {
		System.out.println("error setting up sound for main")
        }

    }

    public void start() {
        try {
            if (clip.isOpen()) {

                clip.stop();
                clip.setMicrosecondPosition(0);
                clip.start();

            } else {
               clip.open(stream);
                clip.start();

            }

        } catch (Exception e) {
            System.out.println("error playing short sound");
        }

    }

    public void kill() {
        clip.stop();

    }
}
