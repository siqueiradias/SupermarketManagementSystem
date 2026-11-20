package supermarketmanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point of the program. Loads the data file and runs the console menu
 * (sort, search, add records, binary tree).
 *
 * Input validation rule used everywhere: every input is checked and, if it is
 * invalid, the user sees the reason and is asked again. The program never
 * crashes on bad input and never stores invalid data.
 *
 * @author diogodias
 */
public class SupermarketManagementSystem {

    private static final int DISPLAY_LIMIT = 20;      // names shown after sorting
    private static final int MIN_TREE_RECORDS = 20;   // minimum records to build the tree
    private static final int MAX_NAME_LENGTH = 50;    // maximum length of a new employee name
    private static final int MAX_RANDOM_RECORDS = 50; // maximum records generated at once

    // Program entry point: loads the data file, then shows the menu until the user exits.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeDatabase database = new EmployeeDatabase();

        displayWelcome();

        // The menu is only shown once the data is loaded, because every option depends on it
        if (!loadDataFile(sc, database)) {
            System.out.println("Exiting the program. Goodbye!");
            sc.close();
            return;
        }

        MenuOption choice;

        do {
            displayMenu();
            choice = readMenuChoice(sc);

            switch (choice) {
                case SORT -> {
                    System.out.println("SORT selected");
                    database.sortByName();
                    displayFirstEmployees(database, DISPLAY_LIMIT);
                }
                case SEARCH -> {
                    System.out.println("SEARCH selected");
                    searchEmployee(sc, database);
                }
                case ADD_RECORDS -> {
                    System.out.println("ADD RECORDS selected");
                    addEmployees(sc, database);
                }
                case GENERATE_RANDOM_DATA -> {
                    System.out.println("GENERATE RANDOM DATA selected");
                    generateRandomEmployees(sc, database);
                }
                case CREATE_BINARY_TREE -> {
                    System.out.println("Create Binary Tree selected");
                    buildEmployeeTree(database);
                }
                case EXIT -> System.out.println("Exiting the program. Goodbye!");
            }

        } while (choice != MenuOption.EXIT);

