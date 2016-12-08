package com.schrobieapps.recyclerviewonmainthread;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ryanjohnston on 12/8/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<String> listItems;

    public RecyclerViewAdapter() {
    }

    public RecyclerViewAdapter(List listItems) {
        this.listItems = listItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cell, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.my_cell_text);
        }
    }
}
