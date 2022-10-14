package fpt.grw.expensemanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenseManagementApp";

    private static final String TABLE_TRIPS = "trips";
    private static final String TABLE_COSTS = "costs";

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";

    public static final String NAME = "name";
    public static final String DATE_FROM = "date_from";
    public static final String DATE_TO = "date_to";
    public static final String DESTINATION = "destination";
    public static final String IS_RISK = "is_risk";
    public static final String TRIP_TYPE = "trip_type";

    public static final String COST_TYPE = "cost_type";
    public static final  String AMOUNT = "amount";
    public static final  String DATE = "date";
    public static final  String TRIP_ID = "trip_id";



    private SQLiteDatabase database;

    private static final String DATABASE_CREATE = String.format(
        "CREATE TABLE %s (" +
            "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "   %s TEXT, " +
            "   %s TEXT NOT NULL, " +
            "   %s TEXT NOT NULL, " +
            "   %s TEXT NOT NULL, " +
            "   %s TEXT NOT NULL, " +
            "   %s BOOLEAN NOT NULL, " +
            "   %s INTEGER NOT NULL); " +
        "CREATE TABLE %s (" +
            "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "   %s TEXT, " +
            "   %s INTEGER NOT NULL, " +
            "   %s INTEGER NOT NULL, " +
            "   %s TEXT NOT NULL, " +
            "   %s TEXT NOT NULL, " +
            "   FOREIGN KEY (\"%s\") REFERENCES \"%s\" (\"%s\") ",
        TABLE_TRIPS, ID, DESCRIPTION, NAME, DATE_FROM, DATE_TO, DESTINATION, IS_RISK, TRIP_TYPE,
        TABLE_COSTS, ID, DESCRIPTION, COST_TYPE, AMOUNT, DATE, TRIP_ID, TABLE_TRIPS, ID);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }
}
