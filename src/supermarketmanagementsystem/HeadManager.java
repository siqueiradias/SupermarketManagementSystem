
package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class HeadManager extends Manager {

    public HeadManager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    @Override
    public String getManagementLevel() {
        return "Head Manager";
    }

    @Override
    public int getHierarchyRank() {
        return 1;
    }
}