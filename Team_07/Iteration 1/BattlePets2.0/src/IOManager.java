import java.util.Scanner;

public class IOManager
{
    static Scanner scanner = new Scanner(System.in);

    public static void promptLine(String message){
        System.out.println(message);
    }
    public static void promptPartial(String message){
        System.out.print(message);
    }
    public static void endLine(){
        System.out.print('\n');
    }

    /**
     * getInputInteger()
     * takes in integer from user and returns what is inputted
     * @return i
     */
    public static int getInputInteger(){
        int i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }
    public static String getInputString(){
        return scanner.nextLine();
    }
    /**
     * getInputDouble()
     * takes in double from user and return what is inputted
     * @return skill
     */
    public static Double getInputDouble() {
        double i = scanner.nextDouble();
        scanner.nextLine();
        return i;
    }
}
