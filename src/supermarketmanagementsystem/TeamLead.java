package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public class TeamLead extends Manager {

    public TeamLead(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    @Override
    public String getManagementLevel() {
        return "Team Lead";
    }
}