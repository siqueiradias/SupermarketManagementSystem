package supermarketmanagementsystem;

/**
 * Parent class for all departments. Creates the correct department type
 * from a department name.
 *
 * @author diogodias
 */
public abstract class Department {

    private final String departmentName;

    // Creates a department with the given name.
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    // Returns the department name, e.g. "Sales".
    public String getDepartmentName() {
        return departmentName;
    }

    // Returns the department type. Each subclass must provide its own.
    public abstract String getDepartmentType();

    // Returns the name followed by the type, e.g. "Sales (Customer-Facing Department)".
    @Override
    public String toString() {
        return departmentName + " (" + getDepartmentType() + ")";
    }

    // Factory method: creates the correct subclass for a department name from the data file.
    public static Department createDepartment(String departmentName) {
        String name = departmentName.trim();

        return switch (name) {
            case "Sales", "Customer Service", "Marketing" -> new CustomerFacingDepartment(name);
            case "HR", "Finance", "Accounting" -> new BackOfficeDepartment(name);
            case "Operations", "IT Development", "Technical Support" -> new OperationsDepartment(name);
            // Fallback for any department name not seen in the sample data
            default -> new OperationsDepartment(name);
        };
    }
}
