package Service;

public interface Genre {

    // Returns the name used for display
    String getDisplayName();

    // Checks if two genres match
    boolean matches(Genre other);
}


