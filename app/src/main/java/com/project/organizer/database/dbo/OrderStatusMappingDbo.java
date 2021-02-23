package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "OrderStatusMapping")
public class OrderStatusMappingDbo {
    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "Trader_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int traderId;

    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "OrderStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int orderStatusId;
}
