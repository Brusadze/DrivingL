package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;



import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.badri.drivingl.R.layout.activity_exam;

public class ExamActivity extends AppCompatActivity {


    TextView shekitxvatext,pasuxiErtiText,pasuxiOriText,pasuxiSamiText
            ,pasuxiOtxiText,countdownTimerText,numberofbiletiText,finishLayoutText,
            biletisAxsnaTexti,shekitxvatextFINISH,pasuxiErtiTextFINISH,pasuxiOriTextFINISH,
            pasuxiSamiTextFINISH,pasuxiOtxiTextFINISH,daxurvaFinish;
    Button nextQuestionButton,nextQuestionButtonFINISH,closeQuestionButtonFINISH;

    Database database;
    //stats database
    Database databaseStats;
    DBManager dbManager;
    ScrollView scrollViewFINISH;
    VideoView videoView;
    RelativeLayout relativeLayoutMesameQuest,relativeLayoutMeotxeQuest,
            firstQuestionRelative,secondQuestionRelative , countAndTimerLayout, nextbuttonCard,finishLayout
            ,firstQuestionRelativeFINISH,secondQuestionRelativeFINISH,relativeLayoutMesameQuestFINISH,
            relativeLayoutMeotxeQuestFINISH;






    ImageView imageQuestion,infoBileti,closeLinearAxsna,imageQuestionFINISH;
    LinearLayout linearImageQuestion,linearExplanation,gridFinishLayout,linearImageQuestionFINISH;

    static int[] myIntArray = new int[30];
    private ArrayList<Integer> questionId = new ArrayList<Integer>();
    private ArrayList<String> gacemuliPasuxi = new ArrayList<String>();

    private List<Integer> _idofBileti = new ArrayList<>();

    public int sworiPasuxebiExamBolos = 0;
    boolean trueAnswer;

    int onclickValueToClose = 1;

