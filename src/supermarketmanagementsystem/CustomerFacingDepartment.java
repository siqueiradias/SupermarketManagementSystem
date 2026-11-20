package supermarketmanagementsystem;

/**
 * Department on the shop floor or in direct contact with customers: Sales,
 * Customer Service, Marketing, Grocery, Frozen Foods, Checkouts, Off-Licence,
 * Online Orders and Cafe.
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
