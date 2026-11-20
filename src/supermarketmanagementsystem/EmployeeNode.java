package supermarketmanagementsystem;

/**
 * Node of the binary tree. Stores an employee's name, designation and department.
 *
 * @author diogodias
 */
public class EmployeeNode {

    private final String name;
    private final String designation;
    private final String department;

    // Children are accessed directly by the tree, as in the class example
    EmployeeNode left;
    EmployeeNode right;

    // Creates a node (with no children yet) from an employee's data.
    public EmployeeNode(Employee employee) {
        this.name = employee.getFullName();
        this.designation = employee.getJobTitle();
        this.department = employee.getDepartment().getDepartmentName();
        this.left = null;
        this.right = null;
    }

    // Returns the employee's full name.
    public String getName() {
        return name;
    }

    // Returns the employee's designation (job title).
    public String getDesignation() {
        return designation;
    }

    // Returns the employee's department name.
    public String getDepartment() {
        return department;
    }

    // Returns the node as "Name - Designation (Department)".
    @Override
    public String toString() {
        return name + " - " + designation + " (" + department + ")";
    }
}
