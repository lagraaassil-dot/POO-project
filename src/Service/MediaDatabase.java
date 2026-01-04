package Service;

import Models.Movie;
import Models.Series;
import Models.Documentary;
import Models.Episode;
import Models.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaDatabase {

    // Stores all media items
    private ArrayList<Media> mediaList;

    // Creates the database and fills it with initial data
    public MediaDatabase() {
        mediaList = new ArrayList<>();
        populateMovies();
        populateDocumentaries();
        populateSeries();
    }

    // Adds initial movies
    private void populateMovies() {
        mediaList.add(new Movie("Inception", "Christopher Nolan", 2010, Movie_Series_Genres.ACTION, 148, true));
        mediaList.add(new Movie("The Dark Knight", "Christopher Nolan", 2008, Movie_Series_Genres.ACTION, 152, true));
        mediaList.add(new Movie("Interstellar", "Christopher Nolan", 2014, Movie_Series_Genres.SCIENCEFICTION, 169, true));
        mediaList.add(new Movie("Pulp Fiction", "Quentin Tarantino", 1994, Movie_Series_Genres.CRIME, 154, true));
        mediaList.add(new Movie("The Matrix", "The Wachowskis", 1999, Movie_Series_Genres.SCIENCEFICTION, 136, true));
        mediaList.add(new Movie("Forrest Gump", "Robert Zemeckis", 1994, Movie_Series_Genres.DRAMA, 142, true));
        mediaList.add(new Movie("The Shawshank Redemption", "Frank Darabont", 1994, Movie_Series_Genres.DRAMA, 142, true));
        mediaList.add(new Movie("Gladiator", "Ridley Scott", 2000, Movie_Series_Genres.ACTION, 155, true));
        mediaList.add(new Movie("The Godfather", "Francis Ford Coppola", 1972, Movie_Series_Genres.CRIME, 175, true));
        mediaList.add(new Movie("The Godfather Part II", "Francis Ford Coppola", 1974, Movie_Series_Genres.CRIME, 202, true));
        mediaList.add(new Movie("Fight Club", "David Fincher", 1999, Movie_Series_Genres.DRAMA, 139, true));
        mediaList.add(new Movie("Titanic", "James Cameron", 1997, Movie_Series_Genres.ROMANCE, 195, true));
        mediaList.add(new Movie("Avatar", "James Cameron", 2009, Movie_Series_Genres.SCIENCEFICTION, 162, true));
        mediaList.add(new Movie("Jurassic Park", "Steven Spielberg", 1993, Movie_Series_Genres.ADVENTURE, 127, true));
        mediaList.add(new Movie("The Lion King", "Roger Allers", 1994, Movie_Series_Genres.ANIMATION, 88, true));
        mediaList.add(new Movie("Star Wars: A New Hope", "George Lucas", 1977, Movie_Series_Genres.SCIENCEFICTION, 121, true));
        mediaList.add(new Movie("The Avengers", "Joss Whedon", 2012, Movie_Series_Genres.ACTION, 143, true));
        mediaList.add(new Movie("Iron Man", "Jon Favreau", 2008, Movie_Series_Genres.ACTION, 126, true));
        mediaList.add(new Movie("Black Panther", "Ryan Coogler", 2018, Movie_Series_Genres.ACTION, 134, true));
        mediaList.add(new Movie("Spider-Man: Into the Spider-Verse", "Bob Persichetti", 2018, Movie_Series_Genres.ANIMATION, 117, true));
    }

    // Adds initial documentaries
    private void populateDocumentaries() {
        mediaList.add(new Documentary("Planet Earth", "Alastair Fothergill", 2006, Documentary_Genres.NATURE, "Wildlife", 550, true));
        mediaList.add(new Documentary("The Last Dance", "Jason Hehir", 2020, Documentary_Genres.SPORTS, "Basketball", 500, false));
        mediaList.add(new Documentary("Cosmos: A Spacetime Odyssey", "Ann Druyan", 2014, Documentary_Genres.SCIENCE, "Space & Science", 600, true));
        mediaList.add(new Documentary("Blackfish", "Gabriela Cowperthwaite", 2013, Documentary_Genres.SCIENCE, "Orcas & Captivity", 83, true));
        mediaList.add(new Documentary("13th", "Ava DuVernay", 2016, Documentary_Genres.HISTORICAL, "Mass Incarceration", 100, true));
        mediaList.add(new Documentary("Making a Murderer", "Laura Ricciardi", 2015, Documentary_Genres.TRUE_CRIME, "Legal System", 600, false));
        mediaList.add(new Documentary("The Social Dilemma", "Jeff Orlowski", 2020, Documentary_Genres.TECHNOLOGY, "Social Media", 94, true));
        mediaList.add(new Documentary("Our Planet", "Alastair Fothergill", 2019, Documentary_Genres.NATURE, "Environment", 320, true));
        mediaList.add(new Documentary("Inside Job", "Charles Ferguson", 2010, Documentary_Genres.HISTORICAL, "2008 Financial Crisis", 109, true));
        mediaList.add(new Documentary("Super Size Me", "Morgan Spurlock", 2004, Documentary_Genres.HEALTH, "Fast Food", 100, false));
        mediaList.add(new Documentary("An Inconvenient Truth", "Davis Guggenheim", 2006, Documentary_Genres.SCIENCE, "Climate Change", 96, true));
        mediaList.add(new Documentary("Jiro Dreams of Sushi", "David Gelb", 2011, Documentary_Genres.BIOGRAPHICAL, "Sushi Craft", 81, false));
        mediaList.add(new Documentary("Food, Inc.", "Robert Kenner", 2008, Documentary_Genres.HEALTH, "Industrial Food", 94, true));
        mediaList.add(new Documentary("The Cove", "Louie Psihoyos", 2009, Documentary_Genres.NATURE, "Dolphin Hunting", 92, true));
        mediaList.add(new Documentary("Icarus", "Bryan Fogel", 2017, Documentary_Genres.SPORTS, "Doping Scandal", 121, false));
        mediaList.add(new Documentary("Won't You Be My Neighbor?", "Morgan Neville", 2018, Documentary_Genres.BIOGRAPHICAL, "Fred Rogers", 94, false));
        mediaList.add(new Documentary("March of the Penguins", "Luc Jacquet", 2005, Documentary_Genres.NATURE, "Penguins", 80, true));
        mediaList.add(new Documentary("The Act of Killing", "Joshua Oppenheimer", 2012, Documentary_Genres.TRUE_CRIME, "Indonesian Massacres", 115, false));
        mediaList.add(new Documentary("Hubble", "Tanya Hamilton", 2010, Documentary_Genres.SCIENCE, "Space Telescope", 78, true));
        mediaList.add(new Documentary("Man on Wire", "James Marsh", 2008, Documentary_Genres.BIOGRAPHICAL, "Philippe Petit", 94, false));
    }

    // Adds initial series with episodes
    private void populateSeries() {
        List<Episode> chernobylEpisodes = new ArrayList<>();
        chernobylEpisodes.add(new Episode("1: 1:23:45", 1, 1, 70));
        chernobylEpisodes.add(new Episode("2: Please Remain Calm", 2, 1, 60));
        chernobylEpisodes.add(new Episode("3: Open Wide, O Earth", 3, 1, 65));
        chernobylEpisodes.add(new Episode("4: The Happiness of All Mankind", 4, 1, 68));
        chernobylEpisodes.add(new Episode("5: Vichnaya Pamyat", 5, 1, 75));
        mediaList.add(new Series("Chernobyl", "Craig Mazin", 2019, Movie_Series_Genres.DRAMA, 1, chernobylEpisodes));

        List<Episode> lokiEpisodes = new ArrayList<>();
        lokiEpisodes.add(new Episode("Glorious Purpose", 1, 1, 45));
        lokiEpisodes.add(new Episode("The Variant", 2, 1, 50));
        lokiEpisodes.add(new Episode("Lamentis", 3, 1, 52));
        lokiEpisodes.add(new Episode("The Nexus Event", 4, 1, 50));
        lokiEpisodes.add(new Episode("Journey into Mystery", 5, 1, 55));
        lokiEpisodes.add(new Episode("For All Time. Always.", 6, 1, 58));
        lokiEpisodes.add(new Episode("Ouroboros", 1, 2, 47));
        lokiEpisodes.add(new Episode("Breaking Brad", 2, 2, 49));
        lokiEpisodes.add(new Episode("1893", 3, 2, 51));
        lokiEpisodes.add(new Episode("Heart of the TVA", 4, 2, 50));
        lokiEpisodes.add(new Episode("Science/Fiction", 5, 2, 53));
        lokiEpisodes.add(new Episode("Glorious Purpose", 6, 2, 55));
        mediaList.add(new Series("Loki", "Michael Waldron", 2021, Movie_Series_Genres.SCIENCEFICTION, 2, lokiEpisodes));

        List<Episode> moonKnightEpisodes = new ArrayList<>();
        moonKnightEpisodes.add(new Episode("The Goldfish Problem", 1, 1, 50));
        moonKnightEpisodes.add(new Episode("Summon the Suit", 2, 1, 48));
        moonKnightEpisodes.add(new Episode("The Friendly Type", 3, 1, 52));
        moonKnightEpisodes.add(new Episode("The Tomb", 4, 1, 53));
        moonKnightEpisodes.add(new Episode("Asylum", 5, 1, 50));
        moonKnightEpisodes.add(new Episode("Gods and Monsters", 6, 1, 52));
        mediaList.add(new Series("Moon Knight", "Jeremy Slater", 2022, Movie_Series_Genres.ACTION, 1, moonKnightEpisodes));
    }

    // Adds a generic media item
    public void addMedia(Media media) {
        mediaList.add(media);
    }

    // Adds a movie
    public void addMovie(String title, String director, int year,
                         Movie_Series_Genres genre, int duration,
                         boolean theatricalRelease) {
        mediaList.add(new Movie(title, director, year, genre, duration, theatricalRelease));
    }

    // Adds a documentary
    public void addDocumentary(String title, String director, int year,
                               Documentary_Genres genre, String topic,
                               int duration, boolean isEducational) {
        mediaList.add(new Documentary(title, director, year, genre, topic, duration, isEducational));
    }

    // Adds an empty series
    public void addSeries(String title, String director, int year,
                          Movie_Series_Genres genre) {
        mediaList.add(new Series(title, director, year, genre, 0, new ArrayList<>()));
    }

    // Adds an episode to a series
    public boolean addEpisodeToSeries(String seriesTitle, String episodeTitle,
                                      int episodeNumber, int seasonNumber,
                                      int duration) {
        for (Media media : mediaList) {
            if (media instanceof Series &&
                media.getTitle().equalsIgnoreCase(seriesTitle)) {
                return ((Series) media).addEpisode(
                        episodeTitle, episodeNumber, seasonNumber, duration);
            }
        }
        return false;
    }

    // Adds a season to a series
    public boolean addSeasonToSeries(String seriesTitle) {
        for (Media media : mediaList) {
            if (media instanceof Series &&
                media.getTitle().equalsIgnoreCase(seriesTitle)) {
                ((Series) media).addSeason();
                return true;
            }
        }
        return false;
    }

    // Removes media by title
    public boolean removeMedia(String title) {
        for (int i = 0; i < mediaList.size(); i++) {
            if (mediaList.get(i).getTitle().equalsIgnoreCase(title)) {
                mediaList.remove(i);
                return true;
            }
        }
        return false;
    }

    // Removes a season from a series
    public boolean removeSeason(String seriesTitle, int seasonNumber) {
        for (Media media : mediaList) {
            if (media instanceof Series && 
                media.getTitle().equalsIgnoreCase(seriesTitle)) {
                return ((Series) media).removeSeason(seasonNumber);
            }
        }
        return false;
    }

    // Removes an episode from a series
    public boolean removeEpisode(String seriesTitle, int seasonNumber, int episodeNumber) {
        for (Media media : mediaList) {
            if (media instanceof Series && 
                media.getTitle().equalsIgnoreCase(seriesTitle)) {
                return ((Series) media).removeEpisode(seasonNumber, episodeNumber);
            }
        }
        return false;
    }

    // Search by type
    public List<Media> searchByType(MediaTypes type) {
        List<Media> results = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.getMediaType() == type) {
                results.add(media);
            }
        }
        return results;
    }

    // Search by genre
    public List<Media> searchByGenre(Genre genre) {
        List<Media> results = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.getGenre() != null &&
                media.getGenre().matches(genre)) {
                results.add(media);
            }
        }
        return results;
    }

    // Search by watched status
    public List<Media> searchByWatchedStatus(boolean isWatched) {
        List<Media> results = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.isWatched() == isWatched) {
                results.add(media);
            }
        }
        return results;
    }

    // Search by title
    public List<Media> searchByTitle(String input, List<Media> filteredMediaList) {
        List<Media> results = new ArrayList<>();
        String query = input.toLowerCase();
        for (Media media : filteredMediaList) {
            if (media.getTitle().toLowerCase().contains(query)) {
                results.add(media);
            }
        }
        return results;
    }

    public List<Media> searchByTitle(String input) {
        return searchByTitle(input, mediaList);
    }

    // Search by director
    public List<Media> searchByDirector(String input, List<Media> filteredMediaList) {
        List<Media> results = new ArrayList<>();
        String query = input.toLowerCase();
        for (Media media : filteredMediaList) {
            if (media.getDirector().toLowerCase().contains(query)) {
                results.add(media);
            }
        }
        return results;
    }

    public List<Media> searchByDirector(String input) {
        return searchByDirector(input, mediaList);
    }

    // Marks media as watched
    public void markAsWatched(String title) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                media.markAsWatched();
                return;
            }
        }
    }

    // Rates a media item
    public void rateMedia(String title, double rating) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title) &&
                media.getMediaType() != MediaTypes.SERIES) {
                media.setRating(rating);
                return;
            }
        }
    }

    // Rates a specific episode
    public void rateEpisode(String seriesTitle, int seasonNumber,
                            int episodeNumber, double rating) {
        for (Media media : mediaList) {
            if (media instanceof Series &&
                media.getTitle().equalsIgnoreCase(seriesTitle)) {
                ((Series) media).rateEpisode(seasonNumber, episodeNumber, rating);
                return;
            }
        }
    }

    // Returns average rating of all rated media
    public double getAverageRating() {
        double total = 0;
        int count = 0;
        for (Media media : mediaList) {
            if (media.getRating() > 0) {
                total += media.getRating();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    // Returns total number of media items
    public int getTotalMediaCount() {
        return mediaList.size();
    }

    // Returns a copy of all media
    public List<Media> getAllMedia() {
        return new ArrayList<>(mediaList);
    }

    // Returns media by title
    public Media getMediaByTitle(String title) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title)) {
                return media;
            }
        }
        return null;
    }

    // Checks if media exists
    public boolean mediaExists(String title) {
        return getMediaByTitle(title) != null;
    }

    // Finds a specific series by title
    public Series getSeriesByTitle(String title) {
        Media media = getMediaByTitle(title);
        if (media instanceof Series) {
            return (Series) media;
        }
        return null;
    }
}