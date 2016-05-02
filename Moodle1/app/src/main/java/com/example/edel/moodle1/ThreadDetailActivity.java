package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class ThreadDetailActivity extends AppCompatActivity {

    String u1 = "http://10.192.37.78:2207/threads/thread.json/";
    String url;
    String u2 = "http://10.192.37.78:2207/threads/post_comment.json?thread_id=";
    String u3 = "&description=";
    String url2;

    private static final String TAG = "message1";

    public Comments currentComment;
    public Users currentuser;

    private List<Comments> myComments = new ArrayList<Comments>();
    private List<Users> comm_users = new ArrayList<Users>();

    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private static final String TAG1 = "message1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_detail);

        Intent intent = getIntent();
        //retrieving the extra data from intent
        final String th_id = intent.getStringExtra("th_id");
        final String th_desc = intent.getStringExtra("th_desc");
        final String th_tit = intent.getStringExtra("th_title");

        // building the url
        url = u1 + th_id;

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final TextView title = (TextView) findViewById(R.id.th_title_textView);
        final TextView desc = (TextView) findViewById(R.id.th_desc_textView);
        final EditText new_comm = (EditText)findViewById(R.id.new_comm_editText);
        final Button add_comm = (Button)findViewById(R.id.add_comment_button);

        title.setText(th_tit);
        desc.setText(th_desc);

        populateListView();

        //when clicked on Add comment button
        add_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u4 = new_comm.getText().toString();

                url2 = u2 + th_id + u3 + u4;

                final StringRequest new_comm_req = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONObject comment = resp.getJSONObject("comment");

                            String user_id = comment.getString("user_id");
                            String desc = comment.getString("description");
                            String created = comment.getString("created_at");
                            String id = comment.getString("id");

                            myComments.add(new Comments(id, user_id, desc, created));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }){
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();//super.getHeaders();
                        String sessionId = MainActivity._preferences.getString(SESSION_COOKIE, "");
                        Log.i(TAG1, sessionId);

                        headers.put(COOKIE_KEY, sessionId);

                        //addSessionCookie(headers);
                        return headers;
                    }

                };
                requestQueue.add(new_comm_req);
                //refresh the activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(),"Comment added",Toast.LENGTH_LONG).show();

            }

        });






        StringRequest comm_req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resp = new JSONObject(response);
                    JSONArray comments = resp.getJSONArray("comments");
                    JSONArray users = resp.getJSONArray("comment_users");

                    for (int i = 0; i < comments.length(); i++) {


                        JSONObject jsonObject = comments.getJSONObject(i);

                        String desc = jsonObject.getString("description");
                        String created = jsonObject.getString("created_at");
                        String comm_id = jsonObject.getString("id");
                        String user_id = jsonObject.getString("user_id");


                           myComments.add(new Comments(comm_id, user_id, desc, created));

                    }

                    for (int j =0; j < users.length(); j++){

                        JSONObject jsonObject = users.getJSONObject(j);

                        String id = jsonObject.getString("id");
                        String f_name = jsonObject.getString("first_name");

                        comm_users.add(new Users(id,f_name));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
                //Add a toast here
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
        requestQueue.add(comm_req);


    }


    private void populateListView() {
        ArrayAdapter<Comments> adapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.comments_listView);
        listView.setAdapter(adapter);

    }

    public class MyListAdapter extends ArrayAdapter<Comments> {
        public MyListAdapter() {
            super(ThreadDetailActivity.this, R.layout.item_view_6, myComments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view_6, parent, false);
            }

            //Find the thread to work with
            currentComment = myComments.get(position);
            currentuser = comm_users.get(position);

            //Fill the view
            TextView comm_desc = (TextView) itemView.findViewById(R.id.comm_desc_textview);
            comm_desc.setText(currentComment.getDescription());

            TextView comm_create = (TextView) itemView.findViewById(R.id.comm_date_textview);
            comm_create.setText(currentComment.getCreated());

            TextView comm_user = (TextView)itemView.findViewById(R.id.comm_name_textview);

            if(currentComment.getUser_id() == currentuser.getId()){
                comm_user.setText(currentuser.getF_name());
            }

            return itemView;
        }
    }


}




