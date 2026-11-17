
package supermarketmanagementsystem;

import java.util.Scanner;

/**
 *
 * @author diogodias
 */
public class SupermarketManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeDatabase database = new EmployeeDatabase();

        loadDataFile(sc, database);

        MenuOption choice;

        do {
            displayMenu();
            choice = readMenuChoice(sc);

            switch (choice) {
                case SORT:
                    System.out.println("SORT selected");
                    // Merge Sort will be implemented in the next commit
                    break;

                case SEARCH:
                    System.out.println("SEARCH selected");
                    // Recursive Binary Search will be implemented later
                    break;

                case ADD_RECORDS:
                    System.out.println("ADD RECORDS selected");
                    // New employee input will be implemented later
                    break;

                case CREATE_BINARY_TREE:
                    System.out.println("Create Binary Tree selected");
                    // Binary tree hierarchy will be implemented later
                    break;

                case EXIT:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
            }

        } while (choice != MenuOption.EXIT);

        sc.close();
    }

    // Asks for the data file name until a valid file is read.
    private static void loadDataFile(Scanner sc, EmployeeDatabase database) {
        boolean loaded = false;

        while (!loaded) {
            System.out.println("Please enter the filename to read:");
            String filename = sc.nextLine().trim();

            loaded = database.loadFromFile(filename);

            if (loaded) {
                System.out.println("File read successfully");
                System.out.println("Total employees loaded: " + database.getCount());
            } else {
                System.out.println("File not found: " + filename + ". Please try again.");
            }
        }
    }

    // Prints every menu option by iterating through the MenuOption enum.
    private static void displayMenu() {
        System.out.println();
        System.out.println("Do you wish to SORT or SEARCH:");

        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getOptionNumber() + ". " + option.getLabel());
        }
    }

    // Reads the user's choice and keeps asking until it is a valid option.
    private static MenuOption readMenuChoice(Scanner sc) {
        int totalOptions = MenuOption.values().length;

        while (true) {
            String input = sc.nextLine().trim();

            try {
                int number = Integer.parseInt(input);
                MenuOption option = MenuOption.fromNumber(number);

                if (option != null) {
                    return option;
                }
            } catch (NumberFormatException e) {
                // Not a number - handled by the message below
            }

            System.out.println("Invalid option. Please enter a number between 1 and "
                    + totalOptions + ":");
        }
    }
}