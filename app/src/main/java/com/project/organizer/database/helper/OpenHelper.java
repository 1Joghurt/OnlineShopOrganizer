package com.project.organizer.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptGroup;

import com.project.organizer.OnlineShopManagerApplication;
import com.project.organizer.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int version = 1;
    private static final String databaseName = "Online-Shop_Organizer.db";

    public OpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, databaseName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> createSqls = this.getCreateSql();

        for(String createSql : createSqls) {
            db.execSQL(createSql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ShippingDetails");
            db.execSQL("DROP TABLE IF EXISTS ShippingStatusMapping");
            db.execSQL("DROP TABLE IF EXISTS ShippingStatus");
            db.execSQL("DROP TABLE IF EXISTS Shipper");
            db.execSQL("DROP TABLE IF EXISTS TraderDetails");
            db.execSQL("DROP TABLE IF EXISTS OrderStatusMapping");
            db.execSQL("DROP TABLE IF EXISTS OrderStatus");
            db.execSQL("DROP TABLE IF EXISTS Trader");
            db.execSQL("DROP TABLE IF EXISTS [Order]");
            onCreate(db);
        }
    }

    private List<String> getCreateSql() {
        try {
            List<String> sqls = new ArrayList<String>();
            InputStream inputStream = OnlineShopManagerApplication.getContext().getResources().openRawResource(R.raw.create_sql);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line + System.lineSeparator());
                line = bufferedReader.readLine();
            }

            String sql = stringBuilder.toString();

            for(String createSql : sql.split(";")) {
                createSql = createSql.trim();
                if(createSql != null && createSql != "" && createSql.contains("CREATE")) {
                    sqls.add(createSql);
                }
            }

            return sqls;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }
}
