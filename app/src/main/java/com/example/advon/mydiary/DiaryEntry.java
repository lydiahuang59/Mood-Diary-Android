package com.example.advon.mydiary;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntry {
    private String title;
    private String date;
    private String body;
    private ArrayList<Integer> mood;
    private ArrayList<Integer> moodLevel;

    public DiaryEntry(String title, String date, String body, ArrayList<Integer> mood,
                      ArrayList<Integer> moodLevels) {
        this.title = title;
        this.date = date;
        this.mood = mood;
        this.moodLevel = moodLevels;
        this.body = body;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public int getMood() {
        int b = 0;
        for (int i = 0; i < moodLevel.size(); i++) {
            if (moodLevel.get(i) > moodLevel.get(b)) {
                b = i;
            }
        }
        return mood.get(b);
    }

    public ArrayList<Integer> getMoods() {return mood;}
    public ArrayList<Integer> getMoodLevel() {return moodLevel;}

    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}
}
