
package supermarketmanagementsystem;

/**
 * Employee hired on a fixed-term contract.
 *
 * @author diogodias
 */
public class ContractEmployee extends Employee {

    public ContractEmployee(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        super(firstName, lastName, gender, email, salary, department, jobTitle, company);
    }

    @Override
    public String getEmploymentType() {
        return "Contract";
    }

    @Override
    public int getHierarchyRank() {
        return 8;
    }
}