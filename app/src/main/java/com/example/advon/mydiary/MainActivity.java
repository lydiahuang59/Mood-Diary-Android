package com.example.advon.mydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextClock;
import android.view.View;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.util.TypedValue;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.reflect.Type;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private Typeface mainFont;
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private List<DiaryEntry> entryList;
    private TextClock currentTime;
    private TextClock currentDate;
    public static final DateFormat sdf = new SimpleDateFormat("HH:mm a EEE MMM dd, yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateTime();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        entryList = new ArrayList<>();
        adapter = new EntryAdapter(this, entryList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        String json = appSharedPrefs.getString("DiaryEntries", "");
        //prepareEntries(json);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            String new_time = extras.getString(DiaryActivity.DIARY_TIME);
            String new_title = extras.getString(DiaryActivity.DIARY_TITLE);
            String new_entry = extras.getString(DiaryActivity.DIARY_BODY);
            ArrayList<Integer> new_emotions = extras.getIntegerArrayList(DiaryActivity.DIARY_EMOTION);
            ArrayList<Integer> emotion_powers = extras.getIntegerArrayList(DiaryActivity.DIARY_EMOTION_LEVEL);
            addNewEntry(new_time, new_title, new_entry, new_emotions, emotion_powers);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String jsonEntries = gson.toJson(entryList);
        prefsEditor.putString("DiaryEntries", jsonEntries);
        prefsEditor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Gson gson = new Gson();
            String jsonEntries = gson.toJson(entryList);
            savedInstanceState.putString("DiaryEntries", jsonEntries);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void prepareEntries(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DiaryEntry>>(){}.getType();
        List<DiaryEntry> newEntryList = gson.fromJson(json, type);
        if (newEntryList != null) {
            for (DiaryEntry i : newEntryList) {
                entryList.add(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) return;
        String json = savedInstanceState.getString("DiaryEntries", "");
        prepareEntries(json);
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    protected void addNewEntry(String time, String title, String entry, ArrayList<Integer> emotions,
                               ArrayList<Integer> powers) {
        DiaryEntry newEntry = new DiaryEntry(title, time, entry, emotions, powers);
        entryList.add(newEntry);
        adapter.notifyDataSetChanged();
    }

    protected void updateTime() {
        currentTime = (TextClock) findViewById(R.id.textClock);
        currentDate = (TextClock) findViewById(R.id.textDate);

        currentTime.setFormat12Hour("hh:mm a");
        currentDate.setFormat12Hour("EEE, MMM d");

        Typeface typeface = ResourcesCompat.getFont(this, R.font.fjalla_one);
        currentTime.setTypeface(typeface);
        currentDate.setTypeface(typeface);
        }

    public void newEntry(View view) {
        Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
        MainActivity.this.startActivity(intent);

    }
}
