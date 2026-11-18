
package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class Intern extends Employee {

    public Intern(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    @Override
    public String getEmploymentType() {
        return "Intern";
    }

    @Override
    public int getHierarchyRank() {
        return 9;
    }
}