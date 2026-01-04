package Models;

import java.util.List;
import Service.MediaTypes;
import Service.Movie_Series_Genres;

public class Series extends Media {

    // Series main data
    private int nbOfSeasons;
    private List<Episode> episodes;

    // Creates a new series with seasons and episodes
    public Series(String title, String director, int releaseYear,
                  Movie_Series_Genres genre, int nbOfSeasons,
                  List<Episode> episodes) {
        super(title, director, releaseYear, genre);
        this.nbOfSeasons = nbOfSeasons;
        this.episodes = episodes;
        updateDuration();
        setRating(getAverageRatingOfSeries());
    }

    // Basic getters
    public int getNbOfSeasons() {
        return nbOfSeasons;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public int getTotalEpisodes() {
        return episodes.size();
    }

    // Returns number of episodes in a season
    public int getEpisodesInSeason(int seasonNumber) {
        int count = 0;
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber) {
                count++;
            }
        }
        return count;
    }

    // Finds a specific episode in a season
    public Episode findEpisode(int seasonNumber, int episodeNumber) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber && 
                ep.getEpisodeNumber() == episodeNumber) {
                return ep;
            }
        }
        return null;
    }

    // Gets total duration of a specific season
    public int getSeasonDuration(int seasonNumber) {
        int total = 0;
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber) {
                total += ep.getDuration();
            }
        }
        return total;
    }

    // Basic setters
    public void setNbOfSeasons(int nbOfSeasons) {
        this.nbOfSeasons = nbOfSeasons;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
        updateDuration();
    }

    // Updates total duration from episodes
    private void updateDuration() {
        int totalDuration = 0;
        if (episodes != null) {
            for (Episode ep : episodes) {
                totalDuration += ep.getDuration();
            }
        }
        setDurationMinutes(totalDuration);
    }

    // Season management
    public boolean addSeason() {
        nbOfSeasons++;
        return true;
    }

    private void shiftSeasonsDown(int removedSeason) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() > removedSeason) {
                ep.setSeasonNumber(ep.getSeasonNumber() - 1);
            }
        }
    }

    public boolean removeSeason(int seasonNumber) {
        if (seasonNumber < 1 || seasonNumber > nbOfSeasons) {
            return false;
        }

        episodes.removeIf(ep -> ep.getSeasonNumber() == seasonNumber);
        nbOfSeasons--;
        shiftSeasonsDown(seasonNumber);
        updateDuration();

        return true;
    }

    // Episode management
    public boolean addEpisode(String title,
                              int episodeNumber,
                              int seasonNumber,
                              int duration) {

        if (seasonNumber < 1 || episodeNumber < 1 || duration <= 0) {
            return false;
        }

        if (seasonNumber > nbOfSeasons + 1) {
            return false;
        }

        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                ep.getEpisodeNumber() == episodeNumber) {
                return false;
            }
        }

        episodes.add(new Episode(title, episodeNumber, seasonNumber, duration));

        if (seasonNumber == nbOfSeasons + 1) {
            nbOfSeasons++;
        }

        updateDuration();
        return true;
    }

    public boolean removeEpisode(int seasonNumber, int episodeNumber) {
        Episode toRemove = null;

        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                ep.getEpisodeNumber() == episodeNumber) {
                toRemove = ep;
                break;
            }
        }

        if (toRemove == null) {
            return false;
        }

        episodes.remove(toRemove);

        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                ep.getEpisodeNumber() > episodeNumber) {
                ep.setEpisodeNumber(ep.getEpisodeNumber() - 1);
            }
        }

        boolean seasonStillHasEpisodes = false;
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber) {
                seasonStillHasEpisodes = true;
                break;
            }
        }

        if (!seasonStillHasEpisodes) {
            nbOfSeasons--;
            shiftSeasonsDown(seasonNumber);
        }

        updateDuration();
        return true;
    }

    // Checks if an episode title already exists in a season
    public boolean episodeTitleExists(int seasonNumber, String episodeTitle) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                ep.getTitle().equalsIgnoreCase(episodeTitle)) {
                return true;
            }
        }
        return false;
    }

    // Duration override
    @Override
    public int getDurationMinutes() {
        return super.getDurationMinutes();
    }

    // Rating calculations
    public double getAverageRatingOfSeason(int seasonNumber) {
        double totalRating = 0.0;
        int count = 0;

        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber) {
                totalRating += ep.getRating();
                count++;
            }
        }
        return count > 0 ? totalRating / count : 0.0;
    }

    public String getAverageRatingStarsOfSeason(int seasonNumber) {
        double avgRating = getAverageRatingOfSeason(seasonNumber);
        int fullStars = (int) Math.round(avgRating);
        int emptyStars = 5 - fullStars;

        String stars = "";
        for (int i = 0; i < fullStars; i++) stars += "★";
        for (int i = 0; i < emptyStars; i++) stars += "☆";

        stars += " (" + String.format("%.1f", avgRating) + ")";
        return stars;
    }

    public double getAverageRatingOfSeries() {
        double totalRating = 0.0;
        int count = 0;

        for (Episode ep : episodes) {
            totalRating += ep.getRating();
            count++;
        }
        return count > 0 ? totalRating / count : 0.0;
    }

    // Rates a specific episode
    public boolean rateEpisode(int seasonNumber, int episodeNumber, double rating) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                ep.getEpisodeNumber() == episodeNumber) {
                ep.setRating(rating);
                setRating(getAverageRatingOfSeries());
                return true;
            }
        }
        return false;
    }

    // Recalculates series rating
    public void recalcRating() {
        setRating(getAverageRatingOfSeries());
    }

    // Watch management
    @Override
    public void markAsWatched() {
        for (Episode ep : episodes) {
            ep.markAsWatched();
        }
        super.markAsWatched();
    }

    public boolean markEpisodeAsWatched(int season, int episodeNumber) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == season &&
                ep.getEpisodeNumber() == episodeNumber) {

                ep.markAsWatched();

                if (isFullyWatched()) {
                    super.markAsWatched();
                }
                return true;
            }
        }
        return false;
    }

    public boolean isFullyWatched() {
        for (Episode ep : episodes) {
            if (!ep.isWatched()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSeasonWatched(int seasonNumber) {
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber &&
                !ep.isWatched()) {
                return false;
            }
        }
        return true;
    }

    // Returns the media type as series
    @Override
    public MediaTypes getMediaType() {
        return MediaTypes.SERIES;
    }

    // Display methods
    public String displaySeasons(boolean showEpisodes) {
        String seasonsList = "Seasons:\n";

        for (int s = 1; s <= nbOfSeasons; s++) {
            seasonsList += "- Season " + s + ": ";
            String eps = displayEpisodesOfSeason(s);

            if (eps.isEmpty()) {
                seasonsList += " (no episodes yet)\n";
            } else {
                seasonsList += "\n" + eps;
            }

            seasonsList += "-Season rating: " +
                           getAverageRatingStarsOfSeason(s) + "\n";
        }
        return seasonsList;
    }

    public String displayEpisodesOfSeason(int seasonNumber) {
        String episodesList = "";
        for (Episode ep : episodes) {
            if (ep.getSeasonNumber() == seasonNumber) {
                episodesList += ep.displayEpisodeInfo() + "\n";
            }
        }
        return episodesList;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
               "\n Seasons: " + nbOfSeasons +
               "\n Total Episodes: " + getTotalEpisodes();
    }

    @Override
    public String displayInfo(boolean showRating) {
        return super.displayInfo(showRating) +
               "\n Seasons: " + nbOfSeasons +
               "\n Total Episodes: " + getTotalEpisodes();
    }

    public String displaySeriesInfo() {
        return displayInfo() +
               "\n" + displaySeasons(true);
    }

    public String displaySeriesInfo(boolean showTotalEpisodes) {
        if (showTotalEpisodes) {
            return displaySeriesInfo();
        }
        return displayInfo() +
               "\n" + displaySeasons(showTotalEpisodes);
    }
}