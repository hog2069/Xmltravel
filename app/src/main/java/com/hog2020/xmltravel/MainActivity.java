package com.hog2020.xmltravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        Intent intent = new Intent(this,Second.class);
        startActivity(intent);
    }

    public void clickBtn2(View view) {
        Intent intent2 = new Intent(this,Third.class);
        startActivity(intent2);
    }
}