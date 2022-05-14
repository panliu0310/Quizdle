package edu.cuhk.csci3310.quizdle;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.cuhk.csci3310.quizdle.adapter.CategoryListAdapter;
import edu.cuhk.csci3310.quizdle.adapter.SubCategoryListAdapter;
import edu.cuhk.csci3310.quizdle.dialogfragment.MessageDialogFragment;
import edu.cuhk.csci3310.quizdle.model.Question;

public class QuestionActivity extends AppCompatActivity {

    public String TAG = "QuestionActivity";

    private FirebaseFirestore mFirestore;

    private String category;
    private String questionSetName;
    public List<Question> questionSet;
    Random ran = new Random();
    private int questionNum = 0;
    private int score = 0;
    private int correctAns;

    TextView tvScore;
    TextView tvQuestion;
    TextView TvExplanation;
    Button btnA, btnB, btnC, btnD, btnNextQuestion;
    Button[] buttonList;
    TextView tvExplanation;
    Toolbar toolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tvScore = findViewById(R.id.tv_score);
        tvQuestion = findViewById(R.id.tv_question);
        tvExplanation = findViewById(R.id.tv_explanation);
        btnA = findViewById(R.id.btn_choice_a);
        btnB = findViewById(R.id.btn_choice_b);
        btnC = findViewById(R.id.btn_choice_c);
        btnD = findViewById(R.id.btn_choice_d);
        btnNextQuestion = findViewById(R.id.btn_next_question);
        buttonList = new Button[]{btnA, btnB, btnC, btnD};
        toolbar = findViewById(R.id.toolbar);

        // get information from intent
        Intent intent = getIntent();
        category = intent.getStringExtra(SubCategoryListAdapter.SubCategoryViewHolder.CATEGORY);
        questionSetName = intent.getStringExtra(SubCategoryListAdapter.SubCategoryViewHolder.SUBCATEGORY);

        mFirestore = FirebaseFirestore.getInstance();
        Query query = mFirestore.collection("/questions/" + category + "/" + questionSetName);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    questionSet = task.getResult().toObjects(Question.class);
                    Log.d(TAG, questionSet.get(0).getQuestion());
                    updateQuestion();
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        setChoiceButtonOnclickListener();
        setNextQuestionButtonOnclickListener();

    }


    private void updateQuestion(){
        Question question = questionSet.get(questionNum);
        int displayNum = questionNum + 1;
        //Button[] buttonList = {btnA, btnB, btnC, btnD};
        String[] falseAnsList= {question.getFalseAns1(), question.getFalseAns2(), question.getFalseAns3()};
        correctAns = ran.nextInt(4);
        int falseAnsNum = 0;
        Log.d(TAG, "CorrectAns" + correctAns);
        for (int i = 0; i < buttonList.length; i++){
            buttonList[i].setEnabled(true);
            buttonList[i].setBackgroundColor(getColor(R.color.purple_500));
            Log.d(TAG, falseAnsNum+"");
            if (i == correctAns){
                buttonList[i].setText(question.getTrueAns());
            }else{
                buttonList[i].setText(falseAnsList[falseAnsNum]);
                falseAnsNum++;
            }
        }
        toolbar.setTitle(category + " - " + questionSetName + ": Question " + displayNum);
        tvQuestion.setText(question.getQuestion());
        tvExplanation.setVisibility(View.INVISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);
    }

    private void setChoiceButtonOnclickListener(){
        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                String btnText = (String) btn.getText();
                if(btnText.equals(questionSet.get(questionNum).getTrueAns())){
                    v.setBackgroundColor(getResources().getColor(R.color.correct));
                    score += 100;
                    tvScore.setText(score + "");
                }else {
                    v.setBackgroundColor(getResources().getColor(R.color.incorrect));
                }
                for (Button choice_btn: buttonList ){
                    choice_btn.setEnabled(false);
                }
                tvExplanation.setVisibility(View.VISIBLE);
                tvExplanation.setText(questionSet.get(questionNum).getExplanation());
                if (questionNum + 1 == questionSet.size()){
                    btnNextQuestion.setText("Finish");
                }
                btnNextQuestion.setVisibility(View.VISIBLE);

            }
        };

        for (Button button : buttonList) {
            button.setOnClickListener(btnOnClickListener);
        }

    }

    private void setNextQuestionButtonOnclickListener(){
        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum++;
                if (questionNum == questionSet.size()){
                    MessageDialogFragment fragment = MessageDialogFragment
                            .newInstance("Completed! You got " + score + " marks.");
                    // cannot directly use getActivity().getSupportFragmentManager() since this is in an override method
                    fragment.show(getSupportFragmentManager(), TAG);
                }else {
                    updateQuestion();
                }
            }
        };
        btnNextQuestion.setOnClickListener(btnOnClickListener);
    }

    /*private void setToolbar(String title){
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
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
    }*/
}
