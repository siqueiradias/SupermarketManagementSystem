
package supermarketmanagementsystem;

/**
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