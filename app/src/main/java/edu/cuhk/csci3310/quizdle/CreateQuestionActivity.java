package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.cuhk.csci3310.quizdle.customview.DropDownMenuView;
import edu.cuhk.csci3310.quizdle.customview.TextInputView;
import edu.cuhk.csci3310.quizdle.dialogfragment.MessageDialogFragment;
import edu.cuhk.csci3310.quizdle.model.Question;
import edu.cuhk.csci3310.quizdle.model.UnvQuestion;
import edu.cuhk.csci3310.quizdle.util.QuestionUtil;
import edu.cuhk.csci3310.quizdle.util.UnvQuestionUtil;

public class CreateQuestionActivity extends AppCompatActivity {

    String TAG = "CreateQuestionActivity";

    TextView tvGreeting;
    DropDownMenuView ddmvCategory;
    DropDownMenuView ddmvSubcategory;
    TextInputView tivQuestionSetName;
    TextInputView tivQuestionSetDescription;
    TextInputView tivQuestion1; TextInputView tivQuestion1TrueAnswer; TextInputView tivQuestion1FalseAnswer1; TextInputView tivQuestion1FalseAnswer2; TextInputView tivQuestion1FalseAnswer3;
    TextInputView tivQuestion2; TextInputView tivQuestion2TrueAnswer; TextInputView tivQuestion2FalseAnswer1; TextInputView tivQuestion2FalseAnswer2; TextInputView tivQuestion2FalseAnswer3;
    TextInputView tivQuestion3; TextInputView tivQuestion3TrueAnswer; TextInputView tivQuestion3FalseAnswer1; TextInputView tivQuestion3FalseAnswer2; TextInputView tivQuestion3FalseAnswer3;
    TextInputView tivQuestion4; TextInputView tivQuestion4TrueAnswer; TextInputView tivQuestion4FalseAnswer1; TextInputView tivQuestion4FalseAnswer2; TextInputView tivQuestion4FalseAnswer3;
    TextInputView tivQuestion5; TextInputView tivQuestion5TrueAnswer; TextInputView tivQuestion5FalseAnswer1; TextInputView tivQuestion5FalseAnswer2; TextInputView tivQuestion5FalseAnswer3;
    TextInputView tivQuestion6; TextInputView tivQuestion6TrueAnswer; TextInputView tivQuestion6FalseAnswer1; TextInputView tivQuestion6FalseAnswer2; TextInputView tivQuestion6FalseAnswer3;
    TextInputView tivQuestion7; TextInputView tivQuestion7TrueAnswer; TextInputView tivQuestion7FalseAnswer1; TextInputView tivQuestion7FalseAnswer2; TextInputView tivQuestion7FalseAnswer3;
    TextInputView tivQuestion8; TextInputView tivQuestion8TrueAnswer; TextInputView tivQuestion8FalseAnswer1; TextInputView tivQuestion8FalseAnswer2; TextInputView tivQuestion8FalseAnswer3;
    TextInputView tivQuestion9; TextInputView tivQuestion9TrueAnswer; TextInputView tivQuestion9FalseAnswer1; TextInputView tivQuestion9FalseAnswer2; TextInputView tivQuestion9FalseAnswer3;
    TextInputView tivQuestion10; TextInputView tivQuestion10TrueAnswer; TextInputView tivQuestion10FalseAnswer1; TextInputView tivQuestion10FalseAnswer2; TextInputView tivQuestion10FalseAnswer3;
    Button btnSubmit;

    private String username;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        setToolbar();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();

