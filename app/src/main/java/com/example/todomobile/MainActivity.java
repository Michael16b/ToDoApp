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

    ListView listTask;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));
        tasks.add(new Task("rien du tout", "coucou", "", "", "", "", ""));

        /* Changer de vue pour aller dans l'activité AddTask */
        btnAdd = (Button) findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTask.class));
            }
        });

        //Initialisation de l'adapter pour city
        Adapter adapt = new Adapter(this, tasks);

        //Récupération de la ListView
        listTask = (ListView) findViewById(R.id.listTask);

        //Passage des données à la ListView
        listTask.setAdapter(adapt);

        //On ajoute un listener (clic sur un item)
        listTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Task selectedItem = (Task) adapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Le film : " + selectedItem.getTaskTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}