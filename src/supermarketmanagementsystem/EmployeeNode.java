
package supermarketmanagementsystem;

/**
 * Node of the binary tree. Stores an employee's name, designation and department.
 *
 * @author diogodias
 */
public class EmployeeNode {

    private String name;
    private String designation;
    private String department;

    // Children are accessed directly by the tree, as in the class example
    EmployeeNode left;
    EmployeeNode right;

    public EmployeeNode(Employee employee) {
        this.name = employee.getFullName();
        this.designation = employee.getJobTitle();
        this.department = employee.getDepartment().getDepartmentName();
        this.left = null;
        this.right = null;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return name + " - " + designation + " (" + department + ")";
    }
}