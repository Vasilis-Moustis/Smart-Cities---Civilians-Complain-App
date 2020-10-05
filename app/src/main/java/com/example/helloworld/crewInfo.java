package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class crewInfo extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String url = "https://mayoroffice.herokuapp.com/api";
    private TextView todo;
    private Button showtask, crewout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_info);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////
        showtask = (Button) findViewById(R.id.showtask);
        showtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = null;
                try {
                    result = run(url);
                    //code
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(result.toString());
                } catch (IOException e) {
                    todo = (TextView) findViewById(R.id.todo);
                    todo.setText(e.toString());
                }
            }
        });

        crewout = (Button) findViewById(R.id.crewout);
        crewout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    public void logout() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public static String run(String url) throws IOException {
        // issue the Get request
        crewInfo example = new crewInfo();
        String getResponse = example.doGetRequest(url);
        return getResponse;
    }

    public String doGetRequest(String url) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                  .addQueryParameter("action", "ontofix");

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

/*
public class test
{
    public static void main(String str[])
    {
        String jsonString = "{\"stat\": { \"sdr\": \"aa:bb:cc:dd:ee:ff\", \"rcv\": \"aa:bb:cc:dd:ee:ff\", \"time\": \"UTC in millis\", \"type\": 1, \"subt\": 1, \"argv\": [{\"type\": 1, \"val\":\"stackoverflow\"}]}}";
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject newJSON = jsonObject.getJSONObject("stat");
        System.out.println(newJSON.toString());
        jsonObject = new JSONObject(newJSON.toString());
        System.out.println(jsonObject.getString("rcv"));
       System.out.println(jsonObject.getJSONArray("argv"));
    }
}
 */