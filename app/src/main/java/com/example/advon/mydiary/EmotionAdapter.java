package com.example.advon.mydiary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder> {

    private List<Integer> emojis;
    private List<Integer> powers;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    EmotionAdapter(Context context, List<Integer> emojis, List<Integer> powers) {
        this.mInflater = LayoutInflater.from(context);
        this.emojis = emojis;
        this.powers = powers;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.emotion, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int emoji = emojis.get(position);
        int power = powers.get(position);
        holder.emoji.setImageResource(emoji);
        holder.emojiText.setText(Integer.toString(power));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return emojis.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView emoji;
        TextView emojiText;

        ViewHolder(View itemView) {
            super(itemView);
            emoji = itemView.findViewById(R.id.emoji);
            emojiText = itemView.findViewById(R.id.emojiText);
        }

    }
}
