package edu.cuhk.csci3310.quizdle.model;

import java.util.ArrayList;

public class Question {
    public static final String FIELD_QUESTION_SET_NAME = "questionSetName";
    public static final String FIELD_QUESTION_SET_DESCRIPTION = "questionSetDescription";
  
    private String questionSetName;
    private String questionSetDescription;
    private int questionNumber;
    private String question;
    private String trueAns;
    private String falseAns1;
    private String falseAns2;
    private String falseAns3;
    private String explanation;
  
    public Question(//String category, String subcategory,
                    String questionSetName, String questionSetDescription, int questionNumber,
                    String question, String trueAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3, String explanation
    )
    {

        this.questionSetName = questionSetName;
        this.questionSetDescription = questionSetDescription;
        this.questionNumber = questionNumber;
        this.explanation = explanation;
        this.question = question; this.trueAns = trueAns; this.falseAns1 = falseAns1; this.falseAns2 = falseAns2; this.falseAns3 = falseAns3;
    }

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
    public String getExplanation() {return explanation; }

    public void setQuestion(String question) {
        this.question = question;
    }
    public void setTrueAns(String trueAns) { this.trueAns = trueAns; }
    public void setFalseAns1(String falseAns1) { this.falseAns1 = falseAns1; }
    public void setFalseAns2(String falseAns2) { this.falseAns2 = falseAns2; }
    public void setFalseAns3(String falseAns3) { this.falseAns3 = falseAns3; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
  
    public String getQuestionSetName() { return questionSetName; }

    public void setQuestionSetName(String questionSetName) { this.questionSetName = questionSetName; }

    public String getQuestionSetDescription() { return questionSetDescription; }

    public void setQuestionSetDescription(String questionSetDescription) { this.questionSetDescription = questionSetDescription; }

    public int getQuestionNumber() { return questionNumber; }

    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }

}
