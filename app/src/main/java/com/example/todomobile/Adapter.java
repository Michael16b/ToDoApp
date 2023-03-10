package com.example.todomobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Adapter extends BaseAdapter {

    private List<Cinema> listCinema;
    private Context context;
    private LayoutInflater inflater;

    public Adapter(Context context, List<Cinema> objects) {
        this.context = context;
        this.listCinema = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.listCinema.size();
    }


    public Object getItem(int position) {
        return this.listCinema.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView == null){
            //Initialisation de la vue cinema_list_item
            view = (View) inflater.inflate(R.layout.list_row, parent, false);
        }else{
            view = (View) convertView;
        }

        //Initialisation des vues du layout
        ImageView image = (ImageView) view.findViewById(R.id.affiche);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView real = (TextView) view.findViewById(R.id.realisateur);
        TextView duree = (TextView) view.findViewById(R.id.duree);

        //mofification des vues
        System.out.println(listCinema.get(position).getCheminAffiche());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            InputStream in = new java.net.URL(listCinema.get(position).getCheminAffiche()).openStream();
            Bitmap map = BitmapFactory.decodeStream(in);
            image.setImageBitmap(map);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //image.setImageResource(listCinema.get(position).getCheminAffiche());
        title.setText(listCinema.get(position).getTitre());
        real.setText(listCinema.get(position).getNomRealisateur());

        int dur = listCinema.get(position).getDuree();
        duree.setText(String.valueOf(dur) + " min");

        //on retourne la vue créée
        return view;
    }
}
