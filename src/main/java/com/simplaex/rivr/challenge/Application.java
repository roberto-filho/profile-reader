package com.simplaex.rivr.challenge;

import com.simplaex.rivr.challenge.model.Event;
import com.simplaex.rivr.challenge.model.UserProfile;
import com.simplaex.rivr.challenge.servcie.FileReader;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Application {

    public static void main(String[] args) throws IOException {

        Map<UUID, UserProfile> profiles = FileReader.readProfiles(args[0]);

        FileReader.readEvents(args[1])
                .forEach(event -> addEvent(profiles, event));

        FileReader.saveProfiles(args[2], profiles.values());
    }

    private static void addEvent(Map<UUID, UserProfile> profiles, Event event) {
        profiles.getOrDefault(event.getUserId(), new UserProfile(event.getUserId(), event.getTime())).add(event);
    }
}
