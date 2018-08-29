package com.example.advon.mydiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {

    private Context mContext;
    private List<DiaryEntry> entryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public String body = "";
        public String titleText = "";
        public int emotion = 0;
        public TextView title, date;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public EntryAdapter(Context mContext, List<DiaryEntry> entryList) {
        this.mContext = mContext;
        this.entryList = entryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diaryentrylayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DiaryEntry entry = entryList.get(position);
        holder.title.setText(entry.getTitle());
        holder.date.setText(entry.getDate());
        holder.body = entry.getBody();
        holder.emotion = entry.getMood();
        holder.titleText = entry.getTitle();

        // loading album cover using Glide library
        Glide.with(mContext).load(entry.getMood()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DiaryActivity.class);
                Bundle b = new Bundle();
                b.putString(DiaryActivity.DIARY_TITLE, holder.titleText);
                b.putString(DiaryActivity.DIARY_BODY, holder.body);
                b.putInt(DiaryActivity.DIARY_EMOTION, holder.emotion);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.entry_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
