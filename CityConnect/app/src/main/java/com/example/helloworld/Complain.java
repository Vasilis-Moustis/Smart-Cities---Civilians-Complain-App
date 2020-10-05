package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import okhttp3.FormBody;
import okio.BufferedSink;

public class Complain extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    String gurl = "https://mayoroffice.herokuapp.com/api";
    private static final int CAMERA_REQUEST = 1001;
    private EditText afm, searchbar;
    private ImageView imageView;
    private Button photoButton, submitcomplain;
    private RadioGroup type;
    private Spinner ids;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        //////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //////////////////////Getting inf ids
        String searching = fillSearchView(gurl);
        String[] options = searching.split(",");
        String[] arraySpinner = options;
        final Spinner s = (Spinner) findViewById(R.id.ids);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        /////////////////////take  photo
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        photoButton = (Button) this.findViewById(R.id.takephoto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraClicked();
            }
        });
        /////////////////////

        submitcomplain = (Button) findViewById(R.id.submitcomplain);
        submitcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitcomplain(s);
            }
        });

    }

    private void submitcomplain(Spinner s){
        //////////////////////gathering data
        final String infID = (String) s.getSelectedItem();
        type = (RadioGroup) findViewById(R.id.type);
        int radioButtonID = type.getCheckedRadioButtonId();
        View radioButton = type.findViewById(radioButtonID);
        int idx = type.indexOfChild(radioButton);
        RadioButton r = (RadioButton) type.getChildAt(idx);
        final String damagetype = r.getText().toString();
        afm = (EditText) findViewById(R.id.afm);
        final String userafm = afm.getText().toString();
        //////////////////////
        String result = null;
        String  url = gurl;
        try {
            result = run(url, userafm, infID.toString(), damagetype);
            /*
            if (result.toString().equals("complain")){
                //backToMenu();
            }else{
                //display failed login message
            }*/
            afm.setText(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cameraClicked(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void backToMenu() {
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }

    public String fillSearchView(String url) {
        String urlcopy = gurl;
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(urlcopy).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "givemeoptions");
            urlcopy = urlBuilder.build().toString();
        }catch(Exception i){
            i.printStackTrace();
        }

        try {
            Request request = new Request.Builder()
                    .url(urlcopy)
                    .get()
                    .build();

            Response response = client.newCall(request)
                    .execute();
            return response.body().string().toString();
        }catch(Exception j){
            return "j\n" + j.toString();
        }
    }


    public static String run(String url, String userafm, String infID, String damagetype) throws IOException {
        // issue the Get request
        Complain example = new Complain();
        String getResponse = example.doGetRequest(url, userafm, infID, damagetype);
        return getResponse;
    }

    public String doGetRequest(String url, String userafm, String infID, String damagetype) throws IOException {

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("action", "icomplain")
                    .addQueryParameter("afm", userafm)
                    .addQueryParameter("infID", infID)
                    .addQueryParameter("type", damagetype);

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
            return "j\n" + j.toString();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
