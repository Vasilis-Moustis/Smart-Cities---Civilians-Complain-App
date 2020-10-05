package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class login extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String url = "https://mayoroffice.herokuapp.com/api";
    private Button gologin;
    private EditText workuuid;
    private TextView returned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////
        gologin = (Button) findViewById(R.id.gologin);
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workuuid = (EditText) findViewById(R.id.workuuid);
                String uuid = workuuid.getText().toString();
                String result = null;
                try {
                    result = run(url, uuid);
                    if (result.toString().equals("crew")){
                        displayCrewPage();
                    }else{
                        //display failed login message
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        });

    }

    private void displayCrewPage() {
        workuuid = (EditText) findViewById(R.id.workuuid);
        workuuid.setText("");
        Intent intent = new Intent(this, crewInfo.class);
        startActivity(intent);
    }


    public static String run(String url, String uuid) throws IOException {
        // issue the Get request
        login example = new login();
        String getResponse = example.doGetRequest(url, uuid);
        return getResponse;
    }

    public String doGetRequest(String url, String uuid) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "login")
                    .addQueryParameter("uuid", uuid);

            url = urlBuilder.build().toString();
        }catch(Exception i){
        }

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request)
                    .execute();
            return response.body().string().toString();
        }catch(Exception j){
            return "j\n" + j.toString();

        }

    }

    /*
        public String doPostRequest(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    */
}
