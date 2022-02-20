import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.Scanner;

public class OptionsForActions {
    private static final Scanner scanner = new Scanner(System.in);

    // mainMenu method is a static method which shows the main menu so the application.main don't look messy
    public static void mainMenu(){

            System.out.println("Welcome to the FindNewsFirst application \nwith this application you can find all" +
                    " the news available");
            System.out.println("You can search for the type of news you want from the list bellow" +
                    "\n1.Top-Headlines" +
                    "\n2.All articles from large and small news sources and blogs" +
                    "\n3.To redo one of your five last searches." +
                    "\n4.To Quit the navigation" +
                    "\nEnter the number of your choice");
    }

    public static String categoryChoices(){

        boolean chooseCorrectly= true;
        String category="";
        while (chooseCorrectly) {
            chooseCorrectly = false;
            System.out.println("The category you want is:" +
                    "\n1.business" +
                    "\n2.entertainment" +
                    "\n3.general" +
                    "\n4.health" +
                    "\n5.science" +
                    "\n6.sports" +
                    "\n7.technology" +
                    "\nPress the number of your choice");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    category = "business";
                    break;
                case 2:
                    category = "entertainment";
                    break;
                case 3:
                    category = "general";
                    break;
                case 4:
                    category = "health";
                    break;
                case 5:
                    category = "science";
                    break;
                case 6:
                    category = "sports";
                    break;
                case 7:
                    category = "technology";
                    break;
                default:
                    System.out.println("You should press a number between 1 to 7");
                    chooseCorrectly = true;
                    break;
            }
        }

        return category;

    }
}
