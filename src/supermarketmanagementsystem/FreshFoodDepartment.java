package supermarketmanagementsystem;

/**
 * Specialist department that handles perishable food: Bakery, Deli, Butchery
 * and Fresh Produce.
 *
 * @author diogodias
 */
public class FreshFoodDepartment extends Department {

    // Creates a fresh food department with the given name.
    public FreshFoodDepartment(String departmentName) {
        super(departmentName);
    }

    // Returns the type of this department.
    @Override
    public String getDepartmentType() {
        return "Fresh Food Department";
    }
}
