package Service;

public enum Movie_Series_Genres implements Genre {

    ACTION,
    COMEDY,
    DRAMA,
    HORROR,
    ROMANCE,
    SCIENCEFICTION,
    THRILLER,
    ANIMATION,
    ADVENTURE,
    CRIME,
    UNKNOWN;

    // Returns a readable genre name
    @Override
    public String getDisplayName() {
        return this.name().replace('_', ' ').toLowerCase();
    }

    // Checks if two movie or series genres match
    @Override
    public boolean matches(Genre other) {
        if (!(other instanceof Movie_Series_Genres)) {
            return false;
        }
        return name().contains(other.toString());
    }
}
