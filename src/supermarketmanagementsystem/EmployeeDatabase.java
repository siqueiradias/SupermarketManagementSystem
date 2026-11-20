package supermarketmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads the data file and stores all employees in memory.
 * Tracks whether the list is sorted and which records were added by the user.
 *
 * @author diogodias
 */
public class EmployeeDatabase {

    private static final int EXPECTED_FIELDS = 9; // fields per line in the data file

    private final ArrayList<Employee> employees;
    private final ArrayList<Employee> newEmployees; // records added by the user during this run
    private boolean sorted;     // true only while the list is in alphabetical order
    private int skippedLines;   // invalid lines found in the last file read

    // Creates an empty database.
    public EmployeeDatabase() {
        employees = new ArrayList<>();
        newEmployees = new ArrayList<>();
        sorted = false;
        skippedLines = 0;
    }

    // Reads the CSV-style data file and populates the employees list.
    // Invalid lines are not loaded, but they are counted so the user can be told.
    // Returns false if the file does not exist.
    public boolean loadFromFile(String filename) {
        skippedLines = 0;

        try (Scanner fileScanner = new Scanner(new File(filename))) {

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // skip the header line
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue; // blank lines are not records, so they are not counted as invalid
                }

                Employee employee = parseEmployeeLine(line);

                if (employee == null) {
                    skippedLines++;
                } else {
                    addEmployee(employee);
                }
            }
            return true;

        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // Converts one line of the data file into the correct Employee subclass.
    // Returns null if the line is invalid.
    private Employee parseEmployeeLine(String line) {
        String[] fields = line.split(",", -1); // -1 keeps empty trailing fields

        // A valid line has exactly one value for each column of the header
        if (fields.length != EXPECTED_FIELDS) {
            return null;
        }

        String firstName = fields[0].trim();
        String lastName = fields[1].trim();
        String gender = fields[2].trim();
        String email = fields[3].trim();
        String departmentName = fields[5].trim();
        String position = fields[6].trim();   // empty means this record is a manager
        String jobTitle = fields[7].trim();
        String company = fields[8].trim();

        // Name and department are required: sorting, searching and the tree depend on them
        if (firstName.isEmpty() || lastName.isEmpty() || departmentName.isEmpty()) {
            return null;
        }

        double salary;
        try {
            salary = Double.parseDouble(fields[4].trim());
        } catch (NumberFormatException e) {
            return null; // the salary is not a number
        }

        Department department = Department.createDepartment(departmentName);

        return Employee.createEmployee(firstName, lastName, gender, email, salary,
                department, position, jobTitle, company);
    }

    // Adds an employee. The list may no longer be in order, so it is marked unsorted.
    public void addEmployee(Employee employee) {
        employees.add(employee);
        sorted = false;
    }

    // Adds a record typed by the user and remembers it as a new record.
    public void addNewEmployee(Employee employee) {
        addEmployee(employee);
        newEmployees.add(employee);
    }

    // Sorts the employee list alphabetically by full name using recursive Merge Sort.
    public void sortByName() {
        EmployeeSorter.mergeSort(employees, 0, employees.size() - 1);
        sorted = true;
    }

    // Finds an employee by full name using recursive Binary Search.
    // Sorts the list first if needed, because Binary Search requires sorted data.
    // Returns the employee, or null if not found.
    public Employee findByName(String name) {
        if (!sorted) {
            sortByName();
        }

        int index = EmployeeSearcher.binarySearch(employees, 0, employees.size() - 1, name);

        if (index == -1) {
            return null;
        }
        return employees.get(index);
    }

    // Returns the names of all departments that currently exist, without duplicates.
    // Each new name is inserted in its alphabetical position (the insertion idea from class),
    // so the list is always shown to the user in the same order.
    public ArrayList<String> getDepartmentNames() {
        ArrayList<String> names = new ArrayList<>();

        for (Employee employee : employees) {
            String name = employee.getDepartment().getDepartmentName();

            if (!names.contains(name)) {
                int position = 0;
                while (position < names.size() && names.get(position).compareTo(name) < 0) {
                    position++;
                }
                names.add(position, name);
            }
        }
        return names;
    }

    // Returns all employees (from the file and added by the user).
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    // Returns only the employees added by the user during this run.
    public ArrayList<Employee> getNewEmployees() {
        return newEmployees;
    }

    // Returns the total number of employees.
    public int getCount() {
        return employees.size();
    }

    // Returns how many invalid lines were skipped in the last file read.
    public int getSkippedLines() {
        return skippedLines;
    }
}
