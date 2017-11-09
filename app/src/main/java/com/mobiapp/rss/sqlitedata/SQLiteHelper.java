package com.mobiapp.rss.sqlitedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lotfi Fetteni on 09/11/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {



    public static String DATABASE_NAME="mobiapps";

    public static final String TABLE_NAME="Articles";

    public static final String Table_Column_ID="id";

    public static final String Table_Column_1_Author="author";

    public static final String Table_Column_2_title="title";

    public static final String Table_Column_3_description="description";

    public static final String Table_Column_4_URL="url";

    public static final String Table_Column_5_UrlImage="urlImage";

    public static final String Table_Column_6_PublishedAt="publishedAt";

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Author+" VARCHAR," +
                " "+Table_Column_2_title+" VARCHAR," +
                ""+Table_Column_3_description+" VARCHAR,"+Table_Column_4_URL+" VARCHAR,"+Table_Column_5_UrlImage+" VARCHAR,"+Table_Column_6_PublishedAt+" VARCHAR)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
