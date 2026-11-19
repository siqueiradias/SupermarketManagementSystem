package supermarketmanagementsystem;

/**
 * Parent class for everyone in the organisation. 
 * Holds the data read from the file and creates the correct employee type.
 *
 * @author diogodias
 */
public abstract class Employee {

    protected String firstName;
    protected String lastName;
    private String gender;
    private String email;
    private double salary;
    protected Department department;
    protected String jobTitle;
    protected String company;

    public Employee(String firstName, String lastName, String gender, String email,
            double salary, Department department, String jobTitle, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.jobTitle = jobTitle;
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Compares this employee's full name with another name, ignoring case.
    // Used by both Merge Sort and Binary Search so they share the same ordering rule.
    public int compareNameTo(String otherName) {
        return getFullName().compareToIgnoreCase(otherName.trim());
    }

    public Department getDepartment() {
        return department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }
    
    // Fields kept from the data file (not shown in the menu, but available for future reports)
    public String getGender() {
        return gender;
    }

     // Fields kept from the data file (not shown in the menu, but available for future reports)
    public String getEmail() {
        return email;
    }

     // Fields kept from the data file (not shown in the menu, but available for future reports)
    public double getSalary() {
        return salary;
    }

    // Each subclass must describe its own employment category
    public abstract String getEmploymentType();

    // Position in the organisational hierarchy: 1 is the highest level.
    // Used to decide where each employee goes in the binary tree.
    public abstract int getHierarchyRank();

    @Override
    public String toString() {
        return getFullName() + " - " + jobTitle + " (" + getEmploymentType()
                + ") - " + department.getDepartmentName();
    }

    // Factory method: decides which subclass to create based on the
    // "Position" field from the data file.
    public static Employee createEmployee(String firstName, String lastName, String gender,
            String email, double salary, Department department, String position,
            String jobTitle, String company) {

        String pos = position == null ? "" : position.trim().toLowerCase();

        switch (pos) {
            case "junior":
            case "middle":
            case "senior":
                return new StaffEmployee(firstName, lastName, gender, email, salary,
                        department, jobTitle, company, pos);

            case "contract":
                return new ContractEmployee(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            case "intern":
                return new Intern(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);

            default:
                // Empty Position means this record is a manager, not a regular employee
                return Manager.createManager(firstName, lastName, gender, email, salary,
                        department, jobTitle, company);
        }
    }
}
