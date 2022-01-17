package com.badri.drivingl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Biletebi extends AppCompatActivity {

    TextView bileti1,bileti2,bileti3,bileti4,bileti5,
            bileti6,bileti7,bileti8,bileti9,bileti10,
            bileti11,bileti12,bileti13,bileti14,bileti15,
            bileti16,bileti17,bileti18,bileti19,bileti20,
            bileti21,bileti22,bileti23,bileti24,bileti25,
            bileti26,bileti27,bileti28,bileti29,bileti30,bileti31,bileti32;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biletebi);

        initializeEnvironment();
        addOnclickListeners();



    }

    private void initializeEnvironment(){
        bileti1 = findViewById(R.id.bileti1);
        bileti2 = findViewById(R.id.bileti2);
        bileti3 = findViewById(R.id.bileti3);
        bileti4 = findViewById(R.id.bileti4);
        bileti5 = findViewById(R.id.bileti5);
        bileti6 = findViewById(R.id.bileti6);
        bileti7 = findViewById(R.id.bileti7);
        bileti8 = findViewById(R.id.bileti8);
        bileti9 = findViewById(R.id.bileti9);
        bileti10 = findViewById(R.id.bileti10);
        bileti11 = findViewById(R.id.bileti11);
        bileti12 = findViewById(R.id.bileti12);
        bileti13 = findViewById(R.id.bileti13);
        bileti14 = findViewById(R.id.bileti14);
        bileti15 = findViewById(R.id.bileti15);
        bileti16 = findViewById(R.id.bileti16);
        bileti17 = findViewById(R.id.bileti17);
        bileti18 = findViewById(R.id.bileti18);
        bileti19 = findViewById(R.id.bileti19);
        bileti20 = findViewById(R.id.bileti20);
        bileti21 = findViewById(R.id.bileti21);
        bileti22 = findViewById(R.id.bileti22);
        bileti23 = findViewById(R.id.bileti23);
        bileti24 = findViewById(R.id.bileti24);
        bileti25 = findViewById(R.id.bileti25);
        bileti26 = findViewById(R.id.bileti26);
        bileti27 = findViewById(R.id.bileti27);
        bileti28 = findViewById(R.id.bileti28);
        bileti29 = findViewById(R.id.bileti29);
        bileti30 = findViewById(R.id.bileti30);
        bileti31 = findViewById(R.id.bileti31);
        bileti32 = findViewById(R.id.bileti32);
    }
    private void addOnclickListeners(){
        bileti1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 1);
                startActivity(intent);
            }
        });
        bileti2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 2);
                startActivity(intent);
            }
        });
        bileti3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 3);
                startActivity(intent);
            }
        });
        bileti4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 4);
                startActivity(intent);
            }
        });
        bileti5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 5);
                startActivity(intent);
            }
        });
        bileti6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 6);
                startActivity(intent);
            }
        });
        bileti7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 7);
                startActivity(intent);
            }
        });
        bileti8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 8);
                startActivity(intent);
            }
        });
        bileti9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 9);
                startActivity(intent);
            }
        });
        bileti10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 10);
                startActivity(intent);
            }
        });
        bileti11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 11);
                startActivity(intent);
            }
        });
        bileti12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 12);
                startActivity(intent);
            }
        });
        bileti13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 13);
                startActivity(intent);
            }
        });
        bileti14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 14);
                startActivity(intent);
            }
        });
        bileti15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 15);
                startActivity(intent);
            }
        });
        bileti16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 16);
                startActivity(intent);
            }
        });
        bileti17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 17);
                startActivity(intent);
            }
        });
        bileti18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 18);
                startActivity(intent);
            }
        });
        bileti19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 19);
                startActivity(intent);
            }
        });
        bileti20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 20);
                startActivity(intent);
            }
        });
        bileti21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 21);
                startActivity(intent);
            }
        });
        bileti22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 22);
                startActivity(intent);
            }
        });
        bileti23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 23);
                startActivity(intent);
            }
        });
        bileti24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 24);
                startActivity(intent);
            }
        });
        bileti25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 25);
                startActivity(intent);
            }
        });
        bileti26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 26);
                startActivity(intent);
            }
        });
        bileti27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 27);
                startActivity(intent);
            }
        });
        bileti28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 28);
                startActivity(intent);
            }
        });
        bileti29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 29);
                startActivity(intent);
            }
        });
        bileti30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 30);
                startActivity(intent);
            }
        });
        bileti31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 31);
                startActivity(intent);
            }
        });
        bileti32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Biletebi.this, CategoriesClass.class);        intent.putExtra("switchMode", 32);
                startActivity(intent);
            }
        });



    }
}