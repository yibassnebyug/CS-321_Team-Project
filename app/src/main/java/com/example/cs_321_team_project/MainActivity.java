package com.example.cs_321_team_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MediaList storage = new MediaList();
    ArrayList<String> list = new ArrayList<String>();
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    int insertIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //list.add("-empty-");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvMedia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);


        FloatingActionButton button = findViewById(R.id.floatingActionButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(v.getContext(), AddActivity.class));
                Intent addIntent = new Intent(v.getContext(), AddActivity.class);
                startActivityForResult.launch(addIntent);
            }
        });

    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    String genre = data.getStringExtra("genre");
                    String name = data.getStringExtra("name");
                    storage.toJSON(genre, name);
                    refreshItems(genre, name);
                }
            }
    );

    private void refreshItems(String genre, String name) {
        //adapter.clear();
        //ArrayList<ArrayList<String>> list = storage.fromJSON();
        list.add(insertIndex, name + "/" + genre + "/Ongoing");
        adapter.setItem(list);
        adapter.notifyItemInserted(insertIndex);
        insertIndex++;
    }
}