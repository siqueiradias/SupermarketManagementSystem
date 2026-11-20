package supermarketmanagementsystem;

/**
 * Employee hired on a fixed-term contract.
 *
 * @author diogodias
 */
public class ContractEmployee extends Employee {

    // Creates a contract employee.
    public ContractEmployee(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    // Returns the employment type of this employee.
    @Override
    public String getEmploymentType() {
        return "Contract";
    }

    // Contract employees rank 8 in the hierarchy.
    @Override
    public int getHierarchyRank() {
        return 8;
    }
}
