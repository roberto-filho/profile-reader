package com.simplaex.rivr.challenge.servcie;

import com.simplaex.rivr.challenge.model.Event;
import com.simplaex.rivr.challenge.model.EventType;
import com.simplaex.rivr.challenge.model.UserProfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class FileReader {

    public static Map<UUID, UserProfile> readProfiles(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(line -> UserProfile.fromCsv(line))
                .collect(toMap(it->it.getId(), Function.identity()));
    }

    public static void saveProfiles(String path, Collection<UserProfile> profiles) throws IOException {
        saveProfiles(path, profiles, Instant.now());
    }

    public static void saveProfiles(String path, Collection<UserProfile> profiles, Instant currentDate) throws IOException {
        final ExpirationFilter expirationFilter = new ExpirationFilter();

        final List<String> profilesToSave = profiles.stream()
                .filter(userProfile -> expirationFilter.notExpired(currentDate, userProfile))
                .map(UserProfile::toCsv)
                .collect(toList());

        Files.write(Paths.get(path), profilesToSave);
    }

    public static List<Event> readEvents(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(line -> line.split(","))
                .map(items -> new Event(
                        UUID.fromString(items[0]),
                        UUID.fromString(items[1]),
                        Instant.parse(items[2]),
                        EventType.valueOf(items[3])
                )).collect(toList());
    }
}
