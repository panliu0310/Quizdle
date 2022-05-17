package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.cuhk.csci3310.quizdle.model.Category;
import edu.cuhk.csci3310.quizdle.model.Question;


public class EndlessQuestionActivity extends AppCompatActivity {

    public String TAG = "EndlessQuestionActivity";
    public static final String CATEGORY = "edu.cuhk.csci3310.quizdle.extra.CATEGORY";
    public static final String SCORE = "edu.cuhk.csci3310.quizdle.extra.SCORE";

    private FirebaseFirestore mFirestore;

    private List<Category> categorySet;

    private String category;
    private String questionSetName;
    public List<Question> questionSet = new ArrayList<>();
    Random ran = new Random();
    private int questionNum = 0;
    private int score = 0;
    private int correctAns;
    private int lifes = 3;

    TextView tvScore;
    TextView tvQuestion;
    Button btnA, btnB, btnC, btnD, btnNextQuestion;
    Button[] buttonList;
    TextView tvExplanation;
    Toolbar toolbar;
    ImageView ivHeart1, ivHeart2, ivHeart3;
    ImageView[] heartList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_question);

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
        ivHeart1 = findViewById(R.id.iv_heart1);
        ivHeart2 = findViewById(R.id.iv_heart2);
        ivHeart3 = findViewById(R.id.iv_heart3);
        heartList = new ImageView[]{ivHeart1, ivHeart2, ivHeart3};

        mFirestore = FirebaseFirestore.getInstance();

        // get questions from different categories (except category: "Customize")
        Query categoryQuery = mFirestore.collection("questions");
        categoryQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                        categorySet = task.getResult().toObjects(Category.class);   // get all categories
                        int i = 1;
                        for (Category cat : categorySet){
                            String categoryName = cat.getName();
                            Log.d(TAG, categoryName);
                            if(!categoryName.equals("Customize")) {
                                int j = 1;

                                // get questions
                                for (String subCategory : cat.getSubCategory()) {
                                    Query questionQuery = mFirestore.collection("questions")
                                            .document(categoryName).collection(subCategory);
                                    int finalI = i;
                                    int finalJ = j;
                                    questionQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            Log.d(TAG, subCategory);
                                            if (task.getResult() != null){
                                                questionSet.addAll(task.getResult().toObjects(Question.class));
                                                if((finalI == categorySet.size()) && (finalJ == cat.getSubCategory().size())){
                                                    Log.d(TAG, "get all questions");
                                                    Collections.shuffle(questionSet);
                                                    updateQuestion();
                                                }
                                            }
                                        }
                                    });
                                    j++;
                                }

                            }
                            i++;
                        }
                }else {
                    Log.d(TAG, "Error getting categories: ", task.getException());
                }
            }


        });

        setChoiceButtonOnclickListener();
        setNextQuestionButtonOnclickListener();

    }

    private void updateQuestion(){
        Question question = questionSet.get(questionNum);
        int displayNum = questionNum + 1;
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
        toolbar.setTitle( "Endless Mode - " + ": Question " + displayNum);
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
                    heartList[3-lifes].setVisibility(View.INVISIBLE);
                    lifes--;
                }
                for (Button choice_btn: buttonList ){
                    choice_btn.setEnabled(false);
                }
                tvExplanation.setVisibility(View.VISIBLE);
                tvExplanation.setText(questionSet.get(questionNum).getExplanation());
                if (questionNum + 1 == questionSet.size()){
                    btnNextQuestion.setText("Finish");
                }else if(lifes == 0){
                    btnNextQuestion.setText("Game Over");
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
                    /*Intent intent = new Intent(view.getContext(), CompleteQuestionSummaryActivity.class);
                    intent.putExtra(CATEGORY, "Endless Mode");
                    intent.putExtra(SCORE, Integer.toString(score));
                    view.getContext().startActivity(intent);*/
                    Log.d(TAG, "Finished!");
                }else if(lifes == 0){
                    Log.d(TAG, "Failed");
                }
                else {
                    updateQuestion();
                }
            }
        };
        btnNextQuestion.setOnClickListener(btnOnClickListener);
    }

}
