package com.example.todomobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String context = intent.getStringExtra("context");
        String priority = intent.getStringExtra("priority");
        String url = intent.getStringExtra("url");
        String beginDate = intent.getStringExtra("start_date");
        String endDate = intent.getStringExtra("end_date");


        TextView titleTextView = findViewById(R.id.title_textview);
        titleTextView.setText(title);

        //TextView descriptionTextView = findViewById(R.id.description_textview);
        //descriptionTextView.setText(description);

        TextView contextTextView = findViewById(R.id.context_textview);
        contextTextView.setText(context);

        TextView priorityTextView = findViewById(R.id.priority_textview);
        priorityTextView.setText(priority);

        TextView beginDateTextView = findViewById(R.id.start_date_textview);
        beginDateTextView.setText(beginDate);

        TextView endDateTextView = findViewById(R.id.end_date_textview);
        endDateTextView.setText(endDate);

        //TextView urlTextView = findViewById(R.id.url_textview);
        //urlTextView.setText(url);
    }
}
