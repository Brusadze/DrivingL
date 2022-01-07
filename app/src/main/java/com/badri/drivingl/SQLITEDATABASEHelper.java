package com.badri.drivingl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SQLITEDATABASEHelper extends SQLiteOpenHelper {


    private static String APP_DATA_PATH = "";
    private SQLiteDatabase dataBase;
    private final Context context;

    public static final String DATABASE_NAME = "examData.dtb";
    public static final String DB_SUB_PATH = "/databases/" + DATABASE_NAME;
    public static final String TABLE_NAME = "exams";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "question";
    public static final String COLUMN_ANSWER = "answerONE";
    public static final String COLUMN_ANSWERTWO = "answerTWO";
    public static final String COLUMN_ANSWERTHREE = "answerTHREE";
    public static final String COLUMN_ANSWERFOUR = "answerFOUR";
    public static final String COLUMN_CORRECT_ANSWER = "correctANSWER";
    private static final int DATABASE_VERSION = 1;


    public SQLITEDATABASEHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        APP_DATA_PATH = context.getApplicationInfo().dataDir;
        this.context = context;
    }
    public boolean openDataBase() throws SQLException {
        String mPath = APP_DATA_PATH + DB_SUB_PATH;
        //Note that this method assumes that the db file is already copied in place
        dataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
        return dataBase != null;
    }

    @Override
    public synchronized void close(){
        if(dataBase != null) {dataBase.close();}
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


/*
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE;
        CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME
                + " text, " + COLUMN_ANSWER + " text, " + COLUMN_ANSWERTWO + " text, " + COLUMN_ANSWERTHREE + " text, "
                + COLUMN_ANSWERFOUR + " text, " + COLUMN_CORRECT_ANSWER + " text);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public static String quote(String s) {
        return new StringBuilder()
                .append('\'')
                .append(s)
                .append('\'')
                .toString();
    }
    void add(String questionInput,String answerOneInput,String answerTwoInput,String answerThreeInput,String answerFourInput,String correctAnswerInput) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO exams VALUES(NULL," + quote(questionInput) + " , " + quote(answerOneInput) + " , " + quote(answerTwoInput)
                + " , " + quote(answerThreeInput) + " , " + quote(answerFourInput) + " , " + quote(correctAnswerInput) + ");");
        //db.execSQL("INSERT INTO exams VALUES(NULL,'ONE','ONE','ONE','ONE','ONE','ONE')");
        */
/*db.execSQL("INSERT INTO " + TABLE_NAME +
                        "(question,answerONE, answerTWO , answerTHREE , answerFOUR, correctANSWER) VALUES ("
                + COLUMN_NAME + ", " + COLUMN_ANSWER + ", " + COLUMN_ANSWERTWO + ", " + COLUMN_ANSWERTHREE +
                ", " + COLUMN_ANSWERFOUR + ", " + COLUMN_CORRECT_ANSWER + ")");*//*
    }
    static String shekitxva;
    static String pirvelipasuxi;
    static String meorepasuxi;
    static String mesamepasuxi;
    static String meotxepasuxi;
    void getExam(int idRaw){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT question," +
                "answerONE," +
                "answerTWO," +
                "answerTHREE,answerFOUR,correctANSWER FROM exams Where _id = " + idRaw,null );
        if (c.moveToFirst()){
            do {
                // Passing values
                shekitxva = c.getString(0); //SHEKITXVA
                pirvelipasuxi = c.getString(1);  //ANSWER  ONE
                meorepasuxi = c.getString(2); //ANSWER  two
                mesamepasuxi = c.getString(3); //ANSWER  three
                meotxepasuxi = c.getString(4); //ANSWER  four
                //String questionaa = c.getString(5);
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        //db.execSQL("select question from exams where _id =" + quote("3"));
    }
    void cleanDatabase(){
    }
*/



}