package edu.cuhk.csci3310.quizdle.util;

import edu.cuhk.csci3310.quizdle.model.Question;

public class QuestionUtil {

    private static final String TAG = "QuestionUtil";

    public static Question createNewQuestion(
            String questionSetName, String questionSetDescription, int questionNumber,
            String question, String trueAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3, String explanation
    ) {
        return new Question(
                questionSetName, questionSetDescription, questionNumber,
                question, trueAnswer, falseAnswer1, falseAnswer2, falseAnswer3, explanation
        );
    }
}
