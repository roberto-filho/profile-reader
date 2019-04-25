package com.simplaex.rivr.challenge.servcie.files;

import com.simplaex.rivr.challenge.model.UserProfile;
import com.simplaex.rivr.challenge.servcie.files.log.LineReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class LogFileReader implements FileReader {

    public Map<UUID, UserProfile> readProfiles(String path) throws IOException {

        final LineReader lineReader = new LineReader();

        // Let's not leak this shall we
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach(lineReader::readLine);
        }

        return lineReader.getProfiles()
                .stream()
                .collect(toMap(it->it.getId(), Function.identity()));
    }

    @Override
    public Map<UUID, UserProfile> read(String[] appArguments) throws IOException {
        return readProfiles(appArguments[0]);
    }

    @Override
    public void saveProfiles(String[] appArguments, Collection<UserProfile> profiles) throws IOException {
        ProfileSaver.saveProfiles(appArguments[1], profiles);
    }
}
