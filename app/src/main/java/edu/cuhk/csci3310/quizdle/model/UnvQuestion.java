package edu.cuhk.csci3310.quizdle.model;

public class UnvQuestion {
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_SUBCATEGORY = "subcategory";
    public static final String FIELD_QUESTION = "question";
    public static final String FIELD_trueAnswer = "trueAnswer";
    public static final String FIELD_falseAnswer1 = "falseAnswer1";
    public static final String FIELD_falseAnswer2 = "falseAnswer2";
    public static final String FIELD_falseAnswer3 = "falseAnswer3";

    private String category;
    private String subcategory;
    private String question;
    private String trueAnswer;
    private String falseAnswer1;
    private String falseAnswer2;
    private String falseAnswer3;

    public UnvQuestion() {}

    public UnvQuestion(String category, String subcategory, String question, String trueAnswer,
                       String falseAnswer1, String falseAnswer2, String falseAnswer3){
        this.category = category;
        this.subcategory = subcategory;
        this.question = question;
        this.trueAnswer = trueAnswer;
        this.falseAnswer1 = falseAnswer1;
        this.falseAnswer2 = falseAnswer2;
        this.falseAnswer3 = falseAnswer3;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }

    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getTrueAnswer() { return trueAnswer; }

    public void setTrueAnswer(String trueAnswer) { this.trueAnswer = trueAnswer; }

    public String getFalseAnswer1() { return falseAnswer1; }

    public void setFalseAnswer1(String falseAnswer1) { this.falseAnswer1 = falseAnswer1; }

    public String getFalseAnswer2() { return falseAnswer2; }

    public void setFalseAnswer2(String falseAnswer2) { this.falseAnswer2 = falseAnswer2; }

    public String getFalseAnswer3() { return falseAnswer3; }

    public void setFalseAnswer3(String falseAnswer3) { this.falseAnswer3 = falseAnswer3; }
}
