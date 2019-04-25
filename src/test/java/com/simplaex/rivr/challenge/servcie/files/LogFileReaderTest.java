package com.simplaex.rivr.challenge.servcie.files;

import com.simplaex.rivr.challenge.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LogFileReaderTest {

    private LogFileReader reader  = new LogFileReader();

    @Test
    void shouldReadProfiles() throws IOException {
        URL resource = getClass().getClassLoader().getResource("profiles/events_20190221_new.log");

        final String[] args = {resource.getPath()};

        final Map<UUID, UserProfile> profiles = reader.read(args);

        assertThat(profiles).hasSize(3);
        assertThat(profiles).hasEntrySatisfying(
                UUID.fromString("3bb87df2-8d9c-4150-b29c-f7c7ce45fdcf"),
                profile -> assertThat(profile.getClicks()).isEqualTo(1)
        );
    }
}