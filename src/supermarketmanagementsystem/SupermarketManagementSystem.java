package supermarketmanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diogodias
 */
public class SupermarketManagementSystem {

    private static final int DISPLAY_LIMIT = 20;
    private static final String NOT_SPECIFIED = "N/A";

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
                    addEmployees(sc, database);
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

    // Lets the user add one or more new managers, then shows all newly added records.
    private static void addEmployees(Scanner sc, EmployeeDatabase database) {
        ArrayList<String> departmentNames = database.getDepartmentNames();

        if (departmentNames.isEmpty()) {
            System.out.println("No departments available. Please load a data file first.");
            return;
        }

        do {
            String name = readNewEmployeeName(sc, database);
            int spaceIndex = name.indexOf(' ');
            String firstName = name.substring(0, spaceIndex);
            String lastName = name.substring(spaceIndex + 1);

            // Manager type choice, listed from the ManagerType enum
            System.out.println("Please select from the following Management Staff:");
            for (ManagerType type : ManagerType.values()) {
                System.out.println(type.getOptionNumber() + ". " + type.getJobTitle());
            }
            int typeNumber = readNumberInRange(sc, 1, ManagerType.values().length);
            ManagerType managerType = ManagerType.fromNumber(typeNumber);

            // Department choice, listed from the departments that already exist
            System.out.println("Please select the Department:");
            for (int i = 0; i < departmentNames.size(); i++) {
                System.out.println((i + 1) + ". " + departmentNames.get(i));
            }
            int departmentNumber = readNumberInRange(sc, 1, departmentNames.size());
            String departmentName = departmentNames.get(departmentNumber - 1);

            Department department = Department.createDepartment(departmentName);

            Employee newEmployee = Manager.createManager(firstName, lastName,
                    NOT_SPECIFIED, NOT_SPECIFIED, 0.0, department,
                    managerType.getJobTitle(), NOT_SPECIFIED);

            database.addNewEmployee(newEmployee);

            System.out.println("\"" + name + "\" has been added as \""
                    + managerType.getJobTitle() + "\" to \"" + departmentName
                    + "\" successfully!");

        } while (askYesNo(sc, "Would you like to add another employee?"));

        displayNewEmployees(database);
    }

    // Reads and validates the new employee's full name.
    private static String readNewEmployeeName(Scanner sc, EmployeeDatabase database) {
        while (true) {
            System.out.println("Please input the Employee Name (first and last name):");
            String name = sc.nextLine().trim().replaceAll("\\s+", " ");

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }

            // Letters only (plus ' and -), with at least two words: first and last name
            if (!name.matches("[A-Za-z'-]+( [A-Za-z'-]+)+")) {
                System.out.println("Please enter a first and last name using letters only.");
                continue;
            }

            name = capitaliseWords(name);

            // Names must be unique, because the Binary Search relies on them
            if (database.findByName(name) != null) {
                System.out.println("An employee named \"" + name + "\" already exists.");
                continue;
            }

            return name;
        }
    }

    // Makes the first letter of each word upper case, e.g. "john joe" -> "John Joe".
    private static String capitaliseWords(String text) {
        String[] words = text.split(" ");
        String result = "";

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            result += Character.toUpperCase(word.charAt(0)) + word.substring(1);

            if (i < words.length - 1) {
                result += " ";
            }
        }
        return result;
    }

    // Reads a whole number and keeps asking until it is between min and max.
    private static int readNumberInRange(Scanner sc, int min, int max) {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // Not a number - handled by the message below
            }

            System.out.println("Invalid choice. Please enter a number between "
                    + min + " and " + max + ":");
        }
    }

    // Asks a yes/no question and keeps asking until the answer is Y or N.
    private static boolean askYesNo(Scanner sc, String question) {
        while (true) {
            System.out.println(question + " (Y/N):");
            String answer = sc.nextLine().trim().toUpperCase();

            if (answer.equals("Y")) {
                return true;
            }
            if (answer.equals("N")) {
                return false;
            }
            System.out.println("Please answer Y or N.");
        }
    }

    // Shows every record the user has added during this run.
    private static void displayNewEmployees(EmployeeDatabase database) {
        ArrayList<Employee> newEmployees = database.getNewEmployees();

        System.out.println("Newly added records (" + newEmployees.size() + "):");
        for (Employee employee : newEmployees) {
            System.out.println("  " + employee);
        }
    }
}
