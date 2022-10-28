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

    //creating table query
    private static final String DATABASE_CREATE_TABLE_TRIPS = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s BOOLEAN NOT NULL, " +
                    "   %s TEXT NOT NULL); " ,
            TABLE_TRIPS, ID, DESCRIPTION, NAME, DATE_FROM, DATE_TO, DESTINATION, IS_RISK, TRIP_TYPE);
    private static final String DATABASE_CREATE_TABLE_COSTS = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER NOT NULL, " +
                    "   %s INTEGER NOT NULL, " +
                    "   %s TEXT NOT NULL, " +
                    "   %s INTEGER NOT NULL, " +
                    "   FOREIGN KEY (\"%s\") REFERENCES \"%s\" (\"%s\") ON DELETE CASCADE)",
            TABLE_COSTS, ID, DESCRIPTION, COST_TYPE, AMOUNT, DATE, TRIP_ID, TRIP_ID,TABLE_TRIPS, ID
    );
    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_TRIPS);
        db.execSQL(DATABASE_CREATE_TABLE_COSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTS);

        onCreate(db);
    }

    public long insertTrip(
            TripModalClass tripModalClass
    ){
        ContentValues rowValues = new ContentValues();

        rowValues.put(DESCRIPTION, tripModalClass.getDescription());
        rowValues.put(NAME, tripModalClass.getName());
        rowValues.put(DATE_FROM, tripModalClass.getDate_from());
        rowValues.put(DATE_TO, tripModalClass.getDate_to());
        rowValues.put(DESTINATION, tripModalClass.getDestination());
        rowValues.put(IS_RISK, tripModalClass.getIs_risk());
        rowValues.put(TRIP_TYPE, tripModalClass.getTrip_type());

        return database.insertOrThrow(TABLE_TRIPS, null, rowValues);
    }
    public ArrayList<TripModalClass> getTrips(String searchQuery){
        String sql = "select * from " + TABLE_TRIPS;
        if(searchQuery != ""){
            sql += " where " + NAME + " like '%" + searchQuery + "%'";
        }
        database = this.getReadableDatabase();
        ArrayList<TripModalClass> tripModalClass = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String description = cursor.getString(1);
                String name = cursor.getString(2);
                String date_from = cursor.getString(3);
                String date_to = cursor.getString(4);
                String destination = cursor.getString(5);
                Boolean is_risk = cursor.getInt(6) > 0;
                Integer trip_type = cursor.getInt(7);
                tripModalClass.add(
                        new TripModalClass(
                                id,
                                description,
                                name, date_from,
                                date_to,destination,
                                is_risk,
                                trip_type
                        )
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        return tripModalClass;
    }
    public TripModalClass getTripDetails(int tripId){
        String sql = "select * from " + TABLE_TRIPS + " where id = " + tripId;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        Integer id = cursor.getInt(0);
        String description = cursor.getString(1);
        String name = cursor.getString(2);
        String date_from = cursor.getString(3);
        String date_to = cursor.getString(4);
        String destination = cursor.getString(5);
        Boolean is_risk = cursor.getInt(6) > 0;
        Integer trip_type = cursor.getInt(7);
        return new TripModalClass(
                id,
                description,
                name, date_from,
                date_to,destination,
                is_risk,
                trip_type
        );
    }
    public void updateTrip(TripModalClass tripModalClass){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DESCRIPTION, tripModalClass.getDescription());
        contentValues.put(NAME, tripModalClass.getName());
        contentValues.put(DATE_FROM, tripModalClass.getDate_from());
        contentValues.put(DATE_TO, tripModalClass.getDate_to());
        contentValues.put(DESTINATION, tripModalClass.getDestination());
        contentValues.put(IS_RISK, tripModalClass.getIs_risk());
        contentValues.put(TRIP_TYPE, tripModalClass.getTrip_type());

        database = this.getWritableDatabase();
        database.update(
                TABLE_TRIPS,
                contentValues,
                ID + " = ?",
                new String[]{String.valueOf(tripModalClass.getId())}
        );
    }
    public void deleteTrip(int idTrip){
        database = this.getWritableDatabase();
        database.delete(
                TABLE_TRIPS,
                ID + " = ?",
                new String[]{String.valueOf(idTrip)}
        );
    }
    //CRUD COST
    public long insertCost(
            CostModalClass costModalClass
    ){
        ContentValues rowValues = new ContentValues();

        rowValues.put(DESCRIPTION, costModalClass.getDescription());
        rowValues.put(COST_TYPE, costModalClass.getCost_type());
        rowValues.put(AMOUNT, costModalClass.getAmount());
        rowValues.put(DATE, costModalClass.getDate());
        rowValues.put(TRIP_ID, costModalClass.getTrip_id());

        return database.insertOrThrow(TABLE_COSTS, null, rowValues);
    }

    public ArrayList<CostModalClass> getCostsByIdTrip(String searchQuery, int idTrip){
        String sql = "Select * from " + TABLE_COSTS + " where " + TRIP_ID + " = " + idTrip;
        if(searchQuery != ""){
            sql += " and " + DESCRIPTION + " like '%" + searchQuery + "%'";
        }
        database = this.getReadableDatabase();
        ArrayList<CostModalClass> costModalClass = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String description = cursor.getString(1);
                Integer cost_type = cursor.getInt(2);
                Integer amount = cursor.getInt(3);
                String date = cursor.getString(4);
                costModalClass.add(
                        new CostModalClass(
                                 id,
                                 description,
                                 cost_type,
                                 amount,
                                 date,
                                 idTrip
                        )
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  costModalClass;
    }
    public CostModalClass getCostDetails(int idCost, int idTrip){
        String sql = "select * from " + TABLE_COSTS + " where " + ID + " = " + idCost + " and " + TRIP_ID + " = " + idTrip;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        Integer id = cursor.getInt(0);
        String description = cursor.getString(1);
        Integer cost_type = cursor.getInt(2);
        Integer amount = cursor.getInt(3);
        String date = cursor.getString(4);
        Integer trip_id = cursor.getInt(5);
        return new CostModalClass(
                id,
                description,
                cost_type,
                amount,
                date,
                trip_id
        );
    }
    public void updateCost(CostModalClass costModalClass){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DESCRIPTION, costModalClass.getDescription());
        contentValues.put(COST_TYPE, costModalClass.getCost_type());
        contentValues.put(AMOUNT, costModalClass.getAmount());
        contentValues.put(DATE, costModalClass.getDate());

        database = this.getWritableDatabase();
        database.update(
                TABLE_COSTS,
                contentValues,
                ID + " = ? and " + TRIP_ID + " = ?",
                new String[]{String.valueOf(costModalClass.getId()), String.valueOf(costModalClass.getTrip_id())}
        );

    }
    public void deleteCost(int idTrip, int idCost){
        database = this.getWritableDatabase();
        database.delete(
                TABLE_COSTS,
                ID + " = ? and " + TRIP_ID + " = ?",
                new String[]{String.valueOf(idCost), String.valueOf(idTrip)}
        );
    }
}
