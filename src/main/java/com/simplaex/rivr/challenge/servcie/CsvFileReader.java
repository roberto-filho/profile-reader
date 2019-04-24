package com.simplaex.rivr.challenge.servcie;

import com.simplaex.rivr.challenge.files.FileReader;
import com.simplaex.rivr.challenge.files.ProfileSaver;
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

public class CsvFileReader implements FileReader {

    public Map<UUID, UserProfile> readProfiles(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(line -> UserProfile.fromCsv(line))
                .collect(toMap(it->it.getId(), Function.identity()));
    }

    public List<Event> readEvents(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(line -> line.split(","))
                .map(items -> new Event(
                        UUID.fromString(items[0]),
                        UUID.fromString(items[1]),
                        Instant.parse(items[2]),
                        EventType.valueOf(items[3])
                )).collect(toList());
    }

    private void addEvent(Map<UUID, UserProfile> profiles, Event event) {
        profiles.getOrDefault(event.getUserId(), new UserProfile(event.getUserId(), event.getTime())).add(event);
    }

    @Override
    public Map<UUID, UserProfile> read(String[] appArguments) throws IOException {
        Map<UUID, UserProfile> profiles = readProfiles(appArguments[0]);

        readEvents(appArguments[1])
                .forEach(event -> addEvent(profiles, event));
        return null;
    }

    @Override
    public void saveProfiles(String[] appArguments, Collection<UserProfile> profiles) throws IOException {
        ProfileSaver.saveProfiles(appArguments[2], profiles);
    }

}
