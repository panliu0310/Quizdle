package edu.cuhk.csci3310.quizdle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public static final String CATEGORY = "edu.cuhk.csci3310.quizdle.extra.CATEGORY";
    public static final String QUESTIONSET = "edu.cuhk.csci3310.quizdle.extra.QUESTIONSET";
    public static final String SCORE = "edu.cuhk.csci3310.quizdle.extra.SCORE";

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mFirebaseAuth;

    private String email = "";
    private String category;
    private String questionSetName;
    public List<Question> questionSet;
    Random ran = new Random();
    private int questionNum = 0;
    private int score = 0;
    private int correctAns;
    private int coin = 0;

    TextView tvScore;
    TextView tvQuestion;
    TextView tvCoin;
    Button btnHalf;
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
        tvCoin = findViewById(R.id.tv_coin);
        btnHalf = findViewById(R.id.btn_halfhalf);
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

        getCoins();

        setHalfButtonOnClickListener();
        setChoiceButtonOnclickListener();
        setNextQuestionButtonOnclickListener();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?\nYou cannot get any exp and coins if exited!")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        QuestionActivity.super.onBackPressed();
                    }
                })
                .create().show();
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
            buttonList[i].setBackgroundColor(getColor(R.color.button));
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
        btnHalf.setEnabled(true);
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
                    Intent intent = new Intent(view.getContext(), CompleteQuestionSummaryActivity.class);
                    intent.putExtra(CATEGORY, category);
                    intent.putExtra(QUESTIONSET, questionSetName);
                    intent.putExtra(SCORE, score);
                    view.getContext().startActivity(intent);
                }else {
                    updateQuestion();
                }
            }
        };
        btnNextQuestion.setOnClickListener(btnOnClickListener);
    }

    private void getCoins(){
        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        email = firebaseUser.getEmail();

        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                coin = ((Number)document.getData().get("coin")).intValue();
                                tvCoin.setText(coin + "");
                                Log.d(TAG, coin + " coooin");

                            }
                        } else {
                            Log.d(TAG, "Error getting user document: ", task.getException());
                        }
                    }
                });
    }

    private void setHalfButtonOnClickListener(){
        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coin >= 100) {
                    int setDisable = 0;
                    for (int i = 0; i < 4; i++) {
                        if (setDisable >= 2) {
                            break;
                        }
                        if (i != correctAns) {
                            buttonList[i].setEnabled(false);
                            setDisable++;
                        }
                    }
                    btnHalf.setEnabled(false);
                    coin -= 100;
                    tvCoin.setText(coin + "");
                    updateCoin();
                }else{
                    MessageDialogFragment fragment = MessageDialogFragment.newInstance("You don't have enough coins to buy hint");
                    fragment.show(getSupportFragmentManager(), TAG);
                }

            }
        };
        btnHalf.setOnClickListener(btnOnClickListener);
    }

    private void updateCoin(){
        Map<String, Object> coinUpdate = new HashMap<>();
        coinUpdate.put("coin", coin);

        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            Log.d(TAG, documentSnapshot.getData().toString());
                            mFirestore.collection("users")
                                    .document(documentId)
                                    .update(coinUpdate)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "successfully update coin");
                                        }
                                    });
                        }
                    }
                });
    }



}
