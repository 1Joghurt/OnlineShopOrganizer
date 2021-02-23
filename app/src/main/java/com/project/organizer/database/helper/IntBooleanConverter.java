package com.project.organizer.database.helper;

public class IntBooleanConverter {
    public static int getInt(Boolean value) {
        return value ? 1 : 0;
    }

    public static Boolean getBoolean(int value) {
        return value == 1;
    }
}
