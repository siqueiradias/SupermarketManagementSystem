
package supermarketmanagementsystem;

/**
 *
 * @author diogodias
 */
public abstract class Department {

    protected String departmentName;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    // Each subclass must say what type of department it is
    public abstract String getDepartmentType();

    @Override
    public String toString() {
        return departmentName + " (" + getDepartmentType() + ")";
    }

    // Factory method: decides which subclass to create based on the
    // department name found in the data file.
    public static Department createDepartment(String departmentName) {
        return switch (departmentName.trim()) {
            case "Sales", "Customer Service", "Marketing" -> new CustomerFacingDepartment(departmentName);
            case "HR", "Finance", "Accounting" -> new BackOfficeDepartment(departmentName);
            case "Operations", "IT Development", "Technical Support" -> new OperationsDepartment(departmentName);
            default -> new OperationsDepartment(departmentName);
        }; // Fallback for any department name not seen in the sample data
    }
}