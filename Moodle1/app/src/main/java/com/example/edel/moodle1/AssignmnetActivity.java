package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmnetActivity extends AppCompatActivity {

    String course_ass_url1 = "http://10.192.37.78:2207/courses/course.json/";
    String course_ass_url2 = "/assignments";
    String url;

    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    public Assignment currentAssignment;

    private List<Assignment> myAssignments = new ArrayList<Assignment>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignmnet);

        Intent intent = getIntent();
        final String course_code = intent.getStringExtra("course_code");

        url = course_ass_url1 + course_code + course_ass_url2;

        final TextView ass_list = (TextView)findViewById(R.id.course_ass_textview);
        ListView listView = (ListView)findViewById(R.id.ass_listView);

        populateListView();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vg  = (ViewGroup) view;

                currentAssignment = myAssignments.get(position);

                Intent intent = new Intent(AssignmnetActivity.this,Assignment_detailActivity.class);
                intent.putExtra("ass_id",currentAssignment.getId());
                intent.putExtra("ass_name",currentAssignment.getName());
                intent.putExtra("ass_desc",currentAssignment.getDescription());
                intent.putExtra("ass_dead",currentAssignment.getDeadline());
                intent.putExtra("ass_late",currentAssignment.getLate());
                startActivity(intent);
            }
        });

        StringRequest ass_resp = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resp = new JSONObject(response);
                    JSONArray assignments = resp.getJSONArray("assignments");

                    for (int i = 0; i < assignments.length(); i++) {

                        JSONObject jsonObject = assignments.getJSONObject(i);

                        String desc = jsonObject.getString("description");
                        String name = jsonObject.getString("name");
                        String created = jsonObject.getString("created_at");
                        String deadline = jsonObject.getString("deadline");
                        String id = jsonObject.getString("id");
                        String late_days = jsonObject.getString("late_days_allowed");

                        myAssignments.add(new Assignment(name,id,deadline,desc,created,late_days));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
                Toast.makeText(AssignmnetActivity.this, "Bad request", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(ass_resp);

    }//onCreate ends here
    private void populateListView() {
        ArrayAdapter<Assignment> adapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.ass_listView);
        listView.setAdapter(adapter);

    }

    public class MyListAdapter extends ArrayAdapter<Assignment> {
        public MyListAdapter(){
            super(AssignmnetActivity.this,R.layout.item_view_4,myAssignments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view_4,parent,false);
            }

            //Find the assignment to work with
            currentAssignment = myAssignments.get(position);

            //Fill the view
            TextView name_text = (TextView) itemView.findViewById(R.id.ass_name_textview);
            name_text.setText(currentAssignment.getName());

            TextView dead_text = (TextView) itemView.findViewById(R.id.textView11);
            dead_text.setText(currentAssignment.getDeadline());

            TextView late_text = (TextView) itemView.findViewById(R.id.textView12);
            late_text.setText(currentAssignment.getLate());

            return itemView;
        }
    }

}



