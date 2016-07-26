package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/28/2016.
 */
public class ReviewItem {
    String text,value;

    public ReviewItem(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
