package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 4/4/2016.
 */
public class LaunchList {
    int id;
    int pos;

    public LaunchList(int pos, int id) {
        this.pos = pos;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
