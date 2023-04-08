package com.example.todomobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.validator.routines.UrlValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTask extends AppCompatActivity {

    /**Zone de saisie du Titre et Description*/
    private EditText mTitleEditText, mDescriptionEditText, mUrlEditText;
    /**Spinner déroulant pour choisir le contexte et la priorité*/
    private Spinner mContextSpinner, mPrioritySpinner;
    /**Selectionneur de date de début et de date de fin*/
    private TextInputEditText mStartDateEditText, mEndDateEditText;
    /**Variable booléenne vrai si tous les attributs sont corrects, faux sinon.*/
    private Boolean mIsValidTask = true;

    //Variables permettant de récupérer les informations rentrées par l'utilisateur
    int id;
    String title, description, context, priority, url, startDate, endDate;

    //Initialisation d'une date au format français
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        //Initialisation des attributs de l'activité
        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mContextSpinner = findViewById(R.id.context_spinner);
        mPrioritySpinner = findViewById(R.id.priority_spinner);
        Button saveButton = findViewById(R.id.save_button);
        mStartDateEditText = findViewById(R.id.start_date_picker_edittext);
        mEndDateEditText = findViewById(R.id.end_date_picker_edittext);
        mUrlEditText = findViewById(R.id.url_edit_text);
        boolean mIsEdit = getIntent().hasExtra("edit");


        ////Configuration du spinner (filtre) de priorité
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);

        //Configuration du spinner (filtre) de contexte
        ArrayAdapter<CharSequence> contextAdapter = ArrayAdapter.createFromResource(this,
                R.array.context_array, android.R.layout.simple_spinner_item);
        contextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mContextSpinner.setAdapter(contextAdapter);


        //Si le but est de modifier une tâche alors on rentre dans cette condition
        if (getIntent().hasExtra("edit")) {

            //Récupération des valeurs originales (de la tâche déjà créée)
            id = getIntent().getIntExtra("id", 0);
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            context = getIntent().getStringExtra("context");
            priority = getIntent().getStringExtra("priority");
            startDate = getIntent().getStringExtra("start_date");
            endDate = getIntent().getStringExtra("end_date");
            url = getIntent().getStringExtra("url");

            //Affichage des anciennes valeurs sur l'activité
            mTitleEditText.setText(title);
            mDescriptionEditText.setText(description);
            mStartDateEditText.setText(startDate);
            mEndDateEditText.setText(endDate);
            if (url != null) mUrlEditText.setText(url);

            switch (context) {
                case "Sur PC":
                    mContextSpinner.setSelection(0);
                    break;
                case "À la maison":
                    mContextSpinner.setSelection(1);
                    break;
                case "Au bureau":
                    mContextSpinner.setSelection(2);
                    break;
                case "Au téléphone":
                    mContextSpinner.setSelection(3);
                    break;
            }

            switch (priority) {
                case "À faire":
                    mPrioritySpinner.setSelection(0);
                    break;
                case "En cours":
                    mPrioritySpinner.setSelection(1);
                    break;
                case "Terminé":
                    mPrioritySpinner.setSelection(2);
                    break;
            }
        }


        //Afficher les date picker sur clic
        mStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDatePickerDialog(mStartDateEditText);}
        });

        mEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDatePickerDialog(mEndDateEditText);}
        });


        //Button enregistrer
        saveButton.setOnClickListener(e -> {

            //Récupération des valeurs
            title = mTitleEditText.getText().toString().trim();
            description = mDescriptionEditText.getText().toString().trim();
            context = mContextSpinner.getSelectedItem().toString();
            priority = mPrioritySpinner.getSelectedItem().toString();
            url = mUrlEditText.getText().toString().trim();
            startDate = mStartDateEditText.getText().toString().trim();
            endDate = mEndDateEditText.getText().toString().trim();
            UrlValidator urlValidator = new UrlValidator();

            //Vérification des champs vides
            if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty())  {
                Toast.makeText(AddTask.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                mIsValidTask = false;
            } else {
                mIsValidTask = true;
            }

            //Vérification de l'url (vide ou non)
            if (!url.isEmpty()) {
                if (!urlValidator.isValid(url)) {
                    Toast.makeText(AddTask.this, "Veuillez entrer une URL valide", Toast.LENGTH_LONG).show();
                    mIsValidTask = false;
                } else {
                    mIsValidTask = true;
                }
            }

            //Si le but est la modification d'une tâche, on rentre dans cette condition
            if (mIsEdit) {
                //Mise à jour
                Database mydb = new Database(this.getBaseContext());
                mydb.updateData(id, title, description, startDate, endDate, context, priority, url);
                finish();
            }else{

                //Si tout les champs sont valides alors on enregistre les modifications
                if (mIsValidTask) {
                    Intent intent = new Intent();
                    Database myDB = new Database(this.getBaseContext());
                    myDB.addTask(title, description, startDate, endDate, context, priority, url);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        //Récupérer les dates dans les dates picker
        String startDate = mStartDateEditText.getText().toString().trim();
        String endDate = mEndDateEditText.getText().toString().trim();

        //Tester la valeur null sur les selectionneur de date
        if (startDate == null) {
            startDate = mDateFormat.format(Calendar.getInstance().getTime());
        }
        if (endDate == null) {
            endDate = mDateFormat.format(Calendar.getInstance().getTime());
        }
        mStartDateEditText.setText(startDate);
        mEndDateEditText.setText(endDate);
    }

    //Affichage des date picker
    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dayString = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                        String monthString = ((monthOfYear + 1) < 10 ? "0" : "") + (monthOfYear + 1);
                        String dateString = dayString + "/" + monthString + "/" + year;
                        editText.setText(dateString);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

}