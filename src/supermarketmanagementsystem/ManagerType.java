package supermarketmanagementsystem;

/**
 * Enum with the valid manager types a user can choose when adding a new record,
 * listed from the top of the store hierarchy to the bottom.
 *
 * @author diogodias
 */
public enum ManagerType {

    HEAD_MANAGER(1, "Head Manager", "Store Manager"),
    ASSISTANT_MANAGER(2, "Assistant Manager", "Deputy Manager"),
    DEPARTMENT_MANAGER(3, "Manager", "Department Manager"),
    TEAM_LEAD(4, "Team Lead", "Supervisor / Chargehand");

    private final int optionNumber;
    private final String jobTitle;
    private final String storeRole;

    // Links each manager type to its menu number, its job title and the
    // equivalent role name used in supermarkets.
    ManagerType(int optionNumber, String jobTitle, String storeRole) {
        this.optionNumber = optionNumber;
        this.jobTitle = jobTitle;
        this.storeRole = storeRole;
    }

    // Returns the number the user types to choose this manager type.
    public int getOptionNumber() {
        return optionNumber;
    }

    // Returns the job title. It is passed to Manager.createManager(),
    // which uses it to create the correct Manager subclass.
    public String getJobTitle() {
        return jobTitle;
    }

    // Returns the equivalent role name used in supermarkets, e.g. "Store Manager".
    public String getStoreRole() {
        return storeRole;
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
