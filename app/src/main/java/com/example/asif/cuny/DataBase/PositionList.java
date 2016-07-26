package com.example.asif.cuny.DataBase;

/**
 * Created by Fazal on 02-Apr-16.
 */
public class PositionList {
    int id;
    String ListTitle;
    int Position;

    public PositionList(int id, String listTitle, int position) {
        this.id = id;
        ListTitle = listTitle;
        Position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListTitle() {
        return ListTitle;
    }

    public void setListTitle(String listTitle) {
        ListTitle = listTitle;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }
}
