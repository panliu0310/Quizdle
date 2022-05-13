package edu.cuhk.csci3310.quizdle.model;

import java.util.ArrayList;

public class Question {
    private String question;
    private String trueAns;
    private String falseAns1;
    private String falseAns2;
    private String falseAns3;

    public String getQuestion() {
        return question;
    }
    public String getTrueAns() { return trueAns; }
    public String getFalseAns1() {
        return falseAns1;
    }
    public String getFalseAns2() {
        return falseAns2;
    }
    public String getFalseAns3() {
        return falseAns3;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public void setTrueAns(String trueAns) { this.trueAns = trueAns; }
    public void setFalseAns1(String falseAns1) { this.falseAns1 = falseAns1; }
    public void setFalseAns2(String falseAns2) { this.falseAns2 = falseAns2; }
    public void setFalseAns3(String falseAns3) { this.falseAns3 = falseAns3; }

}
