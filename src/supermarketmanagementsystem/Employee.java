package supermarketmanagementsystem;

/**
 * Parent class for everyone in the organisation.
 * Holds the data read from the file and creates the correct employee type.
 *
 * @author diogodias
 */
public abstract class Employee {

    // Value stored in fields that are not known, e.g. the company of a record added by the user
    public static final String NOT_SPECIFIED = "N/A";

    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String email;
    private final double salary;
    private final Department department;
    private final String jobTitle;
    private final String company;

    // Creates an employee with the data read from the file or typed by the user.
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

    // Returns the first name.
    public String getFirstName() {
        return firstName;
    }

    // Returns the last name.
    public String getLastName() {
        return lastName;
    }

    // Returns the full name in the format "First Last".
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Returns the gender (kept from the data file for future reports).
    public String getGender() {
        return gender;
    }

    // Returns the email (kept from the data file for future reports).
    public String getEmail() {
        return email;
    }

    // Returns the salary (kept from the data file for future reports).
    public double getSalary() {
        return salary;
    }

    // Returns the department this employee belongs to.
    public Department getDepartment() {
        return department;
    }

    // Returns the job title, e.g. "Senior Manager".
    public String getJobTitle() {
        return jobTitle;
    }

    // Returns the company, interpreted as the store or branch of the chain.
    public String getCompany() {
        return company;
    }

    // Compares this employee's full name with another name, ignoring case.
    // Used by both Merge Sort and Binary Search so they share the same ordering rule.
    public int compareNameTo(String otherName) {
        return getFullName().compareToIgnoreCase(otherName.trim());
    }

    // Returns the employment category. Each subclass must provide its own.
    public abstract String getEmploymentType();

    // Returns the position in the organisational hierarchy (1 is the highest level).
    // Used to decide where each employee goes in the binary tree.
    public abstract int getHierarchyRank();

    // Returns a one-line summary: name, job title, employment type and department.
    @Override
    public String toString() {
        return getFullName() + " - " + jobTitle + " (" + getEmploymentType()
                + ") - " + department.getDepartmentName();
    }

    // Factory method: creates the correct subclass based on the "Position" field
    // from the data file. An empty Position means the record is a manager.
    public static Employee createEmployee(String firstName, String lastName, String gender,
            String email, double salary, Department department, String position,
            String jobTitle, String company) {

        String pos = position == null ? "" : position.trim().toLowerCase();

        return switch (pos) {
            case "junior", "middle", "senior" -> new StaffEmployee(firstName, lastName,
                    gender, email, salary, department, jobTitle, company, pos);
            case "contract" -> new ContractEmployee(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            case "intern" -> new Intern(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
            // Empty Position: the Manager class decides the type from the job title
            default -> Manager.createManager(firstName, lastName,
                    gender, email, salary, department, jobTitle, company);
        };
    }
}
