package com.example.advon.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.TextView;

public class EmotionChoice extends AppCompatActivity {

    private int currentName = 0;
    public final static String EMOTION = "current_emotion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_choice);

        GridView gridview = (GridView) findViewById(R.id.EmotionGrid);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView emotion = (TextView) findViewById(R.id.currentEmotion);
                int current = ImageAdapter.mThumbIds[position];
                String name = getResources().getResourceEntryName(current);
                if (Character.isDigit(name.charAt(name.length()-1))) {
                    name = name.substring(0, name.length()-1);
                }
                emotion.setText(name);
                currentName = current;

            }
        });
    }

    public void exitEmotion(View view) {
        Intent returnIntent = new Intent();
        if (currentName != 0) {
            returnIntent.putExtra(EMOTION, currentName);
            setResult(Activity.RESULT_OK, returnIntent);
        }
        else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();

    }

}
