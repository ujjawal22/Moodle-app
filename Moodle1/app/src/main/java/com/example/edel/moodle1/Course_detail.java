package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Course_detail extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    String u1 = "http://192.168.1.3:2207/threads/new.json?title=";
    String u2 = "&description=";
    String u3  ="&course_code=";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();

        final String code = intent.getStringExtra("course_code");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        final TextView course_text = (TextView)findViewById(R.id.course_detail_id);
        course_text.setText(code);

        final Button course_assignment = (Button)findViewById(R.id.ass_btn);
        final Button course_threads = (Button)findViewById(R.id.thread_btn);
        final EditText title = (EditText)findViewById(R.id.title_editText);
        final EditText description = (EditText)findViewById(R.id.th_desc_editText);
        final Button post = (Button)findViewById(R.id.post_button);


        course_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ass_intent = new Intent(Course_detail.this,AssignmnetActivity.class);
                ass_intent.putExtra("course_code",code);
                startActivity(ass_intent);
            }
        });

        course_threads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thread_intent = new Intent(Course_detail.this,ThreadActivity.class);
                thread_intent.putExtra("course_code",code);
                startActivity(thread_intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t1 = title.getText().toString();
                String t2 = description.getText().toString();

                url = u1 + t1 + u2 + t2 + u3 + code;

                StringRequest new_th_req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject resp = new JSONObject(response);

                            String th_id = resp.getString("thread_id");

                            //Add a toast here


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                })
                    {

                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();//super.getHeaders();
                        String sessionId = MainActivity._preferences.getString(SESSION_COOKIE, "");
                        Log.i(TAG1, sessionId);

                        headers.put(COOKIE_KEY, sessionId);

                        //addSessionCookie(headers);
                        return headers;
                    }

                };
                requestQueue.add(new_th_req);

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }


        });

    }
}
