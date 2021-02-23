package com.project.organizer.database.dbo;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;

@TableAnnotation(TableName = "ShippingStatusMapping")
public class ShippingStatusMappingDbo {
    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "Shipper_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int shipperId;

    @KeyAnnotation(Autoincrement = false)
    @ColumnAnnotation(ColumnName = "ShippingStatus_id", DataType = ColumnAnnotation.DataTypes.Int, CanBeNull = false)
    public int shippingStatusId;
}
