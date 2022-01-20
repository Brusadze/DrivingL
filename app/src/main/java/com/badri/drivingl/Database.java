package com.badri.drivingl;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final Context context;
    private SQLiteDatabase database;
    private SQLITEDATABASEHelper dbHelperr;

    public Database(Context context){
        this.context = context;
        dbHelperr = new SQLITEDATABASEHelper(context);
    }

    public Database open() throws SQLException
    {
        //dbHelper.openDataBase();
        dbHelperr.close();
        database = dbHelperr.getReadableDatabase();
        return this;
    }

    public void close()
    {
        dbHelperr.close();
    }

    public int burjandze;
    public void burjo(){

        //final String MY_QUERY = "SELECT MAX(_id) AS _id FROM exams WHERE category = 1";
        final String MY_QUERY = "SELECT MAX(randomId) AS randomId FROM exams "; // WHERE category = 'ყველა'
        Cursor mCursor = database.rawQuery(MY_QUERY, null);
        try {
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                burjandze = mCursor.getInt(mCursor.getColumnIndex("randomId"));
                Log.d("br", String.valueOf(burjandze));
            }
        } catch (Exception ignored) {

        }

    }

    public String questionValue;
/*

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
*/

    public String answerOneValue;
    public String answerTwoValue;
    public String answerThreeValue;
    public String answerFourValue;
    public String correctAnswerValue;
    public String imageValue;
    public String ganmartebaValue;
    public String categoryName;
    public String _idd;
    public void getAnswers(int id){
        try{
            String s = String.valueOf(id);
            String query ="SELECT answerOne,answerTwo,answerThree,answerFour,correctANSWER,imageView,ganmarteba,question,category,_id FROM exams " + "WHERE randomId = " + s;
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
                    questionValue = cursor.getString(7);
                    categoryName = cursor.getString(8);
                    _idd = cursor.getString(9);
                    Log.d("db", categoryName + " " + _idd);
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }
    public void getAnswersBiletebistvis(int id){
        try{
            String s = String.valueOf(id);
            String query ="SELECT answerOne,answerTwo,answerThree,answerFour,correctANSWER,imageView,ganmarteba,question FROM exams " + "WHERE _id = " + s;
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
                    questionValue = cursor.getString(7);
                    Log.d("db", answerOneValue + answerTwoValue + answerThreeValue + answerFourValue);
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }


    public void insertAnswersForMainManu(boolean correct){
        Log.d("Categ", categoryName);
        String sworiUpdate ="UPDATE rameshleba SET sworipasuxi = sworipasuxi + 1 WHERE categoria = " + "\"" + categoryName + "\"";
        String arasworiUpdate ="UPDATE rameshleba SET arasworipasuxi = arasworipasuxi + 1 WHERE categoria = " +  "\"" + categoryName + "\"";
        if(correct)
            database.execSQL(sworiUpdate);
        else
            database.execSQL(arasworiUpdate);
    }

    public String sumSworipasuxi;
    public String  sumArasworipasuxi;
    public void getCorrectAnswersManu(){
        try{
            String query ="SELECT SUM(sworipasuxi),SUM(arasworipasuxi) FROM rameshleba";
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    sumSworipasuxi = cursor.getString(0);
                    sumArasworipasuxi = cursor.getString(1);
                    Log.d("getCorrectAnswersManu", sumSworipasuxi + "sum" + sumArasworipasuxi + "arasSum");
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }

    List<String> bileti = new ArrayList<>();
    List<String > arasworiPasuxebi = new ArrayList<>();

    public void getTopThreeIncorrectAnswersMenu(){
        try{
            String query ="SELECT * FROM rameshleba ORDER BY arasworipasuxi DESC LIMIT 3";
            Cursor cursor = database.rawQuery(query, null);
            DatabaseUtils.dumpCursorToString(cursor);
            if (cursor.moveToFirst()){
                do{
                    bileti.add(cursor.getString(cursor.getColumnIndex("categoria")));
                    arasworiPasuxebi.add(cursor.getString(cursor.getColumnIndex("arasworipasuxi")));
                    Log.d("getCorrectAnswersManu", bileti.toString() + "  " + arasworiPasuxebi.toString());
                }while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            //handle
        }
    }

}

