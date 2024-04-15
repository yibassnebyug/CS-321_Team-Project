package com.example.cs_321_team_project;

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

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Please check parameters...");
        builder1.setCancelable(true);
        AlertDialog alert1 = builder1.create();

        String[] genreChoices = new String[] { "-Select Genre-", "Movie", "Book", "Video Game", "TV Show", "Music" };
        String[] statusChoices = new String[] { "-Select Status-", "Ongoing", "Finished", "On-Hold", "Dropped", "For Later"};

        Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        Spinner statusSpinner = (Spinner) findViewById(R.id.statusSpinner);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreChoices);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusChoices);

        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        EditText textBox = (EditText) findViewById(R.id.addTextBox);
        Button button = findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genreSelection = genreSpinner.getSelectedItem().toString();
                String statusSelection = statusSpinner.getSelectedItem().toString();
                String mediaName = textBox.getText().toString();
                if(genreSelection.equals("-Select Genre-") || statusSelection.equals("-Select Status-") || mediaName.isEmpty()) {
                    alert1.show();
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("genre", genreSelection);
                    resultIntent.putExtra("status", statusSelection);
                    resultIntent.putExtra("name", mediaName);
                    resultIntent.putExtra("favorite", "false");
                    setResult(2, resultIntent);
                    finish();
                }
            }
        });

    }
}