package com.example.edel.moodle1;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    private static MainActivity _instance;
    private RequestQueue _requestQueue;
    public static SharedPreferences _preferences;

    public static MainActivity get() {
        return _instance;
    }

    private static final String TAG = "message";
    EditText editText1;
    EditText editText2;
    TextView textView;
    Button submit;
    String s1="",s2="";
    String u1 = "http://10.192.37.78:2207/default/login.json?userid=";
    String u2 = "&password=";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.signin_button);
        editText1 =(EditText)findViewById(R.id.email_editText);
        editText2 = (EditText)findViewById(R.id.password_editText);
        textView = (TextView)findViewById(R.id.textView1);
        //instantiate OnClicklistener when submit is pressed
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the user input
                s1 = editText1.getText().toString();
                s2 = editText2.getText().toString();

                //check if the input matches to correct pair of user id and password
                if(LoginCheck.check(s1,s2) == true){
                    u1 = u1.concat(s1);
                    u2 = u2.concat(s2);
                    url = u1 + u2;

                //instantiate a StringRequest using volley
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //on getting the response do this
                                textView.setText("Welcome "+s2.toUpperCase());

                                Log.i(TAG,"response:" + response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Invalid Request");
                    }

            })//StringRequest ends here
                {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String cookie = response.headers.get(SET_COOKIE_KEY);
                        SharedPreferences.Editor prefEditor = _preferences.edit();
                        prefEditor.putString(SESSION_COOKIE, cookie);
                        prefEditor.commit();
                        return super.parseNetworkResponse(response);
                        //return super.parseNetworkResponse(response);
                    }
                };
                    // add the response to requestqueue
                _requestQueue.add(stringRequest);

                    // if credentials are correct then direct to new screen(activity)
                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);

            }
                else
                {textView.setText("wrong id or password");
                }
        }//onClick ends here





    });

}//onCreate ends here.



    /**
            * Checks the response headers for session cookie and saves it
    * if it finds it.
    * @param headers Response Headers.
    */


    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */





}
