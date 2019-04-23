package com.simplaex.rivr.challenge.servcie;

import com.simplaex.rivr.challenge.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpirationFilterTest {

    private ExpirationFilter filter = new ExpirationFilter();

    @Test
    void shouldFilterProfilesOlderThan3Months() {
        final ZonedDateTime now = ZonedDateTime.now();

        final Instant threeMonthsAgo = now
                .minusMonths(3)
                .minusNanos(1)
                .toInstant();

        final UserProfile userProfile = new UserProfile(UUID.randomUUID(), threeMonthsAgo);

        assertEquals(Boolean.TRUE, filter.expired(now.toInstant(), userProfile));
    }

    @Test
    void shouldNotFilterProfilesNewerThan3Months() {
        final ZonedDateTime now = ZonedDateTime.now();

        final Instant twoDaysAgo = now
                .minusDays(2)
                .toInstant();

        final UserProfile userProfile = new UserProfile(UUID.randomUUID(), twoDaysAgo);

        assertEquals(Boolean.FALSE, filter.expired(Instant.from(now), userProfile));
    }

    @Test
    void shouldNotFilterOlderThanOneMonthWithClicks() {
        final ZonedDateTime now = ZonedDateTime.now();

        final Instant twoMonthsAgo = now
                .minusMonths(2)
                .toInstant();

        final UserProfile userProfile = new UserProfile(UUID.randomUUID(), twoMonthsAgo, 4);

        assertEquals(Boolean.FALSE, filter.expired(now.toInstant(), userProfile));
    }
}