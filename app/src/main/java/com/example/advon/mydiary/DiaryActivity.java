package com.example.advon.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.view.View;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import java.util.TimerTask;

public class DiaryActivity extends AppCompatActivity {

    private Typeface mainFont;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private boolean isEditable = true;
    private RecyclerView recyclerView;
    private EmotionAdapter adapter;

    private String bodyText = "";
    private String titleText = "";
    private ArrayList<Integer> emotionList = new ArrayList<Integer>();
    private ArrayList<Integer> emotionPower = new ArrayList<>();

    public final static String DIARY_BODY = "entry_text";
    public final static String DIARY_TITLE = "entry_title_text";
    public final static String DIARY_TIME = "entry_time_text";
    public final static String DIARY_EMOTION = "entry_time_emotion";
    public final static String DIARY_EMOTION_LEVEL = "entry_time_emotion_level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_diary);

        recyclerView = (RecyclerView) findViewById(R.id.emotionDisplay);
        adapter = new EmotionAdapter(this, emotionList, emotionPower);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            isEditable = false;
            String new_title = extras.getString(DiaryActivity.DIARY_TITLE);
            EditText title = (EditText) findViewById(R.id.diaryTitle);
            title.setText(new_title);
            title.setKeyListener(null);
            String new_entry = extras.getString(DiaryActivity.DIARY_BODY);
            EditText body = (EditText) findViewById(R.id.diaryBody);
            body.setText(new_entry);
            body.setKeyListener(null);
            Button save = findViewById(R.id.button2);
            save.setVisibility(View.INVISIBLE);
            save.setOnClickListener(null);
            ArrayList<Integer> new_emotions = extras.getIntegerArrayList(DiaryActivity.DIARY_EMOTION);
            ArrayList<Integer> new_powers = extras.getIntegerArrayList(DiaryActivity.DIARY_EMOTION_LEVEL);
            addEmotions(new_emotions, new_powers);
            Button select = findViewById(R.id.emotionSelect);
            select.setOnClickListener(null);
        }
        time();
        title();
        body();
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int halfSpace;

        public SpacesItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }
            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }

    protected void addEmotions(ArrayList<Integer> emotions, ArrayList<Integer> levels) {
        for (int i = 0; i < emotions.size(); i++) {
            emotionList.add(emotions.get(i));
            emotionPower.add(levels.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void chooseMood(View v) {
        Intent intent = new Intent(DiaryActivity.this, EmotionChoice.class);
        DiaryActivity.this.startActivityForResult(intent, 1);
    }



    protected void time() {
        Typeface typeface = ResourcesCompat.getFont(this, R.font.fjalla_one);
        TextClock textClock = (TextClock) findViewById(R.id.diaryTime);
        textClock.setFormat12Hour("hh:mm a, MMM d");
        textClock.setTypeface(typeface);
    }

    protected void title() {
        EditText editTextStop = (EditText) findViewById(R.id.diaryTitle);
        editTextStop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timer != null)
                    timer.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            titleText = s.toString();
                        }

                    }, DELAY);
                }
            }
        });
    }

    protected void body() {
        EditText editTextStop = (EditText) findViewById(R.id.diaryBody);
        editTextStop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timer != null)
                    timer.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            bodyText = s.toString();
                        }

                    }, DELAY);
                }
            }
        });
    }

    public void saveWork(View view) {
        Date date = new Date();
        String strDate = MainActivity.sdf.format(date);
        Bundle bundle = new Bundle();
        bundle.putString(DIARY_BODY, bodyText);
        bundle.putString(DIARY_TITLE, titleText);
        bundle.putString(DIARY_TIME, strDate);
        bundle.putIntegerArrayList(DIARY_EMOTION, emotionList);
        bundle.putIntegerArrayList(DIARY_EMOTION_LEVEL, emotionPower);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int newEmotion = data.getIntExtra(EmotionChoice.EMOTION, 0);
                int emotionLevel = data.getIntExtra(EmotionChoice.EMOTION_LEVEL, 0);
                if (newEmotion != 0 && emotionLevel > 0) {
                    emotionList.add(newEmotion);
                    emotionPower.add(emotionLevel);
                    adapter.notifyDataSetChanged();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
