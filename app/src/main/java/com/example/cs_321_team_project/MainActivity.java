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
import java.util.Collections;
import java.util.Comparator;

// to get app resources
import android.content.res.Resources;

// debugging
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaList storage = new MediaList();
    ArrayList<String> list = new ArrayList<String>();
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    final int UNSORTED = 0;
    final int SORTED_ASCENDING = 1;
    final int SORTED_DESCENDING = 2;
    int nameSortState = UNSORTED, genreSortState = UNSORTED, statusSortState = UNSORTED;
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

                Log.d("onClick event", "View id " + v.getId());
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

    public void onClickTextView(View view) {
        Log.d("MainActivity", "onClickTextView");

        int viewId = view.getId();
//        TextView textView = findViewById(viewId);

        if (viewId == R.id.nameTitle) {
            Log.d("MainActivity", "onClickTextView: nameTitle");
            if (nameSortState == UNSORTED || nameSortState == SORTED_DESCENDING) {
                Collections.sort(list);
                nameSortState = SORTED_ASCENDING;
                Log.d("MainActivity", "onClickTextView: sorted ascending");
            }
            else if (nameSortState == SORTED_ASCENDING) {
                Collections.sort(list, Collections.reverseOrder());
                nameSortState = SORTED_DESCENDING;
                Log.d("MainActivity", "onClickTextView: sorted descending");
            }

        }

        adapter.setItem(list);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickTextView: adapter notified");



    }
}