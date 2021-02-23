package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "ShippingStatus")
public class ShippingStatusDbo {
    @KeyAnnotation(Autoincrement = true)
    @ColumnAnnotation(ColumnName = "ShippingStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int shippingStatusId;

    @ColumnAnnotation(ColumnName = "Name", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false)
    public String name;
}
