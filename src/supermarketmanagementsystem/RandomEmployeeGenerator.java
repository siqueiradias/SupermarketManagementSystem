package supermarketmanagementsystem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates random but appropriate employee records. "Appropriate" means the
 * data is consistent with a real organisation and with the data file:
 * unique names, existing departments and companies, job titles that match the
 * department, salaries that match the level, and more staff than managers.
 *
 * @author diogodias
 */
public class RandomEmployeeGenerator {

    private static final int MAX_NAME_ATTEMPTS = 100; // tries to find a name not yet in use

    private static final String[] FEMALE_FIRST_NAMES = {
        "Aoife", "Ciara", "Niamh", "Sarah", "Emma", "Grace", "Hannah", "Laura",
        "Rachel", "Chloe", "Megan", "Julia", "Anna", "Clara", "Lucia"
    };

    private static final String[] MALE_FIRST_NAMES = {
        "Sean", "Conor", "Liam", "Patrick", "Jack", "Noah", "Adam", "Mark",
        "Paul", "Peter", "Luke", "Thomas", "Rafael", "Bruno", "Mateus"
    };

    private static final String[] LAST_NAMES = {
        "Murphy", "Kelly", "Byrne", "Ryan", "O'Brien", "Walsh", "Doyle", "McCarthy",
        "Gallagher", "Kennedy", "Lynch", "Quinn", "Moore", "Silva", "Costa",
        "Santos", "Oliveira", "Pereira", "Collins", "Nolan"
    };

    private static final String[] SENIORITY_LEVELS = {"junior", "middle", "senior"};

    // Only one Head Manager makes sense in an organisation, so random managers
    // are created only at the lower management levels.
    private static final ManagerType[] RANDOM_MANAGER_TYPES = {
        ManagerType.DEPARTMENT_MANAGER, ManagerType.ASSISTANT_MANAGER, ManagerType.TEAM_LEAD
    };

    private final EmployeeDatabase database;
    private final ArrayList<String> departmentNames;
    private final ArrayList<String> companyNames;
    private final Random random;

    // Creates a generator that uses the departments and companies already in the database.
    public RandomEmployeeGenerator(EmployeeDatabase database) {
        this.database = database;
        this.departmentNames = database.getDepartmentNames();
        this.companyNames = database.getCompanyNames();
        this.random = new Random();
    }

    // Creates one random employee. Returns null if no unused name could be found
    // or if there are no departments to choose from.
    public Employee createRandomEmployee() {
        if (departmentNames.isEmpty()) {
            return null;
        }

        // Gender first, so the first name matches it
        String gender = random.nextBoolean() ? "Female" : "Male";
        String[] firstNames = gender.equals("Female") ? FEMALE_FIRST_NAMES : MALE_FIRST_NAMES;

        // Names must be unique, because the Binary Search relies on them
        String firstName = "";
        String lastName = "";
        boolean unique = false;

        for (int attempt = 0; attempt < MAX_NAME_ATTEMPTS && !unique; attempt++) {
            firstName = pick(firstNames);
            lastName = pick(LAST_NAMES);
            unique = database.findByName(firstName + " " + lastName) == null;
        }
        if (!unique) {
            return null;
        }

        String departmentName = pick(departmentNames);
        Department department = Department.createDepartment(departmentName);

        String company = companyNames.isEmpty() ? Employee.NOT_SPECIFIED : pick(companyNames);
        String email = createEmail(firstName, lastName, company);

        // Decide the category: 10% managers, 60% staff, 15% contract, 15% interns
        int roll = random.nextInt(100); // a number from 0 to 99

        if (roll < 10) {
            ManagerType type = RANDOM_MANAGER_TYPES[random.nextInt(RANDOM_MANAGER_TYPES.length)];
            String jobTitle = type.getJobTitle();
            return Manager.createManager(firstName, lastName, gender, email,
                    randomSalary(jobTitle), department, jobTitle, company);
        }

        String position;
        if (roll < 70) {
            position = pick(SENIORITY_LEVELS);
        } else if (roll < 85) {
            position = "contract";
        } else {
            position = "intern";
        }

        String jobTitle = pick(jobTitlesFor(departmentName));

        return Employee.createEmployee(firstName, lastName, gender, email,
                randomSalary(position), department, position, jobTitle, company);
    }

    // Returns the job titles that suit a department (taken from the data file),
    // so a Finance employee never gets a title such as "Sales Clerk".
    private String[] jobTitlesFor(String departmentName) {
        return switch (departmentName) {
            case "Sales" -> new String[]{"Sales Clerk", "Clerk"};
            case "Customer Service" -> new String[]{"Client Relations Coordinator",
                "Client Relations Specialist", "Support Clerk"};
            case "Marketing" -> new String[]{"Marketing Specialist"};
            case "HR" -> new String[]{"HR Specialist", "HR Analyst"};
            case "Finance" -> new String[]{"Finance Analyst", "Bookkeeper"};
            case "Accounting" -> new String[]{"Bookkeeper", "Accounts Assistant"};
            case "Operations" -> new String[]{"White-collar Worker", "Office Worker"};
            case "IT Development" -> new String[]{"Backend Developer", "Frontend Developer",
                "Full-stack Developer", "QA", "DevOps"};
            case "Technical Support" -> new String[]{"Support Clerk", "Clerk"};
            // Fallback for any department name not seen in the sample data
            default -> new String[]{"Store Assistant"};
        };
    }

    // Returns a salary in a range that matches the level: the higher the level,
    // the higher the range. 'level' is a Position value or a manager job title.
    private double randomSalary(String level) {
        return switch (level) {
            case "intern" -> randomAmount(1500, 3000);
            case "contract" -> randomAmount(2500, 4500);
            case "junior" -> randomAmount(2000, 3500);
            case "middle" -> randomAmount(3500, 5500);
            case "senior" -> randomAmount(5500, 8000);
            case "Team Lead" -> randomAmount(4000, 7000);
            case "Assistant Manager" -> randomAmount(7000, 15000);
            default -> randomAmount(15000, 80000); // Manager
        };
    }

    // Returns a random amount between min and max, rounded to two decimal places.
    private double randomAmount(double min, double max) {
        double amount = min + random.nextDouble() * (max - min);
        return Math.round(amount * 100) / 100.0;
    }

    // Builds an email in the same format as the data file, e.g. jsmith@DataVision.com.
    private String createEmail(String firstName, String lastName, String company) {
        if (company.equals(Employee.NOT_SPECIFIED)) {
            return Employee.NOT_SPECIFIED;
        }
        String user = (firstName.charAt(0) + lastName).toLowerCase().replace("'", "");
        return user + "@" + company + ".com";
    }

    // Returns a random item from an array.
    private String pick(String[] items) {
        return items[random.nextInt(items.length)];
    }

    // Returns a random item from a list.
    private String pick(ArrayList<String> items) {
        return items.get(random.nextInt(items.size()));
    }
}