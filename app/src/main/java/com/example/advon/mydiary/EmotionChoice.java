package com.example.advon.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class EmotionChoice extends AppCompatActivity {

    private int currentName = 0;
    private int currentLevel = 0;
    private int nPrevSelGridItem = -1;
    public final static String EMOTION = "current_emotion";
    public final static String EMOTION_LEVEL = "current_emotion_level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_choice);

        final GridView gridview = (GridView) findViewById(R.id.EmotionGrid);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                try {
                    if (nPrevSelGridItem != -1) {
                        View viewPrev = (View) gridview.getChildAt(nPrevSelGridItem);
                        viewPrev.setBackgroundColor(Color.WHITE);
                    }
                    nPrevSelGridItem = position;
                    v.setBackgroundColor(Color.CYAN);
                } catch (Exception e) {
                    System.out.println("Error in highlighting");
                    e.printStackTrace();
                }
                try {
                    TextView emotion = (TextView) findViewById(R.id.currentEmotion);
                    int current = ImageAdapter.mThumbIds[position];
                    String name = getResources().getResourceEntryName(current);
                    if (Character.isDigit(name.charAt(name.length() - 1))) {
                        name = name.substring(0, name.length() - 1);
                    }
                    emotion.setText(name);
                    currentName = current;
                } catch (Exception e) {
                    System.out.println("Error in finding emotion resource");
                    e.printStackTrace();
                }
            }
        });

        SeekBar emotionLevel = (SeekBar) findViewById(R.id.emotionLevel);
        emotionLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView display = findViewById(R.id.emotionLevelText);
                String s = "Emotion strengh: " + Integer.toString(progress);
                display.setText(s);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentLevel = seekBar.getProgress();
            }
        });
    }

    public void exitEmotion(View view) {
        Intent returnIntent = new Intent();
        if (currentName != 0) {
            returnIntent.putExtra(EMOTION, currentName);
            returnIntent.putExtra(EMOTION_LEVEL, currentLevel);
            setResult(Activity.RESULT_OK, returnIntent);
        }
        else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();

    }

}
