package com.project.organizer.database.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.organizer.database.annotations.ColumnAnnotation;
import com.project.organizer.database.annotations.KeyAnnotation;
import com.project.organizer.database.annotations.TableAnnotation;
import com.project.organizer.database.helper.Column;
import com.project.organizer.database.helper.DatabaseManager;
import com.project.organizer.database.helper.DatetimeConverter;
import com.project.organizer.database.helper.InsertParameter;
import com.project.organizer.database.helper.SqlParameter;
import com.project.organizer.database.helper.UpdateParameter;
import com.project.organizer.database.helper.WhereParameter;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class ManagerBase<TDbo> {

    protected abstract TDbo createNew();

    protected TDbo getFirst(List<TDbo> dbos) throws Exception {
        return dbos.size() > 0 ? dbos.get(0) : null;
    }

    public List<TDbo> getAll() throws Exception {
        List<TDbo> result = new ArrayList<TDbo>();

        Class<? extends Object> klasse = createNew().getClass();
        TableAnnotation tableAnnotation = getTable(klasse);
        String sql = "SELECT * FROM " + tableAnnotation.TableName();

        Cursor cursor = executeQuery(sql);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                TDbo newItem = createNew();

                for(Field field : klasse.getDeclaredFields()) {
                    setField(newItem, cursor, field);
                }

                result.add(newItem);
            }
        }

        return result;
    }

    public List<TDbo> getBy(WhereParameter whereParameter) throws IllegalAccessException {
        List<WhereParameter> list = new ArrayList<WhereParameter>();
        list.add(whereParameter);
        return getBy(list);
    }

    public List<TDbo> getBy(List<WhereParameter> whereParameters) throws IllegalAccessException {
        List<TDbo> result = new ArrayList<TDbo>();

        Class<? extends Object> klasse = createNew().getClass();
        TableAnnotation tableAnnotation = getTable(klasse);

        String sql = "SELECT * FROM " + tableAnnotation.TableName();
        List<SqlParameter> parameter = new ArrayList<SqlParameter>();
        boolean firstItem = true;
        for(WhereParameter whereParameter : whereParameters) {
            if(firstItem) {
                sql += " WHERE ";
                firstItem = false;
            }
            else {
                sql += " AND ";
            }

            sql += whereParameter.getSql();

            if(whereParameter.needParameter()) {
                parameter.add(whereParameter.getParameter(parameter.size() + 1));
            }
        }

        SQLiteDatabase database = DatabaseManager.getDatabase();
        System.out.println(sql);
        String[] args = new String[parameter.size()];

        for(int i = 0; i < args.length; i++) {
            args[i] = parameter.get(i).getString();
        }

        Cursor cursor = database.rawQuery(sql, args);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                TDbo newItem = createNew();

                for(Field field : klasse.getDeclaredFields()) {
                    setField(newItem, cursor, field);
                }

                result.add(newItem);
            }
        }

        return result;
    }

    public int create(TDbo dbo) throws Exception {
        Class<? extends Object> klasse = createNew().getClass();

        List<Column> columns = getColumns(klasse);
        TableAnnotation tableAnnotation = getTable(klasse);

        String insertString = "INSERT INTO " + tableAnnotation.TableName() + " (";
        String valueString = "";

        List<SqlParameter> parameter = new ArrayList<SqlParameter>();

        boolean firstItem = true;
        for(Column column : columns) {
            if(column.getKey() != null && column.getKey().Autoincrement()) {
                continue;
            }

            if(firstItem) {
                firstItem = false;
            }
            else {
                insertString += ", ";
                valueString += ", ";
            }

            insertString += column.getColumn().ColumnName();
            InsertParameter insertParameter = new InsertParameter(column.getField(), column.getField().get(dbo));
            valueString += insertParameter.getSql();

            if(insertParameter.needParameter()) {
                parameter.add(insertParameter.getParameter(parameter.size() + 1));
            }
        }

        SQLiteDatabase database = DatabaseManager.getDatabase();
        String sql = insertString + ") VALUES (" + valueString + ")";
        System.out.println(sql);

        String[] args = new String[parameter.size()];

        for(int i = 0; i < args.length; i++) {
            args[i] = parameter.get(i).getString();
        }

        Cursor cursor = database.rawQuery(sql, args);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        }

        String getIdSql = "SELECT last_insert_rowid()";
        Cursor cursorId = database.rawQuery(getIdSql, new String[0]);
        if(cursorId != null) {
            while(cursorId.moveToNext()) {
                return cursorId.getInt(0);
            }
        }

        return 0;
    }

    public void delete(WhereParameter whereParameter) throws Exception {
        List<WhereParameter> list = new ArrayList<WhereParameter>();
        list.add(whereParameter);
        this.delete(list);
    }

    public void delete(List<WhereParameter> whereParameters) throws Exception {
        Class<? extends Object> klasse = createNew().getClass();
        List<Column> columns = getColumns(klasse);
        TableAnnotation tableAnnotation = getTable(klasse);
        String sql = "DELETE FROM " + tableAnnotation.TableName() + " WHERE ";
        List<SqlParameter> parameter = new ArrayList<SqlParameter>();
        boolean firstItem = true;

        for(WhereParameter whereParameter : whereParameters) {
            if(firstItem) {
                firstItem = false;
            }
            else {
                sql += " AND ";
            }

            sql += whereParameter.getSql();

            if(whereParameter.needParameter()) {
                parameter.add(whereParameter.getParameter(parameter.size() + 1));
            }
        }

        String[] args = new String[parameter.size()];

        for(int i = 0; i < args.length; i++) {
            args[i] = parameter.get(i).getString();
        }

        execute(sql, args);
    }

    public void delete(TDbo dbo) throws Exception {
        Class<? extends Object> klasse = createNew().getClass();
        List<Column> columns = getColumns(klasse);
        TableAnnotation tableAnnotation = getTable(klasse);
        List<Column> keyColumns = getKeyColumns(columns);

        if(keyColumns.size() > 0) {
            String sql = "DELETE FROM " + tableAnnotation.TableName() + " WHERE ";

            boolean firstItem = true;
            for(Column column : keyColumns) {
                if(firstItem) {
                    firstItem = false;
                }
                else {
                    sql += " AND ";
                }

                sql += tableAnnotation.TableName() + "." + column.getColumn().ColumnName() + " = " + column.getField().get(dbo);
            }

            execute(sql);
        }
    }

    public void update(TDbo dbo, UpdateParameter updateParameter) throws Exception {
        List<UpdateParameter> updateParameters = new ArrayList<UpdateParameter>();
        updateParameters.add(updateParameter);
        this.update(dbo, updateParameters);
    }

    public void update(TDbo dbo, List<UpdateParameter> updateParameters) throws Exception {
        Class<? extends Object> klasse = dbo.getClass();
        List<Column> columns = getColumns(klasse);
        TableAnnotation tableAnnotation = getTable(klasse);

        String sql = "UPDATE " + tableAnnotation.TableName() + " SET ";
        String setSql = "";
        String whereSql = "";
        List<SqlParameter> parameter = new ArrayList<SqlParameter>();
        boolean firstSet = true;
        boolean firstWhere = true;

        for(Column column : columns) {
            if(column.getKey() != null) {
                if(firstWhere) {
                    firstWhere = false;
                }
                else {
                    whereSql += " AND ";
                }

                if(column.getColumn().DataType() == ColumnAnnotation.DataTypes.Varchar || column.getColumn().DataType() == ColumnAnnotation.DataTypes.Datetime) {
                    whereSql += column.getColumn().ColumnName() + " = ?";
                    parameter.add(new SqlParameter(column.getField().get(dbo),
                            column.getColumn().DataType(), parameter.size() + 1));
                }
                else {
                    whereSql += column.getColumn().ColumnName() + " = " + column.getField().get(dbo);
                }
            }
        }

        for(UpdateParameter updateParameter : updateParameters) {
            if(firstSet) {
                firstSet = false;
            }
            else {
                setSql += ", ";
            }

            setSql += updateParameter.getSql();

            if(updateParameter.needParameter()) {
                parameter.add(updateParameter.getParameter(parameter.size() + 1));
            }
        }

        sql += setSql + " WHERE " + whereSql;

        System.out.println(sql);
        String[] args = new String[parameter.size()];

        for(int i = 0; i < args.length; i++) {
            args[i] = parameter.get(i).getString();
        }
        this.execute(sql, args);
    }

    public void update(TDbo dbo) throws Exception {
        Class<? extends Object> klasse = createNew().getClass();
        List<Column> columns = getColumns(klasse);
        TableAnnotation tableAnnotation = getTable(klasse);

        String sql = "UPDATE " + tableAnnotation.TableName() + " SET ";
        String setSql = "";
        String whereSql = "";
        List<SqlParameter> parameter = new ArrayList<SqlParameter>();
        boolean firstSet = true;
        boolean firstWhere = true;

        for(Column column : columns) {
            if(column.getKey() != null) {
                if(firstWhere) {
                    firstWhere = false;
                }
                else {
                    whereSql += " AND ";
                }

                if(column.getColumn().DataType() == ColumnAnnotation.DataTypes.Varchar || column.getColumn().DataType() == ColumnAnnotation.DataTypes.Datetime) {
                    whereSql += column.getColumn().ColumnName() + " = ?";
                    parameter.add(new SqlParameter(column.getField().get(dbo),
                            column.getColumn().DataType(), parameter.size() + 1));
                }
                else {
                    whereSql += column.getColumn().ColumnName() + " = " + column.getField().get(dbo);
                }
            }
            else {
                if(firstSet) {
                    firstSet = false;
                }
                else {
                    setSql += ", ";
                }

                if(column.getColumn().DataType() == ColumnAnnotation.DataTypes.Varchar || column.getColumn().DataType() == ColumnAnnotation.DataTypes.Datetime) {
                    setSql += column.getColumn().ColumnName() + " = ?";
                    parameter.add(new SqlParameter(column.getField().get(dbo),
                            column.getColumn().DataType(), parameter.size() + 1));
                }
                else {
                    setSql += column.getColumn().ColumnName() + " = " + column.getField().get(dbo);
                }
            }
        }

        sql += setSql + " WHERE " + whereSql;

        System.out.println(sql);
        String[] args = new String[parameter.size()];

        for(int i = 0; i < args.length; i++) {
            args[i] = parameter.get(i).getString();
        }

        this.execute(sql, args);
    }

    public Cursor executeQuery(String sql) {
        SQLiteDatabase database = DatabaseManager.getDatabase();

        if(!sql.endsWith(";")) {
            sql += ";";
        }

        System.out.println(sql);

        return database.rawQuery(sql, new String[0]);
    }

    public void execute(String sql) throws Exception {
        this.execute(sql, new String[0]);
    }

    public void execute(String sql, String[] args) throws Exception {
        SQLiteDatabase database = DatabaseManager.getDatabase();
        System.out.println(sql);

        database.execSQL(sql, args);
    }

    private TableAnnotation getTable(Class<? extends Object> klasse)
    {
        TableAnnotation tableAnnotation = klasse.getAnnotation(TableAnnotation.class);
        return tableAnnotation;
    }

    private TDbo setField(TDbo dbo, Cursor cursor, Field field) throws IllegalAccessException {
        Column column = getColumn(field);
        Object value = null;

        switch(column.getColumn().DataType()) {
            case Bit:
            case Int:
                value = cursor.getInt(cursor.getColumnIndex(column.getColumn().ColumnName()));
                break;
            case Datetime:
                value = cursor.getString(cursor.getColumnIndex(column.getColumn().ColumnName()));
                break;
            case Varchar:
                value = cursor.getString(cursor.getColumnIndex(column.getColumn().ColumnName()));
                break;
        }

        field.set(dbo, value);

        return dbo;
    }

    private Column getColumn(Field field)
    {
        ColumnAnnotation columnAnnotation = field.getAnnotation(ColumnAnnotation.class);
        KeyAnnotation keyAnnotation = field.getAnnotation(KeyAnnotation.class);

        return columnAnnotation == null ? null : new Column(columnAnnotation, keyAnnotation, field);
    }

    private List<Column> getColumns(Class<? extends Object> klasse)
    {
        List<Column> spalten = new ArrayList<Column>();

        for(Field field : klasse.getDeclaredFields()) {
            Column column = getColumn(field);
            if(column != null) {
                spalten.add(column);
            }
        }

        return spalten;
    }

    private List<Column> getKeyColumns(List<Column> columns) {
        List<Column> result = new ArrayList<Column>();

        for(Column column : columns) {
            if(column.getKey() != null) {
                result.add(column);
            }
        }

        return result;
    }
}
