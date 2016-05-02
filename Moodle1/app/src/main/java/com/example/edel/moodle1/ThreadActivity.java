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

public class ThreadActivity extends AppCompatActivity {

    String u1 = "http://10.192.37.78:2207/courses/course.json/";
    String u2 = "/threads";
    String url;

    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    public Threads currentThread;

    private List<Threads> myThreads = new ArrayList<Threads>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Intent intent = getIntent();
        final String course_code = intent.getStringExtra("course_code");

        url = u1 + course_code + u2;

        ListView listView = (ListView) findViewById(R.id.thread_listView);

        populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vg = (ViewGroup) view;

                currentThread = myThreads.get(position);

                Intent intent = new Intent(ThreadActivity.this, ThreadDetailActivity.class);

                intent.putExtra("th_id", currentThread.getId());
                intent.putExtra("th_title", currentThread.getTitle());
                intent.putExtra("th_desc",currentThread.getDescription());

                startActivity(intent);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest thread_req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resp = new JSONObject(response);
                    JSONArray threads = resp.getJSONArray("course_threads");

                    for (int i = 0; i < threads.length(); i++) {

                        JSONObject jsonObject = threads.getJSONObject(i);

                        String desc = jsonObject.getString("description");
                        String title = jsonObject.getString("title");
                        String created = jsonObject.getString("created_at");
                        String updated = jsonObject.getString("updated_at");
                        String id = jsonObject.getString("id");
                        String user_id = jsonObject.getString("user_id");

                        myThreads.add(new Threads(title, desc, created, updated, id, user_id));
                    }
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
                Log.i(TAG1,sessionId);

                headers.put(COOKIE_KEY, sessionId);

                //addSessionCookie(headers);
                return headers;
            }
        };
        requestQueue.add(thread_req);
    }

    private void populateListView() {
        ArrayAdapter<Threads> adapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.thread_listView);
        listView.setAdapter(adapter);

    }

    public class MyListAdapter extends ArrayAdapter<Threads> {
        public MyListAdapter(){
            super(ThreadActivity.this,R.layout.item_view_5,myThreads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view_5,parent,false);
            }

            //Find the thread to work with
            currentThread = myThreads.get(position);

            //Fill the view
            TextView title_text = (TextView) itemView.findViewById(R.id.title_textView);
            title_text.setText(currentThread.getTitle());

            TextView update = (TextView) itemView.findViewById(R.id.updated_textView);
            update.setText(currentThread.getUpdated());

            return itemView;
        }
    }
}
