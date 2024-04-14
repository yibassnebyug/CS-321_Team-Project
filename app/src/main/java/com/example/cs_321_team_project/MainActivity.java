package com.example.cs_321_team_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {

    static MediaList storage = new MediaList();
    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<String> sortedList = new ArrayList<String>();
    RecyclerViewAdapter adapter;
    static RecyclerView recyclerView;
    static int insertIndex = 0;
    static AlertDialog alert;
    static boolean freshStart = true;

    final int UNSORTED = 0;
    final int SORTED_ASCENDING = 1;
    final int SORTED_DESCENDING = 2;
    int nameSortState = UNSORTED, genreSortState = UNSORTED, statusSortState = UNSORTED;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mainActivity);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvMedia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, sortedList);
        recyclerView.setAdapter(adapter);

        if(freshStart) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            TextView message = new TextView(this);
            message.setText("Welcome to MyCollection!\nManage your favorite topics!\nTap the ? for help.");
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setTextSize(18);
            builder.setView(message);

            alert = builder.create();
            alert.show();
            freshStart = !freshStart;
        }

        FloatingActionButton button = findViewById(R.id.floatingActionButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(v.getContext(), AddActivity.class);
                startActivityForResult(addIntent, 2); // ignore the depreciation prompt, it works
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView message = new TextView(this);
            message.setText("Tap the plus button to add a new topic.\n\n Tap the column labels to sort your items.\n\nTap an item to edit its information.\n\nLong press on an item to delete it.");
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setTextSize(18);
            builder.setView(message);
            alert = builder.create();
            alert.show();
        }
        else if (item.getItemId() == R.id.search)
        {
            // add search function here
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) { // when adding stuff
            String genre = data.getStringExtra("genre");
            String status = data.getStringExtra("status");
            String name = data.getStringExtra("name");

            String formattedItem = name + "/" + genre + "/" + status;

            if(list.contains(formattedItem)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                TextView message = new TextView(this);
                message.setText("Could not add item.\nItem already exists.");
                message.setGravity(Gravity.CENTER_HORIZONTAL);
                message.setTextSize(18);
                builder.setView(message);
                alert = builder.create();
                alert.show();
            }
            else {
                storage.toJSON(genre, name, status);

                list.add(insertIndex, formattedItem);
                sortedList.add(""); // used to make sortedList and list have same number of indexes

                insertIndex++;

                refreshItems();
            }
        } else if (resultCode == 3) { // when editing stuff
            String newGenre = data.getStringExtra("genre");
            String newStatus = data.getStringExtra("status");
            String newName = data.getStringExtra("name");

            String oldGenre = data.getStringExtra("oldGenre");
            String oldStatus = data.getStringExtra("oldStatus");
            String oldName = data.getStringExtra("oldName");

            String formattedItem = oldName + "/" + oldGenre + "/" + oldStatus;

            if(list.contains(formattedItem)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                TextView message = new TextView(this);
                message.setText("Could not update item.\nItem already exists.");
                message.setGravity(Gravity.CENTER_HORIZONTAL);
                message.setTextSize(18);
                builder.setView(message);
                alert = builder.create();
                alert.show();
            }
            else {
                int position = list.indexOf(formattedItem);
                list.set(position, newName + "/" + newGenre + "/" + newStatus);
                refreshItems();
            }
        }
    }

    public boolean deleteItem(String genre, String name, String status) {
        String formattedItem = name + "/" + genre + "/" + status;
        list.remove(formattedItem);
        sortedList.remove(formattedItem);
        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
        insertIndex--;
        return true;
    }

    private void refreshItems() {
        Collections.copy(sortedList, list);
        adapter.setItem(sortedList);
        nameSortState = UNSORTED;
        genreSortState = UNSORTED;
        statusSortState = UNSORTED;
        refreshTitles();
        adapter.notifyDataSetChanged();
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
        if (nameSortState == UNSORTED) {
            Collections.sort(sortedList);
            nameSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted ascending");
        }
        else if (nameSortState == SORTED_ASCENDING) {
            Collections.sort(sortedList, Collections.reverseOrder());
            nameSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted descending");
        }
        else if(nameSortState == SORTED_DESCENDING) {
            Collections.copy(sortedList, list);
            //sortedList = list;
            nameSortState = UNSORTED;
            Log.d("MainActivity", "onClickNameTitle: unsorted");
        }

        genreSortState = UNSORTED;
        statusSortState = UNSORTED;

        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickNameTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickNameTitle: titles refreshed");

    }

    public void onClickGenreTitle(View view) {
        Log.d("MainActivity", "onClickGenreTitle");

        Log.d("MainActivity", "onClickGenreTitle: nameTitle");
        if (genreSortState == UNSORTED) {
            Collections.sort(sortedList, new Comparator<String>() {
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
            Collections.sort(sortedList, new Comparator<String>() {
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
        else if(genreSortState == SORTED_DESCENDING) {
            Collections.copy(sortedList, list);
            //sortedList = list;
            genreSortState = UNSORTED;
            Log.d("MainActivity", "onClickGenreTitle: unsorted");
        }

        nameSortState = UNSORTED;
        statusSortState = UNSORTED;

        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickGenreTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickGenreTitle: titles refreshed");
    }

    public void onClickStatusTitle(View view) {
        Log.d("MainActivity", "onClickStatusTitle");

        Log.d("MainActivity", "onClickStatusTitle: nameTitle");
        if (statusSortState == UNSORTED) {
            Collections.sort(sortedList, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[2].compareTo(split2[2]);
                }
            });
            statusSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted ascending");
        }
        else if (statusSortState == SORTED_ASCENDING) {
            Collections.sort(sortedList, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[2].compareTo(split1[2]);
                }
            });
            statusSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted descending");
        }
        else if(statusSortState == SORTED_DESCENDING) {
            Collections.copy(sortedList, list);
            //sortedList = list;
            statusSortState = UNSORTED;
            Log.d("MainActivity", "onClickStatusTitle: unsorted");
        }

        nameSortState = UNSORTED;
        genreSortState = UNSORTED;

        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
        Log.d("MainActivity", "onClickStatusTitle: adapter notified");
        refreshTitles();
        Log.d("MainActivity", "onClickStatusTitle: titles refreshed");
    }
}