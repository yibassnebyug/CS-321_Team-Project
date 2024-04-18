package com.example.cs_321_team_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

    MediaList storage;
    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<String> sortedList = new ArrayList<String>();
    static ArrayList<String>searchList=new ArrayList<String>();
    RecyclerViewAdapter adapter;
    static int favoriteCount = 0;
    static AlertDialog alert;

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

        storage = new MediaList(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mainActivity);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mediaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, sortedList);
        recyclerView.setAdapter(adapter);

        if(storage.isFilePresent()) {
            fromStorage(storage.fromJSON());
        }

        // welcome pop-up
        /*if(storage.firstTime()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            TextView message = new TextView(this);
            message.setText("Welcome to MyCollection!\n\nManage your favorite topics!\n\nTap the ? for help.");
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setTextSize(18);
            builder.setView(message);

            alert = builder.create();
            alert.show();
        }*/

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
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        if (searchView != null) {
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            searchList.clear();
                            String text = (String) searchView.getQuery().toString();
                            for(String s : sortedList) {
                                String[] splitString = s.split("/");
                                if(splitString[0].contains(text)) {
                                    searchList.add(s);
                                }
                            }
                            adapter.setItem(searchList);
                            adapter.notifyDataSetChanged();
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return false;
                        }
                    });
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    adapter.setItem(sortedList);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView message = new TextView(this);
            message.setText("\nTap the plus button to add a new item.\n\n Tap the labels to sort your items.\n\nTap an item to edit.\n\nLong press on an item to delete.\n\nTap the star to pin an item.\n\nTap the magnifying glass to search for items.\n");
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setTextSize(18);
            builder.setView(message);
            alert = builder.create();
            alert.show();
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
            String favorite = data.getStringExtra("favorite");
            String formattedItem = name + "/" + genre + "/" + status + "/" + favorite;

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
                list.add(formattedItem);
                sortedList.add(formattedItem);

                storage.toJSON(sortedList);

                refreshItems();
            }
        } else if (resultCode == 3) { // when editing stuff
            String newGenre = data.getStringExtra("genre");
            String newStatus = data.getStringExtra("status");
            String newName = data.getStringExtra("name");

            String oldGenre = data.getStringExtra("oldGenre");
            String oldStatus = data.getStringExtra("oldStatus");
            String oldName = data.getStringExtra("oldName");

            String favorite = data.getStringExtra("favorite");

            String oldItem = oldName + "/" + oldGenre + "/" + oldStatus + "/" + favorite;
            String newItem = newName + "/" + newGenre + "/" + newStatus + "/" + favorite;

            if(list.contains(newItem)) {
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
                list.set(list.indexOf(oldItem), newItem);
                sortedList.set(sortedList.indexOf(oldItem), newItem);

                storage.toJSON(sortedList);

                refreshItems();
            }
        }
    }

    public boolean deleteItem(String genre, String name, String status, String favorite) {
        String formattedItem = name + "/" + genre + "/" + status + "/" + favorite;
        if(favorite.equals("true"))
            favoriteCount--;
        list.remove(formattedItem);
        sortedList.remove(formattedItem);
        storage.toJSON(sortedList);
        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
        return true;
    }

    public void favoriteItem(String genre, String name, String status, String oldFavorite) {
        boolean newFavorite = !(Boolean.parseBoolean(oldFavorite));

        String newItem = name + "/" + genre + "/" + status + "/" + String.valueOf(newFavorite);
        String oldItem = name + "/" + genre + "/" + status + "/" + oldFavorite;

        sortedList.add(favoriteCount, newItem);
        sortedList.remove(oldItem);

        favoriteCount++;

        list.set(list.indexOf(oldItem), newItem);

        storage.toJSON(sortedList);

        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
    }

    public void unfavoriteItem(String genre, String name, String status, String oldFavorite) {
        boolean newFavorite = !(Boolean.parseBoolean(oldFavorite));

        String newItem = name + "/" + genre + "/" + status + "/" + String.valueOf(newFavorite);
        String oldItem = name + "/" + genre + "/" + status + "/" + oldFavorite;

        sortedList.add(list.indexOf(oldItem)+favoriteCount, newItem);
        sortedList.remove(oldItem);

        favoriteCount--;

        list.set(list.indexOf(oldItem), newItem);

        storage.toJSON(sortedList);

        adapter.setItem(sortedList);
        adapter.notifyDataSetChanged();
    }

    public void fromStorage(ArrayList<String> fromStorage) {
        if(!(fromStorage.isEmpty())) {
            list = new ArrayList<String>(fromStorage);
            sortedList = new ArrayList<String>(fromStorage);
            refreshItems();

            for(String s : sortedList) {
                String[] splitString = s.split("/");
                if(splitString[3].equals("false"))
                    break;
                favoriteCount++;
            }
        }
    }

    private void refreshItems() {
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
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }

            Collections.sort(favoriteArray);
            Collections.sort(nonFavoriteArray);

            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);

            //Collections.sort(sortedList);
            nameSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted ascending");
        }
        else if (nameSortState == SORTED_ASCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }

            Collections.sort(favoriteArray, Collections.reverseOrder());
            Collections.sort(nonFavoriteArray, Collections.reverseOrder());

            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);

            nameSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickNameTitle: sorted descending");
        }
        else if(nameSortState == SORTED_DESCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }

            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);

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
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }
            Collections.sort(favoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[1].compareTo(split2[1]);
                }
            });
            Collections.sort(nonFavoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[1].compareTo(split2[1]);
                }
            });
            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);
            genreSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickGenreTitle: sorted ascending");
        }
        else if (genreSortState == SORTED_ASCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }
            Collections.sort(favoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[1].compareTo(split1[1]);
                }
            });
            Collections.sort(nonFavoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[1].compareTo(split1[1]);
                }
            });
            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);
            genreSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickGenreTitle: sorted descending");
        }
        else if(genreSortState == SORTED_DESCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }

            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);

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
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }
            Collections.sort(favoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[2].compareTo(split2[2]);
                }
            });
            Collections.sort(nonFavoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split1[2].compareTo(split2[2]);
                }
            });
            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);
            statusSortState = SORTED_ASCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted ascending");
        }
        else if (statusSortState == SORTED_ASCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }
            Collections.sort(favoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[2].compareTo(split1[2]);
                }
            });
            Collections.sort(nonFavoriteArray, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] split1 = o1.split("/");
                    String[] split2 = o2.split("/");
                    return split2[2].compareTo(split1[2]);
                }
            });
            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);
            statusSortState = SORTED_DESCENDING;
            Log.d("MainActivity", "onClickStatusTitle: sorted descending");
        }
        else if(statusSortState == SORTED_DESCENDING) {
            ArrayList<String> favoriteArray = new ArrayList<String>();
            for(int i = 0; i < favoriteCount; i++) { favoriteArray.add(sortedList.get(i)); }
            ArrayList<String> nonFavoriteArray = new ArrayList<String>();
            for(int i = favoriteCount; i < sortedList.size(); i++) { nonFavoriteArray.add(sortedList.get(i)); }

            ArrayList<String> favoriteBefore = new ArrayList<String>(favoriteArray);
            favoriteBefore.addAll(nonFavoriteArray);
            Collections.copy(sortedList, favoriteBefore);

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