        tvGreeting = findViewById(R.id.tv_greeting);
        ddmvCategory = findViewById(R.id.ddmv_category);
        ddmvSubcategory = findViewById(R.id.ddmv_subcategory);
        tivQuestionSetName = findViewById(R.id.tiv_question_set_name);
        tivQuestionSetDescription = findViewById(R.id.tiv_question_set_description);
        tivQuestion1 = findViewById(R.id.tiv_question_1); tivQuestion1TrueAnswer = findViewById(R.id.tiv_question_1_true_answer); tivQuestion1FalseAnswer1 = findViewById(R.id.tiv_question_1_false_answer_1); tivQuestion1FalseAnswer2 = findViewById(R.id.tiv_question_1_false_answer_2); tivQuestion1FalseAnswer3 = findViewById(R.id.tiv_question_1_false_answer_3);
        tivQuestion2 = findViewById(R.id.tiv_question_2); tivQuestion2TrueAnswer = findViewById(R.id.tiv_question_2_true_answer); tivQuestion2FalseAnswer1 = findViewById(R.id.tiv_question_2_false_answer_1); tivQuestion2FalseAnswer2 = findViewById(R.id.tiv_question_2_false_answer_2); tivQuestion2FalseAnswer3 = findViewById(R.id.tiv_question_2_false_answer_3);
        tivQuestion3 = findViewById(R.id.tiv_question_3); tivQuestion3TrueAnswer = findViewById(R.id.tiv_question_3_true_answer); tivQuestion3FalseAnswer1 = findViewById(R.id.tiv_question_3_false_answer_1); tivQuestion3FalseAnswer2 = findViewById(R.id.tiv_question_3_false_answer_2); tivQuestion3FalseAnswer3 = findViewById(R.id.tiv_question_3_false_answer_3);
        tivQuestion4 = findViewById(R.id.tiv_question_4); tivQuestion4TrueAnswer = findViewById(R.id.tiv_question_4_true_answer); tivQuestion4FalseAnswer1 = findViewById(R.id.tiv_question_4_false_answer_1); tivQuestion4FalseAnswer2 = findViewById(R.id.tiv_question_4_false_answer_2); tivQuestion4FalseAnswer3 = findViewById(R.id.tiv_question_4_false_answer_3);
        tivQuestion5 = findViewById(R.id.tiv_question_5); tivQuestion5TrueAnswer = findViewById(R.id.tiv_question_5_true_answer); tivQuestion5FalseAnswer1 = findViewById(R.id.tiv_question_5_false_answer_1); tivQuestion5FalseAnswer2 = findViewById(R.id.tiv_question_5_false_answer_2); tivQuestion5FalseAnswer3 = findViewById(R.id.tiv_question_5_false_answer_3);
        tivQuestion6 = findViewById(R.id.tiv_question_6); tivQuestion6TrueAnswer = findViewById(R.id.tiv_question_6_true_answer); tivQuestion6FalseAnswer1 = findViewById(R.id.tiv_question_6_false_answer_1); tivQuestion6FalseAnswer2 = findViewById(R.id.tiv_question_6_false_answer_2); tivQuestion6FalseAnswer3 = findViewById(R.id.tiv_question_6_false_answer_3);
        tivQuestion7 = findViewById(R.id.tiv_question_7); tivQuestion7TrueAnswer = findViewById(R.id.tiv_question_7_true_answer); tivQuestion7FalseAnswer1 = findViewById(R.id.tiv_question_7_false_answer_1); tivQuestion7FalseAnswer2 = findViewById(R.id.tiv_question_7_false_answer_2); tivQuestion7FalseAnswer3 = findViewById(R.id.tiv_question_7_false_answer_3);
        tivQuestion8 = findViewById(R.id.tiv_question_8); tivQuestion8TrueAnswer = findViewById(R.id.tiv_question_8_true_answer); tivQuestion8FalseAnswer1 = findViewById(R.id.tiv_question_8_false_answer_1); tivQuestion8FalseAnswer2 = findViewById(R.id.tiv_question_8_false_answer_2); tivQuestion8FalseAnswer3 = findViewById(R.id.tiv_question_8_false_answer_3);
        tivQuestion9 = findViewById(R.id.tiv_question_9); tivQuestion9TrueAnswer = findViewById(R.id.tiv_question_9_true_answer); tivQuestion9FalseAnswer1 = findViewById(R.id.tiv_question_9_false_answer_1); tivQuestion9FalseAnswer2 = findViewById(R.id.tiv_question_9_false_answer_2); tivQuestion9FalseAnswer3 = findViewById(R.id.tiv_question_9_false_answer_3);
        tivQuestion10 = findViewById(R.id.tiv_question_10); tivQuestion10TrueAnswer = findViewById(R.id.tiv_question_10_true_answer); tivQuestion10FalseAnswer1 = findViewById(R.id.tiv_question_10_false_answer_1); tivQuestion10FalseAnswer2 = findViewById(R.id.tiv_question_10_false_answer_2); tivQuestion10FalseAnswer3 = findViewById(R.id.tiv_question_10_false_answer_3);
        btnSubmit = findViewById(R.id.btn_submit);

        tvGreeting.setText(String.format(getString(R.string.create_question_greeting), username));

        setCategoryDropDownMenu();

