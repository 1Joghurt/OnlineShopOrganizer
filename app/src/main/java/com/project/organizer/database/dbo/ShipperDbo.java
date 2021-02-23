package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "Shipper")
public class ShipperDbo {
    @KeyAnnotation(Autoincrement = true)
    @ColumnAnnotation(ColumnName = "Shipper_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int shipperId;

    @ColumnAnnotation(ColumnName = "Name", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false, MaxLength = 128)
    public String name;

    @ColumnAnnotation(ColumnName = "Url", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false, MaxLength = 128)
    public String url;

    @ColumnAnnotation(ColumnName = "Active", DataType = ColumnAnnotation.DataTypes.Bit, CanBeNull = false)
    public int active;

    @ColumnAnnotation(ColumnName = "Api", DataType = ColumnAnnotation.DataTypes.Bit, CanBeNull = false)
    public int api;

    @ColumnAnnotation(ColumnName = "Identifier", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true, MaxLength = 128)
    public String identifier;
}
