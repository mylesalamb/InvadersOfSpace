/*
 * Name: Myles Lamb
 * Project Title: Invaders Of Space
 * Class: DataReading
 */

package testproject;

import java.io.*;
import java.util.Scanner;

public class DataReading {

    int[] scores = new int[3];
    String[] names = new String[3];
    int diffSet;
    String themeColour;
    File fileLoc = new File("");
    String fileName;

    public DataReading() {
        
       
        fileName = fileLoc.getAbsolutePath()+"\\src\\testproject\\file.txt";//file in relation to .jar              
        int numberInFile = 0;
        Scanner fileScanner = null;
        final int COLS = 2;
        try {
            fileScanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            fileScanner.useDelimiter("[\\r\\n,]+");
            while (fileScanner.hasNext()) {
                numberInFile++;
                fileScanner.next();//determines how many pieces of data are in the file
            }
        } catch (FileNotFoundException s) {
            try{
            System.out.print("file not found");
            File writeFile = new File(fileName);
            writeFile.createNewFile();
            FileWriter fw = new FileWriter(fileLoc.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("NoName: Hard,0,NoName: Hard,0,NoName: Hard,0,3,green,");
            }
            catch(Exception e){}
            //System.exit(0);//if the file is not found displays error then exits

        } finally {
            if (fileScanner != null) {
                System.out.println("File found!");
                fileScanner.close();
            }
        }

        try {
            int currentLine = 0;
            fileScanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            fileScanner.useDelimiter("[\\r\\n,]+");
            while (fileScanner.hasNext()) {
                for (int i = 0; i < 3; i++) {
                    names[i] = fileScanner.next();
                    scores[i] = fileScanner.nextInt();
                }

                diffSet = fileScanner.nextInt();

                themeColour = fileScanner.next();
            }
        } catch (FileNotFoundException s) {
            System.out.println("File lost!");//in the event that the file is not found
        } finally {
            if (fileScanner != null) {
                fileScanner.close();

            }
        }
    }

    public void write() {

        try {

            File writeFile = new File(fileName);
            System.out.print(fileName);
            if (!writeFile.exists()) {
                System.out.print("could not print file destination non existent");
                writeFile.createNewFile();

            }
            FileWriter fw = new FileWriter(writeFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < names.length; i++) {
                bw.write("" + names[i] + ",");
                bw.write("" + scores[i] + ",");
            }

            bw.write("" + diffSet + ","); 

            bw.write("" + themeColour + ",");//difficulty settings and colour string name written to file

            bw.close();
            System.out.println("Written to file");//output to file is here
        } catch (IOException s) {
            System.out.println("Couldn't write to file");

        }
    }

    public void update(String nam,String diff, int score) {
        if (nam.isEmpty()) {
            nam = "NoName: "+diff; //prevents no such element exception when the text box is empty
        } else {
            String temp = "";
            for (int i = 0; i < nam.length(); i++) {
                temp = temp + " ";
            }//if the name is just spaces
            if (temp.equalsIgnoreCase(nam)) {//if the name is just spaces
                nam = "SPACE";
            }
            nam = nam+": "+diff;
        }
        System.out.println(score + " " + nam);
        if (score > scores[2]) {
            scores[2] = score;
            names[2] = nam;
            BubbleSort();
        }
        System.out.println("sorted");
    }

    //for sortting high scores not being used yet
    private void BubbleSort() {
        int flag = 0;
        String temp = "";
        do {
            flag = 0;
            for (int i = 0; i < ((names.length) - 1); i++) {
                if (scores[i] < scores[i + 1]) {
                    flag++;//if swaps aree not made will = 0 and loop will terminate
                    scores[i] = scores[i] + scores[i + 1];
                    scores[i + 1] = scores[i] - scores[i + 1];
                    scores[i] = scores[i] - scores[i + 1];//swaps scores

                    temp = names[i];
                    names[i] = names[i + 1];
                    names[i + 1] = temp;//swaps the strings

                }

            }
        } while (!(flag == 0));

    }
}
