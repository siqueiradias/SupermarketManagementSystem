package supermarketmanagementsystem;

/**
 * Employee on an internship.
 *
 * @author diogodias
 */
public class Intern extends Employee {

    // Creates an intern.
    public Intern(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the employment type of this employee.
    @Override
    public String getEmploymentType() {
        return "Intern";
    }

    // Interns rank 9, the lowest level in the hierarchy.
    @Override
    public int getHierarchyRank() {
        return 9;
    }
}
