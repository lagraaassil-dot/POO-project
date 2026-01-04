package UI;
import java.util.*;


import Models.Media;
import Service.*;

public class SearchMedia {

    private static final Scanner scanner = new Scanner(System.in);

    // active filters
    private static Set<MediaTypes> activeTypes = new HashSet<>();
    private static Set<Genre> activeGenres = new HashSet<>();


    // =========================
    // Main entry point
    // =========================
    public static void searchMedia(MediaDatabase database) throws InterruptedException {
        boolean searching = true;

        while (searching) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "SEARCH MEDIA" + UIHelper.RESET);

            displayTypeMenu();

            if (!activeTypes.isEmpty()) {
                displayGenreMenu();
            }

            UIHelper.printInfo("Search: Type a Title/Director.");
            UIHelper.printInfo("Filter: Type numbers (e.g., 1.2 for types OR 10.15 for genres).");
            UIHelper.printInfo("Empty Input: Shows all matching media.");
            System.out.println(UIHelper.YELLOW + "  ⚠️  Cannot mix type and genre filters in same input!" + UIHelper.RESET);
            System.out.print("\nYour input: ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                List<Media> filtered = getFilteredList(database);
                displayResults(filtered);

                int option = handlePostSearchOptions(database);

                if (option == 2) {
                    searching = false;
                    BrowseMedia.browseMedia(database, filtered);
                } else if (option == 0) {
                    searching = false;
                }
                continue;
            }

