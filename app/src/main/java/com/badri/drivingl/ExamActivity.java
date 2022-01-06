package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;



import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.badri.drivingl.R.layout.activity_exam;

public class ExamActivity extends AppCompatActivity {


    TextView shekitxvatext,pasuxiErtiText,pasuxiOriText,pasuxiSamiText
            ,pasuxiOtxiText,countdownTimerText,numberofbiletiText;
    Button nextQuestionButton;

    Database database;
    //stats database
    Database databaseStats;
    DBManager dbManager;
    VideoView videoView;
    RelativeLayout relativeLayoutMesameQuest,relativeLayoutMeotxeQuest,
            firstQuestionRelative,secondQuestionRelative , countAndTimerLayout, nextbuttonCard;
    ImageView imageQuestion;
    LinearLayout linearImageQuestion;

    static int[] myIntArray = new int[30];
    private ArrayList<Integer> questionId = new ArrayList<Integer>();
    private ArrayList<String> gacemuliPasuxi = new ArrayList<String>();

    public int sworiPasuxebiExamBolos = 0;
    boolean trueAnswer;

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

        dbManager.insertExamFinishData(0);

        RelativeLayout videoLinear =  findViewById(R.id.videoLinear);
        RelativeLayout nextbuttonCard = findViewById(R.id.nextbuttonCard);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setMediaController(null);
        LinearLayout linearView = findViewById(R.id.linearView);
        videoView.setVideoPath("https://java106.000webhostapp.com/videoplayback.mp4");
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
        Log.d("Shemtxv" , String.valueOf(shemtxvevitiNumber));
        Integer[] excludeThis = {70,71,72,74,82,83,84,85,86,87};
        if(Arrays.asList(excludeThis).contains(shemtxvevitiNumber)) {
            getRandom();
        }
        Intent mIntent = getIntent();
        int switchMode = mIntent.getIntExtra("switchMode", 0);

        // LOAD switchMode 1 or 0 where 1 is [EXAM] and 0 is [PRACTICE]
        Log.d("switch", String.valueOf(switchMode));

        arrageUI(shemtxvevitiNumber); //ARRANGES UI WITH _id
        //questionId[0] = shemtxvevitiNumber;





        //CHECKING IF is exam mode or practice mode.
        if(switchMode == 1){
            //EXAM MODE
            startTimer();
            nextbuttonCard.setVisibility(View.GONE);
            pasuxiErtiText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
                        Log.d("FALSE++",String.valueOf(falseAnswer));
                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        startActivity(intent);
                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiOriText.setOnClickListener(new View.OnClickListener() {
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

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        dbManager.insertExamFinishData(sworiPasuxebiExamBolos);
                        startActivity(intent);
                    }

                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiSamiText.setOnClickListener(new View.OnClickListener() {
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

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS
                        startActivity(intent);
                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });
            pasuxiOtxiText.setOnClickListener(new View.OnClickListener() {
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

                        countingBiletebi();
                        try {
                            saveNewStats();
                            falseAnswer = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(textNumber > 30){
                        Intent intent = new Intent(ExamActivity.this, finishexamLayout.class);
                        /*intent.putIntegerArrayListExtra("questionId",questionId);
                        intent.putStringArrayListExtra("gacemuliPasuxi",gacemuliPasuxi);*/
                        intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                        //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);

                        //ADD VALUE TO STATS DATABASE FOR SCROLLVIEWS

                        startActivity(intent);
                    }
                    getRandom();
                    arrageUI(shemtxvevitiNumber);
                }
            });


        }else if(switchMode == 0){
            //PRACTICE MODE
            //startTimer();
            countdownTimerText.setVisibility(View.GONE);
            nextbuttonCard.setVisibility(View.VISIBLE);
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

                if(trueAnswer)
                    myIntArray[textNumber - 2] = 1;
                else if (!trueAnswer)
                    myIntArray[textNumber - 2] = 0;


                Log.d("ARRAY", Arrays.toString(myIntArray));

                firstQuestionRelative.setBackgroundResource(R.drawable.pasuxi);
                secondQuestionRelative.setBackgroundResource(R.drawable.pasuxi);
                relativeLayoutMesameQuest.setBackgroundResource(R.drawable.pasuxi);
                relativeLayoutMeotxeQuest.setBackgroundResource(R.drawable.pasuxi);
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
    void initializeEnvironment(){
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
    private void arrageUI(int i){
        database.getQuestion(i);
        database.getAnswers(i);
      Log.v("DATABASE VALUES",database.questionValue + " @@ " +
              database.answerOneValue + " @@ " +
              database.answerTwoValue + " @@ " +
              database.answerThreeValue + " @@ " +
              database.answerFourValue + " @@ ");
            if(database.imageValue.equals("") || database.imageValue.isEmpty()){
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
            if(!database.questionValue.equals(""))
                shekitxvatext.setText(database.questionValue);
            else
                shekitxvatext.setVisibility(View.GONE);
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