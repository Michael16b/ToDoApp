package com.example.todomobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listTasks;
    Database myDB;
    ArrayList<Task> tasks;
    Button btnAdd;
    Spinner filterPrio, filterCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<Task>();
        myDB = new Database(this.getBaseContext());
        storeData();

        TaskAdapter adapt = new TaskAdapter(this, tasks);
        listTasks = (ListView) findViewById(R.id.listTasks);
        listTasks.setAdapter(adapt);



        filterPrio = (Spinner) findViewById(R.id.prio_filter);
        filterCon = (Spinner) findViewById(R.id.context_filter);

        // Configuration des spinners

        String[] priorityArray = getResources().getStringArray(R.array.priority_array);
        String[] priorityArrayWithEmpty = new String[priorityArray.length+1];

        priorityArrayWithEmpty[0] = "";
        for (int i = 0; i < priorityArray.length; i++) {
            priorityArrayWithEmpty[i+1] = priorityArray[i];
        }
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priorityArrayWithEmpty);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterPrio.setAdapter(priorityAdapter);


        String[] contextArray = getResources().getStringArray(R.array.context_array);
        String[] contextArrayWithEmpty = new String[contextArray.length+1];
        contextArrayWithEmpty[0] = "";
        for (int i = 0; i < contextArray.length; i++) {
            contextArrayWithEmpty[i+1] = contextArray[i];
        }
        ArrayAdapter<String> contextAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contextArrayWithEmpty);
        contextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCon.setAdapter(contextAdapter);

        //Button pour ajouter une tâche
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(intent, 1);
            }
        });

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Task selectedItem = (Task) adapter.getItemAtPosition(position);

                // Créer un nouvel intent pour afficher les informations de la tâche
                Intent intent = new Intent(MainActivity.this, TaskDetails.class);
                intent.putExtra("edit", false);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("title", selectedItem.getTaskTitle());
                intent.putExtra("description", selectedItem.getDescription());
                intent.putExtra("context", selectedItem.getContext());
                intent.putExtra("priority", selectedItem.getPriority());
                intent.putExtra("url", selectedItem.getUrl());
                intent.putExtra("start_date", selectedItem.getBeginDate());
                intent.putExtra("end_date", selectedItem.getEndDate());

                // Démarrer la nouvelle activité pour afficher les informations de la tâche
                startActivityForResult(intent, 1);
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
                            Database mydb = new Database(getBaseContext());
                            mydb.deleteOneRow(selectedItem.getId());
                        })
                        .setNegativeButton("Non", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                return true;
            }
        });

        initPriorityFilter();
        initProgressFilter();
        refreshListTasks();
    }

    void storeData(){
        Cursor cursor = myDB.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Aucune tâches.", Toast.LENGTH_SHORT).show();
        }else{
            //On enlève toutes les activités
            tasks.removeAll(tasks);

            while (cursor.moveToNext()){
                Task newTask = new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7));

                tasks.add(newTask);
            }
        }
    }

    void refreshListTasks(){
        storeData();
        TaskAdapter adapt = new TaskAdapter(this, tasks);
        listTasks.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            refreshListTasks();
        }
        refreshListTasks();
    }

    /**
     * Initiate the priority filter
     */
    private void initPriorityFilter(){
        Spinner filterPriority = (Spinner) findViewById(R.id.prio_filter);
        filterPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String priority = parent.getItemAtPosition(position).toString();
                ArrayList<Task> listFound = new ArrayList<Task>();
                if(priority.toLowerCase().equals("")){ // Si le filtre est vide
                    listFound.addAll(tasks); // Ajouter toutes les tâches à la liste
                }else{ // Sinon, appliquer le filtre
                    for (Task task : tasks) {
                        if (task.getPriority().toLowerCase().contains(priority.toLowerCase() ) || priority.toLowerCase() == "") {
                            listFound.add(task);
                        }
                    }
                }
                TaskAdapter adapter = new TaskAdapter(getApplicationContext(), listFound);
                listTasks.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                refreshListTasks();
            }
        });
    }

    /**
     * Initiate the progress filter
     */
    private void initProgressFilter(){
        Spinner filterProgress = (Spinner) findViewById(R.id.context_filter);
        filterProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String progress = parent.getItemAtPosition(position).toString();
                ArrayList<Task> listFound = new ArrayList<Task>();
                if(progress.toLowerCase().equals("")){ // Si le filtre est vide
                    listFound.addAll(tasks); // Ajouter toutes les tâches à la liste
                }else{ // Sinon, appliquer le filtre
                    for (Task task : tasks) {
                        if (task.getContext().toLowerCase().contains(progress.toLowerCase()) ||  progress.toLowerCase()== "") {
                            listFound.add(task);
                        }
                    }
                }
                TaskAdapter adapter = new TaskAdapter(getApplicationContext(), listFound);
                listTasks.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                refreshListTasks();
            }
        });
    }
}


