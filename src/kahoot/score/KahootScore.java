/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kahoot.score;

/**
 * Kahoot Score Program
 *
 * @author bevan5974 (KingGymbo, Gymbo101, Ben van der Stam) Date: December 18th, 2014
 * Description: This is a console program that has the user add in their 8
 * Kahoot scores by entering the mark when prompt. The program takes these
 * marks, and writes them to a file. They can be read after and displayed with a
 * final total of the scores.
 */
import java.io.*;
import java.util.Scanner;

public class KahootScore {

  /**
   * @param args
   *            the command line arguments
   */
  public static void main(String[] args) {
    //Declare variables and initialize scanner for user input
    int numScores = 8; // Number of marks to put in is 8
    double maxScore = 10000; // Maximum score is 10000 for a game
    double minScore = 0; // Minimum score is 0
    Scanner input = new Scanner(System.in);
    File kahootScoreFile = new File("kahootscore.dat");
    String option;
    
    System.out.println("This program will store your 8 Kahoot scores for the semester");
    System.out.println("Please type '1' to add scores or '2' to read score file and hit the enter key.");
    option = input.nextLine();

    if ("1".equals(option)) {
      // Create an output file stream and send text to it
      try {
        double score[] = new double[numScores];
        String userInput = "";

        
        System.out.println("Enter your 8 kahoot scores: ");
        
        for (int i = 0; i < numScores; i++) {
          System.out.print("Enter score " + (i+1) + ": ");
          //Ask for the student's input
          userInput = input.nextLine();
          //Convert the students input to a double, but if it fails, a NaN is returned
          score[i] = doubleConverter(userInput);
          if ((Double.isNaN(score[i])) || 
              (score[i] > maxScore) || (score[i] < minScore)) {
            i--;
            System.out.println("Invalid score (must be between 0 and 10 000 ), try again");
          }
        }
        input.close();
        System.out.println("Writing File to kahootscore.dat");
        System.out.println("Scores you have input:");
        //Create input file stream to write data to file
        FileWriter out = new FileWriter(kahootScoreFile);
        BufferedWriter writeFile = new BufferedWriter(out);
        //Display scores to file so that the student can review their input
        for (int j = 0; j < score.length; j++) {
          if (j != score.length - 1) {
            System.out.print(score[j] + ", ");
            writeFile.write(String.valueOf(score[j]) + ",");
          } else {
            System.out.print(score[j] + "\n");
            writeFile.write(String.valueOf(score[j]));
          }
        }
        writeFile.close();
        out.close();
        System.out.println("Successfully written to kahootscore.dat");
      } catch (IOException e) {
        System.out.println("File cannot be written.");
        System.err.println("IOException: " + e.getMessage());
      }
    }
    
    else if ("2".equals(option)) {
      // Create file input stream for the File object
      FileReader in;
      BufferedReader readFile;
      try {
        double[] score;
        String[] scoreString;
        in = new FileReader(kahootScoreFile);
        readFile = new BufferedReader(in);
        String line;
        double total = 0;
        // Setup an accumulator to find total score
        // Only read the content of the first line
        line = readFile.readLine();
        //System.out.println(line);
        scoreString = line.split(",");
        score = new double[scoreString.length];
        for (int i = 0; i < scoreString.length; i++) {
          score[i] = Double.parseDouble(scoreString[i]);
          if (i != scoreString.length - 1) {
            System.out.print(score[i] + ", ");
          } else {
            System.out.println(score[i]);
          }
          total += score[i];
        }
        System.out.println("Total score: " + total);
      } catch (FileNotFoundException e) {
        System.out.println("File does not exist or could not be found");
        System.err.println("FileNotFoundException: " + e.getMessage());
      } catch (IOException e) {
        System.out.println("Problem reading file.");
        System.err.println("IOException: " + e.getMessage());
      }
    }
    else {
      System.out.println("Invalid option. Program will quit.");
      System.exit(0);
    }
  }
  private static double doubleConverter(String verifyString) {
    // Convert userInput to double, but if input is invalid, a NaN is returned
    try {
      double doubleNum = Double.NaN;
      doubleNum = Double.parseDouble(verifyString);
      return doubleNum;
    } catch (NumberFormatException e) {
      return Double.NaN;
    }
  }
}

