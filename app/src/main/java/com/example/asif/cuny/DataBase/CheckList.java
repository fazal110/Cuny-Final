package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 4/1/2016.
 */
public class CheckList {
    int ChecklistitemId;
    String ChecklistId;
    String ques;

    public CheckList(int checklistitemId, String checklistId, String ques) {
        ChecklistitemId = checklistitemId;
        ChecklistId = checklistId;
        this.ques = ques;
    }

    public int getChecklistitemId() {
        return ChecklistitemId;
    }

    public void setChecklistitemId(int checklistitemId) {
        ChecklistitemId = checklistitemId;
    }

    public String getChecklistId() {
        return ChecklistId;
    }

    public void setChecklistId(String checklistId) {
        ChecklistId = checklistId;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }
}
