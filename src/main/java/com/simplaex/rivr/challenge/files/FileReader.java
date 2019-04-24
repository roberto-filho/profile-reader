package com.simplaex.rivr.challenge.files;

import com.simplaex.rivr.challenge.model.UserProfile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface FileReader {

    Map<UUID, UserProfile> read(String[] appArguments) throws IOException;

    void saveProfiles(String[] appArguments, Collection<UserProfile> values) throws IOException;
}
