package supermarketmanagementsystem;

/**
 * Manager responsible for a department (Manager and Senior Manager titles).
 *
 * @author diogodias
 */
public class DepartmentManager extends Manager {

    // Creates a department manager.
    public DepartmentManager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the management level of this manager.
    @Override
    public String getManagementLevel() {
        return "Department Manager";
    }

    // Department managers rank 2 in the hierarchy.
    @Override
    public int getHierarchyRank() {
        return 2;
    }
}
