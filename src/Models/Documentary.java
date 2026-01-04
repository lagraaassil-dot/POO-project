package Models;

import Service.Documentary_Genres;
import Service.MediaTypes;

public class Documentary extends Media {

    // Main documentary details
    private String topic;
    private boolean isEducational;

    // Creates a new documentary with topic and education flag
    public Documentary(String title, String director, int releaseYear,
                       Documentary_Genres genre, String topic,
                       int duration, boolean isEducational) {
        super(title, director, releaseYear, genre);
        this.topic = topic;
        this.isEducational = isEducational;
        setDurationMinutes(duration);
    }

    // Returns the documentary topic
    public String getTopic() {
        return topic;
    }

    // Updates the documentary topic
    public void setTopic(String topic) {
        this.topic = topic;
    }

    // Checks if the documentary is educational
    public boolean isEducational() {
        return isEducational;
    }

    // Sets whether the documentary is educational
    public void setEducational(boolean educational) {
        isEducational = educational;
    }

    // Returns a readable education value
    public String getEducationalValue() {
        return isEducational ? "Educational" : "Not Educational";
    }

    // Returns the media type as documentary
    @Override
    public MediaTypes getMediaType() {
        return MediaTypes.DOCUMENTARY;
    }

    // Displays documentary information
    @Override
    public String displayInfo() {
        return super.displayInfo() +
               ", Topic: " + topic +
               " | " + getEducationalValue();
    }
}
