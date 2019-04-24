package com.simplaex.rivr.challenge.util;

public enum FileExtension {
    CSV,
    LOG;

    /**
     * Reads a file name and returns its extension representation.
     * @param arg the filename to be read.
     * @return the FileExtension that represents the file's extension, or null if it is not supported.
     */
    public static FileExtension fromExtension(String arg) {
        final String filename = arg.toUpperCase();

        if (filename.endsWith(".CSV"))
            return CSV;

        if (filename.endsWith(".LOG"))
            return LOG;

        return null;
    }
}
