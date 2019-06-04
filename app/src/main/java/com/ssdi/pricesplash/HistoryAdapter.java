package com.ssdi.pricesplash;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private ArrayList<History> histories;
    private Context context;
    private  DatabaseHelper databaseHelper;

    public HistoryAdapter(Context context, ArrayList<History> notes) {
        this.context = context;
        this.histories = notes;
    }

    public HistoryAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_row_layout, parent, false);
            return new MyViewHolder(itemView);

         }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.name.setText(histories.get(position).getName());
       holder.website.setText(histories.get(position).getWebsite());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,LoadHistory.class).putExtra("url",
                        histories.get(position).getUrl()));
            }
        });

       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               databaseHelper = new DatabaseHelper(context);
               databaseHelper.deleteNote(histories.get(position));
               histories.remove(position);
               notifyDataSetChanged();
           }
       });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  name, website;
        public ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.row1);
            website = view.findViewById(R.id.row2);
            delete = view.findViewById(R.id.delete);
        }

    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
