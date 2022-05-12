package edu.cuhk.csci3310.quizdle.util;

import edu.cuhk.csci3310.quizdle.model.UnvQuestion;

public class UnvQuestionUtil {

    private static final String TAG = "UnvQuestionUtil";

    public static UnvQuestion createNewUnvQuestion(String category, String subcategory, String question, String trueAnswer,
                                                   String falseAnswer1, String falseAnswer2, String falseAnswer3){
        return new UnvQuestion(category, subcategory, question, trueAnswer, falseAnswer1, falseAnswer2, falseAnswer3);
    }
}
