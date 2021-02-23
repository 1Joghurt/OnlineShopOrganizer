package com.project.organizer.database.helper;

import java.lang.reflect.Field;

public class WhereParameter extends ParameterBase {
    public WhereParameter(Field field, Object value) {
        super(field, value);
    }
}

