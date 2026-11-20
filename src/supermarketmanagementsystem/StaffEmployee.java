package supermarketmanagementsystem;

/**
 * Permanent employee with a seniority level (junior, middle or senior).
 *
 * @author diogodias
 */
public class StaffEmployee extends Employee {

    private final String seniorityLevel; // junior, middle or senior

    // Creates a staff employee with the given seniority level.
    public StaffEmployee(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company,
            String seniorityLevel) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
        this.seniorityLevel = seniorityLevel;
    }

    // Returns the seniority level: junior, middle or senior.
    public String getSeniorityLevel() {
        return seniorityLevel;
    }

    // Returns the employment type, including the seniority level.
    @Override
    public String getEmploymentType() {
        return "Staff - " + seniorityLevel;
    }

    // Senior staff rank 5, middle 6 and junior 7 in the hierarchy.
    @Override
    public int getHierarchyRank() {
        return switch (seniorityLevel) {
            case "senior" -> 5;
            case "middle" -> 6;
            default -> 7; // junior
        };
    }
}
