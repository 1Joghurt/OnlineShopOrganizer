package com.project.organizer.database.helper;

import com.project.organizer.database.annotations.ColumnAnnotation;

import java.time.LocalDateTime;

public class SqlParameter {
    private ColumnAnnotation.DataTypes type;
    private Object value;
    private int id;

    public SqlParameter(Object value, ColumnAnnotation.DataTypes type, int id) {
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public String getString() {
        if(value == null) {
            return "";
        }

        switch(type) {
            case Datetime:
            case Int:
            case Bit:
            case Varchar:
                return value.toString();
        }

        return "";
    }
}
