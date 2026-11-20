package supermarketmanagementsystem;

/**
 * Supervisor of a shift or team (also called Team Leader or Chargehand).
 * The lowest management level.
 *
 * @author diogodias
 */
public class TeamLead extends Manager {

    // Creates a team lead.
    public TeamLead(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the management level of this manager.
    @Override
    public String getManagementLevel() {
        return "Team Lead";
    }

    // Team leads rank 4, the lowest management level.
    @Override
    public int getHierarchyRank() {
        return 4;
    }
}