    private static final String FORMAT = "%02d:%02d:%02d";
    //ArrayList<String> id,question,answerOne,answerTwo,answerThree,answerFour,correctAnswer;
    int textNumber = 1;
    //Declare timer
    CountDownTimer cTimer = null;
    String correctAnswerValue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_exam);



        //final SampleSQLiteDBHelper db = new SampleSQLiteDBHelper(this);
        database = new Database(this);
        database.open();

        dbManager = new DBManager(this);
        dbManager.open();

        //dbManager.insertExamFinishData(0);

        try {
            createTextFileForResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addDataToTextFile();

        try {
            readDataFromTextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }





        RelativeLayout videoLinear =  findViewById(R.id.videoLinear);
        RelativeLayout nextbuttonCard = findViewById(R.id.nextbuttonCard);
        finishLayout = findViewById(R.id.finishLayout);
        finishLayoutText = findViewById(R.id.finishLayoutText);
        biletisAxsnaTexti = findViewById(R.id.biletisAxsnaTexti);
        infoBileti = findViewById(R.id.infoBileti);
        closeLinearAxsna = findViewById(R.id.closeLinearAxsna);
        linearExplanation = findViewById(R.id.linearExplanation);
        gridFinishLayout = findViewById(R.id.gridFinishLayout);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setMediaController(null);
        LinearLayout linearView = findViewById(R.id.linearView);
        videoView.setVideoPath("https://java106.000webhostapp.com/videoExample.mp4");
        linearView.setVisibility(View.GONE);
        videoLinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearView.setVisibility(View.VISIBLE);
                videoView.start();
            }
        });

        //WHEN VIDEO ENDS, FRAME CLOSES
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                linearView.setVisibility(View.GONE);
            }
        });

        //DEFINING UI OBJECTS WITH R.id s
        initializeEnvironment();
        getRandom(); //GETS RANDOM NUMBER AND ASSIGNS TO shemtxvevitiNumber

        Intent mIntent = getIntent();
        int switchMode = mIntent.getIntExtra("switchMode", 0);

        // LOAD switchMode 1 or 0 where 1 is [EXAM] and 0 is [PRACTICE]
        Log.d("switch", String.valueOf(switchMode));

        finishLayout.setVisibility(View.GONE);

        arrageUI(shemtxvevitiNumber); //ARRANGES UI WITH _id
        //questionId[0] = shemtxvevitiNumber;



        //CHECKING IF is exam mode or practice mode.
        if(switchMode == 1){
            //EXAM MODE
            startTimer();

            initializeExamEnvFinish();
            scrollViewFINISH.setVisibility(View.GONE);

            linearExplanation.setVisibility(View.GONE);
            gridFinishLayout.setVisibility(View.GONE);
            initializeGridButtons();
            infoBileti.setVisibility(View.GONE);
            nextbuttonCard.setVisibility(View.GONE);
            pasuxiErtiText.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                public void onClick(View v) {
                    Log.d("idof" , String.valueOf(_idofBileti.get(0)));
                    //adding answer that we chose to the array for showing stats
                    //gacemuliPasuxi[textNumber - 1] = pasuxiErtiText.getText().toString();
                    gacemuliPasuxi.add(pasuxiErtiText.getText().toString());
                    Log.d("STATEBI" , String.valueOf(gacemuliPasuxi));
                    Log.d("STATEBI" , String.valueOf(questionId));
                    if(database.correctAnswerValue.equals(pasuxiErtiText.getText().toString())){
                        //firstQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        correctAnswer = 1;
                        trueAnswer = true;
                        sworiPasuxebiExamBolos++;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.correcanswerui));

                        Log.d("CORRECTANSWER++",String.valueOf(correctAnswer));
                        countingBiletebi();
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.falseanswerui));

                        Log.d("FALSE++",String.valueOf(falseAnswer));
                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    if(textNumber > 3){
                        /*Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        startActivity(intent);*/
                        gridFinishLayout.setVisibility(View.VISIBLE);

                        bilet1text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(0));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));




                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });

                        bilet2text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(1));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));


                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });

                        if(sworiPasuxebiExamBolos >= 27){
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ წარმატებით ჩააბარე გამოცდა");
                            finishLayoutText.setTextColor(Color.GREEN);
                        }else {
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ ვერ ჩააბრე გამოცდა");
                            finishLayoutText.setTextColor(Color.RED);
                        }

                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiOriText.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                public void onClick(View v) {

                    //adding answer that we chose to the array for showing stats
                    //gacemuliPasuxi[textNumber - 1] = pasuxiOriText.getText().toString();
                    gacemuliPasuxi.add(pasuxiOriText.getText().toString());
                    //Log.d("STATEBI" , Arrays.toString(gacemuliPasuxi));
                    //Log.d("STATEBI" , Arrays.toString(questionId));
                    if(database.correctAnswerValue.equals(pasuxiOriText.getText().toString())){
                        //secondQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        sworiPasuxebiExamBolos++;
                        correctAnswer = 1;
                        trueAnswer = true;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.correcanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.falseanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                       /* Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        dbManager.insertExamFinishData(sworiPasuxebiExamBolos);
                        startActivity(intent);*/
                        gridFinishLayout.setVisibility(View.VISIBLE);

                        bilet1text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(0));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));




                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });

                        bilet2text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(1));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                } if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));


                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });
                        if(sworiPasuxebiExamBolos >= 27){
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ წარმატებით ჩააბარე გამოცდა");
                            finishLayoutText.setTextColor(Color.GREEN);
                        }else {
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ ვერ ჩააბრე გამოცდა");
                            finishLayoutText.setTextColor(Color.RED);
                        }
                    }

                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiSamiText.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                public void onClick(View v) {

                    //adding answer that we chose to the array for showing stats
                    //gacemuliPasuxi[textNumber - 1] = pasuxiSamiText.getText().toString();
                    gacemuliPasuxi.add(pasuxiSamiText.getText().toString());
                    //Log.d("STATEBI" , Arrays.toString(gacemuliPasuxi));
                    //Log.d("STATEBI" , Arrays.toString(questionId));
                    if(database.correctAnswerValue.equals(pasuxiSamiText.getText().toString())){
                        sworiPasuxebiExamBolos++;
                        //relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        correctAnswer = 1;
                        trueAnswer = true;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.correcanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.falseanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        gridFinishLayout.setVisibility(View.VISIBLE);

                        bilet1text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(0));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));




                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });

                        bilet2text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(1));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));


                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });
                        /*Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        startActivity(intent);*/
                        if(sworiPasuxebiExamBolos >= 27){
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ წარმატებით ჩააბარე გამოცდა");
                            finishLayoutText.setTextColor(Color.GREEN);
                        }else {
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ ვერ ჩააბრე გამოცდა");
                            finishLayoutText.setTextColor(Color.RED);
                        }
                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiOtxiText.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                public void onClick(View v) {

                    //adding answer that we chose to the array for showing stats
                    //gacemuliPasuxi[textNumber - 1] = pasuxiOtxiText.getText().toString();
                    gacemuliPasuxi.add(pasuxiOtxiText.getText().toString());
                    //Log.d("STATEBI" , Arrays.toString(gacemuliPasuxi));
                    //.d("STATEBI" , Arrays.toString(questionId));
                    if(database.correctAnswerValue.equals(pasuxiOtxiText.getText().toString())){
                        sworiPasuxebiExamBolos++;
                        //relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        correctAnswer = 1;
                        trueAnswer = true;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.correcanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;

                        //GRID LAYOUT AFTER EXAM FINISHED
                        textViewsList.get(textNumber - 1).setBackground(getResources().getDrawable(R.drawable.falseanswerui));

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        gridFinishLayout.setVisibility(View.VISIBLE);

                        bilet1text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(0));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }

                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));




                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });

                        bilet2text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scrollViewFINISH.setVisibility(View.VISIBLE);
                                database.getAnswers(_idofBileti.get(1));

                                if(database.imageValue.equals("")){
                                    linearImageQuestionFINISH.setVisibility(View.GONE);
                                }
                                else{
                                    linearImageQuestionFINISH.setVisibility(View.VISIBLE);
                                    int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                                    Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                                    imageQuestionFINISH.setImageResource(id);
                                }

                                shekitxvatextFINISH.setText(database.questionValue);
                                pasuxiErtiTextFINISH.setText(database.answerOneValue);
                                pasuxiOriTextFINISH.setText(database.answerTwoValue);

                                if(database.answerThreeValue.isEmpty())
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMesameQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiSamiTextFINISH.setText(database.answerThreeValue);
                                }
                                if(database.answerFourValue.isEmpty())
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.GONE);
                                else{
                                    relativeLayoutMeotxeQuestFINISH.setVisibility(View.VISIBLE);
                                    pasuxiOtxiTextFINISH.setText(database.answerFourValue);
                                }
                                if(database.correctAnswerValue.equals(pasuxiErtiTextFINISH.getText()))
                                    firstQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    firstQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOriTextFINISH.getText()))
                                    secondQuestionRelativeFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    secondQuestionRelativeFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiSamiTextFINISH.getText()))
                                    relativeLayoutMesameQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMesameQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));
                                if(database.correctAnswerValue.equals(pasuxiOtxiTextFINISH.getText()))
                                    relativeLayoutMeotxeQuestFINISH.setBackgroundColor(Color.parseColor("#00ff00"));
                                else
                                    relativeLayoutMeotxeQuestFINISH.setBackground(getResources().getDrawable(R.drawable.pasuxi));

                                Log.d("CHECK", String.valueOf(database.answerThreeValue.isEmpty()));
                                Log.d("CHECK", String.valueOf(database.answerFourValue.isEmpty()));
                            }
                        });
                       /* Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        *//*intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);*//*
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);

                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS

                        startActivity(intent);*/

                        if(sworiPasuxebiExamBolos >= 27){
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ წარმატებით ჩააბარე გამოცდა");
                            finishLayoutText.setTextColor(Color.GREEN);
                        }else {
                            finishLayout.setVisibility(View.VISIBLE);
                            finishLayoutText.setText("შენ ვერ ჩააბრე გამოცდა");
                            finishLayoutText.setTextColor(Color.RED);
                        }
                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });


        }else if(switchMode == 0){
            //PRACTICE MODE
            //startTimer();
            gridFinishLayout.setVisibility(View.GONE);
            closeLinearAxsna.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    linearExplanation.setVisibility(View.GONE);
                }
            });

            infoBileti.setVisibility(View.VISIBLE);
            linearExplanation.setVisibility(View.GONE);
            countdownTimerText.setVisibility(View.GONE);
            nextbuttonCard.setVisibility(View.VISIBLE);
            infoBileti.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                       linearExplanation.setVisibility(View.VISIBLE);
                       biletisAxsnaTexti.setText(database.ganmartebaValue);
                }
            });
            pasuxiErtiText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    gacemuliPasuxi.add(pasuxiErtiText.getText().toString());
                    Log.d("STATEBI" , String.valueOf(gacemuliPasuxi));
                    Log.d("STATEBI" , String.valueOf(questionId));
                    if(database.correctAnswerValue.equals(pasuxiErtiText.getText().toString())){
                        firstQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        makeUnclickable();
                        correctAnswer = 1;
                        trueAnswer = true;
                        Log.d("CORRECTANSWER++",String.valueOf(correctAnswer));
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;
                        makeUnclickable();
                        Log.d("FALSE++",String.valueOf(falseAnswer));
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        firstQuestionRelative.setBackgroundColor(Color.parseColor("#ff0000"));
                        if(database.correctAnswerValue.equals(pasuxiSamiText.getText().toString()))
                            relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOriText.getText().toString()))
                            secondQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOtxiText.getText().toString()))
                            relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                    }

                }
            });
            pasuxiOriText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(database.correctAnswerValue.equals(pasuxiOriText.getText().toString())){
                        secondQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        makeUnclickable();
                        correctAnswer = 1;
                        trueAnswer = true;
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;
                        makeUnclickable();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        secondQuestionRelative.setBackgroundColor(Color.parseColor("#ff0000"));
                        if(database.correctAnswerValue.equals(pasuxiErtiText.getText().toString()))
                            firstQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiSamiText.getText().toString()))
                            relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOtxiText.getText().toString()))
                            relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                    }


                }
            });
            pasuxiSamiText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(database.correctAnswerValue.equals(pasuxiSamiText.getText().toString())){
                        relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        makeUnclickable();
                        correctAnswer = 1;
                        trueAnswer = true;
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;
                        makeUnclickable();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#ff0000"));
                        if(database.correctAnswerValue.equals(pasuxiErtiText.getText().toString()))
                            firstQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOriText.getText().toString()))
                            secondQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOtxiText.getText().toString()))
                            relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                    }
                }
            });
            pasuxiOtxiText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(database.correctAnswerValue.equals(pasuxiOtxiText.getText().toString())){
                        relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                        makeUnclickable();
                        correctAnswer = 1;
                        trueAnswer = true;
                        try {
                            saveNewStats();
                            correctAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        falseAnswer = 1;
                        trueAnswer = false;
                        makeUnclickable();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#ff0000"));
                        if(database.correctAnswerValue.equals(pasuxiErtiText.getText().toString()))
                            firstQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiOriText.getText().toString()))
                            secondQuestionRelative.setBackgroundColor(Color.parseColor("#00ff00"));
                        if(database.correctAnswerValue.equals(pasuxiSamiText.getText().toString()))
                            relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#00ff00"));
                    }

                }
            });


        }



