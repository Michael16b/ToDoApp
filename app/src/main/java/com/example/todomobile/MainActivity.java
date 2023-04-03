package com.example.todomobile;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listTasks;
    Database myDB;
    ArrayList<Task> tasks;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<Task>();

        TaskAdapter adapt = new TaskAdapter(this, tasks);
        listTasks = (ListView) findViewById(R.id.listTasks);
        listTasks.setAdapter(adapt);

        myDB = new Database(this.getBaseContext());
        storeData();


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
                            Database mydb = new Database(getBaseContext());
                            mydb.deleteOneRow(selectedItem.getId());
                            refreshListTasks();
                        })
                        .setNegativeButton("Non", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                return true;
            }
        });
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
        TaskAdapter adapt = (TaskAdapter) listTasks.getAdapter();
        storeData();
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
}


