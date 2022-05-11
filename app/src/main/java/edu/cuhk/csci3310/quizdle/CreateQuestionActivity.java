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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.cuhk.csci3310.quizdle.customview.DropDownMenuView;
import edu.cuhk.csci3310.quizdle.customview.TextInputView;
import edu.cuhk.csci3310.quizdle.dialogfragment.MessageDialogFragment;
import edu.cuhk.csci3310.quizdle.model.UnvQuestion;
import edu.cuhk.csci3310.quizdle.util.UnvQuestionUtil;

public class CreateQuestionActivity extends AppCompatActivity {

    String TAG = "CreateQuestionActivity";

    TextView tvGreeting;
    DropDownMenuView ddmvCategory;
    DropDownMenuView ddmvSubcategory;
    TextInputView tivQuestion;
    TextInputView tivTrueAnswer;
    TextInputView tivFalseAnswer1;
    TextInputView tivFalseAnswer2;
    TextInputView tivFalseAnswer3;
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
        tivQuestion = findViewById(R.id.tiv_question);
        tivTrueAnswer = findViewById(R.id.tiv_true_answer);
        tivFalseAnswer1 = findViewById(R.id.tiv_false_answer_1);
        tivFalseAnswer2 = findViewById(R.id.tiv_false_answer_2);
        tivFalseAnswer3 = findViewById(R.id.tiv_false_answer_3);
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
        // TODO: get values in the custom view and push to the firebase
        String category = ddmvCategory.dropdown.getSelectedItem().toString();
        String subcategory = ddmvSubcategory.dropdown.getSelectedItem().toString();
        String question = tivQuestion.etInput.getText().toString();
        String trueAnswer = tivTrueAnswer.etInput.getText().toString();
        String falseAnswer1 = tivFalseAnswer1.etInput.getText().toString();
        String falseAnswer2 = tivFalseAnswer2.etInput.getText().toString();
        String falseAnswer3 = tivFalseAnswer3.etInput.getText().toString();
        Log.d(TAG, "category: " + category);
        Log.d(TAG, "subcategory: " + subcategory);
        Log.d(TAG, "question: " + question);
        Log.d(TAG, "trueAnswer: " + trueAnswer);
        Log.d(TAG, "falseAnswer1: " + falseAnswer1);
        Log.d(TAG, "falseAnswer2: " + falseAnswer2);
        Log.d(TAG, "falseAnswer3: " + falseAnswer3);
        if (tivQuestion.etInput.getText().toString().equals("") ||
                tivTrueAnswer.etInput.getText().toString().equals("") ||
                tivFalseAnswer1.etInput.getText().toString().equals("") ||
                tivFalseAnswer2.etInput.getText().toString().equals("") ||
                tivFalseAnswer3.etInput.getText().toString().equals("")
        ) {
            MessageDialogFragment fragment = MessageDialogFragment.newInstance("Invalid input! Please try again.");
            fragment.show(getSupportFragmentManager(), TAG);
        } else {
            // create "UnvQuestion" data set in firebase
            CollectionReference newUnvQuestion = mFirestore.collection("unvQuestions");
            UnvQuestion unvQuestion = UnvQuestionUtil.createNewUnvQuestion(category, subcategory, question, trueAnswer, falseAnswer1, falseAnswer2, falseAnswer3);
            newUnvQuestion.add(unvQuestion)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "New unvQuestion successfully added on Firestore!");
                            finish();
                            MessageDialogFragment fragment = MessageDialogFragment.newInstance("We have received your submission! " +
                                    "Your question will be available to everyone if it pass our verification!");
                            fragment.show(getSupportFragmentManager(), TAG);
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