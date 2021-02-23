package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "Trader")
public class TraderDbo {
    @KeyAnnotation(Autoincrement = true)
    @ColumnAnnotation(ColumnName = "Trader_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int traderId;

    @ColumnAnnotation(ColumnName = "Name", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false)
    public String name;

    @ColumnAnnotation(ColumnName = "Url", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String url;

    @ColumnAnnotation(ColumnName = "Active", DataType = ColumnAnnotation.DataTypes.Bit, CanBeNull = false)
    public int active;

    @ColumnAnnotation(ColumnName = "Api", DataType = ColumnAnnotation.DataTypes.Bit, CanBeNull = false)
    public int api;

    @ColumnAnnotation(ColumnName = "Identifier", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String identifier;

    @ColumnAnnotation(ColumnName = "User", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String user;

    @ColumnAnnotation(ColumnName = "Password", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String password;

    @ColumnAnnotation(ColumnName = "Mail", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String mail;
}
