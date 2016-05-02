package com.example.edel.moodle1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Assignment_detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);

        Intent intent = getIntent();

        final String id = intent.getStringExtra("ass_id");
        final String name = intent.getStringExtra("ass_name");
        final String desc = intent.getStringExtra("ass_desc");
        final String deadline = intent.getStringExtra("ass_dead");
        final String late_days = intent.getStringExtra("ass_late");


        final TextView ass_desc = (TextView)findViewById(R.id.coursedescription_textView);

        // parse the html type desfription and set it into textview
        ass_desc.setText(Html.fromHtml(desc));



    }
}
