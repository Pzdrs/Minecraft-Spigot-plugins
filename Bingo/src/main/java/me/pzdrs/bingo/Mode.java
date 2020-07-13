package me.pzdrs.bingo;

import org.apache.commons.lang.WordUtils;

public enum Mode {
    NORMAL,
    FULL_HOUSE;

    public static String toString(Mode mode) {
        return WordUtils.capitalize(mode.toString().toLowerCase().replace("_", " "));
    }
}
