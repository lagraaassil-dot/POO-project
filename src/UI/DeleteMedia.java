package UI;
import java.util.*;


import Models.Media;
import Models.Series;
import Models.Episode;
import Service.*;

/**
 * DeleteMedia - Handles all deletion operations
 * 
 * Uses BrowseMedia interface for selection, but instead of watch/rate,
 * allows deletion at different levels:
 * - Movies/Documentaries: Delete entire media
 * - Series Level 1: Delete entire series
 * - Series Level 2: Delete entire season 
 * - Series Level 3: Delete individual episode
 */
public class DeleteMedia {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Main entry point - uses browse interface for deletion
     * 
     * @param database - The MediaDatabase
     * @param mediaList - List of all media
     */
    public static void deleteMedia(MediaDatabase database, List<Media> mediaList) throws InterruptedException {
        boolean deleting = true;
        
        while (deleting) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "DELETE MEDIA" + UIHelper.RESET);
            System.out.println();

            List<Media> freshList = database.getAllMedia();

            if (freshList.isEmpty()) {
                UIHelper.printInfo("No media items to delete.");
                UIHelper.printInfo("Press any key to return to main menu...");
                scanner.nextLine();
                deleting = false;
                continue;
            }

            // Display browse list
            displayBrowseList(freshList);

