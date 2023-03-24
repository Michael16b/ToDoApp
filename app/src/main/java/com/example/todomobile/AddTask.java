package com.example.todomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddTask extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Spinner mStatusSpinner;
    private Spinner mPrioritySpinner;
    private TextInputEditText mStartDateEditText;
    private TextInputEditText mEndDateEditText;
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
        mSaveButton = findViewById(R.id.save_button);
        mStartDateEditText = findViewById(R.id.start_date_picker_edittext);
        mEndDateEditText = findViewById(R.id.end_date_picker_edittext);

        mStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mStartDateEditText);
            }
        });

        mEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mEndDateEditText);
            }
        });


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString().trim();
                String description = mDescriptionEditText.getText().toString().trim();
                String status = mStatusSpinner.getSelectedItem().toString();
                String priority = mPrioritySpinner.getSelectedItem().toString();
                String startDate = Objects.requireNonNull(mStartDateEditText.getText()).toString().trim();
                String endDate = Objects.requireNonNull(mEndDateEditText.getText()).toString().trim();

                if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(AddTask.this, "Veuillez remplir tous les dates", Toast.LENGTH_LONG).show();
                    return;
                }


                // Faire quelque chose avec les données entrées par l'utilisateur
                Toast.makeText(AddTask.this, "Tâche ajoutée avec succès", Toast.LENGTH_LONG).show();

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

        // Configurer les DatePickers
        String startDate = getIntent().getStringExtra("start_date");
        String endDate = getIntent().getStringExtra("end_date");
        if (startDate != null && endDate != null) {
            mStartDateEditText.setText(startDate);
            mEndDateEditText.setText(endDate);
        }

    }


    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editText.setText(dateString);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }



}