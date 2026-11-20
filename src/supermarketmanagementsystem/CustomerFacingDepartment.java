package supermarketmanagementsystem;

/**
 * Department that deals directly with customers (Sales, Customer Service, Marketing).
 *
 * @author diogodias
 */
public class CustomerFacingDepartment extends Department {

    // Creates a customer-facing department with the given name.
    public CustomerFacingDepartment(String departmentName) {
        super(departmentName);
    }

    // Returns the type of this department.
    @Override
    public String getDepartmentType() {
        return "Customer-Facing Department";
    }
}
