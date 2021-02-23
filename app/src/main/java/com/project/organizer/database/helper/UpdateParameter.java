package com.project.organizer.database.helper;

import java.lang.reflect.Field;

public class UpdateParameter extends ParameterBase {
    public UpdateParameter(Field field, Object value) {
        super(field, value);
    }
}
