package com.fifino.gptw.helpers.db;

import android.provider.BaseColumns;

/**
 * Created by porfiriopartida on 6/14/2016.
 */
public class DFContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = INTEGER_TYPE + "PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";
    private static final String UNIQUE = "  UNIQUE";
    private static final String COMMA_SEP = ",";
    private static final String VALUES = " VALUES ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String CREATE_TABLE = "CREATE TABLE ";

    public DFContract(){ } //Prevent instances
    public static void main( String args[]){
        String scoresTable = DFContract.ScoresTable.getCreateTable();
        System.out.println(scoresTable);
    }
    //We need one of these for each table.
    public static abstract class ScoresTable implements BaseColumns {
        /* List of fields for table */
        //FeedEntry._ID is included in Base Columns
        public static final String TABLE_NAME = "screens";
        public static final String COLUMN_NAME = "name";
        private static final String SQL_CREATE_SCREENS =
                /**
                 * CREATE TABLE screens (
                 *   _id INTEGER PRIMARY KEY,
                 *   name TEXT NOT NULL UNIQUE
                 *   )
                 */
                CREATE_TABLE + ScoresTable.TABLE_NAME + " (" +
                        ScoresTable._ID + PRIMARY_KEY + COMMA_SEP +
                        ScoresTable.COLUMN_NAME + TEXT_TYPE + NOT_NULL + UNIQUE +
                        " )";

        private static final String SQL_DELETE_SCREENS =
                DROP_TABLE + ScoresTable.TABLE_NAME;


        private static final String[] SQL_INSERT_SCREENS = new String[]{
                INSERT_INTO + ScoresTable.TABLE_NAME + VALUES + "( 1, 'FindTheCar' )",
                INSERT_INTO + ScoresTable.TABLE_NAME + VALUES + "( 2, 'Spamranhas')",
                INSERT_INTO + ScoresTable.TABLE_NAME + VALUES + "( 3, 'TurnOffAirs' )",
                INSERT_INTO + ScoresTable.TABLE_NAME + VALUES + "( 4, 'TurnOffAlarms' )",
                INSERT_INTO + ScoresTable.TABLE_NAME + VALUES + "( 5, 'WakeUp' )",
        };

        public static String getCreateTable(){
            return ScoresTable.SQL_CREATE_SCREENS;
        }
        public static String[] getInsertScreens(){
            return ScoresTable.SQL_INSERT_SCREENS;
        }

        public static String getDropTable(){
            return ScoresTable.SQL_DELETE_SCREENS;
        }
    }
}
