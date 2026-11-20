package supermarketmanagementsystem;

/**
 * Highest management level in the store.
 *
 * @author diogodias
 */
public class HeadManager extends Manager {

    // Creates a head manager.
    public HeadManager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the management level of this manager.
    @Override
    public String getManagementLevel() {
        return "Head Manager";
    }

    // Head managers rank 1, the top of the hierarchy (root of the tree).
    @Override
    public int getHierarchyRank() {
        return 1;
    }
}
