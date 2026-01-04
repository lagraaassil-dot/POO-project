package UI;
import java.util.*;


import Models.Media;
import Models.Series;
import Service.*;

/**
 * Shows:
 * - Total counts by type
 * - Average ratings overall and by type
 * - Watched vs unwatched counts + a progress bar to show it
 * - Top rated media
 * - Genre distribution
 */
public class Statistics {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Main entry point for statistics display
     */
    public static void displayStatistics(MediaDatabase database) throws InterruptedException {
        UIHelper.clearScreen();
        UIHelper.centerText(UIHelper.BOLD + UIHelper.WHITE + "📊 STATISTICS" + UIHelper.RESET);
        System.out.println();
        
        List<Media> allMedia = database.getAllMedia();
        
        if (allMedia.isEmpty()) {
            UIHelper.printInfo("No media in database. Add some media to see statistics!");
            UIHelper.printInfo("Press any key to return...");
            scanner.nextLine();
            return;
        }
        
        // Display all statistics sections
        displayOverallStats(database, allMedia);
        System.out.println();
        
        displayTypeStats(allMedia);
        System.out.println();
        
        displayRatingStats(allMedia);
        System.out.println();
        
        displayWatchedStats(allMedia);
        System.out.println();
        
        displayTopRated(allMedia);
        System.out.println();
        
        UIHelper.printFooter("END OF STATISTICS");
        UIHelper.printInfo("Press any key to return to main menu...");
        scanner.nextLine();
    }
    
