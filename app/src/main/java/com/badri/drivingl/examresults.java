package com.badri.drivingl;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class examresults extends MainActivity {


    public TextView testresultText,sworiarasworiText;
    public View qvedaXaziColor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examresults);

        testresultText = findViewById(R.id.testresultText);
        sworiarasworiText = findViewById(R.id.sworiarasworiText);

        qvedaXaziColor = findViewById(R.id.qvedaXaziColor);

        createUi();
    }
    @SuppressLint("SetTextI18n")
    public void createUi(){

        testresultText.setText("CHANAREBULI");
        sworiarasworiText.setText("25 SWORI");
        qvedaXaziColor.setBackgroundColor(Color.GREEN);


    }

}
