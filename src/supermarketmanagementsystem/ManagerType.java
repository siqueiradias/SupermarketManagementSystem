
package supermarketmanagementsystem;

/**
 * Enum with the valid manager types a user can choose when adding a new record.
 * 
 * @author diogodias
 */
public enum ManagerType {

    HEAD_MANAGER(1, "Head Manager"),
    DEPARTMENT_MANAGER(2, "Manager"),
    ASSISTANT_MANAGER(3, "Assistant Manager"),
    TEAM_LEAD(4, "Team Lead");

    private final int optionNumber;
    private final String jobTitle;

    ManagerType(int optionNumber, String jobTitle) {
        this.optionNumber = optionNumber;
        this.jobTitle = jobTitle;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    // The job title is passed to Manager.createManager(), which uses it
    // to create the correct Manager subclass.
    public String getJobTitle() {
        return jobTitle;
    }

    // Finds the manager type that matches the number typed by the user.
    // Returns null if no type has that number.
    public static ManagerType fromNumber(int number) {
        for (ManagerType type : values()) {
            if (type.optionNumber == number) {
                return type;
            }
        }
        return null;
    }
}