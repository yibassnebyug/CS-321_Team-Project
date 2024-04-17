package com.example.cs_321_team_project;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static List<String> mData;
    private static LayoutInflater mInflater;
    private final Context mContext;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mContext = context;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] media = mData.get(position).split("/");
        holder.displayName.setText(media[0]);
        holder.displayGenre.setText(media[1]);
        holder.displayStatus.setText(media[2]);
        holder.favoriteButton.setTag(media[3]);

        String favorite = String.valueOf(holder.favoriteButton.getTag());

        if(favorite.equals("false"))
            holder.favoriteButton.setImageResource(R.drawable.unfilled_star);
        else if(favorite.equals("true"))
            holder.favoriteButton.setImageResource(R.drawable.filled_star);

        holder.favoriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //String favorite = String.valueOf(holder.favoriteButton.getTag());

                if(favorite.equals("false")) {
                    holder.favoriteButton.setImageResource(R.drawable.filled_star);
                    holder.favoriteButton.setTag("true");
                    ((MainActivity)mContext).favoriteItem(holder.displayGenre.getText().toString(), holder.displayName.getText().toString(), holder.displayStatus.getText().toString(), "false");

                }
                else if(favorite.equals("true")) {
                    holder.favoriteButton.setImageResource(R.drawable.unfilled_star);
                    holder.favoriteButton.setTag("false");
                    ((MainActivity)mContext).unfavoriteItem(holder.displayGenre.getText().toString(), holder.displayName.getText().toString(), holder.displayStatus.getText().toString(), "true");

                }
            }
        });
    }

    public void clear() {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView displayName;
        TextView displayGenre;
        TextView displayStatus;
        ImageButton favoriteButton;

        ViewHolder(View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.displayName);
            displayGenre = itemView.findViewById(R.id.displayGenre);
            displayStatus = itemView.findViewById(R.id.displayStatus);
            favoriteButton = itemView.findViewById(R.id.favoriteItem);
            if(favoriteButton.getTag().equals("true"))
                favoriteButton.setImageResource(R.drawable.filled_star);
            else if(favoriteButton.getTag().equals("false"))
                favoriteButton.setImageResource(R.drawable.unfilled_star);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent editIntent = new Intent(v.getContext(), EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("genre", displayGenre.getText().toString());
            bundle.putString("status", displayStatus.getText().toString());
            bundle.putString("name", displayName.getText().toString());
            bundle.putString("favorite", favoriteButton.getTag().toString());
            editIntent.putExtras(bundle);
            ((Activity) mContext).startActivityForResult(editIntent, 3);
        }

        @Override
        public boolean onLongClick(View v) {
            return ((MainActivity)mContext).deleteItem(displayGenre.getText().toString(), displayName.getText().toString(), displayStatus.getText().toString(), favoriteButton.getTag().toString());
        }
    }

    public void setItem(List<String> array) {
        mData = new ArrayList<String>(array);
    }
}