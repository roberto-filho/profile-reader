package com.simplaex.rivr.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Event {

    private final UUID eventId;
    private final UUID userId;
    private final Instant time;
    private final EventType type;
}
