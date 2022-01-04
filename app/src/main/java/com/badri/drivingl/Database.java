package com.badri.drivingl;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private final Context context;
    private SQLiteDatabase database;
    private SampleSQLiteDBHelper dbHelper;

    public Database(Context context){
        this.context = context;
        dbHelper = new SampleSQLiteDBHelper(context);
    }

    public Database open() throws SQLException
    {
        dbHelper.openDataBase();
        dbHelper.close();
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public int burjandze;
    public void burjo(){

        //final String MY_QUERY = "SELECT MAX(_id) AS _id FROM exams WHERE category = 1";
        final String MY_QUERY = "SELECT MAX(_id) AS _id FROM exams WHERE category = 'ყველა'";
        Cursor mCursor = database.rawQuery(MY_QUERY, null);
        try {
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                burjandze = mCursor.getInt(mCursor.getColumnIndex("_id"));
                Log.d("br", String.valueOf(burjandze));
            }
        } catch (Exception ignored) {

        }

    }

    public String questionValue;

    public void getQuestion(int id){
        try{
            String s = String.valueOf(id);
            String query ="SELECT question FROM exams " + "WHERE _id = " + s + " AND category = 'ყველა'";

            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    questionValue = cursor.getString(0);
                    Log.d("db", questionValue);
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }

    }

    public String answerOneValue;
    public String answerTwoValue;
    public String answerThreeValue;
    public String answerFourValue;
    public String correctAnswerValue;
    public String imageValue;
    public String ganmartebaValue;
    public void getAnswers(int id){
        try{
            String s = String.valueOf(id);
            String query ="SELECT answerOne,answerTwo,answerThree,answerFour,correctANSWER,imageView,ganmarteba FROM exams " + "WHERE _id = " + s;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    answerOneValue = cursor.getString(0);
                    answerTwoValue = cursor.getString(1);
                    answerThreeValue = cursor.getString(2);
                    answerFourValue = cursor.getString(3);
                    correctAnswerValue = cursor.getString(4);
                    imageValue = cursor.getString(5);
                    ganmartebaValue = cursor.getString(6);
                    Log.d("db", answerOneValue + answerTwoValue + answerThreeValue + answerFourValue);
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }
}

