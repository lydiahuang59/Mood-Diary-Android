package com.example.advon.mydiary;

public class DiaryEntry {
    private String title;
    private String date;
    private String body;
    private int mood;
    private int weather;

    public DiaryEntry(String title, String date, String body, int mood) {
        this.title = title;
        this.date = date;
        this.mood = mood;
        this.body = body;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public int getMood() {return mood;}
    public void setMood(int mood) {this.mood = mood;}

    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}

    public int getWeather() {return weather;}
    public void setWeather(int weather) {this.weather = weather;}
}
