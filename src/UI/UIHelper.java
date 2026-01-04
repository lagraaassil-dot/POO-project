package UI;

import Models.Media;
import Service.InputChecks;
import java.util.Scanner;

/**
 * UIHelper - Centralized utility class for all UI display operations
 * 
 * Contains:
 * - ANSI color and style codes
 * - Display methods (headers, boxes, messages)
 * - Text formatting utilities
 * - Input helpers
 */
public class UIHelper {
    
    // ==================== ANSI COLOR CODES ====================
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // ==================== ANSI STYLE CODES ====================
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    
    // ==================== BACKGROUND COLORS ====================
    public static final String BG_RED = "\u001B[41m";

    // ==================== CONSTANTS ====================
    private static final int CONSOLE_WIDTH = 78;
    private static final Scanner scanner = new Scanner(System.in);

    // ==================== HEADER METHODS ====================
    
    /**
     * Prints a big header box with a title and subtitle.
     * Used at the start of the program or major sections.
     * 
     * @param title Main title text
     * @param subtitle Secondary text below title
     */
    public static void printHeader(String title, String subtitle) {
        System.out.println(); // spacing

        // Top border
        System.out.println(BOLD + CYAN + "╔" + "═".repeat(CONSOLE_WIDTH) + "╗" + RESET);

        // Title centered
        System.out.println(
            BOLD + CYAN + "║" + RESET +
            centerText(title, CONSOLE_WIDTH) +
            BOLD + CYAN + "║" + RESET
        );

        // Subtitle centered
        System.out.println(
            BOLD + CYAN + "║" + RESET +
            centerText(subtitle, CONSOLE_WIDTH) +
            BOLD + CYAN + "║" + RESET
        );

        // Bottom border
        System.out.println(BOLD + CYAN + "╚" + "═".repeat(CONSOLE_WIDTH) + "╝" + RESET);
        System.out.println(); // spacing
    }

    /**
     * Prints a section title with a number (e.g., "1. Movies").
     * Used to separate menu sections.
     * 
     * @param number Section number
     * @param title Section title
     * @param color Color for the section
     */
    public static void printSection(String number, String title, String color) {
        System.out.println(); // spacing

        // Calculate dashes needed
        int dashCount = CONSOLE_WIDTH - title.length() - number.length() - 6;
        if (dashCount < 0) dashCount = 0;

        System.out.println(
            color + BOLD +
            "┌─ " + number + ". " + title + " " +
            "─".repeat(dashCount) +
            RESET
        );

        System.out.println(); // spacing
    }

    /**
     * Prints a footer at the end of sections or program.
     * 
     * @param message Footer message
     */
    public static void printFooter(String message) {
        System.out.println(BOLD + GREEN + "╔" + "═".repeat(CONSOLE_WIDTH) + "╗" + RESET);
        System.out.println(
            BOLD + GREEN + "║" +
            centerText(message, CONSOLE_WIDTH) + GREEN +
            "║" + RESET
        );
        System.out.println(BOLD + GREEN + "╚" + "═".repeat(CONSOLE_WIDTH) + "╝" + RESET);
        System.out.println();
    }

    // ==================== MEDIA DISPLAY ====================
    
    /**
     * Displays a media object inside a decorative box.
     * Uses the media's displayInfo() method.
     * 
     * @param media Media object to display
     */
    public static void printMediaBox(Media media) {
        // Top border
        System.out.println(WHITE + "  ╭" + "─".repeat(76) + "╮" + RESET);

        // Get media info and split into lines
        String info = media.displayInfo();
        String[] lines = info.split("\n");

        // Print each line inside the box
        for (String line : lines) {
            String printedLine = line;

            // Highlight title line
            if (line.contains("Title:")) {
                printedLine = BOLD + CYAN + line + RESET;
            }

            // Highlight rating text
            if (line.contains("Rating:")) {
                printedLine = line.replace(
                    "Rating:",
                    BOLD + YELLOW + "Rating:" + RESET
                );
            }

            // Add padding for box alignment
            int visibleLength = stripAnsi(printedLine).length();
            int spaces = 74 - visibleLength;
            if (spaces < 0) spaces = 0;

            System.out.println(
                WHITE + "  │ " + RESET +
                printedLine +
                " ".repeat(spaces) +
                WHITE + "│" + RESET
            );
        }

        // Bottom border
        System.out.println(WHITE + "  ╰" + "─".repeat(76) + "╯" + RESET);
        System.out.println();
    }

    // ==================== MESSAGE METHODS ====================
    
    /**
     * Prints a success message in green.
     * Used when operations complete successfully.
     * 
     * @param message Success message
     */
    public static void printSuccess(String message) {
        System.out.println(GREEN + "  ✓ " + message + RESET);
    }

    /**
     * Prints a failure message with red background.
     * Used when operations fail or invalid input is detected.
     * 
     * @param message Failure message
     */
    public static void printFailure(String message) {
        System.out.println(BG_RED + WHITE + "  " + message + RESET);
    }

    /**
     * Prints an informational message.
     * Used for hints, tips, or explanations.
     * 
     * @param message Info message
     */
    public static void printInfo(String message) {
        System.out.println(CYAN + "  💡 " + message + RESET);
    }

    // ==================== TEXT FORMATTING ====================
    
    /**
     * Centers text within a given width.
     * Used by header and footer functions.
     * 
     * @param text Text to center
     * @param width Total width to center within
     * @return Centered text with padding
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        if (padding < 0) padding = 0;

        String leftSpaces = " ".repeat(padding);
        String rightSpaces = " ".repeat(width - text.length() - padding);

        return leftSpaces + BOLD + WHITE + text + RESET + rightSpaces;
    }

    /**
     * Centers and prints text directly (assumes standard console width).
     * Used throughout UI classes.
     * 
     * @param text Text to center and print
     */
    public static void centerText(String text) {
        int padding = (CONSOLE_WIDTH - stripAnsi(text).length()) / 2;
        if (padding < 0) padding = 0;

        System.out.print(" ".repeat(padding));
        System.out.println(text);
    }

    /**
     * Strips ANSI escape codes from text.
     * Used for calculating actual text length.
     * 
     * @param text Text with ANSI codes
     * @return Clean text without ANSI codes
     */
    private static String stripAnsi(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    // ==================== SCREEN CONTROL ====================
    
    /**
     * Clears the screen by printing many newlines.
     * Cross-platform solution that works everywhere.
     */
    public static void clearScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    // ==================== INPUT HELPERS ====================
    
    /**
     * Reads an integer from user input with validation.
     * Returns -1 if input is invalid.
     * 
     * @return Parsed integer or -1 for invalid input
     */
    public static int readInt() {
        String input = scanner.nextLine().trim();
        if (InputChecks.isSimpleInteger(input)) {
            return Integer.parseInt(input);
        }
        return -1;
    }

    /**
     * Waits for any key press.
     * Used for "Press any key to continue" prompts.
     */
    public static void readKey() {
        scanner.nextLine();
    }

    /**
     * Gets the scanner instance.
     * Useful for UI classes that need direct scanner access.
     * 
     * @return Scanner instance
     */
    public static Scanner getScanner() {
        return scanner;
    }
}