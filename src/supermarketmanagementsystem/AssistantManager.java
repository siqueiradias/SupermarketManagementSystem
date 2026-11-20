package supermarketmanagementsystem;

/**
 * Deputy manager: the "number two" of the store. Reports to the Head Manager,
 * stands in for them, and has the Department Managers and Team Leads below.
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

    // Assistant managers rank 2, just below the Head Manager.
    @Override
    public int getHierarchyRank() {
        return 2;
    }
}
