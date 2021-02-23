package com.project.organizer.database.helper;

import com.project.organizer.database.annotations.ColumnAnnotation;

import java.lang.reflect.Field;

public class ParameterBase {
    protected Object value;
    protected ColumnAnnotation column;

    public ParameterBase(Field field, Object value) {
        this.value = value;
        this.column = field.getAnnotation(ColumnAnnotation.class);
    }

    public boolean needParameter() {
        return column.DataType() == ColumnAnnotation.DataTypes.Varchar
                || column.DataType() == ColumnAnnotation.DataTypes.Datetime;
    }

    public SqlParameter getParameter(int index) {
        return new SqlParameter(value, column.DataType(), index);
    }

    public String getSql() {
        return column.ColumnName() + " = " + (needParameter() ? "?" : (value != null ? value.toString() : ""));
    }
}