        sc.close();
    }

    // ---------- Welcome ----------

    // Shows the name of the organisation modelled by the program.
    private static void displayWelcome() {
        System.out.println("==============================================");
        System.out.println("        SUPERMARKET MANAGEMENT SYSTEM");
        System.out.println("  Staff records of a large supermarket chain");
        System.out.println("==============================================");
    }

    // ---------- File loading ----------

    // Asks for the data file name until a file with valid employee records is read.
    // Returns true when the data is loaded, or false if the user types EXIT to quit.
    private static boolean loadDataFile(Scanner sc, EmployeeDatabase database) {
        while (true) {
            System.out.println("Please enter the filename to read (or type EXIT to quit):");
            String filename = sc.nextLine().trim();

            if (filename.equalsIgnoreCase("EXIT")) {
                return false;
            }

            if (filename.isEmpty()) {
                System.out.println("Filename cannot be empty.");
                continue;
            }

            if (!database.loadFromFile(filename)) {
                System.out.println("File not found: " + filename + ". Please try again.");
                continue;
            }

            if (database.getCount() == 0) {
                System.out.println("The file \"" + filename
                        + "\" has no valid employee records. Please choose another file.");
                continue;
            }

            System.out.println("File read successfully");
            System.out.println("Total employees loaded: " + database.getCount());

            if (database.getSkippedLines() > 0) {
                System.out.println("Invalid lines skipped: " + database.getSkippedLines());
            }
            return true;
        }
    }

    // ---------- Menu ----------

    // Prints every menu option by iterating through the MenuOption enum.
    private static void displayMenu() {
        System.out.println();
        System.out.println("Do you wish to SORT or SEARCH:");

        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getOptionNumber() + ". " + option.getLabel());
        }
    }

    // Reads the user's choice and keeps asking until it matches a menu option.
    private static MenuOption readMenuChoice(Scanner sc) {
        int number = readNumberInRange(sc, 1, MenuOption.values().length);
        return MenuOption.fromNumber(number);
    }

    // ---------- Sort ----------

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

    // ---------- Search ----------

    // Asks for a name, searches for it and shows the employee's details.
    private static void searchEmployee(Scanner sc, EmployeeDatabase database) {
        String name = readNonEmptyText(sc,
                "Please enter the full name to search (e.g. John Smith):", "Name");

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
        if (employee instanceof Manager manager) {
            return manager.getManagementLevel();
        }
        return "Not a manager (" + employee.getEmploymentType() + ")";
    }

    // ---------- Add records ----------

    // Lets the user add one or more new managers, then shows all newly added records.
    private static void addEmployees(Scanner sc, EmployeeDatabase database) {
        ArrayList<String> departmentNames = database.getDepartmentNames();

        do {
            String name = readNewEmployeeName(sc, database);
            int spaceIndex = name.indexOf(' ');
            String firstName = name.substring(0, spaceIndex);
            String lastName = name.substring(spaceIndex + 1);

            // Manager type: only the types in the ManagerType enum, and only one Head Manager
            ManagerType managerType = readManagerType(sc, database);

            // Department: only departments that exist in the supermarket can be chosen.
            // Each one is shown with its type, e.g. "Bakery (Customer-Facing Department)".
            System.out.println("Please select the Department:");
            for (int i = 0; i < departmentNames.size(); i++) {
                System.out.println((i + 1) + ". "
                        + Department.createDepartment(departmentNames.get(i)));
            }
            int departmentNumber = readNumberInRange(sc, 1, departmentNames.size());
            String departmentName = departmentNames.get(departmentNumber - 1);

            Department department = Department.createDepartment(departmentName);

            // Fields the brief does not ask for are stored as "N/A" (salary as 0.0)
            Employee newEmployee = Manager.createManager(firstName, lastName,
                    Employee.NOT_SPECIFIED, Employee.NOT_SPECIFIED, 0.0, department,
                    managerType.getJobTitle(), Employee.NOT_SPECIFIED);

            database.addNewEmployee(newEmployee);

            System.out.println("\"" + name + "\" has been added as \""
                    + managerType.getJobTitle() + "\" to \"" + departmentName
                    + "\" successfully!");

        } while (askYesNo(sc, "Would you like to add another employee?"));

        displayNewEmployees(database);
    }

    // Reads and validates the new employee's full name. Checks, in order:
    // not empty, not too long, correct format, and not already in the list.
    private static String readNewEmployeeName(Scanner sc, EmployeeDatabase database) {
        while (true) {
            String name = readNonEmptyText(sc,
                    "Please input the Employee Name (first and last name):", "Name");

            if (name.length() > MAX_NAME_LENGTH) {
                System.out.println("Name is too long. Please use up to "
                        + MAX_NAME_LENGTH + " characters.");
                continue;
            }

            // At least two words (first and last name). Each word must start with a letter
            // and may contain letters, apostrophes and hyphens (e.g. O'Brien, Mary-Jane).
            if (!name.matches("[A-Za-z][A-Za-z'-]*( [A-Za-z][A-Za-z'-]*)+")) {
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

    // Shows the manager types and reads the user's choice. Only one Head Manager
    // is allowed, so that choice is refused when the organisation already has one.
    private static ManagerType readManagerType(Scanner sc, EmployeeDatabase database) {
        // Each type is shown with the equivalent supermarket role, e.g. "Head Manager (Store Manager)"
        System.out.println("Please select from the following Management Staff:");
        for (ManagerType type : ManagerType.values()) {
            System.out.println(type.getOptionNumber() + ". " + type.getJobTitle()
                    + " (" + type.getStoreRole() + ")");
        }

        while (true) {
            int typeNumber = readNumberInRange(sc, 1, ManagerType.values().length);
            ManagerType managerType = ManagerType.fromNumber(typeNumber);

            if (managerType == ManagerType.HEAD_MANAGER && database.hasHeadManager()) {
                System.out.println("A Head Manager already exists. Please choose another type:");
            } else {
                return managerType;
            }
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

    // Shows every record the user has added during this run.
    private static void displayNewEmployees(EmployeeDatabase database) {
        ArrayList<Employee> newEmployees = database.getNewEmployees();

        System.out.println("Newly added records (" + newEmployees.size() + "):");
        for (Employee employee : newEmployees) {
            System.out.println("  " + employee);
        }
    }

    // ---------- Random data ----------

    // Asks how many records to generate, creates them with random but appropriate
    // data, adds them to the list and shows them.
    private static void generateRandomEmployees(Scanner sc, EmployeeDatabase database) {
        System.out.println("How many random employees would you like to generate? (1-"
                + MAX_RANDOM_RECORDS + "):");
        int amount = readNumberInRange(sc, 1, MAX_RANDOM_RECORDS);

        RandomEmployeeGenerator generator = new RandomEmployeeGenerator(database);
        ArrayList<Employee> generated = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Employee employee = generator.createRandomEmployee();

            if (employee == null) {
                System.out.println("No more unique names available. Stopped after "
                        + generated.size() + " records.");
                break;
            }
            database.addEmployee(employee);
            generated.add(employee);
        }

        System.out.println("Randomly generated records (" + generated.size() + "):");
        for (Employee employee : generated) {
            System.out.println("  " + employee + " - " + employee.getCompany()
                    + " - " + employee.getSalary());
        }
        System.out.println("Total employees: " + database.getCount());
    }

    // ---------- Binary tree ----------

    // Builds the employee hierarchy as a binary tree using level-order insertion,
    // then shows the level-order traversal, the height and the number of nodes.
    private static void buildEmployeeTree(EmployeeDatabase database) {
        if (database.getCount() < MIN_TREE_RECORDS) {
            System.out.println("At least " + MIN_TREE_RECORDS
                    + " employee records are needed to build the tree. Currently: "
                    + database.getCount() + ". Please add records or generate random data.");
            return;
        }

        // Sorting first keeps employees of the same rank in alphabetical order
        database.sortByName();

        EmployeeTree tree = new EmployeeTree();
        tree.buildFromEmployees(database.getEmployees());

        System.out.println("Binary tree created successfully.");
        System.out.println();
        System.out.println("Supermarket staff hierarchy (level-order traversal):");
        tree.displayLevelOrder();
        System.out.println();
        System.out.println("Tree height: " + tree.height() + " levels");
        System.out.println("Total nodes: " + tree.countNodes());
    }

    // ---------- Reusable input helpers ----------

    // Shows a prompt and reads text until it is not empty.
    // Extra spaces are removed: outer spaces with trim(), repeated inner spaces with replaceAll().
    private static String readNonEmptyText(Scanner sc, String prompt, String fieldName) {
        while (true) {
            System.out.println(prompt);
            String text = sc.nextLine().trim().replaceAll("\\s+", " ");

            if (!text.isEmpty()) {
                return text;
            }
            System.out.println(fieldName + " cannot be empty.");
        }
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
                // Not a whole number: handled by the message below
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
}
