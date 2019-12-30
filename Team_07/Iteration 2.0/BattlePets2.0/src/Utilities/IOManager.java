package Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IOManager
{
    private static Scanner scanner = new Scanner(System.in);

    /**
     * This method prints out a given string
     * @param message The message to be prompted
     */
    public static void promptLine(String message){
        System.out.println(message);
    }

    /**
     * This method prints out a given string without a line terminator at the end
     * @param message The message to be partially prompted
     */
    public static void promptPartial(String message){
        System.out.print(message);
    }

    /**
     * This method prints out a line terminator
     */
    public static void endLine(){
        System.out.print('\n');
    }

    /**
     * getInputInteger()
     * takes in integer from user and returns what is inputted
     * @return i the integer
     */
    public static int getInputInteger()
    {
        boolean validInput = false;
        int i = -1;
        while(!validInput) {
            try {
                i = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input: try again.");
            }
        }
        return i;
    }
    /**
     * getInputString()
     * takes in string from user and returns what is inputted
     * @return i the string
     */
    public static String getInputString(){

        boolean validInput = false;
        String i="";
        while(!validInput)
        {
            i = scanner.nextLine();
            if(i.length()!=0) {
                validInput = true;
            }
        }
        return i;
    }
    /**
     * getInputDouble()
     * takes in double from user and return what is inputted
     * @return i the double
     */
    public static Double getInputDouble()
    {
        boolean validInput = false;
        double i = -1;
        while(!validInput) {
            try {
            i = scanner.nextDouble();
            scanner.nextLine();
            validInput = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input: try again.");
            }
        }
        return i;
    }
}
