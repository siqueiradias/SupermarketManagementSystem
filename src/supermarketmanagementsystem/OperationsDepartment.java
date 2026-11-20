package supermarketmanagementsystem;

/**
 * Department that keeps the store running: Operations, IT Development,
 * Technical Support and Warehouse.
 *
 * @author diogodias
 */
public class OperationsDepartment extends Department {

    // Creates an operations department with the given name.
    public OperationsDepartment(String departmentName) {
        super(departmentName);
    }

    // Returns the type of this department.
    @Override
    public String getDepartmentType() {
        return "Operations Department";
    }
}
