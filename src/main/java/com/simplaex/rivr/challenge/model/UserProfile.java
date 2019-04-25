package com.simplaex.rivr.challenge.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserProfile {

    private final UUID id;
    private Instant lastActive;
    private long impressions = 0;
    private long clicks = 0;
    private long conversions = 0;

    public UserProfile(UUID id) {
        this.id = id;
    }

    public UserProfile(UUID id, Instant lastActive) {
        this.id = id;
        this.lastActive = lastActive;
    }

    public UserProfile(UUID id, Instant lastActive, long clicks) {
        this.id = id;
        this.lastActive = lastActive;
        this.clicks = clicks;
    }

    public String toCsv() {
        return id + "," + lastActive + "," + impressions + "," + clicks + "," + conversions;
    }

    public static UserProfile fromCsv(String csv) {
        String[] split = csv.split(",");
        UserProfile profile = new UserProfile(
                UUID.fromString(split[0]),
                Instant.parse(split[1])
        );
        profile.setClicks(Long.parseLong(split[2]));
        profile.setImpressions(Long.parseLong(split[3]));
        profile.setConversions(Long.parseLong(split[4]));
        return profile;
    }

    public void add(Event event) {
        switch (event.getType()) {
            case IMPRESSION: impressions++; break;
            case CLICK:      clicks++;      break;
            case CONVERSION: conversions++; break;
        }
        lastActive = max(lastActive, event.getTime());
    }

    private Instant max(Instant instant1, Instant instant2) {
        if (instant1 == null) {
            return instant2;
        }
        return instant1.isAfter(instant2) ? instant1 : instant2;
    }
}
