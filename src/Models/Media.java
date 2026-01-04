package Models;

import Service.MediaTypes;
import Service.Genre;

public abstract class Media {

    // ANSI color codes for rating display
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";

    // Basic media information
    private String title;
    private String director;
    private int releaseYear;
    private Genre genre;
    private double rating;
    private boolean isWatched;
    private int durationMinutes;

    // Constructor without genre
    public Media(String title, String director, int releaseYear) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = null;
        this.rating = 0.0;
        this.isWatched = false;
        this.durationMinutes = 0;
    }

    // Constructor with genre
    public Media(String title, String director, int releaseYear, Genre genre) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = 0.0;
        this.isWatched = false;
        this.durationMinutes = 0;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear > 1800) {
            this.releaseYear = releaseYear;
        }
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setRating(double rating) {
        if (rating < 0) rating = 0;
        if (rating > 5) rating = 5;
        this.rating = rating;
    }

    // Marks the media as watched
    public void markAsWatched() {
        this.isWatched = true;
    }

    // Converts rating into stars
    public String getRatingStars() {
        int fullStars = (int) Math.round(rating);
        int emptyStars = 5 - fullStars;

        String stars = YELLOW;
        for (int i = 0; i < fullStars; i++) stars += "★ ";
        for (int i = 0; i < emptyStars; i++) stars += "☆ ";

        stars += RESET + " (" + String.format("%.1f", rating) + ")";
        return stars;
    }

    // Displays full media information
    public String displayInfo() {
        return "Title: " + title +
               "\n Director: " + director +
               "\n Release Year: " + releaseYear +
               "\n Genre: " + genre +
               "\n Rating: " + getRatingStars() +
               "\n Duration: " + durationMinutes + " minutes";
    }

    // Displays media information without rating if needed
    public String displayInfo(boolean showRating) {
        if (showRating) {
            return displayInfo();
        }
        return "Title: " + title +
               ", Director: " + director +
               ", Release Year: " + releaseYear +
               ", Genre: " + genre +
               ", Duration: " + durationMinutes + " minutes";
    }

    // Returns the specific media type
    public abstract MediaTypes getMediaType();
}
