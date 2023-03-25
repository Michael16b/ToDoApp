package com.example.todomobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Spinner mContextSpinner;
    private Spinner mPrioritySpinner;
    private TextInputEditText mStartDateEditText;
    private TextInputEditText mEndDateEditText;
    private EditText mUrlEditText;
    private Boolean mIsValidTask = true;
    private Button mSaveButton;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mContextSpinner = findViewById(R.id.context_spinner);
        mPrioritySpinner = findViewById(R.id.priority_spinner);
        mSaveButton = findViewById(R.id.save_button);
        mStartDateEditText = findViewById(R.id.start_date_picker_edittext);
        mEndDateEditText = findViewById(R.id.end_date_picker_edittext);
        mUrlEditText = findViewById(R.id.url_edit_text);

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
                String context = mContextSpinner.getSelectedItem().toString();
                String priority = mPrioritySpinner.getSelectedItem().toString();
                String url = mUrlEditText.getText().toString().trim();
                String startDate = Objects.requireNonNull(mStartDateEditText.getText()).toString().trim();
                String endDate = Objects.requireNonNull(mEndDateEditText.getText()).toString().trim();
                UrlValidator urlValidator = new UrlValidator();

                if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty())  {
                    Toast.makeText(AddTask.this, "Veuillez remplir tous les dates", Toast.LENGTH_LONG).show();
                    mIsValidTask = false;
                }
                if (!url.isEmpty()) {
                    if (!urlValidator.isValid(url)) {
                        Toast.makeText(AddTask.this, "Veuillez entrer une URL valide", Toast.LENGTH_LONG).show();
                        mIsValidTask = false;
                    }
                }
                if (mIsValidTask) {
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("context", context);
                    intent.putExtra("priority", priority);
                    intent.putExtra("url", url);
                    intent.putExtra("start_date", startDate);
                    intent.putExtra("end_date", endDate);
                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
        });

        // Configurer les spinners
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.context_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mContextSpinner.setAdapter(statusAdapter);

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