            UIHelper.printInfo("Enter selection to DELETE (e.g., 1.5 for 5th movie) or type 'Q' to go back:");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equalsIgnoreCase("q")) {
                deleting = false;
                continue;
            }

            Media selectedMedia = parseSelection(input, freshList);

            if (selectedMedia != null) {
                deleting = handleMediaDeletion(database, selectedMedia);
            } else {
                UIHelper.printFailure("Invalid selection. Please use format 'type.number' (e.g., 1.3)");
                Thread.sleep(2000);
            }
        }
    }

    
    /**
     * Displays browse list (same as BrowseMedia)
     */
    private static void displayBrowseList(List<Media> mediaList) {
        List<Media> movies = new ArrayList<>();
        List<Media> series = new ArrayList<>();
        List<Media> documentaries = new ArrayList<>();
        
        for (Media media : mediaList) {
            if (media.getMediaType() == MediaTypes.MOVIE) {
                movies.add(media);
            } else if (media.getMediaType() == MediaTypes.SERIES) {
                series.add(media);
            } else if (media.getMediaType() == MediaTypes.DOCUMENTARY) {
                documentaries.add(media);
            }
        }
        
        if (!movies.isEmpty()) {
            System.out.println(UIHelper.BOLD + UIHelper.YELLOW + "1) MOVIES (" + movies.size() + ")" + UIHelper.RESET);
            for (int i = 0; i < movies.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + movies.get(i).getTitle() + 
                                 " (" + movies.get(i).getReleaseYear() + ")");
            }
            System.out.println();
        }
        
        if (!series.isEmpty()) {
            System.out.println(UIHelper.BOLD + UIHelper.CYAN + "2) SERIES (" + series.size() + ")" + UIHelper.RESET);
            for (int i = 0; i < series.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + series.get(i).getTitle() + 
                                 " (" + series.get(i).getReleaseYear() + ")");
            }
            System.out.println();
        }
        
        if (!documentaries.isEmpty()) {
            System.out.println(UIHelper.BOLD + UIHelper.GREEN + "3) DOCUMENTARIES (" + documentaries.size() + ")" + UIHelper.RESET);
            for (int i = 0; i < documentaries.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + documentaries.get(i).getTitle() + 
                                 " (" + documentaries.get(i).getReleaseYear() + ")");
            }
            System.out.println();
        }
        
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }
    
    /**
     * Parses selection (same logic as BrowseMedia)
     */
    private static Media parseSelection(String input, List<Media> mediaList) {
        if (!input.matches("^\\d+\\.\\d+$")) {
            return null;
        }
        
        String[] parts = input.split("\\.");
        
  
        int typeNum = Integer.parseInt(parts[0]);
        int itemNum = Integer.parseInt(parts[1]);
        
        if (typeNum < 1 || typeNum > 3) {
            return null;
        }
        
        List<Media> filteredList = new ArrayList<>();
        MediaTypes targetType = null;
        
        if (typeNum == 1) targetType = MediaTypes.MOVIE;
        else if (typeNum == 2) targetType = MediaTypes.SERIES;
        else if (typeNum == 3) targetType = MediaTypes.DOCUMENTARY;
        
        for (Media media : mediaList) {
            if (media.getMediaType() == targetType) {
                filteredList.add(media);
            }
        }
        
        if (itemNum < 1 || itemNum > filteredList.size()) {
            return null;
        }
        
        return filteredList.get(itemNum - 1);
    }
    
    /**
     * Routes to appropriate deletion handler
     */
    private static boolean handleMediaDeletion(MediaDatabase database, Media media) throws InterruptedException {
        if (media.getMediaType() == MediaTypes.SERIES) {
            return handleSeriesDeletion(database, (Series) media);
        }
        return handleSimpleMediaDeletion(database, media);
    }
    
    /**
     * Handles deletion of simple media (Movies/Documentaries)
     */
    private static boolean handleSimpleMediaDeletion(MediaDatabase database, Media media) throws InterruptedException {
        UIHelper.clearScreen();
        UIHelper.centerText(UIHelper.BOLD + UIHelper.RED + "⚠️ DELETE CONFIRMATION" + UIHelper.RESET);
        System.out.println();
        
        UIHelper.printMediaBox(media);
        
        System.out.println(UIHelper.RED + "Are you sure you want to delete this media? (Y/N)" + UIHelper.RESET);
        System.out.print("Your choice: ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            // Call database method instead of local method
            boolean success = database.removeMedia(media.getTitle());
            if (success) {
                UIHelper.printSuccess("✓ Media deleted successfully!");
            } else {
                UIHelper.printFailure("Failed to delete media.");
            }
            Thread.sleep(2000);
        }
        
        return true; // Continue deleting
    }
    
    /**
     * LEVEL 1: Series deletion menu
     */
    private static boolean handleSeriesDeletion(MediaDatabase database, Series series) throws InterruptedException {
        boolean inSeriesView = true;
        
        while (inSeriesView) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "DELETE FROM: " + series.getTitle() + UIHelper.RESET);
            System.out.println();
            
            UIHelper.printMediaBox(series);
            displaySeasonList(series);
            
            System.out.println(UIHelper.BOLD + UIHelper.RED + "Deletion Options:" + UIHelper.RESET);
            System.out.println("1. Delete entire series");
            System.out.println("2. Select season to manage");
            System.out.println("Q. Go back");
            System.out.println();
            
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equalsIgnoreCase("q")) {
                inSeriesView = false;
            } else if (choice.equals("1")) {
                if (confirmDeletion("series '" + series.getTitle() + "'")) {
                    // Call database method instead of local method
                    boolean success = database.removeMedia(series.getTitle());
                    if (success) {
                        UIHelper.printSuccess("✓ Series deleted successfully!");
                        Thread.sleep(2000);
                        return true;
                    }
                }
            } else if (choice.equals("2")) {
                handleSeasonDeletion(database, series);
            } else {
                UIHelper.printFailure("Invalid choice.");
                Thread.sleep(1500);
            }
        }
        
        return true;
    }
    
    /**
     * LEVEL 2: Season deletion/management
     */
    private static void handleSeasonDeletion(MediaDatabase database, Series series) throws InterruptedException {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "SELECT SEASON - " + series.getTitle() + UIHelper.RESET);
            System.out.println();
            
            displaySeasonList(series);
            
            UIHelper.printInfo("Enter season number to manage, or 'Q' to go back:");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equalsIgnoreCase("q")) {
                return;
            }
            
            if (!isValidNumber(input)) {
                UIHelper.printFailure("Invalid season number.");
                Thread.sleep(1500);
                continue;
            }
            
            int seasonNum = Integer.parseInt(input);
            
            if (seasonNum < 1 || seasonNum > series.getNbOfSeasons()) {
                UIHelper.printFailure("Invalid season number.");
                Thread.sleep(1500);
                continue;
            }
            
            handleSeasonOptions(database, series, seasonNum);
        }
    }
    
    /**
     * LEVEL 2 options: Delete season or manage episodes
     */
    private static void handleSeasonOptions(MediaDatabase database, Series series, int seasonNum) throws InterruptedException {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "SEASON " + seasonNum + " OPTIONS" + UIHelper.RESET);
            System.out.println();
            
            displayEpisodeList(series, seasonNum);
            
            System.out.println(UIHelper.BOLD + UIHelper.RED + "Options:" + UIHelper.RESET);
            System.out.println("1. Delete entire season");
            System.out.println("2. Select episode to delete");
            System.out.println("Q. Go back");
            System.out.println();
            
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equalsIgnoreCase("q")) {
                return;
            } else if (choice.equals("1")) {
                if (confirmDeletion("Season " + seasonNum + " and all its episodes")) {
                    // Call database method instead of local method
                    boolean success = database.removeSeason(series.getTitle(), seasonNum);
                    if (success) {
                        UIHelper.printSuccess("✓ Season deleted successfully!");
                        Thread.sleep(2000);
                        return;
                    }
                }
            } else if (choice.equals("2")) {
                handleEpisodeDeletion(database, series, seasonNum);
                if (series.getEpisodesInSeason(seasonNum) == 0) {
                    return; // Season was auto-deleted
                }
            } else {
                UIHelper.printFailure("Invalid choice.");
                Thread.sleep(1500);
            }
        }
    }
    
    /**
     * LEVEL 3: Episode deletion
     */
    private static void handleEpisodeDeletion(MediaDatabase database, Series series, int seasonNum) throws InterruptedException {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "DELETE EPISODE - Season " + seasonNum + UIHelper.RESET);
            System.out.println();
            
            displayEpisodeList(series, seasonNum);
            
            UIHelper.printInfo("Enter episode number to delete, or 'Q' to go back:");
            System.out.print("Your input: ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equalsIgnoreCase("q")) {
                return;
            }
            
            if (!isValidNumber(input)) {
                UIHelper.printFailure("Invalid episode number.");
                Thread.sleep(1500);
                continue;
            }
            
            int episodeNum = Integer.parseInt(input);
            
            // Call Series method to find episode
            Episode ep = series.findEpisode(seasonNum, episodeNum);
            if (ep == null) {
                UIHelper.printFailure("Episode not found.");
                Thread.sleep(1500);
                continue;
            }
            
            if (confirmDeletion("Episode " + episodeNum + ": " + ep.getTitle())) {
                // Call database method instead of local method
                boolean success = database.removeEpisode(series.getTitle(), seasonNum, episodeNum);
                if (success) {
                    UIHelper.printSuccess("✓ Episode deleted successfully!");
                    Thread.sleep(2000);
                    if (series.getEpisodesInSeason(seasonNum) == 0) {
                        UIHelper.printInfo("Season is now empty and will be removed.");
                        Thread.sleep(2000);
                        return;
                    }
                }
            }
        }
    }
    
    
      // Confirmation dialog
     
    private static boolean confirmDeletion(String item) throws InterruptedException {
        System.out.println();
        System.out.println(UIHelper.RED + "⚠️  WARNING: This action cannot be undone!" + UIHelper.RESET);
        System.out.println(UIHelper.RED + "Are you sure you want to delete " + item + "? (Y/N)" + UIHelper.RESET);
        System.out.print("Confirm: ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }
    
    // Helper methods for display
    private static void displaySeasonList(Series series) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "SEASONS:" + UIHelper.RESET);
        System.out.println();
        
        for (int s = 1; s <= series.getNbOfSeasons(); s++) {
            int episodeCount = series.getEpisodesInSeason(s);
            System.out.println("  Season " + s + " - " + episodeCount + " episodes");
        }
        System.out.println();
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }
    
    private static void displayEpisodeList(Series series, int seasonNum) {
        System.out.println(UIHelper.BOLD + UIHelper.YELLOW + "EPISODES:" + UIHelper.RESET);
        System.out.println();
        
        for (Episode ep : series.getEpisodes()) {
            if (ep.getSeasonNumber() == seasonNum) {
                System.out.println("  " + ep.getEpisodeNumber() + ". " + ep.getTitle());
            }
        }
        System.out.println();
        System.out.println(UIHelper.BOLD + UIHelper.WHITE + "─".repeat(78) + UIHelper.RESET);
    }
    
    private static boolean isValidNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}