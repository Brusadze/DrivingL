package com.badri.drivingl;import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {

    private SampleSQLiteDBHelper dbHelper;

    private final Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new SampleSQLiteDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(int correctAnswers, int falseAnswers) {/*
        ContentValues contentValue = new ContentValues();
        contentValue.put(SampleSQLiteDBHelper.CORRECT_ANSWERS, correctAnswers);
        contentValue.put(SampleSQLiteDBHelper.INCORRECT_ANSWERS, falseAnswers);
        database.insert(SampleSQLiteDBHelper.TABLE_NAME, null, contentValue);*/


       /* String query = "UPDATE stats SET correctAnswers = correctAnswers " + correctAnswers;
        database.execSQL(query);*/
        String queryInsert = "INSERT INTO stats VALUES(null," + correctAnswers + "," + falseAnswers + ")";
        database.execSQL(queryInsert);

        /*String query = "UPDATE stats SET correctAnswers = correctAnswers " + correctAnswers;
        database.execSQL(query);*/
    }

    public void insertExamFinishData(int correctAnswers) {
        String queryInsert = "INSERT INTO stats VALUES(null," + correctAnswers + "," + null + "," + null +"," + null + ")";
        database.execSQL(queryInsert);
    }
    public void updateValues(int correctAnswers, int falseAnswers) {/*
        ContentValues contentValue = new ContentValues();
        contentValue.put(SampleSQLiteDBHelper.CORRECT_ANSWERS, correctAnswers);
        contentValue.put(SampleSQLiteDBHelper.INCORRECT_ANSWERS, falseAnswers);
        database.insert(SampleSQLiteDBHelper.TABLE_NAME, null, contentValue);*/


       /* String query = "UPDATE stats SET correctAnswers = correctAnswers " + correctAnswers;
        database.execSQL(query);*//*
        String queryInsert = "INSERT INTO stats VALUES(null," + correctAnswers + "," + falseAnswers + ")";
        database.execSQL(queryInsert);*/

        String query = "UPDATE stats SET correctAnswers = correctAnswers + " + correctAnswers + "," +
                "incorrectAnswers = incorrectAnswers + " + falseAnswers + " WHERE _id = 2" ;

        database.execSQL(query);
    }

    public int correctAnswersGotFromDatabase;
    public int incorrectAnswersGotFromDatabase;
    public int correctsForSrollview;
    public void getValues(){
        try{
            String query ="SELECT correctAnswers,incorrectAnswers FROM stats WHERE _id = 1";

            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    correctAnswersGotFromDatabase = Integer.parseInt(cursor.getString(0));
                    //incorrectAnswersGotFromDatabase = Integer.parseInt(cursor.getString(1));
                    Log.d("db", correctAnswersGotFromDatabase + " -- " + incorrectAnswersGotFromDatabase);
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }

    }
    public void getScrollValuesDB(){
        try{
            String query ="SELECT correctAnswers FROM stats WHERE _id = 2";

            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    correctsForSrollview = Integer.parseInt(cursor.getString(0));
                    Log.d("db", correctsForSrollview + " SCROLLISTVIS ");
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }

    public int update(long _id, String correctAnswers, String falseAnswers) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SampleSQLiteDBHelper.CORRECT_ANSWERS, correctAnswers);
        contentValues.put(SampleSQLiteDBHelper.INCORRECT_ANSWERS, falseAnswers);
        int i = database.update(SampleSQLiteDBHelper.TABLE_NAME, contentValues, SampleSQLiteDBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(SampleSQLiteDBHelper.TABLE_NAME, SampleSQLiteDBHelper._ID + "=" + _id, null);
    }

}