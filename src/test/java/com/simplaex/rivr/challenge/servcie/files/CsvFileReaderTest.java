package com.simplaex.rivr.challenge.servcie.files;

import com.simplaex.rivr.challenge.model.Event;
import com.simplaex.rivr.challenge.model.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CsvFileReaderTest {

    private CsvFileReader reader = new CsvFileReader();

    @Test
    void shouldReadEvents() throws IOException {

        final URL resource = CsvFileReaderTest.class.getClassLoader().getResource("events/events_20190221.csv");
        final List<Event> events = reader.readEvents(resource.getPath());

        assertThat(events).hasSize(5);
        System.out.println(events);
    }

    @Test
    void shouldNotCopyProfilesOlderThanThreeMonths(@TempDir Path tempDir) throws IOException {
        final Instant today = Instant.parse("2019-04-23T00:02:23.803365Z");
        final Path tempFile = tempDir.resolve("test.csv");

        final Map<UUID, UserProfile> profiles = readProfiles("profiles/user_profiles_20190220.csv");

        ProfileSaver.saveProfiles(tempFile.toString(), profiles.values(), today);

        final Map<UUID, UserProfile> savedProfiles = reader.readProfiles(tempFile.toString());

        assertThat(savedProfiles).hasSize(1);
    }

    @Test
    void shouldNotCopyProfilesOlderThanOneMonthWithNoClicks(@TempDir Path tempDir) throws IOException {
        final Instant today = Instant.parse("2019-04-23T20:02:23.803365Z");
        final Path tempFile = tempDir.resolve("test.csv");

        final Map<UUID, UserProfile> profiles = readProfiles("profiles/user_profiles_20190422.csv");

        ProfileSaver.saveProfiles(tempFile.toString(), profiles.values(), today);

        final Map<UUID, UserProfile> savedProfiles = reader.readProfiles(tempFile.toString());

        assertThat(savedProfiles).hasSize(0);
    }

    private Map<UUID, UserProfile> readProfiles(String fileName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return reader.readProfiles(resource.getPath());
    }
}