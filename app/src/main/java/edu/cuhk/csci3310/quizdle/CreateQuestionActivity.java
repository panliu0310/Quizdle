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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //DropDownMenuView ddmvCategory;
    //DropDownMenuView ddmvSubcategory;
    TextInputView tivQuestionSetName;
    TextInputView tivQuestionSetDescription;
    ArrayList<TextInputView> tivQuestionList = new ArrayList<>();
    ArrayList<TextInputView> tivTrueAnswerList = new ArrayList<>();
    ArrayList<TextInputView> tivFalseAnswer1List = new ArrayList<>();
    ArrayList<TextInputView> tivFalseAnswer2List = new ArrayList<>();
    ArrayList<TextInputView> tivFalseAnswer3List = new ArrayList<>();
    ArrayList<TextInputView> tivExplanationList = new ArrayList<>();
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
        //ddmvCategory = findViewById(R.id.ddmv_category);
        //ddmvSubcategory = findViewById(R.id.ddmv_subcategory);
        tivQuestionSetName = findViewById(R.id.tiv_question_set_name);
        tivQuestionSetDescription = findViewById(R.id.tiv_question_set_description);
        tivQuestionList.add(findViewById(R.id.tiv_question_1)); tivQuestionList.add(findViewById(R.id.tiv_question_2)); tivQuestionList.add(findViewById(R.id.tiv_question_3)); tivQuestionList.add(findViewById(R.id.tiv_question_4)); tivQuestionList.add(findViewById(R.id.tiv_question_5)); tivQuestionList.add(findViewById(R.id.tiv_question_6)); tivQuestionList.add(findViewById(R.id.tiv_question_7)); tivQuestionList.add(findViewById(R.id.tiv_question_8)); tivQuestionList.add(findViewById(R.id.tiv_question_9)); tivQuestionList.add(findViewById(R.id.tiv_question_10));
        tivTrueAnswerList.add(findViewById(R.id.tiv_question_1_true_answer)); tivTrueAnswerList.add(findViewById(R.id.tiv_question_2_true_answer)); tivTrueAnswerList.add(findViewById(R.id.tiv_question_3_true_answer)); tivTrueAnswerList.add(findViewById(R.id.tiv_question_4_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_5_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_6_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_7_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_8_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_9_true_answer));  tivTrueAnswerList.add(findViewById(R.id.tiv_question_10_true_answer));
        tivFalseAnswer1List.add(findViewById(R.id.tiv_question_1_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_2_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_3_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_4_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_5_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_6_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_7_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_8_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_9_false_answer_1)); tivFalseAnswer1List.add(findViewById(R.id.tiv_question_10_false_answer_1));
        tivFalseAnswer2List.add(findViewById(R.id.tiv_question_1_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_2_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_3_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_4_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_5_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_6_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_7_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_8_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_9_false_answer_2)); tivFalseAnswer2List.add(findViewById(R.id.tiv_question_10_false_answer_2));
        tivFalseAnswer3List.add(findViewById(R.id.tiv_question_1_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_2_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_3_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_4_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_5_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_6_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_7_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_8_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_9_false_answer_3)); tivFalseAnswer3List.add(findViewById(R.id.tiv_question_10_false_answer_3));
        tivExplanationList.add(findViewById(R.id.tiv_question_1_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_2_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_3_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_4_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_5_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_6_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_7_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_8_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_9_explanation)); tivExplanationList.add(findViewById(R.id.tiv_question_10_explanation));
        btnSubmit = findViewById(R.id.btn_submit);

        tvGreeting.setText(String.format(getString(R.string.create_question_greeting), username));

        //setCategoryDropDownMenu();

        /*
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
        */

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });

    }

    private void setToolbar() {
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);
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

    /*
    private void setCategoryDropDownMenu() {
        ddmvCategory.setSpinner(this);
    }
    */

    private void validateForm() {
        //String category = ddmvCategory.dropdown.getSelectedItem().toString();
        //String subcategory = ddmvSubcategory.dropdown.getSelectedItem().toString();
        String questionSetName = tivQuestionSetName.etInput.getText().toString();
        String questionSetDescription = tivQuestionSetDescription.etInput.getText().toString();
        Log.d(TAG, "questionSetName: " + questionSetName);
        Log.d(TAG, "questionSetDescription: " + questionSetDescription);
        String[] questions = new String[10]; String[] trueAnswers = new String[10]; String[] falseAnswers1 = new String[10]; String[] falseAnswers2 = new String[10]; String[] falseAnswers3 = new String[10]; String[] explanations = new String[10];
        for (int i = 0; i < tivQuestionList.size(); i++){
            questions[i] = tivQuestionList.get(i).etInput.getText().toString();
            trueAnswers[i] = tivTrueAnswerList.get(i).etInput.getText().toString();
            falseAnswers1[i] = tivFalseAnswer1List.get(i).etInput.getText().toString();
            falseAnswers2[i] = tivFalseAnswer2List.get(i).etInput.getText().toString();
            falseAnswers3[i] = tivFalseAnswer3List.get(i).etInput.getText().toString();
            explanations[i] = tivExplanationList.get(i).etInput.getText().toString();
            Log.d(TAG, "question" + (i+1) + ": " + questions[i]);
            Log.d(TAG, "question" + (i+1) + "TrueAnswer: " + trueAnswers[i]);
            Log.d(TAG, "question" + (i+1) + "FalseAnswer1: " + falseAnswers1[i]);
            Log.d(TAG, "question" + (i+1) + "FalseAnswer2: " + falseAnswers2[i]);
            Log.d(TAG, "question" + (i+1) + "FalseAnswer3: " + falseAnswers3[i]);
            Log.d(TAG, "question" + (i+1) + "Explanation: " + explanations[i]);
        }
        //Log.d(TAG, "category: " + category);
        //Log.d(TAG, "subcategory: " + subcategory);
        if (!(questionSetName.equals("") || questionSetDescription.equals("") ||
                questions[0].equals("") || trueAnswers[0].equals("") || falseAnswers1[0].equals("") || falseAnswers2[0].equals("") || falseAnswers3[0].equals("") || explanations[0].equals("") ||
                questions[1].equals("") || trueAnswers[1].equals("") || falseAnswers1[1].equals("") || falseAnswers2[1].equals("") || falseAnswers3[1].equals("") || explanations[1].equals("") ||
                questions[2].equals("") || trueAnswers[2].equals("") || falseAnswers1[2].equals("") || falseAnswers2[2].equals("") || falseAnswers3[2].equals("") || explanations[2].equals("") ||
                questions[3].equals("") || trueAnswers[3].equals("") || falseAnswers1[3].equals("") || falseAnswers2[3].equals("") || falseAnswers3[3].equals("") || explanations[3].equals("") ||
                questions[4].equals("") || trueAnswers[4].equals("") || falseAnswers1[4].equals("") || falseAnswers2[4].equals("") || falseAnswers3[4].equals("") || explanations[4].equals("") ||
                questions[5].equals("") || trueAnswers[5].equals("") || falseAnswers1[5].equals("") || falseAnswers2[5].equals("") || falseAnswers3[5].equals("") || explanations[5].equals("") ||
                questions[6].equals("") || trueAnswers[6].equals("") || falseAnswers1[6].equals("") || falseAnswers2[6].equals("") || falseAnswers3[6].equals("") || explanations[6].equals("") ||
                questions[7].equals("") || trueAnswers[7].equals("") || falseAnswers1[7].equals("") || falseAnswers2[7].equals("") || falseAnswers3[7].equals("") || explanations[7].equals("") ||
                questions[8].equals("") || trueAnswers[8].equals("") || falseAnswers1[8].equals("") || falseAnswers2[8].equals("") || falseAnswers3[8].equals("") || explanations[8].equals("") ||
                questions[9].equals("") || trueAnswers[9].equals("") || falseAnswers1[9].equals("") || falseAnswers2[9].equals("") || falseAnswers3[9].equals("") || explanations[9].equals("")
        )) {
            MessageDialogFragment fragment = MessageDialogFragment.newInstance("Some fields are empty! Please try again.");
            fragment.show(getSupportFragmentManager(), TAG);
        } else {
            // create a HashMap to store subcategory
            Map<String, Object> subcategoryDetail = new HashMap<>();

            DocumentReference customizeRef = mFirestore.collection("questions").document("Customize");

            customizeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            //Object documentObject = document.getData().get("subCategory");
                            List<String> documentList = (List<String>) document.get("subCategory");

                            if (documentList.contains(questionSetName)){
                                Log.d(TAG, "such question set name exists");
                                MessageDialogFragment fragment = MessageDialogFragment.newInstance("Question set name exists in database! " +
                                        "Please use another name!");
                                fragment.show(getSupportFragmentManager(), TAG);
                            } else {
                                Log.d(TAG, "not contain such question set name");
                                // update subCategory attribute in customize document schema
                                documentList.add(questionSetName);
                                subcategoryDetail.put("subCategory", documentList);
                                customizeRef.update(subcategoryDetail);
                                // add question documents on Firebase
                                addCustomizeQuestion();
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    private void addCustomizeQuestion(){
        String questionSetName = tivQuestionSetName.etInput.getText().toString();
        String questionSetDescription = tivQuestionSetDescription.etInput.getText().toString();
        String[] questions = new String[10]; String[] trueAnswers = new String[10]; String[] falseAnswers1 = new String[10]; String[] falseAnswers2 = new String[10]; String[] falseAnswers3 = new String[10]; String[] explanations = new String[10];
        for (int i = 0; i < tivQuestionList.size(); i++){
            questions[i] = tivQuestionList.get(i).etInput.getText().toString();
            trueAnswers[i] = tivTrueAnswerList.get(i).etInput.getText().toString();
            falseAnswers1[i] = tivFalseAnswer1List.get(i).etInput.getText().toString();
            falseAnswers2[i] = tivFalseAnswer2List.get(i).etInput.getText().toString();
            falseAnswers3[i] = tivFalseAnswer3List.get(i).etInput.getText().toString();
            explanations[i] = tivExplanationList.get(i).etInput.getText().toString();
        }

        CollectionReference questionSetRef = mFirestore.collection("questions").document("Customize").collection(questionSetName);

        // if success, toast
        final boolean[] success = {false};
        for (int i = 0; i < 10; i++){
            Question newQuestion = QuestionUtil.createNewQuestion(questionSetName, questionSetDescription, i + 1, questions[i], trueAnswers[i], falseAnswers1[i], falseAnswers2[i], falseAnswers3[i], explanations[i]);
            int finalI = i + 1;
            questionSetRef.add(newQuestion)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "New Question " + finalI + " successfully added on Firestore!");
                            success[0] = true;
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding new unvQuestion", e);
                        }
                    });
        }
        if (success[0]){
            Toast.makeText(getApplicationContext(), "We have received your submission! " +
                    "Your question will be available to everyone", Toast.LENGTH_LONG).show();
        }


    }

}