package supermarketmanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diogodias
 */
public class SupermarketManagementSystem {

    private static final int DISPLAY_LIMIT = 20;

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
                    database.sortByName();
                    displayFirstEmployees(database, DISPLAY_LIMIT);
                    break;

                case SEARCH:
                    System.out.println("SEARCH selected");
                    searchEmployee(sc, database);
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

    // Displays the first employees of the list (up to the given limit).
    private static void displayFirstEmployees(EmployeeDatabase database, int limit) {
        ArrayList<Employee> employees = database.getEmployees();
        int total = Math.min(limit, employees.size());

        System.out.println("First " + total + " employees in alphabetical order:");

        for (int i = 0; i < total; i++) {
            Employee employee = employees.get(i);
            System.out.printf("%2d. %-22s %-30s %s%n",
                    i + 1,
                    employee.getFullName(),
                    employee.getJobTitle(),
                    employee.getDepartment().getDepartmentName());
        }
    }

    // Asks for a name, searches for it and shows the employee's details.
    private static void searchEmployee(Scanner sc, EmployeeDatabase database) {
        String name = "";

        while (name.isEmpty()) {
            System.out.println("Please enter the full name to search (e.g. John Smith):");
            // trim() removes outer spaces; replaceAll joins repeated inner spaces into one
            name = sc.nextLine().trim().replaceAll("\\s+", " ");

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            }
        }

        Employee found = database.findByName(name);

        if (found == null) {
            System.out.println("No employee found with the name \"" + name + "\".");
            return;
        }

        System.out.println("Employee found:");
        System.out.println("  Name:         " + found.getFullName());
        System.out.println("  Job Title:    " + found.getJobTitle());
        System.out.println("  Manager Type: " + getManagerType(found));
        System.out.println("  Department:   " + found.getDepartment());
        System.out.println("  Company:      " + found.getCompany());
    }

    // Returns the management level for managers, or the employment type otherwise.
    private static String getManagerType(Employee employee) {
        if (employee instanceof Manager) {
            return ((Manager) employee).getManagementLevel();
        }
        return "Not a manager (" + employee.getEmploymentType() + ")";
    }
}
