package supermarketmanagementsystem;

/**
 * Parent class for all managers. A manager is also an employee.
 *
 * @author diogodias
 */
public abstract class Manager extends Employee {

    // Creates a manager. All the data is stored by the Employee parent class.
    public Manager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the management level. Each subclass must provide its own.
    public abstract String getManagementLevel();

    // Returns the employment type, built from the management level.
    @Override
    public String getEmploymentType() {
        return "Manager - " + getManagementLevel();
    }

    // Factory method: creates the correct subclass based on the "Job title" field.
    // Called by Employee.createEmployee() whenever Position is empty,
    // and when the user adds a new manager.
    public static Manager createManager(String firstName, String lastName, String gender,
            String email, double salary, Department department, String jobTitle,
            String company) {

        String title = jobTitle == null ? "" : jobTitle.trim();

        return switch (title) {
            case "Head Manager" -> new HeadManager(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            case "Manager", "Senior Manager" -> new DepartmentManager(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            case "Assistant Manager" -> new AssistantManager(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            case "Team Lead" -> new TeamLead(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            // Fallback for any management title not seen in the sample data
            default -> new DepartmentManager(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
        };
    }
}
