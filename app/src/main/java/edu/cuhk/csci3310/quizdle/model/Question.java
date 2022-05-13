package edu.cuhk.csci3310.quizdle.model;

public class Question {
    //public static final String FIELD_CATEGORY = "category";
    //public static final String FIELD_SUBCATEGORY = "subcategory";
    public static final String FIELD_QUESTION_SET_NAME = "questionSetName";
    public static final String FIELD_QUESTION_SET_DESCRIPTION = "questionSetDescription";

    //private String category;
    //private String subcategory;
    private String questionSetName;
    private String questionSetDescription;
    private int questionNumber;
    private String question; private String trueAnswer; private String falseAnswer1; private String falseAnswer2; private String falseAnswer3;
    /*
    private String question1; private String question1TrueAnswer; private String question1FalseAnswer1; private String question1FalseAnswer2; private String question1FalseAnswer3;
    private String question2; private String question2TrueAnswer; private String question2FalseAnswer1; private String question2FalseAnswer2; private String question2FalseAnswer3;
    private String question3; private String question3TrueAnswer; private String question3FalseAnswer1; private String question3FalseAnswer2; private String question3FalseAnswer3;
    private String question4; private String question4TrueAnswer; private String question4FalseAnswer1; private String question4FalseAnswer2; private String question4FalseAnswer3;
    private String question5; private String question5TrueAnswer; private String question5FalseAnswer1; private String question5FalseAnswer2; private String question5FalseAnswer3;
    private String question6; private String question6TrueAnswer; private String question6FalseAnswer1; private String question6FalseAnswer2; private String question6FalseAnswer3;
    private String question7; private String question7TrueAnswer; private String question7FalseAnswer1; private String question7FalseAnswer2; private String question7FalseAnswer3;
    private String question8; private String question8TrueAnswer; private String question8FalseAnswer1; private String question8FalseAnswer2; private String question8FalseAnswer3;
    private String question9; private String question9TrueAnswer; private String question9FalseAnswer1; private String question9FalseAnswer2; private String question9FalseAnswer3;
    private String question10; private String question10TrueAnswer; private String question10FalseAnswer1; private String question10FalseAnswer2; private String question10FalseAnswer3;
    */
    public Question() {}

    public Question(//String category, String subcategory,
                    String questionSetName, String questionSetDescription, int questionNumber,
                    String question, String trueAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3
                    /*
                    String question1, String question1TrueAnswer, String question1FalseAnswer1, String question1FalseAnswer2, String question1FalseAnswer3
                    String question2, String question2TrueAnswer, String question2FalseAnswer1, String question2FalseAnswer2, String question2FalseAnswer3,
                    String question3, String question3TrueAnswer, String question3FalseAnswer1, String question3FalseAnswer2, String question3FalseAnswer3,
                    String question4, String question4TrueAnswer, String question4FalseAnswer1, String question4FalseAnswer2, String question4FalseAnswer3,
                    String question5, String question5TrueAnswer, String question5FalseAnswer1, String question5FalseAnswer2, String question5FalseAnswer3,
                    String question6, String question6TrueAnswer, String question6FalseAnswer1, String question6FalseAnswer2, String question6FalseAnswer3,
                    String question7, String question7TrueAnswer, String question7FalseAnswer1, String question7FalseAnswer2, String question7FalseAnswer3,
                    String question8, String question8TrueAnswer, String question8FalseAnswer1, String question8FalseAnswer2, String question8FalseAnswer3,
                    String question9, String question9TrueAnswer, String question9FalseAnswer1, String question9FalseAnswer2, String question9FalseAnswer3,
                    String question10, String question10TrueAnswer, String question10FalseAnswer1, String question10FalseAnswer2, String question10FalseAnswer3
                    */
    )
    {
        //this.category = category;
        //this.subcategory = subcategory;
        this.questionSetName = questionSetName;
        this.questionSetDescription = questionSetDescription;
        this.questionNumber = questionNumber;
        this.question = question; this.trueAnswer = trueAnswer; this.falseAnswer1 = falseAnswer1; this.falseAnswer2 = falseAnswer2; this.falseAnswer3 = falseAnswer3;
        /*
        this.question1 = question1; this.question1TrueAnswer = question1TrueAnswer; this.question1FalseAnswer1 = question1FalseAnswer1; this.question1FalseAnswer2 = question1FalseAnswer2; this.question1FalseAnswer3 = question1FalseAnswer3;
        this.question2 = question2; this.question2TrueAnswer = question2TrueAnswer; this.question2FalseAnswer1 = question2FalseAnswer1; this.question2FalseAnswer2 = question2FalseAnswer2; this.question2FalseAnswer3 = question2FalseAnswer3;
        this.question3 = question3; this.question3TrueAnswer = question3TrueAnswer; this.question3FalseAnswer1 = question3FalseAnswer1; this.question3FalseAnswer2 = question3FalseAnswer2; this.question3FalseAnswer3 = question3FalseAnswer3;
        this.question4 = question4; this.question4TrueAnswer = question4TrueAnswer; this.question4FalseAnswer1 = question4FalseAnswer1; this.question4FalseAnswer2 = question4FalseAnswer2; this.question4FalseAnswer3 = question4FalseAnswer3;
        this.question5 = question5; this.question5TrueAnswer = question5TrueAnswer; this.question5FalseAnswer1 = question5FalseAnswer1; this.question5FalseAnswer2 = question5FalseAnswer2; this.question5FalseAnswer3 = question5FalseAnswer3;
        this.question6 = question6; this.question6TrueAnswer = question6TrueAnswer; this.question6FalseAnswer1 = question6FalseAnswer1; this.question6FalseAnswer2 = question6FalseAnswer2; this.question6FalseAnswer3 = question6FalseAnswer3;
        this.question7 = question7; this.question7TrueAnswer = question7TrueAnswer; this.question7FalseAnswer1 = question7FalseAnswer1; this.question7FalseAnswer2 = question7FalseAnswer2; this.question7FalseAnswer3 = question7FalseAnswer3;
        this.question8 = question8; this.question8TrueAnswer = question8TrueAnswer; this.question8FalseAnswer1 = question8FalseAnswer1; this.question8FalseAnswer2 = question8FalseAnswer2; this.question8FalseAnswer3 = question8FalseAnswer3;
        this.question9 = question9; this.question9TrueAnswer = question9TrueAnswer; this.question9FalseAnswer1 = question9FalseAnswer1; this.question9FalseAnswer2 = question9FalseAnswer2; this.question9FalseAnswer3 = question9FalseAnswer3;
        this.question10 = question10; this.question10TrueAnswer = question10TrueAnswer; this.question10FalseAnswer1 = question10FalseAnswer1; this.question10FalseAnswer2 = question10FalseAnswer2; this.question10FalseAnswer3 = question10FalseAnswer3;
        */
    }

