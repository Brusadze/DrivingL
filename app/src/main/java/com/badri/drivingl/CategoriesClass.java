package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CategoriesClass extends AppCompatActivity {

    LinearLayout linearImageQuestionBilet,scrollBiletebiCategory;
    ImageView imageQuestionBilet;
    TextView shekitxvatextBilet,pasuxiErtiTextBilet,pasuxiOriTextBilet,pasuxiSamiTextBilet,pasuxiOtxiTextBilet;
    RelativeLayout firstQuestionRelativeBilet,secondQuestionRelativeBilet,relativeLayoutMesameQuestBilet
            ,relativeLayoutMeotxeQuestBilet;


    Database database;
    //stats database
    Database databaseStats;
    DBManager dbManager;

    int[] category1 = {37,62,123,125,130,204,208,260,263,334,335,390,391,
            471,878,879,883,884,887,888,889,891,892,893,895,896,900,902,905,
            906,910,914,916,918,919,920,921,922,923,924,925,949,961,962,1053,1056,
            1059,1089,1091,1092,1093,1098,1106,1114,1125,1126,1219,1220,1224,1225,
            1226,1227,1228,1230,1340,1341,1342,1379,1380,1482,1486,1572};
    int[] category2 = {79,829,999,1000,1001,1069,1318,1321,1322,1325,1327,1328,1330,1331,1332,1336,1337,1338,1339,1484};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_class);

        database = new Database(this);
        database.open();

        dbManager = new DBManager(this);
        dbManager.open();
        //initializeEnv();
        //arrangeUi(25);

        Intent mIntent = getIntent();
        int switchMode = mIntent.getIntExtra("switchMode", 1);
        if(switchMode == 1)
            for(int i = 0; i < category1.length; i++){
                layoutInflateUserExams(category1[i]);
            }
        if(switchMode == 2)
            for(int i = 0; i < category2.length; i++){
                layoutInflateUserExams(category2[i]);
            }

    }
    private void initializeEnv(){
        scrollBiletebiCategory = findViewById(R.id.scrollBiletebiCategory);
        linearImageQuestionBilet = findViewById(R.id.linearImageQuestionBilet);
        imageQuestionBilet = findViewById(R.id.imageQuestionBilet);
        shekitxvatextBilet = findViewById(R.id.shekitxvatextBilet);
        pasuxiErtiTextBilet = findViewById(R.id.pasuxiErtiTextBilet);
        pasuxiOriTextBilet = findViewById(R.id.pasuxiOriTextBilet);
        pasuxiSamiTextBilet = findViewById(R.id.pasuxiSamiTextBilet);
        pasuxiOtxiTextBilet = findViewById(R.id.pasuxiOtxiTextBilet);
        firstQuestionRelativeBilet = findViewById(R.id.firstQuestionRelativeBilet);
        secondQuestionRelativeBilet = findViewById(R.id.secondQuestionRelativeBilet);
        relativeLayoutMesameQuestBilet = findViewById(R.id.relativeLayoutMesameQuestBilet);
        relativeLayoutMeotxeQuestBilet = findViewById(R.id.relativeLayoutMeotxeQuestBilet);

    }

    String correctAnswerValue;
    private void arrangeUi(int i){
        database.getAnswers(i);
        if(database.imageValue.equals("")){
            linearImageQuestionBilet.setVisibility(View.GONE);
        }
        else{
            linearImageQuestionBilet.setVisibility(View.VISIBLE);
            int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
            Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
            imageQuestionBilet.setImageResource(id);
        }
        shekitxvatextBilet.setText(database.questionValue);

        if(!database.answerOneValue.equals(""))
            pasuxiErtiTextBilet.setText(database.answerOneValue);
        else
            pasuxiErtiTextBilet.setVisibility(View.GONE);
        if(!database.answerTwoValue.equals(""))
            pasuxiOriTextBilet.setText(database.answerTwoValue);
        else
            pasuxiOriTextBilet.setVisibility(View.GONE);
        if(!database.answerThreeValue.equals("")){
            relativeLayoutMesameQuestBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setText(database.answerThreeValue);
        }
        else{
            pasuxiSamiTextBilet.setVisibility(View.GONE);
            relativeLayoutMesameQuestBilet.setVisibility(View.GONE);
        }
        if(!database.answerFourValue.equals("")){
            relativeLayoutMeotxeQuestBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setText(database.answerFourValue);
        }
        else{
            pasuxiOtxiTextBilet.setVisibility(View.GONE);
            relativeLayoutMeotxeQuestBilet.setVisibility(View.GONE);
        }
        if(!database.correctAnswerValue.equals(""))
            correctAnswerValue = database.correctAnswerValue;
    }

    @SuppressLint("SetTextI18n")
    void layoutInflateUserExams(int i){

        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.biletiscroll, null, false);

        scrollBiletebiCategory = (LinearLayout) findViewById(R.id.scrollBiletebiCategory);
        scrollBiletebiCategory.addView(layout);


        linearImageQuestionBilet = layout.findViewById(R.id.linearImageQuestionBilet);
        imageQuestionBilet = layout.findViewById(R.id.imageQuestionBilet);
        shekitxvatextBilet = layout.findViewById(R.id.shekitxvatextBilet);
        pasuxiErtiTextBilet = layout.findViewById(R.id.pasuxiErtiTextBilet);
        pasuxiOriTextBilet = layout.findViewById(R.id.pasuxiOriTextBilet);
        pasuxiSamiTextBilet = layout.findViewById(R.id.pasuxiSamiTextBilet);
        pasuxiOtxiTextBilet = layout.findViewById(R.id.pasuxiOtxiTextBilet);
        firstQuestionRelativeBilet = layout.findViewById(R.id.firstQuestionRelativeBilet);
        secondQuestionRelativeBilet = layout.findViewById(R.id.secondQuestionRelativeBilet);
        relativeLayoutMesameQuestBilet = layout.findViewById(R.id.relativeLayoutMesameQuestBilet);
        relativeLayoutMeotxeQuestBilet = layout.findViewById(R.id.relativeLayoutMeotxeQuestBilet);

        database.getAnswersBiletebistvis(i);

        if(database.imageValue.equals("")){
            linearImageQuestionBilet.setVisibility(View.GONE);
        }
        else{
            linearImageQuestionBilet.setVisibility(View.VISIBLE);
            int id = getResources().getIdentifier("drawable/" + "bdr" + database.imageValue.substring(0,database.imageValue.length()-4), "drawable", getPackageName());
            Log.d("RANDOM NUM",database.imageValue.substring(0,database.imageValue.length()-4) + " " + id);
            imageQuestionBilet.setImageResource(id);
        }
        shekitxvatextBilet.setText(database.questionValue);

        if(!database.answerOneValue.equals(""))
            pasuxiErtiTextBilet.setText(database.answerOneValue);
        else
            pasuxiErtiTextBilet.setVisibility(View.GONE);
        if(!database.answerTwoValue.equals(""))
            pasuxiOriTextBilet.setText(database.answerTwoValue);
        else
            pasuxiOriTextBilet.setVisibility(View.GONE);
        if(!database.answerThreeValue.equals("")){
            relativeLayoutMesameQuestBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setVisibility(View.VISIBLE);
            pasuxiSamiTextBilet.setText(database.answerThreeValue);
        }
        else{
            pasuxiSamiTextBilet.setVisibility(View.GONE);
            relativeLayoutMesameQuestBilet.setVisibility(View.GONE);
        }
        if(!database.answerFourValue.equals("")){
            relativeLayoutMeotxeQuestBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setVisibility(View.VISIBLE);
            pasuxiOtxiTextBilet.setText(database.answerFourValue);
        }
        else{
            pasuxiOtxiTextBilet.setVisibility(View.GONE);
            relativeLayoutMeotxeQuestBilet.setVisibility(View.GONE);
        }
        if(!database.correctAnswerValue.equals(""))
            correctAnswerValue = database.correctAnswerValue;

    }
}