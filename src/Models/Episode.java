package Models;

public class Episode {

    // Episode basic information
    private String title;
    private int episodeNumber;
    private int seasonNumber;
    private int duration;
    private double rating;
    private boolean isWatched;

    // Creates a new episode
    public Episode(String title, int episodeNumber,
                   int seasonNumber, int duration) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.duration = duration;
        this.rating = 0.0;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRating(double rating) {
        if (rating < 0) rating = 0;
        if (rating > 5) rating = 5;
        this.rating = rating;
    }

    // Marks the episode as watched
    public void markAsWatched() {
        isWatched = true;
    }

    // Converts rating to star format
    public String getRatingStars() {
        int fullStars = (int) Math.round(rating);
        int emptyStars = 5 - fullStars;

        String stars = Media.YELLOW;
        for (int i = 0; i < fullStars; i++) stars += "★ ";
        for (int i = 0; i < emptyStars; i++) stars += "☆ ";

        stars += Media.RESET + " (" + String.format("%.1f", rating) + ")";
        return stars;
    }

    // Displays episode information
    public String displayEpisodeInfo() {
        return "Episode " + episodeNumber +
               "\n Title: " + title +
               "\n Duration: " + duration + " minutes" +
               (isWatched ? "\n Watched" : "\n Not Watched") +
               "\n Rating: " + getRatingStars();
    }
}