            if (input.matches("^[0-9.\\s]+$")) {
                handleFilterInput(input);
            } else {
                List<Media> filtered = getFilteredList(database);
                List<Media> searched = performSearch(database, filtered, input);

                int option = handlePostSearchOptions(database);

                if (option == 2) {
                    searching = false;
                    BrowseMedia.browseMedia(database, searched);
                } else if (option == 0) {
                    searching = false;
                }
            }
        }
    }


    // =========================
    // Menus display
    // =========================
    private static void displayTypeMenu() {
        System.out.println(UIHelper.BOLD + UIHelper.MAGENTA + "Types:" + UIHelper.RESET);

        System.out.println("1. Movie " + (activeTypes.contains(MediaTypes.MOVIE) ? "■ " : "□"));
        System.out.println("2. Series " + (activeTypes.contains(MediaTypes.SERIES) ? "■ " : "□"));
        System.out.println("3. Documentary " + (activeTypes.contains(MediaTypes.DOCUMENTARY) ? "■ " : "□"));
    }

    private static void displayGenreMenu() {
        System.out.println(UIHelper.BOLD + UIHelper.MAGENTA + "\nGenres:" + UIHelper.RESET);

        if (activeTypes.contains(MediaTypes.MOVIE) || activeTypes.contains(MediaTypes.SERIES)) {
            System.out.println(UIHelper.CYAN + "Movie/Series Genres:" + UIHelper.RESET);

            printGenreRow(
                    new GenreOption(10, Movie_Series_Genres.ACTION),
                    new GenreOption(11, Movie_Series_Genres.COMEDY),
                    new GenreOption(12, Movie_Series_Genres.DRAMA),
                    new GenreOption(13, Movie_Series_Genres.HORROR)
            );
            printGenreRow(
                    new GenreOption(14, Movie_Series_Genres.ROMANCE),
                    new GenreOption(15, Movie_Series_Genres.SCIENCEFICTION),
                    new GenreOption(16, Movie_Series_Genres.THRILLER),
                    new GenreOption(17, Movie_Series_Genres.ANIMATION)
            );
            printGenreRow(
                    new GenreOption(18, Movie_Series_Genres.ADVENTURE),
                    new GenreOption(19, Movie_Series_Genres.CRIME),
                    null,
                    null
            );
        }

        if (activeTypes.contains(MediaTypes.DOCUMENTARY)) {
            System.out.println(UIHelper.CYAN + "\nDocumentary Genres:" + UIHelper.RESET);

            printGenreRow(
                    new GenreOption(20, Documentary_Genres.NATURE),
                    new GenreOption(21, Documentary_Genres.SCIENCE),
                    new GenreOption(22, Documentary_Genres.TRUE_CRIME),
                    new GenreOption(23, Documentary_Genres.HISTORICAL)
            );
            printGenreRow(
                    new GenreOption(24, Documentary_Genres.BIOGRAPHICAL),
                    new GenreOption(25, Documentary_Genres.SPORTS),
                    new GenreOption(26, Documentary_Genres.HEALTH),
                    new GenreOption(27, Documentary_Genres.TECHNOLOGY)
            );
        }
        System.out.println();
    }


    // =========================
    // Genre helper classes
    // =========================
    private static class GenreOption {
        int number;
        Genre genre;

        GenreOption(int number, Genre genre) {
            this.number = number;
            this.genre = genre;
        }
    }

    private static void printGenreRow(GenreOption g1, GenreOption g2, GenreOption g3, GenreOption g4) {
        String[] cols = new String[4];
        GenreOption[] opts = {g1, g2, g3, g4};

        for (int i = 0; i < 4; i++) {
            if (opts[i] != null) {
                String box = activeGenres.contains(opts[i].genre) ? "■ " : "□";
                
                String name = InputChecks.capitalizeWords(opts[i].genre.getDisplayName());
                cols[i] = String.format("%2d. %-15s %s", opts[i].number, name, box);
            } else {
                cols[i] = " ".repeat(20);
            }
        }

        System.out.println(cols[0] + "  " + cols[1] + "  " + cols[2] + "  " + cols[3]);
    }


    // =========================
    // Filter handling
    // =========================
    private static void handleFilterInput(String input) throws InterruptedException {
        // Split by dot (escaped as \\.)
        String[] parts = input.split("\\.");
        
        // Collect all valid numbers first
        List<Integer> validNumbers = new ArrayList<>();
        boolean hasInvalid = false;

        for (String p : parts) {
            String trimmed = p.trim();
            if (trimmed.isEmpty()) continue; 

            if (InputChecks.isValidNumber(trimmed)) {
                int val = Integer.parseInt(trimmed);
                if ((val >= 1 && val <= 3) || (val >= 10 && val <= 27)) {
                    validNumbers.add(val);
                } else {
                    hasInvalid = true;
                }
            } else {
                hasInvalid = true;
            }
        }

        // Check if mixing types and genres
        boolean hasType = false;
        boolean hasGenre = false;

        for (int val : validNumbers) {
            if (val >= 1 && val <= 3) {
                hasType = true;
            }
            if (val >= 10 && val <= 27) {
                hasGenre = true;
            }
        }

        // Cannot mix types and genres
        if (hasType && hasGenre) {
            UIHelper.printFailure("Cannot mix type filters (1-3) and genre filters (10-27) in the same input!");
            UIHelper.printInfo("Use separate inputs: '1.2' for types, then '10.15' for genres.");
            Thread.sleep(2500);
            return;
        }

        // If validation passes, apply filters
        for (int val : validNumbers) {
            toggleFilter(val);
        }

        if (hasInvalid) {
            System.out.println(UIHelper.BOLD + UIHelper.YELLOW + "Warning: Some invalid filter numbers were ignored." + UIHelper.RESET);
            Thread.sleep(1500);
        }
    }

    // Helper to define allowed ranges
    private static void toggleFilter(int val) throws InterruptedException {
        // 1. Handle Media Types
        if (val == 1) toggleType(MediaTypes.MOVIE);
        else if (val == 2) toggleType(MediaTypes.SERIES);
        else if (val == 3) toggleType(MediaTypes.DOCUMENTARY);

        // 2. Handle Movie/Series Genres (10-19)
        // Only allow if Movie or Series type is currently active
        else if (val >= 10 && val <= 19) {
            if (activeTypes.contains(MediaTypes.MOVIE) || activeTypes.contains(MediaTypes.SERIES)) {
                if (val == 10) toggleGenre(Movie_Series_Genres.ACTION);
                else if (val == 11) toggleGenre(Movie_Series_Genres.COMEDY);
                else if (val == 12) toggleGenre(Movie_Series_Genres.DRAMA);
                else if (val == 13) toggleGenre(Movie_Series_Genres.HORROR);
                else if (val == 14) toggleGenre(Movie_Series_Genres.ROMANCE);
                else if (val == 15) toggleGenre(Movie_Series_Genres.SCIENCEFICTION);
                else if (val == 16) toggleGenre(Movie_Series_Genres.THRILLER);
                else if (val == 17) toggleGenre(Movie_Series_Genres.ANIMATION);
                else if (val == 18) toggleGenre(Movie_Series_Genres.ADVENTURE);
                else if (val == 19) toggleGenre(Movie_Series_Genres.CRIME);
            } else {
                UIHelper.printFailure("Movie/Series type must be selected first to use these genres.");
                Thread.sleep(1500);
            }
        }

        // 3. Handle Documentary Genres (20-27)
        // Only allow if Documentary type is currently active
        else if (val >= 20 && val <= 27) {
            if (activeTypes.contains(MediaTypes.DOCUMENTARY)) {
                if (val == 20) toggleGenre(Documentary_Genres.NATURE);
                else if (val == 21) toggleGenre(Documentary_Genres.SCIENCE);
                else if (val == 22) toggleGenre(Documentary_Genres.TRUE_CRIME);
                else if (val == 23) toggleGenre(Documentary_Genres.HISTORICAL);
                else if (val == 24) toggleGenre(Documentary_Genres.BIOGRAPHICAL);
                else if (val == 25) toggleGenre(Documentary_Genres.SPORTS);
                else if (val == 26) toggleGenre(Documentary_Genres.HEALTH);
                else if (val == 27) toggleGenre(Documentary_Genres.TECHNOLOGY);
            } else {
                UIHelper.printFailure("Documentary type must be selected first to use these genres.");
                Thread.sleep(1500);
            }
        }

        // Cleanup: If no types are selected, clear all genre filters
        if (activeTypes.isEmpty()) {
            activeGenres.clear();
        }
    }

    private static void toggleType(MediaTypes t) {
        if (activeTypes.contains(t)) activeTypes.remove(t);
        else activeTypes.add(t);
    }

    private static void toggleGenre(Genre g) {
        if (activeGenres.contains(g)) activeGenres.remove(g);
        else activeGenres.add(g);
    }


    // =========================
    // Core logic
    // =========================
    private static List<Media> getFilteredList(MediaDatabase database) {
        List<Media> result = new ArrayList<>();

        if (activeTypes.isEmpty()) {
            result.addAll(database.getAllMedia());
        } else {
            for (MediaTypes t : activeTypes) {
                result.addAll(database.searchByType(t));
            }
        }

        if (!activeGenres.isEmpty()) {
            List<Media> genreFiltered = new ArrayList<>();

            for (Media m : result) {
                for (Genre g : activeGenres) {
                    if (m.getGenre() != null && m.getGenre().matches(g)) {
                        genreFiltered.add(m);
                        break;
                    }
                }
            }
            return genreFiltered;
        }

        return result;
    }

    private static List<Media> performSearch(MediaDatabase database, List<Media> list, String query) {
        Set<Media> combined = new HashSet<>();
        combined.addAll(database.searchByTitle(query, list));
        combined.addAll(database.searchByDirector(query, list));

        List<Media> result = new ArrayList<>(combined);
        displayResults(result);
        return result;
    }


    // =========================
    // Output helpers
    // =========================
    private static void displayResults(List<Media> results) {
        if (results.isEmpty()) {
            UIHelper.printFailure("No media found.");
        } else {
            UIHelper.printSuccess("Found " + results.size() + " items:");
            for (Media m : results) {
                UIHelper.printMediaBox(m);
            }
            UIHelper.printFooter("END OF SEARCH RESULTS");
        }
    }

    private static int handlePostSearchOptions(MediaDatabase database) {
        System.out.println("\n1. New Search");
        System.out.println("2. Continue Searching");
        System.out.println("3. Confirm Search");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            activeTypes.clear();
            activeGenres.clear();
            return 1;
        } else if (choice.equals("2")) {
            return 1;
        } else if (choice.equals("3")) {
            return 2;
        }
        return 0;
    }
}