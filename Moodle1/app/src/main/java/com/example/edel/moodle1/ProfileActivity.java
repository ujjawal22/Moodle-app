package com.example.edel.moodle1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.edel.moodle1.MainActivity.*;

public class ProfileActivity extends AppCompatActivity {



    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";
    ImageButton course;
    ImageButton grade;
    ImageButton notif;

    String logout_url = "http://10.192.37.78:2207/default/logout.json";

    private static final String TAG = "message";

    //private SharedPreferences _preferences;

    String grade_url = "http://10.192.37.78:2207/default/grades.json";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();

        course =(ImageButton)findViewById(R.id.courses_imageButton);
        grade = (ImageButton)findViewById(R.id.grades_imageButton);
        notif = (ImageButton)findViewById(R.id.notif_imageButton);

        requestQueue = Volley.newRequestQueue(this);
        // floating action button for logout
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user clicks on logout action button, send a request for logout
                StringRequest logout_req = new StringRequest(Request.Method.GET, logout_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String log_string = response.toString();

                        Log.i(TAG, "response:" + response.toString());
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");

                    }
                });
                requestQueue.add(logout_req);
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        // if clicked on course button
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ProfileActivity.this,CourseActivity.class);
                startActivity(intent1);

            }

        });
        //when clicked on Grades button
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfileActivity.this,GradeActivity.class);
                startActivity(intent2);

            }


});

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ProfileActivity.this,NotificationActivity.class);
                startActivity(intent3);
            }
        });
    }//onCreate ends here
}