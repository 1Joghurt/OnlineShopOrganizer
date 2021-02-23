package com.project.organizer.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnAnnotation {

    public String ColumnName();

    public DataTypes DataType();

    public boolean CanBeNull() default false;

    public int MaxLength() default -1;

    public enum DataTypes {
        Varchar,
        Int,
        Datetime,
        Bit
    }
}
