package com.simplaex.rivr.challenge.servcie.files;

import com.simplaex.rivr.challenge.model.UserProfile;
import com.simplaex.rivr.challenge.servcie.ExpirationFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ProfileSaver {

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
}
