package com.project.organizer.database.helper;

import java.lang.reflect.Field;

public class InsertParameter extends ParameterBase {
    public InsertParameter(Field field, Object value) {
        super(field, value);
    }

    @Override
    public String getSql() {
        return needParameter() ? "?" : (value != null ? value.toString() : "");
    }
}
