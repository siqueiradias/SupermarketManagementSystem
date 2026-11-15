
package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class StaffEmployee extends Employee {

    private String seniorityLevel; // junior, middle or senior

    public StaffEmployee(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company,
            String seniorityLevel) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
        this.seniorityLevel = seniorityLevel;
    }

    public String getSeniorityLevel() {
        return seniorityLevel;
    }

    @Override
    public String getEmploymentType() {
        return "Staff - " + seniorityLevel;
    }
}
