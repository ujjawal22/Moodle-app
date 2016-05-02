package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

public class NotificationActivity extends AppCompatActivity {

    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    RequestQueue requestQueue;
    private static final String TAG = "message";

    TextView notification;
    String notification_url = "http://10.192.37.78:2207/notifications.json";

    private List<Notifications> myNotifications = new ArrayList<Notifications>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent3 = getIntent();

        requestQueue = Volley.newRequestQueue(this);

        populateListView();


        StringRequest notification_req = new StringRequest(Request.Method.GET, notification_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONObject notf = new JSONObject(response);
                            JSONArray notifications = notf.getJSONArray("notifications");

                            if (notifications.length() != 0) {
                                for (int i = 0; i < notifications.length(); i++) {

                                    JSONObject notificationsJSONObject = notifications.getJSONObject(i);

                                    String user_id = notificationsJSONObject.getString("user_id");
                                    String description = notificationsJSONObject.getString("description");
                                    String is_seen = notificationsJSONObject.getString("is_seen");
                                    String created = notificationsJSONObject.getString("created_at");
                                    String id = notificationsJSONObject.getString("id");

                                    myNotifications.add(new Notifications(user_id,description,is_seen,created,id));
                                }

                            } else {
                                Toast.makeText(NotificationActivity.this,"No Notifications",Toast.LENGTH_LONG).show();
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
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();//super.getHeaders();
                String sessionId = MainActivity._preferences.getString(SESSION_COOKIE, "");
                Log.i(TAG, sessionId);

                headers.put(COOKIE_KEY, sessionId);

                //addSessionCookie(headers);

                return headers;
            }
        };
        requestQueue.add(notification_req);
    }//onCreate ends here

    private void populateListView() {
        ArrayAdapter<Notifications> adapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.notif_listView);
        listView.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Notifications> {
        public MyListAdapter(){
            super(NotificationActivity.this,R.layout.item_view3,myNotifications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view3,parent,false);
            }

            //Find the notification to work with
           Notifications currentNotif = myNotifications.get(position);


            //Fill the view

            TextView desc_text = (TextView) itemView.findViewById(R.id.thdesc_textView);
            desc_text.setText(Html.fromHtml(currentNotif.getDescription()));

            TextView date_text = (TextView) itemView.findViewById(R.id.thdate_textview);
            date_text.setText(currentNotif.getCreated());


            return itemView;
        }
    }
}
