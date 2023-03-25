package com.example.todomobile;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listTasks;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> tasks = new ArrayList<Task>();

        /* Changer de vue pour aller dans l'activité AddTask */
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(intent, 1);
            }
        });

        TaskAdapter adapt = new TaskAdapter(this, tasks);
        listTasks = (ListView) findViewById(R.id.listTasks);
        listTasks.setAdapter(adapt);

        //On ajoute un listener (clic sur un item)
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Task selectedItem = (Task) adapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "La tâche : " + selectedItem.getTaskTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String context = data.getStringExtra("context");
            String priority = data.getStringExtra("priority");
            String url = data.getStringExtra("url");
            String startDate = data.getStringExtra("start_date");
            String endDate = data.getStringExtra("end_date");

            Task task = new Task(title, description, startDate, endDate, context, priority, url);
            TaskAdapter adapt = (TaskAdapter) listTasks.getAdapter();
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }
}
