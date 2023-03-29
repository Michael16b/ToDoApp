package com.example.todomobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
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

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Task selectedItem = (Task) adapter.getItemAtPosition(position);

                // Créer un nouvel intent pour afficher les informations de la tâche
                Intent intent = new Intent(MainActivity.this, TaskDetails.class);
                intent.putExtra("edit", false);
                intent.putExtra("title", selectedItem.getTaskTitle());
                intent.putExtra("description", selectedItem.getDescription());
                intent.putExtra("context", selectedItem.getContext());
                intent.putExtra("priority", selectedItem.getPriority());
                intent.putExtra("url", selectedItem.getUrl());
                intent.putExtra("start_date", selectedItem.getBeginDate());
                intent.putExtra("end_date", selectedItem.getEndDate());

                // Démarrer la nouvelle activité pour afficher les informations de la tâche
                startActivity(intent);
            }
        });


        // Supprimer une tâche
        listTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
                Task selectedItem = (Task) adapter.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Voulez-vous supprimer cette tâche ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            TaskAdapter adapt = (TaskAdapter) listTasks.getAdapter();
                            adapt.remove(selectedItem);
                            adapt.notifyDataSetChanged();
                        })
                        .setNegativeButton("Non", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                return true;
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

            Task task = new Task(0, title, description, startDate, endDate, context, priority, url);
            TaskAdapter adapt = (TaskAdapter) listTasks.getAdapter();
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }
}


