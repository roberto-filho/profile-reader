package com.simplaex.rivr.challenge.servcie;

import com.simplaex.rivr.challenge.model.UserProfile;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ExpirationFilter {

    /**
     * Checks if this userProfile should be considered "expired".
     * A UserProfile will be considered expired if:
     * * It is older than 3 months
     * * Is older than one month and has no click events
     * @param userProfile the profile being validated.
     * @return true if it is o be considered expired, false otherwise.
     */
    public boolean expired(Instant today, UserProfile userProfile) {
        // Get 3 months ago date
        final ZoneId utc = ZoneId.of("UTC");
        final ZonedDateTime threeMonthsAgo = ZonedDateTime.ofInstant(today, utc).minusMonths(3);
        final ZonedDateTime oneMonthAgo = ZonedDateTime.ofInstant(today, utc).minusMonths(1);

        if(olderThanThreeMonths(userProfile, threeMonthsAgo)
                || olderThanOneMonthZeroClicks(userProfile, oneMonthAgo)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if a profile is still valid as per {@link #expired(Instant, UserProfile)}.
     * @param today       the date to be considered 'today'.
     * @param userProfile the profile to check.
     * @return true if the profile is still considered valid.
     */
    public boolean notExpired(Instant today, UserProfile userProfile) {
        return !expired(today, userProfile);
    }

    private boolean olderThanOneMonthZeroClicks(UserProfile userProfile, ZonedDateTime oneMonthAgo) {
        return userProfile.getLastActive().isBefore(oneMonthAgo.toInstant())
                && userProfile.getClicks() < 1;
    }

    private boolean olderThanThreeMonths(UserProfile userProfile, ZonedDateTime threeMonthsAgo) {
        return userProfile.getLastActive().isBefore(threeMonthsAgo.toInstant());
    }
}
