package supermarketmanagementsystem;

/**
 * Parent class for all departments. Creates the correct department type
 * from a department name.
 *
 * @author diogodias
 */
public abstract class Department {

    // The departments of the supermarket, in alphabetical order. Nine of them also
    // appear in the data file; the others are typical departments of an Irish
    // supermarket (fresh food counters, grocery, frozen foods, tills, off-licence,
    // online orders, cafe and the warehouse that receives deliveries).
    private static final String[] SUPERMARKET_DEPARTMENTS = {
        "Accounting", "Bakery", "Butchery", "Cafe", "Checkouts", "Customer Service",
        "Deli", "Finance", "Fresh Produce", "Frozen Foods", "Grocery", "HR",
        "IT Development", "Marketing", "Off-Licence", "Online Orders", "Operations",
        "Sales", "Technical Support", "Warehouse"
    };

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

    // Returns a copy of the supermarket's department list
    // (a copy, so the original list cannot be changed from outside this class).
    public static String[] getSupermarketDepartments() {
        return SUPERMARKET_DEPARTMENTS.clone();
    }

    // Factory method: creates the correct subclass for a department name.
    public static Department createDepartment(String departmentName) {
        String name = departmentName.trim();

        return switch (name) {
            // Shop floor and customer contact
            case "Sales", "Customer Service", "Marketing", "Grocery", "Frozen Foods",
                    "Checkouts", "Off-Licence", "Online Orders", "Cafe" ->
                new CustomerFacingDepartment(name);
            // Specialist teams that handle perishable products
            case "Bakery", "Deli", "Butchery", "Fresh Produce" -> new FreshFoodDepartment(name);
            // Administration
            case "HR", "Finance", "Accounting" -> new BackOfficeDepartment(name);
            // Keeping the store running (the warehouse receives the deliveries)
            case "Operations", "IT Development", "Technical Support", "Warehouse" ->
                new OperationsDepartment(name);
            // Fallback for any department name not seen in the sample data
            default -> new OperationsDepartment(name);
        };
    }
}
