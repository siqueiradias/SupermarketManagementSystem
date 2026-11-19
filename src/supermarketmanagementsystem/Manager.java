
package supermarketmanagementsystem;

/**
 * Parent class for all managers. A manager is also an employee.
 *
 * @author diogodias
 */
public abstract class Manager extends Employee {

    public Manager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // A manager is still an employee, but every manager is also assigned
    // to a management level, described by this abstract method.
    public abstract String getManagementLevel();

    @Override
    public String getEmploymentType() {
        return "Manager - " + getManagementLevel();
    }

    // Factory method: decides which subclass to create based on the
    // "Job title" field from the data file. Called by
    // Employee.createEmployee() whenever Position is empty.
    public static Manager createManager(String firstName, String lastName, String gender,
            String email, double salary, Department department, String jobTitle,
            String company) {

        String title = jobTitle == null ? "" : jobTitle.trim();

        switch (title) {
            case "Head Manager":
                return new HeadManager(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            case "Manager":
            case "Senior Manager":
                return new DepartmentManager(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            case "Assistant Manager":
                return new AssistantManager(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            case "Team Lead":
                return new TeamLead(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            default:
                // Fallback for any management title not seen in the sample data
                return new DepartmentManager(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);
        }
    }
}    


