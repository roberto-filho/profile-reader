package com.simplaex.rivr.challenge;

import com.simplaex.rivr.challenge.files.FileReader;
import com.simplaex.rivr.challenge.files.FileReaderFactory;
import com.simplaex.rivr.challenge.model.UserProfile;
import com.simplaex.rivr.challenge.util.FileExtension;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Application {

    public static void main(String[] args) throws IOException {

        // Check the file extension, csv or log

        FileExtension extension = FileExtension.fromExtension(args[0]);

        FileReader reader = FileReaderFactory.fromFileExtension(extension);

        Map<UUID, UserProfile> profiles = reader.read(args);

        reader.saveProfiles(args, profiles.values());
    }

}
