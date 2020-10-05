package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {

    private Button opentheform,openqr,history;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

       opentheform = (Button) findViewById(R.id.openform);
       opentheform.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               opentheform();
           }
       });

       openqr = (Button) findViewById(R.id.openQr);
       openqr.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openqrscanner();
           }
       });

       history = (Button) findViewById(R.id.openhistory);
       history.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showPreviousComplains();
           }
       });

    }



    private void openqrscanner() {
        Intent intent = new Intent(this, Qrcomplain.class);
        startActivity(intent);
    }

    private void opentheform() {
        Intent intent2 = new Intent(this, Complain.class);
        startActivity(intent2);
    }

    private void showPreviousComplains() {
        Intent intent2 = new Intent(this, Requests.class);
        startActivity(intent2);
    }


}
