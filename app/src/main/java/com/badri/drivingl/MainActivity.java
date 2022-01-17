package com.badri.drivingl;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends YouTubeBaseActivity{

    private static String videoId;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    TextView nameSurname , varjishiGamocda, pirvelisTextProcent
            ,meoretxtProcent,mesametxtProcent,albatoba,mycompany;
    View mwvaneLine;
    CardView cardViewOfProgress;
    examresults _examresults;
    LinearLayout userExamResultsLinear;
    PieChart pieChart;



    int size;
    private DBManager dbManager;

    private SimpleCursorAdapter adapter;
    //to check firstrun
    SharedPreferences prefs = null;

    //VALUES FOR PROGRESSBAR
    private int widthProgress;
    private int heightProgress;

    private int profilp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView profilePic = findViewById(R.id.profilePic);

        prefs = getSharedPreferences("com.badri.drivingl", MODE_PRIVATE);


        checkFirstRun();


        //nameSurname = findViewById(R.id.nameSurname);
        mycompany = findViewById(R.id.mycompany);
        varjishiGamocda = findViewById(R.id.varjishiGamocda);
        RelativeLayout firstLayout = findViewById(R.id.firstLayout);
        RelativeLayout secondLayout = findViewById(R.id.secondLayout);
        RelativeLayout chaabarebTuVer = findViewById(R.id.chaabarebTuVer);
        RelativeLayout thirdLayout = findViewById(R.id.thirdLayout);
        RelativeLayout kitxvisNishani = findViewById(R.id.kitxvisNishani);
        cardViewOfProgress = findViewById(R.id.cardViewOfProgress); //CARDVIEW OF PROGRESS
        //RelativeLayout biletebiLayout = findViewById(R.id.biletebiLayout);
        userExamResultsLinear = findViewById(R.id.userExamResultsLinear);
        pirvelisTextProcent = findViewById(R.id.pirvelisTextProcent);
        mwvaneLine = findViewById(R.id.mwvaneLine);
        meoretxtProcent = findViewById(R.id.meoretxtProcent);
        mesametxtProcent = findViewById(R.id.mesametxtProcent);
        albatoba = findViewById(R.id.albatoba);
        pieChart = findViewById(R.id.piechart);

        /*String appDataPath = this.getApplicationInfo().dataDir;
        File file = new File(appDataPath + "/databases/examData.dtb");
        if(file.exists())
           Log.d("databasecopy", "FILE EXISTS ");
        else{
            databaseCopy();
            Log.d("databasecopy", "SUCCESFULLY COPIED");
        }


        //COPYING DATABASE FROM ASSETS TO DATA/DATABASES/
        databaseCopy();
        //statsDatabaseCopy();*/
        //databaseCopy();
        dbManager = new DBManager(this);
        dbManager.open();



        chaabarebsTuVera();
        setPieChartData();
        pieChart.setInnerPaddingColor(Color.parseColor("#25343d"));



        //GETTING WIDTH AND HEIGHT OF CARDVIEW OF PROGRESS
        ViewTreeObserver vto = cardViewOfProgress.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    cardViewOfProgress.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    cardViewOfProgress.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                widthProgress  = cardViewOfProgress.getMeasuredWidth();
                heightProgress = cardViewOfProgress.getMeasuredHeight();
                Log.d("CARD",widthProgress + "width height" + heightProgress);
                startWidthAnimation();
            }
        });

        mycompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://facebook.com/badri.rusadze";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        kitxvisNishani.setVisibility(View.GONE);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, progress.class);
                startActivity(intent);*/
                profilp++;
                if(profilp % 2 != 0)
                    kitxvisNishani.setVisibility(View.VISIBLE);
                else
                    kitxvisNishani.setVisibility(View.GONE);
            }
        });
        thirdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Biletebi.class);
                startActivity(intent);
            }
        });

        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SENDING EXAM MODE 1
                Intent intent = new Intent(MainActivity.this, ExamActivity.class);
                intent.putExtra("switchMode", 1);
                startActivity(intent);
            }
        });
        secondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SENDING PRACTICE MODE 0
                Intent intent = new Intent(MainActivity.this, ExamActivity.class);
                intent.putExtra("switchMode", 0);
                startActivity(intent);
            }
        });


        //nameSurname.setText("გამარჯობა,");
        profilePic.setImageResource(R.drawable.questionmark);



        startCountAnimation(pirvelisTextProcent, 73);
        startCountAnimation(meoretxtProcent, 17);
        startCountAnimation(mesametxtProcent, 10);


        try {
            createTextFileForResults();
            readDataFromTextFile();
            Log.d("logi" , String.valueOf(listStrings));
            //Log.d("logi" , String.valueOf(listStrings.get(listStrings.size()-1)));
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Button buttonPlay = findViewById(R.id.button);
        //final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtube_player);

        try {
            saveStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadStats();
        try {
            saveNewStats();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                youTubePlayer.loadVideo("7GdsftXc0yU");
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        /*size = chaabarebTuVer.getWidth();
        Log.v("SIZE ", String.valueOf(size));*/



    }
    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            databaseCopy();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }


    private void startCountAnimation(TextView txtView , int _lastNum) {
        ValueAnimator animator = ValueAnimator.ofInt(0, _lastNum);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("SetTextI18n")
            public void onAnimationUpdate(ValueAnimator animation) {
                txtView.setText(animation.getAnimatedValue().toString() + " %");
            }
        });
        animator.start();
    }

    private float chaabarebsTuVera(){
        loadStats();
        //ramden pasuxzemaq danacheri xeli
        float jami = loadStatsCorrect + loadStatsFalse;
        float resultati = (loadStatsCorrect / jami) * 100;
        Log.d("resultati", Math.round(resultati) + " " + jami + "<jami" + loadStatsCorrect + "< corrects");
        return  resultati;
    }

    @SuppressLint("SetTextI18n")
    private void startWidthAnimation(){
        albatoba.setText("ჩაბარების ალბათობა არის " + Math.round((widthProgress * chaabarebsTuVera())/1000) + " %");
        ValueAnimator widthAnimator = ValueAnimator.ofInt(mwvaneLine.getWidth(), Math.round((widthProgress * chaabarebsTuVera())/100));
        Log.d("PROCENT", String.valueOf(Math.round((widthProgress * chaabarebsTuVera())/100)) + " es aris witlis sigrdzis 33 procenti");
        widthAnimator.setDuration(8000);
        widthAnimator.setInterpolator(new DecelerateInterpolator());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mwvaneLine.getLayoutParams().width = (int) animation.getAnimatedValue();
                mwvaneLine.requestLayout();
            }
        });
        widthAnimator.start();
    }







    void databaseCopy(){
        //get context by calling "this" in activity or getActivity() in fragment
        //call this if API level is lower than 17  String appDataPath = "/data/data/" + context.getPackageName() + "/databases/"
        String appDataPath = this.getApplicationInfo().dataDir;

        File dbFolder = new File(appDataPath + "/databases");//Make sure the /databases folder exists
        dbFolder.mkdir();//This can be called multiple times.

        File dbFilePath = new File(appDataPath + "/databases/examData.dtb");

        try {
            InputStream inputStream = this.getAssets().open("examData.dtb");
            OutputStream outputStream = new FileOutputStream(dbFilePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer))>0)
            {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e){
            //handle
        }

    }
    void statsDatabaseCopy() {
        //get context by calling "this" in activity or getActivity() in fragment
        //call this if API level is lower than 17  String appDataPath = "/data/data/" + context.getPackageName() + "/databases/"
        String appDataPath = this.getApplicationInfo().dataDir;

        File dbFolder = new File(appDataPath + "/databases");//Make sure the /databases folder exists
        dbFolder.mkdir();//This can be called multiple times.

        File dbFilePath = new File(appDataPath + "/databases/statsData.dtb");

        try {
            InputStream inputStream = this.getAssets().open("statsData.dtb");
            OutputStream outputStream = new FileOutputStream(dbFilePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            //handle
        }

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


        FileOutputStream fos= null;
        FileOutputStream fos2 = null;
        try{
            String toinput1 = String.valueOf(corrects + correctAnswer);
            String toinput2 = String.valueOf(falses + falseAnswer);
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos2 = openFileOutput(FILE_NAME2,MODE_PRIVATE);
            fos.write(toinput1.getBytes());
            fos2.write(toinput2.getBytes());
            Log.v("SAVE",getFilesDir() + "/" + FILE_NAME);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                fos.close();
            }
        }
    }

    void saveStats() throws IOException {
        File file = new File(getFilesDir() + "/" + FILE_NAME);
        File file2 = new File(getFilesDir() + "/" + FILE_NAME2);
        if(file.exists()){
            //Toast.makeText(this,"FILE EXISTS",Toast.LENGTH_LONG).show();
            Log.v("SAVE",getFilesDir() + "/" + FILE_NAME);
            loadStats();
        }
        else {
            FileOutputStream fos = null;
            FileOutputStream fos2 = null;
            FileOutputStream fos3 = null;
            try{
                fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
                fos2 = openFileOutput(FILE_NAME2,MODE_PRIVATE);
                String toinput1 = "0";
                String toinput2 = "0";
                fos.write(toinput1.getBytes());
                fos2.write(toinput2.getBytes());
                Log.v("SAVE",getFilesDir() + "/" + FILE_NAME);
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(fos != null) {
                    fos.close();
                }
            }

        }
        if(file2.exists()){
            //Toast.makeText(this,"FILE EXISTS",Toast.LENGTH_LONG).show();
            Log.v("SAVE",getFilesDir() + "/" + FILE_NAME);
            loadStats();
        }else {
            FileOutputStream fos = null;
            try{
                fos = openFileOutput(FILE_NAME2,MODE_PRIVATE);
                Log.v("SAVE",getFilesDir() + "/" + FILE_NAME2);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } finally {
                if(fos != null) {
                    fos.close();
                }
            }
        }

    }

    private final String fileSaxeli = "examResultTextFile.txt";
    List<String> listStrings = new ArrayList<>();
    private File file;
    private void addDataToTextFile(int corrects){
        if(file.exists()){
            //Toast.makeText(this,"FILE EXISTS",Toast.LENGTH_LONG).show();
            Log.v("EXISTS",getFilesDir() + "/" + fileSaxeli);
            FileWriter fw = null; //the true will append the new data
            try {
                fw = new FileWriter(getFilesDir() + "/" + fileSaxeli,true);
                fw.write(corrects + "\n");//appends the string to the file
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(getFilesDir() + "/" + fileSaxeli), StandardCharsets.UTF_8))) {
                writer.write(corrects + "\n");


            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        else {
            Log.v("NOTEXISTS",getFilesDir() + "/" + fileSaxeli);
        }
    }
    private void createTextFileForResults() throws IOException {
        file = new File(getFilesDir() + "/" + fileSaxeli);
        file.createNewFile(); // if file already exists will do nothing
    }
    private void readDataFromTextFile() throws IOException {
        // Open the file
        FileInputStream fstream = new FileInputStream(getFilesDir() + "/" + fileSaxeli);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            listStrings.add(strLine);
        }
        Log.d("readFrom" , String.valueOf(listStrings));
        if(listStrings != null){
            for (int i = 0; i < listStrings.size(); i++) {
                Log.d("forloop" , listStrings.get(i));
                layoutInflateUserExams(Integer.parseInt(listStrings.get(i)));
            }
        }



        //Close the input stream
        fstream.close();
    }

    public static int loadStatsCorrect;
    public static int loadStatsFalse;
    public void loadStats(){
        try {
            BufferedReader brTest = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            BufferedReader brTest2 = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME2));
            loadStatsCorrect = Integer.parseInt(brTest.readLine());
            loadStatsFalse = Integer.parseInt(brTest2.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    void layoutInflateUserExams(int swori){
            String shedegiText = "ჩაბარებული";
            String sworiText = String.valueOf(swori);

            int arasworipasuxi = 30 - swori;

            LayoutInflater inflater = LayoutInflater.from(this);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.examresults, null, false);

            userExamResultsLinear = (LinearLayout) findViewById(R.id.userExamResultsLinear);
            userExamResultsLinear.addView(layout);

            TextView testresultText = layout.findViewById(R.id.testresultText);
            TextView sworiarasworiText = layout.findViewById(R.id.sworiarasworiText);
            View qvedaXaziColor = layout.findViewById(R.id.qvedaXaziColor);


            if(30 - swori > 3){
                shedegiText = "ჩაუბარებელი";
                qvedaXaziColor.setBackgroundColor(Color.RED);
            }
            else{
                shedegiText = "ჩაბარებული";
                qvedaXaziColor.setBackgroundColor(Color.GREEN);
            }


            testresultText.setText(" ტესტის შედეგი : " + shedegiText);
            sworiarasworiText.setText(" სწორი პასუხი : " + sworiText + " არასწორი პასუხი : " + arasworipasuxi);
            qvedaXaziColor.setBackgroundColor(Color.RED);



    }
    void setPieChartData(){
        TextView tvR = findViewById(R.id.tvR);
        TextView tvPython = findViewById(R.id.tvPython);

        PieChart pieChart = findViewById(R.id.piechart);

        TextView statsfinished = findViewById(R.id.statsfinished);
        String FILE_NAME = "statistics.txt";
        String FILE_NAME2 = "statistics2.txt";

        int loadStatsCorrect = 0;
        int loadStatsFalse = 0;
            try {
                BufferedReader brTest = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
                BufferedReader brTest2 = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME2));
                loadStatsCorrect = Integer.parseInt(brTest.readLine());
                loadStatsFalse = Integer.parseInt(brTest2.readLine());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // Set the percentage of language used
            tvR.setText(Integer.toString(loadStatsCorrect));
            tvPython.setText(Integer.toString(loadStatsFalse));
        /*tvCPP.setText(Integer.toString(5));
        tvJava.setText(Integer.toString(25));*/

            // Set the data and color to the pie chart
            pieChart.addPieSlice(
                    new PieModel(
                            "სწორი პასუხები",
                            Integer.parseInt(tvR.getText().toString()),
                            Color.parseColor("#2BFFAB")));
            pieChart.addPieSlice(
                    new PieModel(
                            "არასწორი პასუხები",
                            Integer.parseInt(tvPython.getText().toString()),
                            Color.parseColor("#E0183D")));
            tvR.setText("სწორი  - " + loadStatsCorrect);
            tvPython.setText("არასწორი  - " + loadStatsFalse);
        /*pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));*/

            // To animate the pie chart
            pieChart.startAnimation();

    }

}
