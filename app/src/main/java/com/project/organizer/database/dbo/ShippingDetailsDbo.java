package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "ShippingDetails")
public class ShippingDetailsDbo {
    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "Order_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int orderId;

    @ColumnAnnotation(ColumnName = "Shipper_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int shipperId;

    @ColumnAnnotation(ColumnName = "Trackingnumber", DataType = ColumnAnnotation.DataTypes.Varchar, CanBeNull = false, MaxLength = 128)
    public String trackingnumber;

    @ColumnAnnotation(ColumnName = "ShippingStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = true)
    public int shipperStatusId;
}
