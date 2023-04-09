package com.example.todomobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    Drawable priorityDrawable;

    /**Constructeur de l'adapteur*/
    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        String context = task.getContext();

        //Test de la nullité de convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        //Initialisation des attributs
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
        ImageView contextImageView = (ImageView) convertView.findViewById(R.id.context_imageview);
        TextView priorityTextView = (TextView) convertView.findViewById(R.id.priority_textview);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_textview);

        dateTextView.setText("Du : " + task.getBeginDate() + " au " + task.getEndDate());
        titleTextView.setText(task.getTaskTitle());
        priorityTextView.setText(task.getPriority());

        //Affichage des icones
        if (context.equals("Sur PC")) {
            contextImageView.setImageResource(R.drawable.baseline_computer_24);

        } else if (context.equals("À la maison")) {
            contextImageView.setImageResource(R.drawable.baseline_home_24);

        } else if (context.equals("Au bureau")) {
            contextImageView.setImageResource(R.drawable.baseline_work_24);

        } else if (context.equals("Au téléphone")) {
            contextImageView.setImageResource(R.drawable.baseline_phone_android_24);

        }

        //Changement de couleur en fonction de la priorité
        if (task.getPriority().equalsIgnoreCase("Terminé")) {
            priorityDrawable = ContextCompat.getDrawable(getContext(), R.drawable.priority_done_background);
        } else if (task.getPriority().equalsIgnoreCase("En cours")) {
            priorityDrawable = ContextCompat.getDrawable(getContext(), R.drawable.priority_in_progress_background);
        } else {
            priorityDrawable = ContextCompat.getDrawable(getContext(), R.drawable.priority_todo_background);
        }
        priorityTextView.setBackground(priorityDrawable);

        return convertView;
    }
}