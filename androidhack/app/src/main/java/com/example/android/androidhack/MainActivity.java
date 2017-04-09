package com.example.android.androidhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static String paymentUrl, paymentBody, paymentAmount, payee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }


    public void launchStudentActivity(View view){
        Intent studentActivityIntent = new Intent(this, StudentActivity.class);
        startActivity(studentActivityIntent);
    }

    public void launchSupporterActivity(View view){
        Intent supporterActivityIntent = new Intent(this, SupporterActivity.class);
        startActivity(supporterActivityIntent);
    }

}
