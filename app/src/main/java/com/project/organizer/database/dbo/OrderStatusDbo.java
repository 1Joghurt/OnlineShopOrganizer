package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "OrderStatus")
public class OrderStatusDbo {
    @KeyAnnotation(Autoincrement = true)
    @ColumnAnnotation(ColumnName = "OrderStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int orderStatusId;

    @ColumnAnnotation(ColumnName = "Name", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false, MaxLength = 128)
    public String name;

    @ColumnAnnotation(ColumnName = "IsClosed", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int isClosed;
}