    /*
    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getSubcategory() { return subcategory; }

    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
    */

    public String getQuestionSetName() { return questionSetName; }

    public void setQuestionSetName(String questionSetName) { this.questionSetName = questionSetName; }

    public String getQuestionSetDescription() { return questionSetDescription; }

    public void setQuestionSetDescription(String questionSetDescription) { this.questionSetDescription = questionSetDescription; }

    public int getQuestionNumber() { return questionNumber; }

    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }

    public String getQuestion() { return question; }

    public String getTrueAnswer() { return trueAnswer; }

    public String getFalseAnswer1() { return falseAnswer1; }

    public String getFalseAnswer2() { return falseAnswer2; }

    public String getFalseAnswer3() { return falseAnswer3; }

    /*
    public String getQuestion1() { return question1; }

    public String getQuestion1TrueAnswer() { return question1TrueAnswer; }

    public String getQuestion1FalseAnswer1() { return question1FalseAnswer1; }

    public String getQuestion1FalseAnswer2() { return question1FalseAnswer2; }

    public String getQuestion1FalseAnswer3() { return question1FalseAnswer3; }

    public String getQuestion2() { return question2; }

    public String getQuestion2TrueAnswer() { return question2TrueAnswer; }

    public String getQuestion2FalseAnswer1() { return question2FalseAnswer1; }

    public String getQuestion2FalseAnswer2() { return question2FalseAnswer2; }

    public String getQuestion2FalseAnswer3() { return question2FalseAnswer3; }

    public String getQuestion3() { return question3; }

    public String getQuestion3TrueAnswer() { return question3TrueAnswer; }

    public String getQuestion3FalseAnswer1() { return question3FalseAnswer1; }

    public String getQuestion3FalseAnswer2() { return question3FalseAnswer2; }

    public String getQuestion3FalseAnswer3() { return question3FalseAnswer3; }

    public String getQuestion4() { return question4; }

    public String getQuestion4TrueAnswer() { return question4TrueAnswer; }

    public String getQuestion4FalseAnswer1() { return question4FalseAnswer1; }

    public String getQuestion4FalseAnswer2() { return question4FalseAnswer2; }

    public String getQuestion4FalseAnswer3() { return question4FalseAnswer3; }

    public String getQuestion5() { return question5; }

    public String getQuestion5TrueAnswer() { return question5TrueAnswer; }

    public String getQuestion5FalseAnswer1() { return question5FalseAnswer1; }

    public String getQuestion5FalseAnswer2() { return question5FalseAnswer2; }

    public String getQuestion5FalseAnswer3() { return question5FalseAnswer3; }

    public String getQuestion6() { return question6; }

    public String getQuestion6TrueAnswer() { return question6TrueAnswer; }

    public String getQuestion6FalseAnswer1() { return question6FalseAnswer1; }

    public String getQuestion6FalseAnswer2() { return question6FalseAnswer2; }

    public String getQuestion6FalseAnswer3() { return question6FalseAnswer3; }

    public String getQuestion7() { return question7; }

    public String getQuestion7TrueAnswer() { return question7TrueAnswer; }

    public String getQuestion7FalseAnswer1() { return question7FalseAnswer1; }

    public String getQuestion7FalseAnswer2() { return question7FalseAnswer2; }

    public String getQuestion7FalseAnswer3() { return question7FalseAnswer3; }

    public String getQuestion8() { return question8; }

    public String getQuestion8TrueAnswer() { return question8TrueAnswer; }

    public String getQuestion8FalseAnswer1() { return question8FalseAnswer1; }

    public String getQuestion8FalseAnswer2() { return question8FalseAnswer2; }

    public String getQuestion8FalseAnswer3() { return question8FalseAnswer3; }

    public String getQuestion9() { return question9; }

    public String getQuestion9TrueAnswer() { return question9TrueAnswer; }

    public String getQuestion9FalseAnswer1() { return question9FalseAnswer1; }

    public String getQuestion9FalseAnswer2() { return question9FalseAnswer2; }

    public String getQuestion9FalseAnswer3() { return question9FalseAnswer3; }

    public String getQuestion10() { return question10; }

    public String getQuestion10TrueAnswer() { return question10TrueAnswer; }

    public String getQuestion10FalseAnswer1() { return question10FalseAnswer1; }

    public String getQuestion10FalseAnswer2() { return question10FalseAnswer2; }

    public String getQuestion10FalseAnswer3() { return question10FalseAnswer3; }
    */

}
