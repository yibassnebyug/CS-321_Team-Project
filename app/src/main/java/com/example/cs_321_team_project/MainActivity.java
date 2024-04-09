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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    /**
     * Refreshes name, genre, status titles based on sort states
     */
    private void refreshTitles() {
        TextView textView;

        // name state
        textView = findViewById(R.id.nameTitle);
        switch (nameSortState) {
            case UNSORTED:
                textView.setText(R.string.name);
                break;
            case SORTED_ASCENDING:
                textView.setText(R.string.nameAscending);
                break;
            case SORTED_DESCENDING:
                textView.setText(R.string.nameDescending);
                break;
        }

        // genre state
        textView = findViewById(R.id.genreTitle);
        switch (genreSortState) {
            case UNSORTED:
                textView.setText(R.string.genre);
                break;
            case SORTED_ASCENDING:
                textView.setText(R.string.genreAscending);
                break;
            case SORTED_DESCENDING:
                textView.setText(R.string.genreDescending);
                break;
        }

        // status state
        textView = findViewById(R.id.statusTitle);
        switch (statusSortState) {
            case UNSORTED:
                textView.setText(R.string.status);
                break;
            case SORTED_ASCENDING:
                textView.setText(R.string.statusAscending);
                break;
            case SORTED_DESCENDING:
                textView.setText(R.string.statusDescending);
                break;
        }
    }

    public void onClickNameTitle(View view) {
        Log.d("MainActivity", "onClickNameTitle");

        Log.d("MainActivity", "onClickNameTitle: nameTitle");
        if (nameSortState == UNSORTED || nameSortState == SORTED_DESCENDING) {
            Collections.sort(list);
            nameSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted ascending");
        }
        else if (nameSortState == SORTED_ASCENDING) {
            Collections.sort(list, Collections.reverseOrder());
            nameSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted descending");
        }
        genreSortState = UNSORTED;
        statusSortState = UNSORTED;

        adapter.setItem(list);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickNameTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickNameTitle: titles refreshed");

    }

    public void onClickGenreTitle(View view) {
        Log.d("MainActivity", "onClickGenreTitle");

        Log.d("MainActivity", "onClickGenreTitle: nameTitle");
        if (genreSortState == UNSORTED || genreSortState == SORTED_DESCENDING) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[1].compareTo(split2[1]);
                }
            });
            genreSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickGenreTitle: sorted ascending");
        }
        else if (genreSortState == SORTED_ASCENDING) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[1].compareTo(split1[1]);
                }
            });
            genreSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickGenreTitle: sorted descending");
        }
        nameSortState = UNSORTED;
        statusSortState = UNSORTED;

        adapter.setItem(list);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickGenreTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickGenreTitle: titles refreshed");
    }

    public void onClickStatusTitle(View view) {
        Log.d("MainActivity", "onClickStatusTitle");

        Log.d("MainActivity", "onClickStatusTitle: nameTitle");
        if (statusSortState == UNSORTED || statusSortState == SORTED_DESCENDING) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[1].compareTo(split2[1]);
                }
            });
            statusSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted ascending");
        }
        else if (statusSortState == SORTED_ASCENDING) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[1].compareTo(split1[1]);
                }
            });
            statusSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted descending");
        }
        nameSortState = UNSORTED;
        genreSortState = UNSORTED;

        adapter.setItem(list);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickStatusTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickStatusTitle: titles refreshed");
    }
}