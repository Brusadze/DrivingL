package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class uploadAct extends AppCompatActivity {

    EditText questionInput,answerOneInput,answerTwoInput,answerThreeInput,answerFourInput,correctAnswerInput;
    Button addQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        final SampleSQLiteDBHelper db = new SampleSQLiteDBHelper(this);
        questionInput = (EditText) findViewById(R.id.questionInput);
        answerOneInput = (EditText) findViewById(R.id.answerOneInput);
        answerTwoInput = (EditText) findViewById(R.id.answerTwoInput);
        answerThreeInput = (EditText) findViewById(R.id.answerThreeInput);
        answerFourInput = (EditText) findViewById(R.id.answerFourInput);
        correctAnswerInput = (EditText) findViewById(R.id.correctAnswerInput);

        addQuizButton = (Button) findViewById(R.id.addQuizButton);
        /*addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.add(questionInput.getText().toString(),
                        answerOneInput.getText().toString(),
                        answerTwoInput.getText().toString(),
                        answerThreeInput.getText().toString(),
                        answerFourInput.getText().toString(),
                        correctAnswerInput.getText().toString());
            }
        });*/



    }
}