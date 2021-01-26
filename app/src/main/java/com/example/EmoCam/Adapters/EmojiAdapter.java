package com.example.EmoCam.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EmoCam.R;
import com.example.EmoCam.sticker.DrawableSticker;
import com.example.EmoCam.sticker.StickerView;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {

    private List<Drawable> filters;
    private Context mContext;
    private List<String> filternames;
    private StickerView stickerView;

    public EmojiAdapter(List<Drawable> filters, List<String> filternames, Context context, StickerView stickerView) {
        this.filters = filters;
        this.mContext = context;
        this.filternames = filternames;
        this.stickerView = stickerView;
    }

    @NonNull
    @Override
    public EmojiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiAdapter.ViewHolder holder, final int position) {

        holder.filter_img.setImageDrawable(filters.get(position));
        holder.filter_name.setText(filternames.get(position));
        holder.filter_name.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.filter_layout.setOnClickListener(v -> stickerView.addSticker(new DrawableSticker(filters.get(position))));

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
