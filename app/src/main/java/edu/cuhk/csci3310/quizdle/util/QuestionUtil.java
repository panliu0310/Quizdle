package edu.cuhk.csci3310.quizdle.util;

import edu.cuhk.csci3310.quizdle.model.Question;

public class QuestionUtil {

    private static final String TAG = "QuestionUtil";

    public static Question createNewQuestion(
            //String category, String subcategory,
            String questionSetName, String questionSetDescription, int questionNumber,
            String question, String trueAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3
            /*
            String question1, String question1TrueAnswer, String question1FalseAnswer1, String question1FalseAnswer2, String question1FalseAnswer3,
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
        return new Question(
                //category, subcategory,
                questionSetName, questionSetDescription, questionNumber,
                question, trueAnswer, falseAnswer1, falseAnswer2, falseAnswer3
                /*
                question1, question1TrueAnswer, question1FalseAnswer1, question1FalseAnswer2, question1FalseAnswer3,
                question2, question2TrueAnswer, question2FalseAnswer1, question2FalseAnswer2, question2FalseAnswer3,
                question3, question3TrueAnswer, question3FalseAnswer1, question3FalseAnswer2, question3FalseAnswer3,
                question4, question4TrueAnswer, question4FalseAnswer1, question4FalseAnswer2, question4FalseAnswer3,
                question5, question5TrueAnswer, question5FalseAnswer1, question5FalseAnswer2, question5FalseAnswer3,
                question6, question6TrueAnswer, question6FalseAnswer1, question6FalseAnswer2, question6FalseAnswer3,
                question7, question7TrueAnswer, question7FalseAnswer1, question7FalseAnswer2, question7FalseAnswer3,
                question8, question8TrueAnswer, question8FalseAnswer1, question8FalseAnswer2, question8FalseAnswer3,
                question9, question9TrueAnswer, question9FalseAnswer1, question9FalseAnswer2, question9FalseAnswer3,
                question10, question10TrueAnswer, question10FalseAnswer1, question10FalseAnswer2, question10FalseAnswer3
                */
        );
    }
}
