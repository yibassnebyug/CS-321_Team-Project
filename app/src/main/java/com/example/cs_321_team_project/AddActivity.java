package com.example.cs_321_team_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_topic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Please enter a valid name");
        builder1.setCancelable(true);
        AlertDialog alert1 = builder1.create();


        String[] arraySpinner = new String[] { "-Select-", "Movie", "Book", "Video Game", "TV Show", "Music" };
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText textBox = (EditText) findViewById(R.id.editTextText2);
        Button button = findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerSelection = spinner.getSelectedItem().toString();
                String mediaName = textBox.getText().toString();
                if(spinnerSelection.equals("-Select-") || mediaName.isEmpty()) {
                    alert1.show();
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("genre", spinnerSelection);
                    resultIntent.putExtra("name", mediaName);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}