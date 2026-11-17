
package supermarketmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diogodias
 */
public class EmployeeDatabase {

    private ArrayList<Employee> employees;

    public EmployeeDatabase() {
        employees = new ArrayList<>();
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
                    employees.add(employee);
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

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public int getCount() {
        return employees.size();
    }
}