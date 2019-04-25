package com.simplaex.rivr.challenge.servcie.files.log;

import com.simplaex.rivr.challenge.model.Event;
import com.simplaex.rivr.challenge.model.EventType;
import com.simplaex.rivr.challenge.model.UserProfile;
import com.simplaex.rivr.challenge.servcie.files.LogFileReader;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Reads a .log file line by line.
 */
public class LineReader {

    private static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private List<UserProfile> profiles = new ArrayList<>();
    private UserProfile current;

    public void readLine(String line) {
        // Sanity checks
        if (current == null && !line.matches(UUID_REGEX)) {
            throw new RuntimeException(String.format("Log file should start with an UUID but got [%s]", line));
        }

        // Check if its UUID
        if (line.matches(UUID_REGEX)) {
            handleProfile(line);
        } else {
            // It's an event type
            handleEvent(line);
        }
    }

    private void handleEvent(String line) {
        // Format is: instant,event_type
        final String[] eventStrings = line.split(",");

        final Event event = createEventFromLine(eventStrings);

        current.add(event);
    }

    private Event createEventFromLine(String[] eventStrings) {
        final Instant when = Instant.parse(eventStrings[0]);
        final EventType eventType = EventType.valueOf(eventStrings[1]);

        return new Event(UUID.randomUUID(), current.getId(), when, eventType);
    }

    private void handleProfile(String line) {
        // Read the user profile
        final UserProfile profile = new UserProfile(UUID.fromString(line));
        profiles.add(profile);

        // Use as current... events will be added to it
        current = profile;
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }
}
