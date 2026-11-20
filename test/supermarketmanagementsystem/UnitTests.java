package supermarketmanagementsystem;

import java.util.ArrayList;

/**
 * Test driver for the two documented unit tests:
 * UT-01 recursive Binary Search and UT-02 new employee name validation.
 *
 * Each case follows Arrange - Act - Assert: build fixed test data in memory,
 * call the method under test, and compare the actual result with the expected one.
 * No testing framework is used, because the brief does not allow external
 * dependencies. Run with: right-click this file > Run File.
 *
 * @author diogodias
 */
public class UnitTests {

    private static int passed = 0;
    private static int failed = 0;

    // Runs every test case and prints a summary.
    public static void main(String[] args) {
        System.out.println("UT-01 - Recursive Binary Search");
        testBinarySearch();

        System.out.println();
        System.out.println("UT-02 - New employee name validation");
        testNameValidation();

        System.out.println();
        System.out.println("Summary: " + passed + " passed, " + failed + " failed");
    }

    // ---------- UT-01: EmployeeSearcher.binarySearch ----------

    // Test cases for the recursive Binary Search (see the test document for the reasons).
    private static void testBinarySearch() {
        // Arrange: a list already sorted alphabetically (Binary Search needs sorted data)
        ArrayList<Employee> seven = createList("Alex Turner", "Benjamin Hill", "Jessica Lee",
                "Kate Summer", "Ken Winter", "Sarah Johnson", "Victor Stewart");
        ArrayList<Employee> one = createList("Kate Summer");
        ArrayList<Employee> empty = new ArrayList<>();

        checkSearch("BS-01", "Normal - middle element", seven, "Kate Summer", 4);
        checkSearch("BS-02", "Normal - left half", seven, "Benjamin Hill", 1);
        checkSearch("BS-03", "Boundary - first element", seven, "Alex Turner", 0);
        checkSearch("BS-04", "Boundary - last element", seven, "Victor Stewart", 6);
        checkSearch("BS-05", "Normal - different letter case", seven, "jessica LEE", 2);
        checkSearch("BS-06", "Erroneous - absent, between names", seven, "Jane Doe", -1);
        checkSearch("BS-07", "Erroneous - absent, before first", seven, "Aaron Abbott", -1);
        checkSearch("BS-08", "Erroneous - absent, after last", seven, "Zoe Young", -1);
        checkSearch("BS-09", "Erroneous - partial name", seven, "Kate", -1);
        checkSearch("BS-10", "Extreme - one-element list, present", one, "Kate Summer", 0);
        checkSearch("BS-11", "Extreme - one-element list, absent", one, "Ken Winter", -1);
        checkSearch("BS-12", "Extreme - empty list", empty, "Kate Summer", -1);
    }

    // Act and Assert for one Binary Search case.
    private static void checkSearch(String id, String description, ArrayList<Employee> list,
            String target, int expected) {
        int actual = EmployeeSearcher.binarySearch(list, 0, list.size() - 1, target);
        report(id, description, "\"" + target + "\"",
                String.valueOf(expected), String.valueOf(actual), actual == expected);
    }

    // Builds a list of employees with the given full names (the other fields do not
    // matter for the search, so they are fixed values).
    private static ArrayList<Employee> createList(String... fullNames) {
        ArrayList<Employee> list = new ArrayList<>();
        Department department = Department.createDepartment("Sales");

        for (String fullName : fullNames) {
            int spaceIndex = fullName.indexOf(' ');
            String firstName = fullName.substring(0, spaceIndex);
            String lastName = fullName.substring(spaceIndex + 1);

            list.add(Employee.createEmployee(firstName, lastName, "N/A", "N/A", 0.0,
                    department, "junior", "Sales Clerk", "N/A"));
        }
        return list;
    }

    // ---------- UT-02: NameValidator.validate ----------

    // Test cases for the name validation (null expected = the name is valid).
    private static void testNameValidation() {
        String formatMessage = "Please enter a first and last name using letters only.";
        String name50 = "A".repeat(24) + " " + "B".repeat(25); // 50 characters
        String name51 = "A".repeat(25) + " " + "B".repeat(25); // 51 characters

        checkName("NV-01", "Normal - first and last name", "John Joe", null);
        checkName("NV-02", "Normal - hyphen and apostrophe", "Mary-Jane O'Brien", null);
        checkName("NV-03", "Normal - three words", "Ana de Souza", null);
        checkName("NV-04", "Boundary - shortest valid name", "A B", null);
        checkName("NV-05", "Boundary - maximum length (50)", name50, null);
        checkName("NV-06", "Boundary - one over maximum (51)", name51,
                "Name is too long. Please use up to 50 characters.");
        checkName("NV-07", "Erroneous - empty", "", "Name cannot be empty.");
        checkName("NV-08", "Erroneous - no value (null)", null, "Name cannot be empty.");
        checkName("NV-09", "Erroneous - one word only", "John", formatMessage);
        checkName("NV-10", "Erroneous - digits", "John 123", formatMessage);
        checkName("NV-11", "Erroneous - word made of a symbol", "John -", formatMessage);
        checkName("NV-12", "Erroneous - word starting with a symbol", "John 'x", formatMessage);
        checkName("NV-13", "Erroneous - not normalised (double space)", "John  Joe", formatMessage);
    }

    // Act and Assert for one name validation case.
    private static void checkName(String id, String description, String input, String expected) {
        String actual = NameValidator.validate(input);
        boolean ok = (expected == null) ? actual == null : expected.equals(actual);

        String shownInput = (input == null) ? "null" : "\"" + input + "\" (" + input.length() + " chars)";
        report(id, description, shownInput, describe(expected), describe(actual), ok);
    }

    // Shows a validation result in words: null means the name is valid.
    private static String describe(String result) {
        return (result == null) ? "VALID" : result;
    }

    // ---------- Shared ----------

    // Prints one test case result and updates the totals.
    private static void report(String id, String description, String input,
            String expected, String actual, boolean ok) {
        if (ok) {
            passed++;
        } else {
            failed++;
        }
        System.out.println((ok ? "PASS  " : "FAIL  ") + id + "  " + description);
        System.out.println("      input: " + input + " | expected: " + expected
                + " | actual: " + actual);
    }
}
