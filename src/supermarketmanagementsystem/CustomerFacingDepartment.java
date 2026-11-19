
package supermarketmanagementsystem;

/**
 * Department that deals directly with customers (Sales, Customer Service, Marketing).
 *
 * @author diogodias
 */
public class CustomerFacingDepartment extends Department {

    public CustomerFacingDepartment(String departmentName) {
        super(departmentName);
    }

    @Override
    public String getDepartmentType() {
        return "Customer-Facing Department";
    }
}