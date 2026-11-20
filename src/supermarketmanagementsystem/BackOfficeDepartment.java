package supermarketmanagementsystem;

/**
 * Administrative department (HR, Finance, Accounting).
 *
 * @author diogodias
 */
public class BackOfficeDepartment extends Department {

    // Creates a back-office department with the given name.
    public BackOfficeDepartment(String departmentName) {
        super(departmentName);
    }

    // Returns the type of this department.
    @Override
    public String getDepartmentType() {
        return "Back-Office Department";
    }
}
