package com.fifino.gptw.helpers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by porfiriopartida on 6/14/2016.
 */

public class DFDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DramaFriday.db";
//    public static final String SQL_CREATE_ENTRIES = "";
//    public static final String SQL_DELETE_ENTRIES = "";

    public DFDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables and the initial population of the tables should happen.
     *
     * @param db
     */

    private void createTables(SQLiteDatabase db){
        db.execSQL(DFContract.ScoresTable.getCreateTable());
    }
    private void executeInserts(SQLiteDatabase db){
        String[] inserts = DFContract.ScoresTable.getInsertScreens();
        for(String insert : inserts){
            db.execSQL(insert);
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DFDBHelper onCreate: ");
        createTables(db);
        executeInserts(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("DFDBHelper ON UPGRADE: ");
        System.out.println("---" + oldVersion + " --> " + newVersion);
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DFContract.ScoresTable.getDropTable());
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}