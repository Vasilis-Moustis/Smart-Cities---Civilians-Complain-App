package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Requests extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String url = "https://mayoroffice.herokuapp.com/api";
    private TextView previousComplains;
    private Button showprevious;
    private EditText userAFM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////
        showprevious = (Button) findViewById(R.id.showprevious);
        showprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAFM = (EditText) findViewById(R.id.userAFM);
                String afm = userAFM.getText().toString();
                String result = null;
                try {
                    result = run(url, afm);
                    //code
                    previousComplains = (TextView) findViewById(R.id.previousComplains);
                    previousComplains.setText(result.toString());
                } catch (IOException e) {
                    previousComplains = (TextView) findViewById(R.id.previousComplains);
                    previousComplains.setText(e.toString());
                }
            }
        });
    }

    public static String run(String url, String afm) throws IOException {
        // issue the Get request
        Requests example = new Requests();
        String getResponse = example.doGetRequest(url, afm);
        return getResponse;
    }

    public String doGetRequest(String url, String afm) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "previous")
                    .addQueryParameter("afm", afm);

            url = urlBuilder.build().toString();
        }catch(Exception i){
            i.printStackTrace();
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
            j.printStackTrace();
            return j.toString();
        }

    }
}

