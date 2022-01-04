package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class finishexamLayout extends ExamActivity{

    Database database;

    RelativeLayout statPirveliKitxva;
    Button mtavarzeGadasvlaButton;

    /*private ArrayList<Integer> gotquestionId = new ArrayList<Integer>();
    private ArrayList<String> gotgacemuliPasuxi = new ArrayList<String>();*/
    int sworiPasuxebiExamBolos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishexam_layout);

        TextView textViewSworiPasuxi = findViewById(R.id.textViewSworiPasuxi);
        mtavarzeGadasvlaButton = (Button)findViewById(R.id.mtavarzeGadasvlaButton);

        database = new Database(this);
        database.open();

        Intent intent = getIntent();
       /* gotgacemuliPasuxi = intent.getStringArrayListExtra("gacemuliPasuxi");
        gotquestionId = intent.getIntegerArrayListExtra("questionId");*/
        sworiPasuxebiExamBolos = intent.getIntExtra("sworiPasuxebiExamBolos" , 0);
        textViewSworiPasuxi.setText("შენ გაქვს " + sworiPasuxebiExamBolos + " არასწორი პასუხი");

        mtavarzeGadasvlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finishexamLayout.this, MainActivity.class);
                intent.putExtra("sworiPasuxebiExamBolos", sworiPasuxebiExamBolos);
                //intent.putExtra("gacemuliPasuxi", gacemuliPasuxi);
                startActivity(intent);
            }
        });

        Log.d("GOTS" , String.valueOf(sworiPasuxebiExamBolos) + "SWORI PASUXi");

        /*Log.d("GOTS" , gotgacemuliPasuxi.toString());
        Log.d("GOTS" , gotquestionId.toString());

        database.getQuestion(gotquestionId.get(0));
        database.getAnswers(gotquestionId.get(0));

        Log.d("GOTS" , database.correctAnswerValue);

        if(database.correctAnswerValue.equals(gotgacemuliPasuxi.get(0)))
            statPirveliKitxva.setBackgroundColor(Color.GREEN);
        else if(!database.correctAnswerValue.equals(gotgacemuliPasuxi.get(0)))
            statPirveliKitxva.setBackgroundColor(Color.RED);
*/
    }
}