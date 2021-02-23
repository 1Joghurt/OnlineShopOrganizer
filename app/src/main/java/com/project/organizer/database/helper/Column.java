package com.project.organizer.database.helper;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;

import java.lang.reflect.Field;

public class Column {
    private ColumnAnnotation column;
    private KeyAnnotation key;
    private Field field;

    public Column(ColumnAnnotation column, KeyAnnotation key, Field field) {
        this.column = column;
        this.key = key;
        this.field = field;
    }

    public ColumnAnnotation getColumn() {
        return column;
    }

    public KeyAnnotation getKey() {
        return key;
    }

    public Field getField() {
        return field;
    }
}
