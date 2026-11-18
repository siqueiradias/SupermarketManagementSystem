package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class AssistantManager extends Manager {

    public AssistantManager(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    @Override
    public String getManagementLevel() {
        return "Assistant Manager";
    }

    @Override
    public int getHierarchyRank() {
        return 3;
    }
}