package com.example.notepad;

public class thum_item {
    public  String title;
    public  String content;
    public  String filename;

    public thum_item(String title, String content,String filename) {
        this.title = title;
        this.content = content;
        this.filename = filename;
    }

    @Override
    public String toString() {
        return content;
    }
}

