package com.simplaex.rivr.challenge.servcie.files.log;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class LineReaderTest {

    final LineReader reader = new LineReader();

    @Test
    void shouldReadUserProfileWhenWithOneLine() {
        final String line = UUID.randomUUID().toString();

        reader.readLine(line);

        assertThat(reader.getProfiles()).hasSize(1);
    }

    @Test
    void shouldThrowWhenFirstLineNotUUID() {
        assertThatThrownBy(() -> reader.readLine("2019-02-21T16:38:03Z,IMPRESSION"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldReadEventAfterReadingProfile() {
        final String uuid = UUID.randomUUID().toString();

        reader.readLine(uuid);
        reader.readLine("2019-02-21T16:38:03Z,IMPRESSION");

        assertThat(reader.getProfiles())
                .element(0)
                .hasFieldOrPropertyWithValue("impressions", 1L);
    }

    @Test
    void shouldReadMultipleUserProfiles() {
        final String uuid = UUID.randomUUID().toString();
        final String uuid2 = UUID.randomUUID().toString();

        reader.readLine(uuid);
        reader.readLine("2019-02-21T16:38:03Z,IMPRESSION");
        reader.readLine(uuid2);

        assertThat(reader.getProfiles()).hasSize(2);
    }
}