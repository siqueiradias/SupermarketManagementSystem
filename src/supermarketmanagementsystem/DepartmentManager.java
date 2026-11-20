package supermarketmanagementsystem;

/**
 * Manager responsible for one or more departments, e.g. a bakery or deli manager
 * (job titles Manager and Senior Manager in the data file).
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

    // Department managers rank 3, below the Assistant Manager.
    @Override
    public int getHierarchyRank() {
        return 3;
    }
}
