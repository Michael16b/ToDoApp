package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTask extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Spinner mStatusSpinner;
    private Spinner mPrioritySpinner;
    private DatePicker mStartDatePicker;
    private DatePicker mEndDatePicker;
    private Button mSaveButton;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mStatusSpinner = findViewById(R.id.status_spinner);
        mPrioritySpinner = findViewById(R.id.priority_spinner);
        mStartDatePicker = findViewById(R.id.start_date_picker);
        mEndDatePicker = findViewById(R.id.end_date_picker);

        mSaveButton = findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString().trim();
                String description = mDescriptionEditText.getText().toString().trim();
                String status = mStatusSpinner.getSelectedItem().toString();
                String priority = mPrioritySpinner.getSelectedItem().toString();
                String startDate = mDateFormat.format(getDateFromDatePicker(mStartDatePicker));
                String endDate = mDateFormat.format(getDateFromDatePicker(mEndDatePicker));

                // Faire quelque chose avec les données entrées par l'utilisateur
                // Par exemple, les afficher dans un Toast
                String message = "Titre : " + title + "\nDescription : " + description
                        + "\nEtat : " + status + "\nPriorité : " + priority
                        + "\nDate de Départ : " + startDate + "\nDate de Fin : " + endDate;
                Toast.makeText(AddTask.this, message, Toast.LENGTH_LONG).show();

                finish();
            }
        });

        // Configurer les spinners
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(statusAdapter);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);

        // Configurer les DatePicker
        Calendar calendar = Calendar.getInstance();
        mStartDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        mEndDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
    }

    // Fonction pour convertir un DatePicker en Date
    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}