        ddmvCategory.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Log.d(TAG, item.toString() + " category is picked");
                // set subcategory drop down menu
                ddmvSubcategory.setSpinner(CreateQuestionActivity.this, item.toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });

    }

    private void setToolbar() {
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
    }

    private void setCategoryDropDownMenu() {
        ddmvCategory.setSpinner(this);
    }

    private void validateForm() {
        String category = ddmvCategory.dropdown.getSelectedItem().toString();
        String subcategory = ddmvSubcategory.dropdown.getSelectedItem().toString();
        String questionSetName = tivQuestionSetName.etInput.getText().toString();
        String questionSetDescription = tivQuestionSetDescription.etInput.getText().toString();
        String question1 = tivQuestion1.etInput.getText().toString(); String question1TrueAnswer = tivQuestion1TrueAnswer.etInput.getText().toString(); String question1FalseAnswer1 = tivQuestion1FalseAnswer1.etInput.getText().toString(); String question1FalseAnswer2 = tivQuestion1FalseAnswer2.etInput.getText().toString(); String question1FalseAnswer3 = tivQuestion1FalseAnswer3.etInput.getText().toString();
        String question2 = tivQuestion2.etInput.getText().toString(); String question2TrueAnswer = tivQuestion2TrueAnswer.etInput.getText().toString(); String question2FalseAnswer1 = tivQuestion2FalseAnswer1.etInput.getText().toString(); String question2FalseAnswer2 = tivQuestion2FalseAnswer2.etInput.getText().toString(); String question2FalseAnswer3 = tivQuestion2FalseAnswer3.etInput.getText().toString();
        String question3 = tivQuestion3.etInput.getText().toString(); String question3TrueAnswer = tivQuestion3TrueAnswer.etInput.getText().toString(); String question3FalseAnswer1 = tivQuestion3FalseAnswer1.etInput.getText().toString(); String question3FalseAnswer2 = tivQuestion3FalseAnswer2.etInput.getText().toString(); String question3FalseAnswer3 = tivQuestion3FalseAnswer3.etInput.getText().toString();
        String question4 = tivQuestion4.etInput.getText().toString(); String question4TrueAnswer = tivQuestion4TrueAnswer.etInput.getText().toString(); String question4FalseAnswer1 = tivQuestion4FalseAnswer1.etInput.getText().toString(); String question4FalseAnswer2 = tivQuestion4FalseAnswer2.etInput.getText().toString(); String question4FalseAnswer3 = tivQuestion4FalseAnswer3.etInput.getText().toString();
        String question5 = tivQuestion5.etInput.getText().toString(); String question5TrueAnswer = tivQuestion5TrueAnswer.etInput.getText().toString(); String question5FalseAnswer1 = tivQuestion5FalseAnswer1.etInput.getText().toString(); String question5FalseAnswer2 = tivQuestion5FalseAnswer2.etInput.getText().toString(); String question5FalseAnswer3 = tivQuestion5FalseAnswer3.etInput.getText().toString();
        String question6 = tivQuestion6.etInput.getText().toString(); String question6TrueAnswer = tivQuestion6TrueAnswer.etInput.getText().toString(); String question6FalseAnswer1 = tivQuestion6FalseAnswer1.etInput.getText().toString(); String question6FalseAnswer2 = tivQuestion6FalseAnswer2.etInput.getText().toString(); String question6FalseAnswer3 = tivQuestion6FalseAnswer3.etInput.getText().toString();
        String question7 = tivQuestion7.etInput.getText().toString(); String question7TrueAnswer = tivQuestion7TrueAnswer.etInput.getText().toString(); String question7FalseAnswer1 = tivQuestion7FalseAnswer1.etInput.getText().toString(); String question7FalseAnswer2 = tivQuestion7FalseAnswer2.etInput.getText().toString(); String question7FalseAnswer3 = tivQuestion7FalseAnswer3.etInput.getText().toString();
        String question8 = tivQuestion8.etInput.getText().toString(); String question8TrueAnswer = tivQuestion8TrueAnswer.etInput.getText().toString(); String question8FalseAnswer1 = tivQuestion8FalseAnswer1.etInput.getText().toString(); String question8FalseAnswer2 = tivQuestion8FalseAnswer2.etInput.getText().toString(); String question8FalseAnswer3 = tivQuestion8FalseAnswer3.etInput.getText().toString();
        String question9 = tivQuestion9.etInput.getText().toString(); String question9TrueAnswer = tivQuestion9TrueAnswer.etInput.getText().toString(); String question9FalseAnswer1 = tivQuestion9FalseAnswer1.etInput.getText().toString(); String question9FalseAnswer2 = tivQuestion9FalseAnswer2.etInput.getText().toString(); String question9FalseAnswer3 = tivQuestion9FalseAnswer3.etInput.getText().toString();
        String question10 = tivQuestion10.etInput.getText().toString(); String question10TrueAnswer = tivQuestion10TrueAnswer.etInput.getText().toString(); String question10FalseAnswer1 = tivQuestion10FalseAnswer1.etInput.getText().toString(); String question10FalseAnswer2 = tivQuestion10FalseAnswer2.etInput.getText().toString(); String question10FalseAnswer3 = tivQuestion10FalseAnswer3.etInput.getText().toString();
        Log.d(TAG, "category: " + category);
        Log.d(TAG, "subcategory: " + subcategory);
        Log.d(TAG, "questionSetName: " + questionSetName);
        Log.d(TAG, "questionSetDescription: " + questionSetDescription);
        Log.d(TAG, "question1: " + question1); Log.d(TAG, "question1TrueAnswer: " + question1TrueAnswer); Log.d(TAG, "question1FalseAnswer1: " + question1FalseAnswer1); Log.d(TAG, "question1FalseAnswer2: " + question1FalseAnswer2); Log.d(TAG, "question1FalseAnswer3: " + question1FalseAnswer3);
        Log.d(TAG, "question2: " + question2); Log.d(TAG, "question2TrueAnswer: " + question2TrueAnswer); Log.d(TAG, "question2FalseAnswer1: " + question2FalseAnswer1); Log.d(TAG, "question2FalseAnswer2: " + question2FalseAnswer2); Log.d(TAG, "question2FalseAnswer3: " + question2FalseAnswer3);
        Log.d(TAG, "question3: " + question3); Log.d(TAG, "question3TrueAnswer: " + question3TrueAnswer); Log.d(TAG, "question3FalseAnswer1: " + question3FalseAnswer1); Log.d(TAG, "question3FalseAnswer2: " + question3FalseAnswer2); Log.d(TAG, "question3FalseAnswer3: " + question3FalseAnswer3);
        Log.d(TAG, "question4: " + question4); Log.d(TAG, "question4TrueAnswer: " + question4TrueAnswer); Log.d(TAG, "question4FalseAnswer1: " + question4FalseAnswer1); Log.d(TAG, "question4FalseAnswer2: " + question4FalseAnswer2); Log.d(TAG, "question4FalseAnswer3: " + question4FalseAnswer3);
        Log.d(TAG, "question5: " + question5); Log.d(TAG, "question5TrueAnswer: " + question5TrueAnswer); Log.d(TAG, "question5FalseAnswer1: " + question5FalseAnswer1); Log.d(TAG, "question5FalseAnswer2: " + question5FalseAnswer2); Log.d(TAG, "question5FalseAnswer3: " + question5FalseAnswer3);
        Log.d(TAG, "question6: " + question6); Log.d(TAG, "question6TrueAnswer: " + question6TrueAnswer); Log.d(TAG, "question6FalseAnswer1: " + question6FalseAnswer1); Log.d(TAG, "question6FalseAnswer2: " + question6FalseAnswer2); Log.d(TAG, "question6FalseAnswer3: " + question6FalseAnswer3);
        Log.d(TAG, "question7: " + question7); Log.d(TAG, "question7TrueAnswer: " + question7TrueAnswer); Log.d(TAG, "question7FalseAnswer1: " + question7FalseAnswer1); Log.d(TAG, "question7FalseAnswer2: " + question7FalseAnswer2); Log.d(TAG, "question7FalseAnswer3: " + question7FalseAnswer3);
        Log.d(TAG, "question8: " + question8); Log.d(TAG, "question8TrueAnswer: " + question8TrueAnswer); Log.d(TAG, "question8FalseAnswer1: " + question8FalseAnswer1); Log.d(TAG, "question8FalseAnswer2: " + question8FalseAnswer2); Log.d(TAG, "question8FalseAnswer3: " + question8FalseAnswer3);
        Log.d(TAG, "question9: " + question9); Log.d(TAG, "question9TrueAnswer: " + question9TrueAnswer); Log.d(TAG, "question9FalseAnswer1: " + question9FalseAnswer1); Log.d(TAG, "question9FalseAnswer2: " + question9FalseAnswer2); Log.d(TAG, "question9FalseAnswer3: " + question9FalseAnswer3);
        Log.d(TAG, "question10: " + question10); Log.d(TAG, "question10TrueAnswer: " + question10TrueAnswer); Log.d(TAG, "question10FalseAnswer1: " + question10FalseAnswer1); Log.d(TAG, "question10FalseAnswer2: " + question10FalseAnswer2); Log.d(TAG, "question10FalseAnswer3: " + question10FalseAnswer3);
        if (questionSetName.equals("") || questionSetDescription.equals("") ||
                question1.equals("") || question1TrueAnswer.equals("") || question1FalseAnswer1.equals("") || question1FalseAnswer2.equals("") || question1FalseAnswer3.equals("") ||
                question2.equals("") || question2TrueAnswer.equals("") || question2FalseAnswer1.equals("") || question2FalseAnswer2.equals("") || question2FalseAnswer3.equals("") ||
                question3.equals("") || question3TrueAnswer.equals("") || question3FalseAnswer1.equals("") || question3FalseAnswer2.equals("") || question3FalseAnswer3.equals("") ||
                question4.equals("") || question4TrueAnswer.equals("") || question4FalseAnswer1.equals("") || question4FalseAnswer2.equals("") || question4FalseAnswer3.equals("") ||
                question5.equals("") || question5TrueAnswer.equals("") || question5FalseAnswer1.equals("") || question5FalseAnswer2.equals("") || question5FalseAnswer3.equals("") ||
                question6.equals("") || question6TrueAnswer.equals("") || question6FalseAnswer1.equals("") || question6FalseAnswer2.equals("") || question6FalseAnswer3.equals("") ||
                question7.equals("") || question7TrueAnswer.equals("") || question7FalseAnswer1.equals("") || question7FalseAnswer2.equals("") || question7FalseAnswer3.equals("") ||
                question8.equals("") || question8TrueAnswer.equals("") || question8FalseAnswer1.equals("") || question8FalseAnswer2.equals("") || question8FalseAnswer3.equals("") ||
                question9.equals("") || question9TrueAnswer.equals("") || question9FalseAnswer1.equals("") || question9FalseAnswer2.equals("") || question9FalseAnswer3.equals("") ||
                question10.equals("") || question10TrueAnswer.equals("") || question10FalseAnswer1.equals("") || question10FalseAnswer2.equals("") || question10FalseAnswer3.equals("")
        ) {
            MessageDialogFragment fragment = MessageDialogFragment.newInstance("Some fields are empty! Please try again.");
            fragment.show(getSupportFragmentManager(), TAG);
        } else {
            // create "UnvQuestion" data set in firebase
            CollectionReference questionRef = mFirestore.collection("questions");
            Question question = QuestionUtil.createNewQuestion(category, subcategory, questionSetName, questionSetDescription,
                    question1, question1TrueAnswer, question1FalseAnswer1, question1FalseAnswer2, question1FalseAnswer3,
                    question2, question2TrueAnswer, question2FalseAnswer1, question2FalseAnswer2, question2FalseAnswer3,
                    question3, question3TrueAnswer, question3FalseAnswer1, question3FalseAnswer2, question3FalseAnswer3,
                    question4, question4TrueAnswer, question4FalseAnswer1, question4FalseAnswer2, question4FalseAnswer3,
                    question5, question5TrueAnswer, question5FalseAnswer1, question5FalseAnswer2, question5FalseAnswer3,
                    question6, question6TrueAnswer, question6FalseAnswer1, question6FalseAnswer2, question6FalseAnswer3,
                    question7, question7TrueAnswer, question7FalseAnswer1, question7FalseAnswer2, question7FalseAnswer3,
                    question8, question8TrueAnswer, question8FalseAnswer1, question8FalseAnswer2, question8FalseAnswer3,
                    question9, question9TrueAnswer, question9FalseAnswer1, question9FalseAnswer2, question9FalseAnswer3,
                    question10, question10TrueAnswer, question10FalseAnswer1, question10FalseAnswer2, question10FalseAnswer3);

            questionRef.document(category).collection(subcategory).add(question)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "New Question successfully added on Firestore!");
                            finish();
                            Toast.makeText(getApplicationContext(), "We have received your submission! " +
                                    "Your question will be available to everyone if it passes our verification!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding new unvQuestion", e);
                        }
                    });

        }
    }

}