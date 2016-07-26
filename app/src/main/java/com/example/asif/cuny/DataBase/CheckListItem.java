package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 4/5/2016.
 */
public class CheckListItem {
    int id,checklistid;

    public CheckListItem(int id, int checklistid) {
        this.id = id;
        this.checklistid = checklistid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecklistid() {
        return checklistid;
    }

    public void setChecklistid(int checklistid) {
        this.checklistid = checklistid;
    }
}
