package com.example.advon.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.view.View;
import android.content.DialogInterface;

import java.util.Date;
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

    private String bodyText = "";
    private String titleText = "";
    private int currentEmotion = R.drawable.happy1;

    public final static String DIARY_BODY = "entry_text";
    public final static String DIARY_TITLE = "entry_title_text";
    public final static String DIARY_TIME = "entry_time_text";
    public final static String DIARY_EMOTION = "entry_time_emotion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_diary);

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
            int new_emotion = extras.getInt(DiaryActivity.DIARY_EMOTION);
            ImageView v = findViewById(R.id.emotionView);
            v.setImageResource(new_emotion);
            currentEmotion = new_emotion;
            Button b = findViewById(R.id.emotionSelect);
            b.setOnClickListener(null);
            Button save = findViewById(R.id.button2);
            save.setVisibility(View.INVISIBLE);
            save.setOnClickListener(null);
        }

        mainFont = Typeface.createFromAsset(getAssets(), "fonts/coolvetica.ttf");
        time();
        title();
        body();
    }

    public void chooseMood(View v) {
        Intent intent = new Intent(DiaryActivity.this, EmotionChoice.class);
        DiaryActivity.this.startActivityForResult(intent, 1);
    }


    protected void time() {
        TextClock textClock = (TextClock) findViewById(R.id.diaryTime);
        textClock.setFormat12Hour("hh:mm a, MMM d");
        textClock.setTypeface(mainFont);

        TextView date = (TextView) findViewById(R.id.diaryTimeTitle);
        date.setTypeface(mainFont);
    }

    protected void title() {
        EditText editTextStop = (EditText) findViewById(R.id.diaryTitle);
        editTextStop.setTypeface(mainFont);
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
        bundle.putInt(DIARY_EMOTION, currentEmotion);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                TextView text = findViewById(R.id.diaryBody);
                int newEmotion = data.getIntExtra(EmotionChoice.EMOTION, 0);
                if (newEmotion != 0) {
                    ImageView v = findViewById(R.id.emotionView);
                    v.setImageResource(newEmotion);
                    currentEmotion = newEmotion;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