    /**
     * Displays overall statistics
     */
    private static void displayOverallStats(MediaDatabase database, List<Media> allMedia) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "═══ OVERALL STATISTICS ═══" + UIHelper.RESET);
        System.out.println();
        
        System.out.println("📦 Total Media Items: " + UIHelper.BOLD + database.getTotalMediaCount() + UIHelper.RESET);
        
        double avgRating = database.getAverageRating();
        System.out.println("⭐ Average Rating: " + UIHelper.BOLD + 
                         String.format("%.2f", avgRating) + "/5.0" + UIHelper.RESET);
        
        // Calculate total duration
        int totalMinutes = 0;
        for (Media media : allMedia) {
            totalMinutes += media.getDurationMinutes();
        }
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        System.out.println("⏱️  Total Duration: " + UIHelper.BOLD + hours + " hours " + minutes + " minutes" + UIHelper.RESET);
    }
    
    /**
     * Displays statistics by media type
     */
    private static void displayTypeStats(List<Media> allMedia) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "═══ BY TYPE ═══" + UIHelper.RESET);
        System.out.println();
        
        int movieCount = 0, seriesCount = 0, docCount = 0;
        int totalEpisodes = 0;
        
        for (Media media : allMedia) {
            if (media.getMediaType() == MediaTypes.MOVIE) {
                movieCount++;
            } else if (media.getMediaType() == MediaTypes.SERIES) {
                seriesCount++;
                totalEpisodes += ((Series) media).getTotalEpisodes();
            } else if (media.getMediaType() == MediaTypes.DOCUMENTARY) {
                docCount++;
            }
        }
        
        System.out.println(UIHelper.YELLOW + "🎬 Movies: " + UIHelper.RESET + movieCount);
        System.out.println(UIHelper.CYAN + "📺 Series: " + UIHelper.RESET + seriesCount + 
                         " (" + totalEpisodes + " episodes total)");
        System.out.println(UIHelper.GREEN + "🎥 Documentaries: " + UIHelper.RESET + docCount);
    }
    
    /**
     * Displays rating statistics by type
     */
    private static void displayRatingStats(List<Media> allMedia) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "═══ RATINGS BY TYPE ═══" + UIHelper.RESET);
        System.out.println();
        
        double movieSum = 0, seriesSum = 0, docSum = 0;
        int movieCount = 0, seriesCount = 0, docCount = 0;
        
        for (Media media : allMedia) {
            if (media.getRating() > 0) {
                if (media.getMediaType() == MediaTypes.MOVIE) {
                    movieSum += media.getRating();
                    movieCount++;
                } else if (media.getMediaType() == MediaTypes.SERIES) {
                    seriesSum += media.getRating();
                    seriesCount++;
                } else if (media.getMediaType() == MediaTypes.DOCUMENTARY) {
                    docSum += media.getRating();
                    docCount++;
                }
            }
        }
        
        System.out.println(UIHelper.YELLOW + "🎬 Movies Average: " + UIHelper.RESET + 
                         (movieCount > 0 ? String.format("%.2f/5.0", movieSum / movieCount) : "No ratings yet"));
        System.out.println(UIHelper.CYAN + "📺 Series Average: " + UIHelper.RESET + 
                         (seriesCount > 0 ? String.format("%.2f/5.0", seriesSum / seriesCount) : "No ratings yet"));
        System.out.println(UIHelper.GREEN + "🎥 Documentaries Average: " + UIHelper.RESET + 
                         (docCount > 0 ? String.format("%.2f/5.0", docSum / docCount) : "No ratings yet"));
    }
    
    /**
     * Displays watched vs unwatched statistics
     */
    private static void displayWatchedStats(List<Media> allMedia) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "═══ WATCH STATUS ═══" + UIHelper.RESET);
        System.out.println();
        
        int watchedCount = 0;
        int unwatchedCount = 0;
        
        for (Media media : allMedia) {
            if (media.isWatched()) {
                watchedCount++;
            } else {
                unwatchedCount++;
            }
        }
        
        double watchedPercent = (watchedCount * 100.0) / allMedia.size();
        
        System.out.println(UIHelper.GREEN + "✓ Watched: " + UIHelper.RESET + watchedCount + 
                         " (" + String.format("%.1f%%", watchedPercent) + ")");
        System.out.println("⏳ Unwatched: " + unwatchedCount + 
                         " (" + String.format("%.1f%%", 100 - watchedPercent) + ")");
        
        // Display progress bar
        displayProgressBar(watchedPercent);
    }
    
    /**
     * Displays a visual progress bar
     */
    //basically compute the percentage of watched and that will be how many filled bars will there be
    private static void displayProgressBar(double percent) {
        int barWidth = 50;
        int filled = (int) (barWidth * percent / 100);
        
        System.out.print("\n[");
        System.out.print(UIHelper.GREEN);
        for (int i = 0; i < filled; i++) {
            System.out.print("█");
        }
        System.out.print(UIHelper.RESET);
        for (int i = filled; i < barWidth; i++) {
            System.out.print("░");
        }
        System.out.println("]");
    }
    
    /**
     * Displays top 5 rated media
     */
    private static void displayTopRated(List<Media> allMedia) {
        System.out.println(UIHelper.BOLD + UIHelper.CYAN + "═══ TOP 5 RATED ═══" + UIHelper.RESET);
        System.out.println();
        
        // Filter media with ratings and sort
        List<Media> ratedMedia = new ArrayList<>();
        for (Media media : allMedia) {
            if (media.getRating() > 0) {
                ratedMedia.add(media);
            }
        }
        
        if (ratedMedia.isEmpty()) {
            System.out.println("No rated media yet. Start rating your media!");
            return;
        }
        
        // Sort by rating (descending)
        sortByRating(ratedMedia);
        
        // Display top 5 (or fewer if less than 5)
        int count = Math.min(5, ratedMedia.size());
        for (int i = 0; i < count; i++) {
            Media media = ratedMedia.get(i);
            String typeIcon = getTypeIcon(media);
            System.out.println((i + 1) + ". " + typeIcon + " " + 
                             UIHelper.BOLD + media.getTitle() + UIHelper.RESET + 
                             " - " + media.getRatingStars());
        }
    }
    
    /**
     * Sorts media by rating (descending) 
     */
    private static void sortByRating(List<Media> mediaList) {
        int n = mediaList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (mediaList.get(j).getRating() < mediaList.get(j + 1).getRating()) {
                    // Swap
                    Media temp = mediaList.get(j);
                    mediaList.set(j, mediaList.get(j + 1));
                    mediaList.set(j + 1, temp);
                }
            }
        }
    }
    
    /**
     * Gets icon based on media type
     */
    private static String getTypeIcon(Media media) {
        if (media.getMediaType() == MediaTypes.MOVIE) {
            return UIHelper.YELLOW + "🎬" + UIHelper.RESET;
        } else if (media.getMediaType() == MediaTypes.SERIES) {
            return UIHelper.CYAN + "📺" + UIHelper.RESET;
        } else {
            return UIHelper.GREEN + "🎥" + UIHelper.RESET;
        }
    }
}