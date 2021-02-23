package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "[Order]")
public class OrderDbo {
    @KeyAnnotation(Autoincrement = true)
    @ColumnAnnotation(ColumnName = "Order_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int orderId;

    @ColumnAnnotation(ColumnName = "Date", DataType = ColumnAnnotation.DataTypes.Datetime, CanBeNull = false)
    public String date;

    @ColumnAnnotation(ColumnName = "Note", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = true)
    public String note;

    @ColumnAnnotation(ColumnName = "IsClosed", DataType = ColumnAnnotation.DataTypes.Bit, CanBeNull = false)
    public int isClosed;
}
