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

        ArrayList<Cinema> listCinema = new ArrayList<Cinema>();
        listCinema.add(new Cinema("https://fr.web.img2.acsta.net/c_310_420/medias/nmedia/18/78/61/18/19518457.jpg", "RED", "Robert Schwentke", 111));
        listCinema.add(new Cinema("https://fr.web.img3.acsta.net/pictures/210/146/21014630_20130621185839634.jpg", "RED 2", "Dean Parisot", 116));
        listCinema.add(new Cinema("https://upload.wikimedia.org/wikipedia/en/9/98/John_Wick_TeaserPoster.jpg", "JOHN WICK", "Chad Stahelski", 101));
        listCinema.add(new Cinema("https://m.media-amazon.com/images/M/MV5BMjE2NDkxNTY2M15BMl5BanBnXkFtZTgwMDc2NzE0MTI@._V1_.jpg", "JOHN WICK Chapter 2", "Chad Stahelski", 122));
        listCinema.add(new Cinema("https://fr.web.img5.acsta.net/pictures/19/05/21/15/23/4992794.jpg", "JOHN WICK Parabellum", "Chad Stahelski", 131));
        listCinema.add(new Cinema("https://flxt.tmsimg.com/assets/p22804_p_v8_av.jpg", "The Matrix", "Les Wachowski", 150));
        listCinema.add(new Cinema("https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg", "Matrix Reloaded", "Les Wachowski", 132));
        listCinema.add(new Cinema("https://fr.web.img6.acsta.net/medias/nmedia/18/35/14/64/18364977.jpg", "Matrix Revolutions", "Les Wachowski", 129));
        listCinema.add(new Cinema("https://fr.web.img4.acsta.net/pictures/21/11/17/17/24/3336846.jpg", "Matrix Resurrections", "Les Wachowski", 148));

        //Initialisation de l'adapter pour city
        Adapter adapt = new Adapter(this, listCinema);

        //Récupération de la ListView
        listFilm = (ListView) findViewById(R.id.listFilm);

        //Passage des données à la ListView
        listFilm.setAdapter(adapt);

        //On ajoute un listener (clic sur un item)
        listFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Cinema selectedItem = (Cinema) adapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Le film : " + selectedItem.getTitre(), Toast.LENGTH_LONG).show();
            }
        });
    }
}