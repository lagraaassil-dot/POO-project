package UI;

import java.util.*;
import Models.Media;
import Models.Series;
import Models.Episode;
import Service.*;

public class BrowseMedia {

    private static final Scanner scanner = new Scanner(System.in);

    // =========================
    // Entry Point
    // =========================
    public static void browseMedia(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
        boolean browsing = true;

        while (browsing) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "BROWSE MEDIA" + UIHelper.RESET);
            System.out.println();

            if (mediaList.isEmpty()) {
                UIHelper.printInfo("No media items to display.");
                UIHelper.printInfo("Press any key to return...");
                scanner.nextLine();
                return;
            }

            displayBrowseList(mediaList);

            UIHelper.printInfo("Enter selection (e.g., 1.5) or Q to go back:");
            UIHelper.printInfo("Input way: num of Type.num of media");
            UIHelper.printInfo("eg: 1.5 means movie 5 or 2.2 means series 2 or 3.6 means documentary 6 ");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) {
                browsing = false;
                continue;
            }

            Media selected = parseSelection(input, mediaList);

            if (selected == null) {
                UIHelper.printFailure("Invalid selection. Please use format 'type.number' (e.g., 1.3)");
                Thread.sleep(2000);
                continue;
            }

            browsing = handleMediaDetail(database, selected);
        }
    }

    // =========================
    // Browse List
    // =========================
    private static void displayBrowseList(List<Media> mediaList) {

        List<Media> movies = new ArrayList<>();
        List<Media> series = new ArrayList<>();
        List<Media> documentaries = new ArrayList<>();

        for (Media m : mediaList) {
            if (m.getMediaType() == MediaTypes.MOVIE) movies.add(m);
            else if (m.getMediaType() == MediaTypes.SERIES) series.add(m);
            else documentaries.add(m);
        }

        printCategory("1) MOVIES", movies, UIHelper.YELLOW);
        printCategory("2) SERIES", series, UIHelper.CYAN);
        printCategory("3) DOCUMENTARIES", documentaries, UIHelper.GREEN);

        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }

    private static void printCategory(String title, List<Media> list, String color) {
        if (list.isEmpty()) return;

        System.out.println(UIHelper.BOLD + color + title + " (" + list.size() + ")" + UIHelper.RESET);

        for (int i = 0; i < list.size(); i++) {
            Media m = list.get(i);
            String watched = m.isWatched() ? UIHelper.MAGENTA : "";
            System.out.println(watched + "   " + (i + 1) + ". " +
                    m.getTitle() + " (" + m.getReleaseYear() + ") - " +
                    m.getDirector() + UIHelper.RESET);
        }
        System.out.println();
    }

    // =========================
    // Selection Parsing
    // =========================
    private static Media parseSelection(String input, List<Media> mediaList) {

        if (!InputChecks.isValidSelection(input)) return null;

        String[] parts = input.split("\\.");
        int type = Integer.parseInt(parts[0]);
        int index = Integer.parseInt(parts[1]) - 1;

        MediaTypes target =
                type == 1 ? MediaTypes.MOVIE :
                type == 2 ? MediaTypes.SERIES :
                type == 3 ? MediaTypes.DOCUMENTARY : null;

        if (target == null) return null;

        List<Media> filtered = new ArrayList<>();
        for (Media m : mediaList)
            if (m.getMediaType() == target)
                filtered.add(m);

        if (index < 0 || index >= filtered.size()) return null;

        return filtered.get(index);
    }

    // =========================
    // Media Detail Router
    // =========================
    private static boolean handleMediaDetail(MediaDatabase database, Media media) throws InterruptedException {
        if (media.getMediaType() == MediaTypes.SERIES)
            return handleSeriesDetail(database, (Series) media);

        return handleSimpleMediaDetail(database, media);
    }

    // =========================
    // Movie / Documentary View
    // =========================
    private static boolean handleSimpleMediaDetail(MediaDatabase database, Media media) throws InterruptedException {

        while (true) {
            UIHelper.clearScreen();
            UIHelper.printMediaBox(media);

            System.out.println(UIHelper.BOLD + UIHelper.MAGENTA + "Actions:" + UIHelper.RESET);
            System.out.println("1. Mark as Watched");
            System.out.println("2. Rate");
            System.out.println("Q. Back");
            System.out.println();

            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("Q")) return true;

            if (choice.equals("1")) {
                database.markAsWatched(media.getTitle());
                UIHelper.printSuccess("Marked as watched!");
                Thread.sleep(1500);
            } else if (choice.equals("2")) {
                handleRating(database, media);
            } else {
                UIHelper.printFailure("Invalid choice.");
                Thread.sleep(1500);
            }
        }
    }

    // =========================
    // Series Navigation
    // =========================
    private static boolean handleSeriesDetail(MediaDatabase database, Series series) throws InterruptedException {

        while (true) {
            UIHelper.clearScreen();
            UIHelper.printMediaBox(series);
            displaySeasonList(series);

            UIHelper.printInfo("Enter season number or Q to go back:");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) return true;
            
            if (!InputChecks.isValidNumber(input)) {
                UIHelper.printFailure("Invalid season number.");
                Thread.sleep(1500);
                continue;
            }

            int season = Integer.parseInt(input);
            
            if (season < 1 || season > series.getNbOfSeasons()) {
                UIHelper.printFailure("Invalid season number. Choose between 1 and " + series.getNbOfSeasons());
                Thread.sleep(1500);
                continue;
            }

            handleSeasonView(database, series, season);
        }
    }

    private static void handleSeasonView(MediaDatabase database, Series series, int season) throws InterruptedException {

        while (true) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "SEASON " + season + " - " + series.getTitle() + UIHelper.RESET);
            System.out.println();
            
            displaySeasonStats(series, season);
            displayEpisodeList(series, season);

            UIHelper.printInfo("Enter episode number or Q to go back:");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) return;
            
            if (!InputChecks.isValidNumber(input)) {
                UIHelper.printFailure("Invalid episode number.");
                Thread.sleep(1500);
                continue;
            }

            Episode ep = series.findEpisode(season, Integer.parseInt(input));
            
            if (ep == null) {
                UIHelper.printFailure("Episode not found.");
                Thread.sleep(1500);
                continue;
            }

            handleEpisodeView(database, series, ep, season, ep.getEpisodeNumber());
        }
    }

    private static void handleEpisodeView(MediaDatabase database, Series series,
                                          Episode episode, int season, int number) throws InterruptedException {

        while (true) {
            UIHelper.clearScreen();
            displayEpisodeBox(episode, series.getTitle(), season);

            System.out.println(UIHelper.BOLD + UIHelper.MAGENTA + "Actions:" + UIHelper.RESET);
            System.out.println("1. Mark as Watched");
            System.out.println("2. Rate");
            System.out.println("Q. Back");
            System.out.println();

            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("Q")) return;

            if (choice.equals("1")) {
                series.markEpisodeAsWatched(season, number);
                UIHelper.printSuccess("Episode marked as watched!");
                
                if (series.isFullyWatched()) {
                    database.markAsWatched(series.getTitle());
                    UIHelper.printSuccess("🎉 All episodes watched! Series marked as complete!");
                }
                Thread.sleep(1500);
            } else if (choice.equals("2")) {
                handleEpisodeRating(database, series, season, number);
            } else {
                UIHelper.printFailure("Invalid choice.");
                Thread.sleep(1500);
            }
        }
    }

    // =========================
    // Display Helpers
    // =========================
    private static void displaySeasonList(Series series) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "SEASONS:" + UIHelper.RESET);
        System.out.println();
        
        for (int s = 1; s <= series.getNbOfSeasons(); s++) {
            String watchedMarker = series.isSeasonWatched(s) ? UIHelper.MAGENTA + "✓ " : "  ";
            System.out.println(watchedMarker + UIHelper.BOLD + "Season " + s + UIHelper.RESET);
            System.out.println("    Episodes: " + series.getEpisodesInSeason(s));
            System.out.println("    Avg Rating: " + series.getAverageRatingStarsOfSeason(s));
            System.out.println();
        }
        
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }

    private static void displaySeasonStats(Series series, int season) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "Season Statistics:" + UIHelper.RESET);
        System.out.println("  Total Episodes: " + series.getEpisodesInSeason(season));
        System.out.println("  Total Duration: " + series.getSeasonDuration(season) + " minutes");
        System.out.println("  Average Rating: " + series.getAverageRatingStarsOfSeason(season));
        System.out.println();
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
        System.out.println();
    }

    private static void displayEpisodeList(Series series, int season) {
        System.out.println(UIHelper.BOLD + UIHelper.YELLOW + "EPISODES:" + UIHelper.RESET);
        System.out.println();
        
        for (Episode ep : series.getEpisodes()) {
            if (ep.getSeasonNumber() == season) {
                String watchedMarker = ep.isWatched() ? UIHelper.MAGENTA + "✓ " : "  ";
                System.out.println(watchedMarker + ep.getEpisodeNumber() + ". " + ep.getTitle() + UIHelper.RESET);
            }
        }
        
        System.out.println();
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }

    private static void displayEpisodeBox(Episode episode, String seriesTitle, int season) {
        // Top border
        System.out.println(UIHelper.WHITE + "  ╭" + "─".repeat(76) + "╮" + UIHelper.RESET);
        
        // Series and season header
        String header = seriesTitle + " - Season " + season;
        int headerPadding = 74 - header.length();
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         UIHelper.BOLD + UIHelper.CYAN + header + UIHelper.RESET +
                         " ".repeat(Math.max(0, headerPadding)) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Empty line
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         " ".repeat(74) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Episode title
        String episodeHeader = "Episode " + episode.getEpisodeNumber() + ": " + episode.getTitle();
        int epPadding = 74 - episodeHeader.length();
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         UIHelper.BOLD + episodeHeader + UIHelper.RESET +
                         " ".repeat(Math.max(0, epPadding)) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Empty line
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         " ".repeat(74) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Duration
        String durationLine = "Duration: " + episode.getDuration() + " minutes";
        int durPadding = 74 - durationLine.length();
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         durationLine +
                         " ".repeat(Math.max(0, durPadding)) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Watch status
        String watchStatus = episode.isWatched() ? "Watched" : "Not Watched";
        String watchLine = "Status: " + watchStatus;
        int watchPadding = 74 - watchLine.length();
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         watchLine +
                         " ".repeat(Math.max(0, watchPadding)) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Rating
        String ratingStars = episode.getRatingStars();
        String ratingLine = "Rating: " + ratingStars;
        // get width right by ignoring collors which have an long ansi code that can mess up the display
        int visibleLength = ratingStars.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int ratingPadding = 74 - ("Rating: ".length() + visibleLength);
        System.out.println(UIHelper.WHITE + "  │ " + UIHelper.RESET + 
                         ratingLine +
                         " ".repeat(Math.max(0, ratingPadding)) +
                         UIHelper.WHITE + "│" + UIHelper.RESET);
        
        // Bottom border
        System.out.println(UIHelper.WHITE + "  ╰" + "─".repeat(76) + "╯" + UIHelper.RESET);
        System.out.println();
    }

    // =========================
    // Ratings
    // =========================
    private static void handleRating(MediaDatabase database, Media media) throws InterruptedException {
        UIHelper.printInfo("Enter a rating (0.0 to 5.0):");
        System.out.print("Rating: ");
        String input = scanner.nextLine().trim();
        
        if (!InputChecks.isValidDecimal(input)) {
            UIHelper.printFailure("Invalid rating. Please enter a number between 0.0 and 5.0");
            Thread.sleep(1500);
            return;
        }
        
        double rating = Double.parseDouble(input);
        
        if (rating < 0 || rating > 5) {
            UIHelper.printFailure("Rating must be between 0.0 and 5.0");
            Thread.sleep(1500);
            return;
        }
        
        database.rateMedia(media.getTitle(), rating);
        UIHelper.printSuccess("★ Rating saved: " + rating + "/5.0");
        Thread.sleep(1500);
    }

    private static void handleEpisodeRating(MediaDatabase database, Series series,
                                            int season, int episode) throws InterruptedException {
        UIHelper.printInfo("Enter a rating (0.0 to 5.0):");
        System.out.print("Rating: ");
        String input = scanner.nextLine().trim();
        
        if (!InputChecks.isValidDecimal(input)) {
            UIHelper.printFailure("Invalid rating. Please enter a number between 0.0 and 5.0");
            Thread.sleep(1500);
            return;
        }
        
        double rating = Double.parseDouble(input);
        
        if (rating < 0 || rating > 5) {
            UIHelper.printFailure("Rating must be between 0.0 and 5.0");
            Thread.sleep(1500);
            return;
        }
        
        database.rateEpisode(series.getTitle(), season, episode, rating);
        UIHelper.printSuccess("★ Rating saved: " + rating + "/5.0");
        UIHelper.printInfo("Season and series ratings updated automatically.");
        Thread.sleep(2000);
    }
}