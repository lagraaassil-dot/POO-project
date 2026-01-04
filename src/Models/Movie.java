package Models;

import Service.MediaTypes;
import Service.Movie_Series_Genres;

public class Movie extends Media {

    // Indicates if the movie was released in theaters
    private boolean theatricalRelease;

    // Creates a new movie with all required details
    public Movie(String title, String director, int releaseYear,
                 Movie_Series_Genres genre, int duration, boolean theatricalRelease) {
        super(title, director, releaseYear, genre);
        setDurationMinutes(duration);
        this.theatricalRelease = theatricalRelease;
    }

    // Returns whether the movie had a theatrical release
    public boolean isTheatricalRelease() {
        return theatricalRelease;
    }

    // Updates the theatrical release status
    public void setTheatricalRelease(boolean theatricalRelease) {
        this.theatricalRelease = theatricalRelease;
    }

    // Returns the media type as movie
    @Override
    public MediaTypes getMediaType() {
        return MediaTypes.MOVIE;
    }

    // Displays movie information with theatrical release details
    @Override
    public String displayInfo() {
        return super.displayInfo() +
               "\n Theatrical Release: " + (theatricalRelease ? "Yes" : "No");
    }

    // Displays movie information with optional rating
    @Override
    public String displayInfo(boolean showRating) {
        return super.displayInfo(showRating) +
               "\n Theatrical Release: " + (theatricalRelease ? "Yes" : "No");
    }
}
