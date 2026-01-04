package UI;
import java.util.*;


import Models.Media;
import Models.Series;
import Service.*;

/**
 * AddSeries - Handles adding new series or modifying existing ones
 * Extends Addmedia to reuse genre display and other helper methods
 * 
 * Features:
 * - Add a brand new series (just basic info, no episodes)
 * - Add episodes/seasons to existing series
 * - Auto-creates Season 1 if series has 0 seasons
 * - Option to fill series with episodes right after creation
 */
public class AddSeries extends Addmedia {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Main entry point for series addition
     * Presents user with two options: new series or modify existing
     * 
     * @param database - The MediaDatabase
     * @param mediaList - List of all media
     */
    public static void addSeries(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
        boolean adding = true;
        
        while (adding) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "ADD SERIES" + UIHelper.RESET);
            System.out.println();
            
            UIHelper.printInfo("What would you like to do?");
            UIHelper.printSection("1", "📺 Add a New Series", UIHelper.BLUE);
            UIHelper.printSection("2", "➕ Add Episodes/Seasons to Existing Series", UIHelper.BLUE);
            UIHelper.printSection("Q", "🔙 Back", UIHelper.BLUE);
            
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equalsIgnoreCase("q")) {
                adding = false;
            } else if (choice.equals("1")) {
                addNewSeries(database, mediaList);
                adding = false;
            } else if (choice.equals("2")) {
                modifyExistingSeries(database, mediaList);
                adding = false;
            } else {
                UIHelper.printFailure("Invalid choice. Please try again.");
                Thread.sleep(1500);
            }
        }
    }
    
    /**
     * Creates a brand new series with basic information only
     * Episodes start at 0 and can be added afterward
     * 
     * @param database - The MediaDatabase
     * @param mediaList - List of all media
     */
    private static void addNewSeries(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
        String title = "";
        String director = "";
        int releaseYear = 0;
        Movie_Series_Genres genre = Movie_Series_Genres.UNKNOWN;
        
        // STEP 1: Get title and director
        boolean typing = true;
        while (typing) {
            UIHelper.clearScreen();
            UIHelper.printInfo(
                "To add a new series please enter its title and director name separated by a " +
                UIHelper.RESET + UIHelper.BOLD + UIHelper.UNDERLINE + UIHelper.YELLOW + "comma(',')"
            );
            
            System.out.println(UIHelper.GREEN + "Correct input: series title,director name " + UIHelper.RESET);
            System.out.println(UIHelper.RED + "Wrong input: director name,series title" + UIHelper.RESET);
            
            String input = scanner.nextLine().trim();
            
            // Check empty input
            if (input.isEmpty()) {
                UIHelper.printFailure("Input cannot be empty.");
                Thread.sleep(1500);
                continue;
            }
            
            String[] parts = input.split(",");
            
            // Must be exactly 2 parts
            if (parts.length != 2) {
                UIHelper.printFailure("Invalid input format. Please use: title,director");
                Thread.sleep(1500);
                continue;
            }
            
            title = parts[0].trim();
            director = parts[1].trim();
            
            // Check empty title or director
            if (title.isEmpty() || director.isEmpty()) {
                UIHelper.printFailure("Title and director name cannot be empty.");
                Thread.sleep(1500);
                continue;
            }
            
            // Check if series already exists
            boolean flag = true;
            for (Media media : mediaList) {
                if (media instanceof Series && media.getTitle().equalsIgnoreCase(title)) {
                    UIHelper.printFailure("A series with this title already exists.");
                    Thread.sleep(1500);
                    flag = false;
                    break;
                }
            }
            
            if (!flag) continue;
            
            // Successfully got title and director
            typing = false;
        }
        
        // STEP 2: Get release year
        while (true) {
            UIHelper.clearScreen();
            UIHelper.printInfo("Enter additional details for the series:");
            System.out.print("1) Release Year: ");
            
            String yearInput = scanner.nextLine().trim();
            
            
            if (!InputChecks.isValidNumber(yearInput)) {
                UIHelper.printFailure("Please enter a valid year.");
                Thread.sleep(1500);
                continue;
            }
            
            releaseYear = Integer.parseInt(yearInput);
            
            if (releaseYear < 1888 || releaseYear > Calendar.getInstance().get(Calendar.YEAR)) {
                UIHelper.printFailure("Please enter a valid release year between 1888 and " + 
                                Calendar.getInstance().get(Calendar.YEAR) + ".");
                Thread.sleep(1500);
                continue;
            }
            break;
        }
        
        // STEP 3: Genre selection (using inherited method from Addmedia)
        while (true) {
            UIHelper.clearScreen();
            UIHelper.printInfo("Enter additional details for the series:");
            displayGenreMenu(true); // true for Movie/Series genres
            System.out.print("\nEnter genre number: ");
            
            String input = scanner.nextLine().trim();
            
            // Use ValidationUtils method instead of local method
            if (!InputChecks.isValidNumber(input)) {
                UIHelper.printFailure("Please enter a valid number.");
                Thread.sleep(1500);
                continue;
            }
            
            int choice = Integer.parseInt(input);
            
            switch (choice) {
                case 1 -> genre = Movie_Series_Genres.ACTION;
                case 2 -> genre = Movie_Series_Genres.COMEDY;
                case 3 -> genre = Movie_Series_Genres.DRAMA;
                case 4 -> genre = Movie_Series_Genres.HORROR;
                case 5 -> genre = Movie_Series_Genres.ROMANCE;
                case 6 -> genre = Movie_Series_Genres.SCIENCEFICTION;
                case 7 -> genre = Movie_Series_Genres.THRILLER;
                case 8 -> genre = Movie_Series_Genres.ANIMATION;
                case 9 -> genre = Movie_Series_Genres.ADVENTURE;
                case 10 -> genre = Movie_Series_Genres.CRIME;
                default -> {
                    UIHelper.printFailure("Invalid genre number.");
                    Thread.sleep(1500);
                    continue;
                }
            }
            break;
        }
        
        // Create the series with 0 seasons and no episodes
        Series newSeries = new Series(title, director, releaseYear, genre, 0, new ArrayList<>());
        database.addMedia(newSeries);
        UIHelper.printSuccess("Series '" + title + "' added successfully!");
        Thread.sleep(1500);
        
        // STEP 4: Ask if user wants to add episodes now
        UIHelper.clearScreen();
        UIHelper.printInfo("Would you like to add episodes to '" + title + "' now? (Y/N)");
        System.out.print("Your choice: ");
        String addNow = scanner.nextLine().trim().toLowerCase();
        
        if (addNow.equals("y")) {
            // Go to the modify series interface
            modifySeriesByTitle(database, title);
        }
    }
    
    /**
     * Shows list of existing series and lets user select one to modify
     * 
     * @param database - The MediaDatabase
     * @param mediaList - List of all media
     */
    private static void modifyExistingSeries(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
        // Get all series from the media list
        List<Series> seriesList = new ArrayList<>();
        for (Media media : mediaList) {
            if (media instanceof Series) {
                seriesList.add((Series) media);
            }
        }
        
        // Check if there are any series
        if (seriesList.isEmpty()) {
            UIHelper.clearScreen();
            UIHelper.printFailure("No series found in the database.");
            UIHelper.printInfo("Press any key to go back...");
            scanner.nextLine();
            return;
        }
        
        // Display list of series
        while (true) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "SELECT SERIES TO MODIFY" + UIHelper.RESET);
            System.out.println();
            
            System.out.println(UIHelper.BOLD + UIHelper.CYAN + "Available Series:" + UIHelper.RESET);
            for (int i = 0; i < seriesList.size(); i++) {
                Series s = seriesList.get(i);
                System.out.println((i + 1) + ". " + s.getTitle() + 
                                 " (" + s.getReleaseYear() + ") - " + 
                                 s.getNbOfSeasons() + " seasons, " + 
                                 s.getTotalEpisodes() + " episodes");
            }
            System.out.println();
            
            UIHelper.printInfo("Enter series number, or type 'Q' to go back:");
            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("q")) {
                return;
            }
            
            
            if (!InputChecks.isValidNumber(input)) {
                UIHelper.printFailure("Please enter a valid number.");
                Thread.sleep(1500);
                continue;
            }
            
            int choice = Integer.parseInt(input);
            
            if (choice < 1 || choice > seriesList.size()) {
                UIHelper.printFailure("Invalid series number.");
                Thread.sleep(1500);
                continue;
            }
            
            // Get the selected series and modify it
            Series selectedSeries = seriesList.get(choice - 1);
            modifySeriesByTitle(database, selectedSeries.getTitle());
            break;
        }
    }
    
    /**
     * Main series modification interface
     * Allows adding seasons or episodes to a specific series
     * Automatically creates Season 1 if series has 0 seasons
     * 
     * @param database - The MediaDatabase
     * @param seriesTitle - Title of the series to modify
     */
    private static void modifySeriesByTitle(MediaDatabase database, String seriesTitle) throws InterruptedException {
        // Find the series using database method
        Series series = database.getSeriesByTitle(seriesTitle);
        
        if (series == null) {
            UIHelper.printFailure("Series not found.");
            Thread.sleep(1500);
            return;
        }
        
        // AUTO-CREATE SEASON 1 if series has 0 seasons
        if (series.getNbOfSeasons() == 0) {
            UIHelper.clearScreen();
            UIHelper.printInfo("This series has no seasons yet. Creating Season 1 automatically...");
            database.addSeasonToSeries(series.getTitle());
            UIHelper.printSuccess("✓ Season 1 created!");
            Thread.sleep(1500);
        }
        
        boolean modifying = true;
        
        while (modifying) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "MODIFY: " + series.getTitle() + UIHelper.RESET);
            System.out.println();
            
            // Show current series info
            System.out.println(UIHelper.BOLD + UIHelper.CYAN + "Current Status:" + UIHelper.RESET);
            System.out.println("  Seasons: " + series.getNbOfSeasons());
            System.out.println("  Total Episodes: " + series.getTotalEpisodes());
            System.out.println();
            
            UIHelper.printInfo("What would you like to do?");
            UIHelper.printSection("1", "➕ Add a New Season", UIHelper.BLUE);
            UIHelper.printSection("2", "📝 Add Episode to Existing Season", UIHelper.BLUE);
            UIHelper.printSection("Q", "✅ Done (Go Back)", UIHelper.BLUE);
            
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("q")) {
                modifying = false;
            } else if (choice.equals("1")) {
                addSeasonToSeries(database, series);
            } else if (choice.equals("2")) {
                addEpisodeToSeries(database, series);
            } else {
                UIHelper.printFailure("Invalid choice. Please try again.");
                Thread.sleep(1500);
            }
        }
    }
    
    /**
     * Adds a new empty season to the series
     * 
     * @param database - The MediaDatabase
     * @param series - The Series to modify
     */
    private static void addSeasonToSeries(MediaDatabase database, Series series) throws InterruptedException {
        UIHelper.clearScreen();
        UIHelper.printInfo("Adding new season to '" + series.getTitle() + "'...");
        
        boolean success = database.addSeasonToSeries(series.getTitle());
        
        if (success) {
            UIHelper.printSuccess("✓ Season " + series.getNbOfSeasons() + " added successfully!");
            UIHelper.printInfo("You can now add episodes to this season.");
        } else {
            UIHelper.printFailure("Failed to add season.");
        }
        
        Thread.sleep(2000);
    }
    
    /**
     * Adds an episode to a specific season
     * 
     * @param database - The MediaDatabase
     * @param series - The Series to modify
     */
    private static void addEpisodeToSeries(MediaDatabase database, Series series) throws InterruptedException {
        String episodeTitle = "";
        int episodeNumber = 0;
        int seasonNumber = 0;
        int duration = 0;
        
        // STEP 1: Get season number
        if (series.getNbOfSeasons() == 1) {
            // Only one season → auto-select
            UIHelper.printInfo("Only one season available. Adding episode to Season 1.");
            Thread.sleep(1500);
            seasonNumber = 1;
        } else {
            while (true) {
                UIHelper.clearScreen();
                UIHelper.printInfo("Adding episode to '" + series.getTitle() + "'");
                System.out.println("Available seasons: 1 to " + series.getNbOfSeasons());
                System.out.print("Which season? ");

                String input = scanner.nextLine().trim();

                
                if (!InputChecks.isValidNumber(input)) {
                    UIHelper.printFailure("Please enter a valid season number.");
                    Thread.sleep(1500);
                    continue;
                }

                seasonNumber = Integer.parseInt(input);

                if (seasonNumber < 1 || seasonNumber > series.getNbOfSeasons()) {
                    UIHelper.printFailure("Invalid season number. Choose between 1 and " +
                                    series.getNbOfSeasons());
                    Thread.sleep(1500);
                    continue;
                }

                break;
            }
        }

        
        // STEP 2: Auto-generate episode number using Series model method
        int existingEpisodes = series.getEpisodesInSeason(seasonNumber);
        episodeNumber = existingEpisodes + 1;

        
        // STEP 3: Get episode title
        while (true) {
            UIHelper.clearScreen();
            UIHelper.printInfo("Adding Episode " + episodeNumber + " to Season " + seasonNumber);
            System.out.print("Episode title: ");
            
            episodeTitle = scanner.nextLine().trim();
            
            if (episodeTitle.isEmpty()) {
                UIHelper.printFailure("Episode title cannot be empty.");
                Thread.sleep(1500);
                continue;
            }
            
            // Use Series model method to check if title exists
            if (series.episodeTitleExists(seasonNumber, episodeTitle)) {
                UIHelper.printFailure("An episode with this title already exists in this season.");
                Thread.sleep(1500);
                continue;
            }

            break;
        }
        
        // STEP 4: Get duration
        while (true) {
            UIHelper.clearScreen();
            UIHelper.printInfo("Adding Episode: " + episodeTitle);
            System.out.print("Duration (in minutes): ");
            
            String input = scanner.nextLine().trim();
            
            
            if (!InputChecks.isValidNumber(input)) {
                UIHelper.printFailure("Please enter a valid duration.");
                Thread.sleep(1500);
                continue;
            }
            
            duration = Integer.parseInt(input);
            
            if (duration <= 0) {
                UIHelper.printFailure("Duration must be positive.");
                Thread.sleep(1500);
                continue;
            }
            
            if (duration > 500) {
                UIHelper.printFailure("Episode is too long (max 500 minutes).");
                Thread.sleep(1500);
                continue;
            }
            
            break;
        }
        
        // Add the episode using database method
        boolean success = database.addEpisodeToSeries(
            series.getTitle(),
            episodeTitle,
            episodeNumber,
            seasonNumber,
            duration
        );
        
        UIHelper.clearScreen();
        if (success) {
            UIHelper.printSuccess("✓ Episode '" + episodeTitle + "' added successfully!");
            if (seasonNumber == series.getNbOfSeasons()) {
                UIHelper.printInfo("(Season " + seasonNumber + " was created automatically)");
            }
        } else {
            UIHelper.printFailure("Failed to add episode. It may already exist.");
        }
        
        Thread.sleep(2000);
    }
}