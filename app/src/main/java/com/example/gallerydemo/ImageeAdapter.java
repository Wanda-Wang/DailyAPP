package com.example.gallerydemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImageeAdapter extends RecyclerView.Adapter <ImageeAdapter.ViewHolder> {

    private List<Imagee> mImageList = new ArrayList<>();

    public ImageeAdapter (List<Imagee> imagees){
        this.mImageList = imagees;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_album, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Imagee imagee = mImageList.get(position);
        holder.imageeImage.setImageResource(imagee.getImageeId());

    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageeImage = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageeImage = (ImageView) itemView.findViewById(R.id.album);
        }
    }
}
