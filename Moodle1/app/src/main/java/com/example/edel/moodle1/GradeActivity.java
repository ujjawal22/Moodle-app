package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeActivity extends AppCompatActivity {

    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    String grade_url = "http://10.192.37.78:2207/default/grades.json";
    private List<Grades> myGrades = new ArrayList<Grades>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        Intent intent2 = getIntent();

        ListView listView = (ListView)findViewById(R.id.grade_listView);

        populateListView();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest grade_resp = new StringRequest(Request.Method.GET, grade_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {

                try{
                    //convert the String response to json object
                    JSONObject resp = new JSONObject(response1);
                    // retrieve the json array using the json response
                    JSONArray grades = resp.getJSONArray("grades");
                    JSONArray courses = resp.getJSONArray("courses");

                    if (grades.length()!= 0) {
                        for (int i = 0; i < grades.length(); i++) {

                            JSONObject jsonObject = grades.getJSONObject(i);
                            JSONObject jsonObject1 = courses.getJSONObject(i);


                            String code = jsonObject1.getString("code");
                            String desc = jsonObject1.getString("description");
                            String name = jsonObject1.getString("name");
                            String credits = jsonObject1.getString("credits");
                            String format = jsonObject1.getString("l_t_p");
                            String id = jsonObject1.getString("id");

                            String weight = jsonObject.getString("weightage");
                            String user_id = jsonObject.getString("user_id");
                            String ass_name = jsonObject.getString("name");
                            String total = jsonObject.getString("out_of");
                            String course_id = jsonObject.getString("registered_course_id");
                            String score = jsonObject.getString("score");
                            String grade_id = jsonObject.getString("id");

                            myGrades.add(new Grades(code,weight,user_id,ass_name,total,course_id,score,grade_id));
                        }
                    }
                    else {Toast.makeText(GradeActivity.this, "No Grades Posted", Toast.LENGTH_LONG).show();}

                }

                catch(JSONException e){e.printStackTrace();}

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");

                    }
                })
        {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();//super.getHeaders();
                String sessionId = MainActivity._preferences.getString(SESSION_COOKIE, "");
                Log.i(TAG1,sessionId);

                headers.put(COOKIE_KEY, sessionId);

                //addSessionCookie(headers);

                return headers;
            }
        };
        requestQueue.add(grade_resp);
    }//onCreate ends here

    private void populateListView() {
        ArrayAdapter<Grades> adapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.grade_listView);
        listView.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Grades> {
        public MyListAdapter(){
            super(GradeActivity.this,R.layout.item_view2,myGrades);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view2,parent,false);
            }
            //Find the grade to work with
            Grades currentGrade = myGrades.get(position);

            //Fill the view

            TextView code_text = (TextView) itemView.findViewById(R.id.course_code);
            code_text.setText(currentGrade.getCode());

            TextView ass_text = (TextView) itemView.findViewById(R.id.ass_textView);
            ass_text.setText(currentGrade.getAss_name());

            TextView weight = (TextView) itemView.findViewById(R.id.wt_textView);
            weight.setText(currentGrade.getWeight());

            TextView score = (TextView) itemView.findViewById(R.id.score_textView);
            score.setText(currentGrade.getScore());


            TextView total = (TextView) itemView.findViewById(R.id.tot_textView);
            total.setText(currentGrade.getTotal());


            return itemView;
        }
    }
}
