package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "TraderDetails")
public class TraderDetailsDbo {
    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "Order_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int orderId;

    @ColumnAnnotation(ColumnName = "Trader_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int traderId;

    @ColumnAnnotation(ColumnName = "Ordernumber", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false, MaxLength = 128)
    public String ordernumber;

    @ColumnAnnotation(ColumnName = "OrderStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = true)
    public int orderStatusId;
}

