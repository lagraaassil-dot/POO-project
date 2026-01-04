package Service;

//since many input checks are the same then they are all gathered in this class
public class InputChecks {

    // Validates if string is a positive integer
    public static boolean isValidNumber(String str) {
        if (str == null) {
            return false;
        }

        str = str.trim();

        if (str.isEmpty()) {
            return false;
        }

        // No leading zeros unless the number is exactly "0"
        if (str.length() > 1 && str.charAt(0) == '0') {
            return false;
        }

        // Max int length check (10 digits for positive int)
        if (str.length() > 10) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Validates if string is a valid decimal number
    public static boolean isValidDecimal(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        boolean hasDecimalPoint = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            // Allow decimal point, but only once
            if (c == '.') {
                if (hasDecimalPoint) {
                    return false; // Second decimal point found
                }
                hasDecimalPoint = true;
                continue;
            }

            // All other characters must be digits
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    // Validates if string is a simple integer (including negative)
    public static boolean isSimpleInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0 && c == '-') {
                continue;
            }
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    // Validates if string matches a pattern like "1.5"
    public static boolean isValidSelection(String str) {
        return str != null && str.matches("\\d+\\.\\d+");
    }

    // Capitalizes first letter of each word
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        String[] words = str.split(" ");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }

        return result.toString().trim();
    }
}