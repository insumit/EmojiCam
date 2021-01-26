package com.example.EmoCam.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EmoCam.R;
import com.example.EmoCam.helper.CompressImage;

import java.util.List;

public class ImageFiltersAdapter extends RecyclerView.Adapter<ImageFiltersAdapter.ViewHolder> {

    private List<Bitmap> filters;
    private ImageView imageView;
    private String imagepath;
    private Context mContext;
    private CompressImage compressImage;
    private List<String> filternames;
    private int currentPosition = -1;

    public ImageFiltersAdapter(List<Bitmap> filters, List<String> filternames, ImageView img, String originalImagepath, Context context) {
        this.filters = filters;
        this.imageView = img;
        this.imagepath = originalImagepath;
        this.mContext = context;
        this.compressImage = new CompressImage();
        this.filternames = filternames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.filter_img.setImageBitmap(filters.get(position));
        holder.filter_name.setText(filternames.get(position));
        //holder.filter_name.setTextColor(mContext.getResources().getColor(R.color.black));

        holder.filter_layout.setOnClickListener(view -> {
            imageView.setImageBitmap(filters.get(position));
            currentPosition = position;
            notifyDataSetChanged();
        });
        if(currentPosition==position){
            holder.filter_name.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        else
        {
            holder.filter_name.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }



    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView filter_name;
        ImageView filter_img;
        LinearLayout filter_layout;

        public ViewHolder(View view) {
            super(view);
            filter_name = view.findViewById(R.id.filter_item_name);
            filter_img = view.findViewById(R.id.filter_item_img);
            filter_layout = view.findViewById(R.id.filter_item_layout);
        }
    }
}
