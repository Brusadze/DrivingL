package com.badri.drivingl;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SampleSQLiteDBHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "stats";

    // Table columns
    public static final String _ID = "_id";
    public static final String CORRECT_ANSWERS = "correctAnswers";
    public static final String INCORRECT_ANSWERS = "incorrectAnswers";
    public static final String RESULT = "result";
    public static final String EXAMFINISHCORRECT = "examfinishcorrect";


    // Database Information
    static final String DB_NAME = "examStatistics.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CORRECT_ANSWERS + " INTEGER , " + INCORRECT_ANSWERS + " INTEGER ," + RESULT + " TEXT, " + EXAMFINISHCORRECT + " INTEGER );";

    public SampleSQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

