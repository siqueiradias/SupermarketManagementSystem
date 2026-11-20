package supermarketmanagementsystem;

/**
 * Checks and formats the name of a new employee. Kept separate from the
 * console code (no Scanner, no printing), so it can be unit tested on its own.
 *
 * @author diogodias
 */
public class NameValidator {

    public static final int MAX_NAME_LENGTH = 50; // maximum length of a full name

    // At least two words (first and last name). Each word must start with a letter
    // and may contain letters, apostrophes and hyphens (e.g. O'Brien, Mary-Jane).
    private static final String NAME_PATTERN = "[A-Za-z][A-Za-z'-]*( [A-Za-z][A-Za-z'-]*)+";

    // Removes extra spaces: outer spaces with trim(), repeated inner spaces with replaceAll().
    // A null value is treated as an empty name.
    public static String normalise(String name) {
        if (name == null) {
            return "";
        }
        return name.trim().replaceAll("\\s+", " ");
    }

    // Checks a (normalised) name. Returns null if the name is valid,
    // otherwise the message that explains why it is not valid.
    public static String validate(String name) {
        if (name == null || name.isEmpty()) {
            return "Name cannot be empty.";
        }
        if (name.length() > MAX_NAME_LENGTH) {
            return "Name is too long. Please use up to " + MAX_NAME_LENGTH + " characters.";
        }
        if (!name.matches(NAME_PATTERN)) {
            return "Please enter a first and last name using letters only.";
        }
        return null;
    }

    // Makes the first letter of each part of a name upper case and keeps the rest as typed.
    // A new part starts after a space, a hyphen or an apostrophe,
    // e.g. "mary-jane o'brien" -> "Mary-Jane O'Brien".
    public static String capitalise(String name) {
        String result = "";
        boolean startOfPart = true; // the first letter of the name starts a part

        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);

            if (startOfPart) {
                result += Character.toUpperCase(character);
            } else {
                result += character;
            }
            startOfPart = character == ' ' || character == '-' || character == '\'';
        }
        return result;
    }
}
