package Service;

public enum Documentary_Genres implements Genre {

    NATURE,
    SCIENCE,
    TRUE_CRIME,
    HISTORICAL,
    BIOGRAPHICAL,
    SPORTS,
    HEALTH,
    TECHNOLOGY,
    UNKNOWN;

    // Returns a readable genre name
    @Override
    public String getDisplayName() {
        return this.name().toLowerCase();
    }

    // Checks if two documentary genres match
    @Override
    public boolean matches(Genre other) {
        if (!(other instanceof Documentary_Genres)) {
            return false;
        }
        return name().contains(other.toString());
    }
}
