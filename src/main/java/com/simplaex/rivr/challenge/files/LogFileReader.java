package com.simplaex.rivr.challenge.files;

import com.simplaex.rivr.challenge.model.UserProfile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class LogFileReader implements FileReader {
    @Override
    public Map<UUID, UserProfile> read(String[] appArguments) {
        return null;
    }

    @Override
    public void saveProfiles(String[] appArguments, Collection<UserProfile> profiles) throws IOException {
        ProfileSaver.saveProfiles(appArguments[1], profiles);
    }
}
