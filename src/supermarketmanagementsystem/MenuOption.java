
package supermarketmanagementsystem;

/**
 * Enum with the options of the main menu. The menu is printed by iterating through its values.
 * 
 * @author diogodias
 */
public enum MenuOption {

    SORT(1, "SORT"),
    SEARCH(2, "SEARCH"),
    ADD_RECORDS(3, "ADD RECORDS"),
    CREATE_BINARY_TREE(4, "Create Binary Tree"),
    EXIT(5, "EXIT");

    private final int optionNumber;
    private final String label;

    MenuOption(int optionNumber, String label) {
        this.optionNumber = optionNumber;
        this.label = label;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public String getLabel() {
        return label;
    }

    // Finds the menu option that matches the number typed by the user.
    // Returns null if no option has that number.
    public static MenuOption fromNumber(int number) {
        for (MenuOption option : values()) {
            if (option.optionNumber == number) {
                return option;
            }
        }
        return null;
    }
}