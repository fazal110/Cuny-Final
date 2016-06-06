package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/18/2016.
 */
public class AskExpertModel {
    String ques,desc;

    public AskExpertModel(String ques, String desc) {
        this.ques = ques;
        this.desc = desc;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
