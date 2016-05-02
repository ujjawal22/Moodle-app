package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class CourseActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    String course_url = "http://10.192.37.78:2207/courses/list.json";
    private List<Courses> myCourses = new ArrayList<Courses>();
    public Courses currentCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // recieve intent from ProfileActivity
        Intent intent1 = getIntent();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final TextView textView = (TextView)findViewById(R.id.courseac_textView);
        ListView listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vg  = (ViewGroup) view;

                currentCourse = myCourses.get(position);

                //start an intent that directs to Course list activity and send course code as extra data
                Intent intent = new Intent(CourseActivity.this,Course_detail.class);
                intent.putExtra("course_code",currentCourse.getCode());
                startActivity(intent);

            }
        });
        // populate the listview with items using list adapter
        populateListView();

        com.android.volley.toolbox.StringRequest course_resp = new StringRequest(Request.Method.GET, course_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject resp = new JSONObject(response);
                            JSONArray course = resp.getJSONArray("courses");

                            for (int i = 0; i < course.length(); i++){

                                JSONObject jsonObject = course.getJSONObject(i);

                                String code = jsonObject.getString("code");
                                String desc = jsonObject.getString("description");
                                String name = jsonObject.getString("name");
                                String credits = jsonObject.getString("credits");
                                String format = jsonObject.getString("l_t_p");
                                String id = jsonObject.getString("id");

                                //add entries to myCourses array
                                myCourses.add(new Courses(code,desc,name,credits,format,id));
                            }
                        }
                        catch(JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_LONG).show();

                    }
                })// StringRequest ends here

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
        requestQueue.add(course_resp);



    }//onCreate ends here

    private void populateListView() {
        ArrayAdapter<Courses> adapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }
    //custom List Adapter using array aradpter with entries of type Courses
    public class MyListAdapter extends ArrayAdapter<Courses> {
        public MyListAdapter(){
            super(CourseActivity.this,R.layout.item_view,myCourses);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }
            //Find the course to work with
            currentCourse = myCourses.get(position);

            //Fill the view

            TextView code_text = (TextView) itemView.findViewById(R.id.code);
            code_text.setText(currentCourse.getCode());

            TextView name_text = (TextView) itemView.findViewById(R.id.name);
            name_text.setText(currentCourse.getName());

            TextView credits_text = (TextView) itemView.findViewById(R.id.credits);
            credits_text.setText(currentCourse.getCredits());


            TextView format_text = (TextView) itemView.findViewById(R.id.format);
            format_text.setText(currentCourse.getFormat());

            TextView desc_text = (TextView) itemView.findViewById(R.id.coursedescription_textView);
            desc_text.setText(currentCourse.getDescription());

            return itemView;
        }
    }
}
