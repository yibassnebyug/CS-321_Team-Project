package com.example.cs_321_team_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Arrays;

public class AddActivity extends AppCompatActivity {

    static ArrayList<String> genreChoices = new ArrayList<String>(Arrays.asList("-Select Genre-", "Movie", "Book", "Video Game", "TV Show", "Music", "-New Genre-"));
    static ArrayList<String> statusChoices = new ArrayList<String>(Arrays.asList("-Select Status-", "Ongoing", "Finished", "On-Hold", "Dropped", "For Later"));

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please check parameters...");
        builder.setCancelable(true);
        AlertDialog alert = builder.create();

        AlertDialog.Builder newGenreAlert = new AlertDialog.Builder(this);
        newGenreAlert.setTitle("Please name new genre...");
        newGenreAlert.setCancelable(false);

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
                    alert.show();
                }
                else {
                    if(genreSelection.equals("-New Genre-")) {
                        EditText newGenreText = new EditText(AddActivity.this);

                        newGenreAlert.setView(newGenreText);
                        newGenreAlert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String newGenre = newGenreText.getText().toString();
                                if(!(newGenre.isEmpty())) {
                                    dialog.cancel();
                                    genreChoices.add(genreChoices.indexOf("-New Genre-"), newGenre);
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("genre", newGenre);
                                    resultIntent.putExtra("status", statusSelection);
                                    resultIntent.putExtra("name", mediaName);
                                    resultIntent.putExtra("favorite", "false");
                                    setResult(2, resultIntent);
                                    finish();
                                }
                                else {
                                    dialog.cancel();
                                }
                            }
                        });

                        newGenreAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                        newGenreAlert.show();
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
            }
        });
    }
}