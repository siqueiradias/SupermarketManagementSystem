package supermarketmanagementsystem;

/**
 * Manager who supports a higher-level manager.
 *
 * @author diogodias
 */
public class AssistantManager extends Manager {

    // Creates an assistant manager.
    public AssistantManager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the management level of this manager.
    @Override
    public String getManagementLevel() {
        return "Assistant Manager";
    }

    // Assistant managers rank 3 in the hierarchy.
    @Override
    public int getHierarchyRank() {
        return 3;
    }
}
