
package supermarketmanagementsystem;

/**
 * Department that keeps the store running (Operations, IT Development, Technical Support).
 *
 * @author diogodias
 */
public class OperationsDepartment extends Department {

    public OperationsDepartment(String departmentName) {
        super(departmentName);
    }

    @Override
    public String getDepartmentType() {
        return "Operations Department";
    }
}
