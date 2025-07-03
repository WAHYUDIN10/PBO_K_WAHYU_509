package pengirimanbarang.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TrackingEvent {
    private LocalDateTime timestamp;
    private String location;
    private String description;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TrackingEvent(LocalDateTime timestamp, String location, String description) {
        this.timestamp = timestamp;
        this.location = location;
        this.description = description;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return timestamp.format(FORMATTER) + " [" + location + "] - " + description;
    }
}