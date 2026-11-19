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

    private ArrayList<Employee> employees;
    private boolean sorted; // true only while the list is in alphabetical order
    private ArrayList<Employee> newEmployees; // records added by the user during this run

    public EmployeeDatabase() {
        employees = new ArrayList<>();
        newEmployees = new ArrayList<>();
        sorted = false;
    }

    // Reads the CSV-style data file and populates the employees list.
    // Returns true if the file was read successfully.
    public boolean loadFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // skip the header line
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue; // skip blank lines
                }

                Employee employee = parseEmployeeLine(line);

                if (employee != null) {
                    addEmployee(employee);
                }
            }

            return true;

        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // Converts one line of the data file into the correct Employee subclass.
    private Employee parseEmployeeLine(String line) {
        String[] fields = line.split(",", -1); // -1 keeps empty trailing fields

        if (fields.length < 9) {
            return null; // malformed line, skip it safely
        }

        String firstName = fields[0].trim();
        String lastName = fields[1].trim();
        String gender = fields[2].trim();
        String email = fields[3].trim();

        double salary;
        try {
            salary = Double.parseDouble(fields[4].trim());
        } catch (NumberFormatException e) {
            salary = 0.0;
        }

        String departmentName = fields[5].trim();
        String position = fields[6].trim();   // empty means this record is a manager
        String jobTitle = fields[7].trim();
        String company = fields[8].trim();

        Department department = Department.createDepartment(departmentName);

        return Employee.createEmployee(firstName, lastName, gender, email, salary,
                department, position, jobTitle, company);
    }

    // Adds an employee. The list may no longer be in order, so it is marked unsorted.
    public void addEmployee(Employee employee) {
        employees.add(employee);
        sorted = false;
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

    // Adds a record typed by the user and remembers it as a new record.
    public void addNewEmployee(Employee employee) {
        addEmployee(employee);
        newEmployees.add(employee);
    }

    public ArrayList<Employee> getNewEmployees() {
        return newEmployees;
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

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public int getCount() {
        return employees.size();
    }
}
