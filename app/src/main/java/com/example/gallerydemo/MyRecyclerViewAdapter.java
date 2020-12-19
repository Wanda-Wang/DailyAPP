package com.example.gallerydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private LayoutInflater mLayoutInflater = null;
    private String[] mTitles = null;

    public MyRecyclerViewAdapter (Context context){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mTitles = new String[20];
        for (int i = 0; i < 20; i++){
            int index = i + 1;
            mTitles[i] = "item" + index;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_listview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mTitles[position]);

    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
