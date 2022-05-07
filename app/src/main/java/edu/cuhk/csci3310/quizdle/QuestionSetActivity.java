package edu.cuhk.csci3310.quizdle;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionSetActivity extends AppCompatActivity {

    private final String TAG = "QuestionSetActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set);
        Log.d(TAG, "QuestionSetActivity");
    }
}