/*
        Uri uri = Uri.parse("/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4");
        VideoView videoView = (VideoView) findViewById(R.id.videoView); // initiate a video view
        videoView.setVideoURI(uri);
        videoView.start();*/


        nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textNumber++; //counting untill 30
                linearExplanation.setVisibility(View.GONE);
                if(trueAnswer)
                    myIntArray[textNumber - 2] = 1;
                else if (!trueAnswer)
                    myIntArray[textNumber - 2] = 0;


                Log.d("ARRAY", Arrays.toString(myIntArray));

                /*firstQuestionRelative.setBackgroundResource(R.drawable.pasuxi);
                secondQuestionRelative.setBackgroundResource(R.drawable.pasuxi);
                relativeLayoutMesameQuest.setBackgroundResource(R.drawable.pasuxi);
                relativeLayoutMeotxeQuest.setBackgroundResource(R.drawable.pasuxi);*/

                firstQuestionRelative.setBackgroundColor(Color.parseColor("#1f2c34"));
                secondQuestionRelative.setBackgroundColor(Color.parseColor("#1f2c34"));
                relativeLayoutMesameQuest.setBackgroundColor(Color.parseColor("#1f2c34"));
                relativeLayoutMeotxeQuest.setBackgroundColor(Color.parseColor("#1f2c34"));

                //textView6.setText(String.valueOf(textNumber));
                getRandom();
                arrageUI(shemtxvevitiNumber);

                makeClickable();

                questionId.add(shemtxvevitiNumber);

                Log.d("ARRAY", String.valueOf(questionId));
                numberofbiletiText.setText(String.valueOf(textNumber) + " / 30");
                if(textNumber == 30){
                    numberofbiletiText.setTextColor(Color.GREEN);
                }

            }
        });
        //database.close();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ExamActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
    void countingBiletebi(){
        textNumber++; //counting untill 30
        numberofbiletiText.setText(String.valueOf(textNumber) + " / 30");

        //questionId[textNumber - 2] = shemtxvevitiNumber;
        questionId.add(shemtxvevitiNumber);

        if(textNumber == 30){
            numberofbiletiText.setTextColor(Color.GREEN);
        }
    }
    void initializeExamEnvFinish(){
        scrollViewFINISH = findViewById(R.id.scrollViewFINISH);
        firstQuestionRelativeFINISH = findViewById(R.id.firstQuestionRelativeFINISH);
        secondQuestionRelativeFINISH = findViewById(R.id.secondQuestionRelativeFINISH);
        relativeLayoutMesameQuestFINISH = findViewById(R.id.relativeLayoutMesameQuestFINISH);
        relativeLayoutMeotxeQuestFINISH = findViewById(R.id.relativeLayoutMeotxeQuestFINISH);
        // nextQuestionButtonFINISH = findViewById(R.id.nextQuestionButtonFINISH);
        // closeQuestionButtonFINISH = findViewById(R.id.closeQuestionButtonFINISH);
        shekitxvatextFINISH = findViewById(R.id.shekitxvatextFINISH);
        pasuxiErtiTextFINISH = findViewById(R.id.pasuxiErtiTextFINISH);
        pasuxiOriTextFINISH = findViewById(R.id.pasuxiOriTextFINISH);
        pasuxiSamiTextFINISH = findViewById(R.id.pasuxiSamiTextFINISH);
        pasuxiOtxiTextFINISH = findViewById(R.id.pasuxiOtxiTextFINISH);
        firstQuestionRelativeFINISH = findViewById(R.id.firstQuestionRelativeFINISH);
        secondQuestionRelativeFINISH = findViewById(R.id.secondQuestionRelativeFINISH);
        relativeLayoutMesameQuestFINISH = findViewById(R.id.relativeLayoutMesameQuestFINISH);
        relativeLayoutMeotxeQuestFINISH = findViewById(R.id.relativeLayoutMeotxeQuestFINISH);
        linearImageQuestionFINISH = findViewById(R.id.linearImageQuestionFINISH);
        imageQuestionFINISH = findViewById(R.id.imageQuestionFINISH);
        daxurvaFinish = findViewById(R.id.daxurvaFinish);

        daxurvaFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewFINISH.setVisibility(View.GONE);
            }
        });

    }
    void initializeEnvironment() {
        numberofbiletiText = (TextView) findViewById(R.id.numberofbiletiText);
        numberofbiletiText.setText("01 / 30");
        countdownTimerText = (TextView) findViewById(R.id.countdownTimerText);
        shekitxvatext = (TextView) findViewById(R.id.shekitxvatext);
        pasuxiErtiText = (TextView) findViewById(R.id.pasuxiErtiText);
        pasuxiOriText = (TextView) findViewById(R.id.pasuxiOriText);
        pasuxiSamiText = (TextView) findViewById(R.id.pasuxiSamiText);
        pasuxiOtxiText = (TextView) findViewById(R.id.pasuxiOtxiText);
        relativeLayoutMesameQuest = (RelativeLayout) findViewById(R.id.relativeLayoutMesameQuest);
        relativeLayoutMeotxeQuest = (RelativeLayout) findViewById(R.id.relativeLayoutMeotxeQuest);
        imageQuestion = (ImageView) findViewById(R.id.imageQuestion);
        linearImageQuestion = (LinearLayout) findViewById(R.id.linearImageQuestion);
        firstQuestionRelative = (RelativeLayout) findViewById(R.id.firstQuestionRelative);
        secondQuestionRelative = (RelativeLayout) findViewById(R.id.secondQuestionRelative);

        countAndTimerLayout = (RelativeLayout) findViewById(R.id.countAndTimerLayout);

    }
    void makeUnclickable(){
        pasuxiErtiText.setClickable(false);
        pasuxiOriText.setClickable(false);
        pasuxiSamiText.setClickable(false);
        pasuxiOtxiText.setClickable(false);
    }
    void makeClickable(){
        pasuxiErtiText.setClickable(true);
        pasuxiOriText.setClickable(true);
        pasuxiSamiText.setClickable(true);
        pasuxiOtxiText.setClickable(true);
    }



    private String fileSaxeli = "examResultsTextFile.txt";
    private File file;
    private void createTextFileForResults() throws IOException {
        file = new File(getFilesDir() + "/" + fileSaxeli);
    }
    private void addDataToTextFile(){
        if(file.exists()){
            //Toast.makeText(this,"FILE EXISTS",Toast.LENGTH_LONG).show();
            Log.v("EXISTS",getFilesDir() + "/" + fileSaxeli);
        }
        else {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(getFilesDir() + "/" + fileSaxeli), StandardCharsets.UTF_8))) {
                writer.write("bb" + "\n");
                writer.write("bb" + "\n");
                writer.write("bb" + "\n");
                writer.write("bb" + "\n");
                writer.write("bb" + "\n");
                writer.write("5" + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void readDataFromTextFile() throws IOException {
        // Open the file
        FileInputStream fstream = new FileInputStream(getFilesDir() + "/" + fileSaxeli);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        List<String> listStrings = new ArrayList<>();
//Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            listStrings.add(strLine);
        }
        Log.d("readFrom" , String.valueOf(listStrings.get(5)));
//Close the input stream
        fstream.close();
    }


    public static final String FILE_NAME = "statistics.txt";
    public static final String FILE_NAME2 = "statistics2.txt";
    public static int correctAnswer = 0;
    public static int falseAnswer = 0;

    static int corrects = 0;
    static int falses = 0;
    public void saveNewStats() throws IOException {
        try {
            BufferedReader brTest = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            BufferedReader brTest2 = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME2));
            corrects = Integer.parseInt(brTest.readLine());
            falses = Integer.parseInt(brTest2.readLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileOutputStream fos = null;
        FileOutputStream fos2 = null;
        try{
            String toinput1 = String.valueOf(corrects + correctAnswer);
            String toinput2 = String.valueOf(falses + falseAnswer);
            Log.d("corrects" , String.valueOf(corrects));
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos2 = openFileOutput(FILE_NAME2,MODE_PRIVATE);
            fos.write(toinput1.getBytes());
            fos2.write(toinput2.getBytes());
            Log.v("SAVE",getFilesDir() + "/" + FILE_NAME);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(fos != null) {
                fos.close();
                fos2.close();
            }
        }
    }

    TextView bilet1text,bilet2text,bilet3text,bilet4text,bilet5text,bilet6text
            ,bilet7text,bilet8text,bilet9text,bilet10text,bilet11text,bilet12text,bilet13text
            ,bilet14text,bilet15text,bilet16text,bilet17text,bilet18text,bilet19text,bilet20text,
            bilet21text,bilet22text,bilet23text,bilet24text,bilet25text,bilet26text,bilet27text,bilet28text
            ,bilet29text,bilet30text;
    List<TextView> textViewsList = new ArrayList<>();

    private void initializeGridButtons(){
         bilet1text = findViewById(R.id.bilet1text);
         bilet2text = findViewById(R.id.bilet2text);
         bilet3text = findViewById(R.id.bilet3text);
         bilet4text = findViewById(R.id.bilet4text);
         bilet5text = findViewById(R.id.bilet5text);
         bilet6text = findViewById(R.id.bilet6text);
         bilet7text = findViewById(R.id.bilet7text);
         bilet8text = findViewById(R.id.bilet8text);
         bilet9text = findViewById(R.id.bilet9text);
         bilet10text = findViewById(R.id.bilet10text);
        bilet11text = findViewById(R.id.bilet11text);
        bilet12text = findViewById(R.id.bilet12text);
        bilet13text = findViewById(R.id.bilet13text);
        bilet14text = findViewById(R.id.bilet14text);
        bilet15text = findViewById(R.id.bilet15text);
        bilet16text = findViewById(R.id.bilet16text);
        bilet17text = findViewById(R.id.bilet17text);
        bilet18text = findViewById(R.id.bilet18text);
        bilet19text = findViewById(R.id.bilet19text);
        bilet20text = findViewById(R.id.bilet20text);
        bilet21text = findViewById(R.id.bilet21text);
        bilet22text = findViewById(R.id.bilet22text);
        bilet23text = findViewById(R.id.bilet23text);
        bilet24text = findViewById(R.id.bilet24text);
        bilet25text = findViewById(R.id.bilet25text);
        bilet26text = findViewById(R.id.bilet26text);
        bilet27text = findViewById(R.id.bilet27text);
        bilet28text = findViewById(R.id.bilet28text);
        bilet29text = findViewById(R.id.bilet29text);
        bilet30text = findViewById(R.id.bilet30text);

         textViewsList.add(bilet1text);
         textViewsList.add(bilet2text);
         textViewsList.add(bilet3text);
         textViewsList.add(bilet4text);
         textViewsList.add(bilet5text);
         textViewsList.add(bilet6text);
         textViewsList.add(bilet7text);
         textViewsList.add(bilet8text);
         textViewsList.add(bilet9text);
         textViewsList.add(bilet10text);
        textViewsList.add(bilet11text);
        textViewsList.add(bilet12text);
        textViewsList.add(bilet13text);
        textViewsList.add(bilet14text);
        textViewsList.add(bilet15text);
        textViewsList.add(bilet16text);
        textViewsList.add(bilet17text);
        textViewsList.add(bilet18text);
        textViewsList.add(bilet19text);
        textViewsList.add(bilet20text);
        textViewsList.add(bilet21text);
        textViewsList.add(bilet22text);
        textViewsList.add(bilet23text);
        textViewsList.add(bilet24text);
        textViewsList.add(bilet25text);
        textViewsList.add(bilet26text);
        textViewsList.add(bilet27text);
        textViewsList.add(bilet28text);
        textViewsList.add(bilet29text);
        textViewsList.add(bilet30text);



    }
    private void arrageUI(int i){
        //database.getQuestion(i);

        //ADDING VALUES FOR EXAMFINISH LAYOUT ONCLICKS
        _idofBileti.add(i);

        database.getAnswers(i);
      Log.v("DATABASE VALUES",database.questionValue + " @@ " +
              database.answerOneValue + " @@ " +
              database.answerTwoValue + " @@ " +
              database.answerThreeValue + " @@ " +
              database.answerFourValue + " @@ ");
            if(database.imageValue.equals("")){
                linearImageQuestion.setVisibility(View.GONE);
               /* String imgURL  = "http://nelazviadi.ge/exams/images/" + database.imageValue;
                new DownLoadImageTask(imageQuestion).execute(imgURL);*/
            }
            else{
                linearImageQuestion.setVisibility(View.VISIBLE);
                //int id = getResources().getIdentifier("com.badri.drivingl:drawable/" + "bdr" + database.imageValue + String.valueOf(i), null, null);
                //int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue , "id", "com.badri.drivingl");
                int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
                Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
                imageQuestion.setImageResource(id);
                //(database.questionValue.equals(database.imageValue))
                /*String imgURL  = "http://nelazviadi.ge/exams/images/" + database.imageValue;
                new DownLoadImageTask(imageQuestion).execute(imgURL);*/

            }
            shekitxvatext.setText(database.questionValue);

            if(!database.answerOneValue.equals(""))
                pasuxiErtiText.setText(database.answerOneValue);
            else
                pasuxiErtiText.setVisibility(View.GONE);
            if(!database.answerTwoValue.equals(""))
                pasuxiOriText.setText(database.answerTwoValue);
            else
                pasuxiOriText.setVisibility(View.GONE);
            if(!database.answerThreeValue.equals("")){
                relativeLayoutMesameQuest.setVisibility(View.VISIBLE);
                pasuxiSamiText.setVisibility(View.VISIBLE);
                pasuxiSamiText.setText(database.answerThreeValue);
            }
            else{
                pasuxiSamiText.setVisibility(View.GONE);
                relativeLayoutMesameQuest.setVisibility(View.GONE);
            }
            if(!database.answerFourValue.equals("")){
                relativeLayoutMeotxeQuest.setVisibility(View.VISIBLE);
                pasuxiOtxiText.setVisibility(View.VISIBLE);
                pasuxiOtxiText.setText(database.answerFourValue);
            }
            else{
                pasuxiOtxiText.setVisibility(View.GONE);
                relativeLayoutMeotxeQuest.setVisibility(View.GONE);
            }
            if(!database.correctAnswerValue.equals(""))
                correctAnswerValue = database.correctAnswerValue;
    }
    /*int[] biletebi1 = {2,3,4,5,6,7,8,9,10,11,20,21,22,23,24,25,26,27,28,29,30,31,32,33,
                        34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55
                        ,56,57,58,59,60,61,62,63,64,65,66,67};*/



    public int shemtxvevitiNumber;
    private List<Integer> notRepeat = new ArrayList<Integer>();;
    public void getRandom(){
        /* int rnd = new Random().nextInt(biletebi1.length - 1);
        shemtxvevitiNumber = biletebi1[rnd];
        Log.d("RANDOM NUM",String.valueOf(shemtxvevitiNumber));*/
        database.burjo();
        Random r = new Random();
        int low = 1; //INCLUSIVE
        int high = database.burjandze + 1; //EXCLUSIVE max category 1,2,3,4
        shemtxvevitiNumber = r.nextInt(high-low) + low;

        Log.d("RANDOM NUM",String.valueOf(shemtxvevitiNumber));
    }


    void startTimer(){
        cTimer = new CountDownTimer(1800000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTimerText.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
            }
        };
        cTimer.start();
    }

}