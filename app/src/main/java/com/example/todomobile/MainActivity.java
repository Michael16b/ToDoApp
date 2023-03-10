package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> listTask = new ArrayList<Task>();
        /*
        //Initialisation de l'adapter pour city
        Adapter adapt = new Adapter(this, listTask);

         */



        /*
        //Récupération de la ListView
        listFilm = (ListView) findViewById(R.id.listFilm);

        //Passage des données à la ListView
        listFilm.setAdapter(adapt);

        //On ajoute un listener (clic sur un item)
        listFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Task selectedItem = (Cinema) adapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Le film : " + selectedItem.getTitre(), Toast.LENGTH_LONG).show();
            }
        });

         */
    }
}