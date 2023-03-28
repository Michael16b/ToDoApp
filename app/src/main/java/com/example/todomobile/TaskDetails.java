package com.example.todomobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class TaskDetails extends AppCompatActivity {


    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);


        TextView titleTextView = findViewById(R.id.title_textview);
        TextView descriptionTextView = findViewById(R.id.description_textview);
        ImageView contextImageView = findViewById(R.id.context_imageview);
        TextView priorityTextView = findViewById(R.id.priority_textview);
        TextView dateTextView = findViewById(R.id.date_textview);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnURL = findViewById(R.id.btnURL);




        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String context = getIntent().getStringExtra("context");
        String priority = getIntent().getStringExtra("priority");
        String startDate = getIntent().getStringExtra("start_date");
        String endDate = getIntent().getStringExtra("end_date");
        String url = getIntent().getStringExtra("url");

        if (!url.isEmpty()) {
            url = getIntent().getStringExtra("url");
            String finalUrl = url;
            btnURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskDetails.this, WebViewToDo.class);
                    intent.putExtra("url", finalUrl);
                    startActivity(intent);
                }
            });
        } else {
            btnURL.setVisibility(View.GONE);
            btnURL.setEnabled(false);
        }




        // Mettre à jour les TextView avec les informations de la tâche
        titleTextView.setText(title);
        descriptionTextView.setText("Description : " + description);

        // Afficher l'icone du contexte en fonction de sa valeur
        if (context.equals("Sur PC")) {
            contextImageView.setImageResource(R.drawable.baseline_computer_24);
        } else if (context.equals("À la maison")) {
            contextImageView.setImageResource(R.drawable.baseline_home_24);
        } else if (context.equals("Au bureau")) {
            contextImageView.setImageResource(R.drawable.baseline_work_24);
        } else if (context.equals("Au téléphone")) {
            contextImageView.setImageResource(R.drawable.baseline_phone_android_24);
        }

        // Obtenir le Drawable pour la priorité en fonction de sa valeur
        Drawable priorityDrawable;
        if (priority.equalsIgnoreCase("Terminé")) {
            priorityDrawable = ContextCompat.getDrawable(this, R.drawable.priority_done_background);
        } else if (priority.equalsIgnoreCase("En cours")) {
            priorityDrawable = ContextCompat.getDrawable(this, R.drawable.priority_in_progress_background);
        } else {
            priorityDrawable = ContextCompat.getDrawable(this, R.drawable.priority_todo_background);
        }

        // Mettre à jour le TextView pour la priorité
        priorityTextView.setText(priority);
        priorityTextView.setBackground(priorityDrawable);


        // Mettre à jour le TextView pour les dates de début et de fin
        String dateString = "Du : " + formatDate(startDate) + " au " + formatDate(endDate);
        dateTextView.setText(dateString);




        // Bouton pour éditer la tâche
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetails.this, AddTask.class);
                intent.putExtra("edit", true);
                intent.putExtra("title", getIntent().getStringExtra("title"));
                intent.putExtra("description", getIntent().getStringExtra("description"));
                intent.putExtra("context", getIntent().getStringExtra("context"));
                intent.putExtra("priority", getIntent().getStringExtra("priority"));
                intent.putExtra("url", getIntent().getStringExtra("url"));
                intent.putExtra("start_date", getIntent().getStringExtra("start_date"));
                intent.putExtra("end_date", getIntent().getStringExtra("end_date"));
                startActivity(intent);
            }
        });

    }


    private String formatDate(String date) {
        try {
            return mDateFormat.format(Objects.requireNonNull(mDateFormat.parse(date)));
        } catch (Exception e) {
            return date;
        }
    }

}
