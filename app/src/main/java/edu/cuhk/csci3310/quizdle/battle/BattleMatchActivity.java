package edu.cuhk.csci3310.quizdle.battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Random;

import edu.cuhk.csci3310.quizdle.R;
import edu.cuhk.csci3310.quizdle.dialogfragment.MessageDialogFragment;
import edu.cuhk.csci3310.quizdle.model.Question;

public class BattleMatchActivity extends AppCompatActivity {

    private String TAG = "BattleMatchActivity";

    private String usernamePlayer1 = "";
    private String usernamePlayer2 = "";
    private String role = "";
    private String roomName = "";
    private String category;
    private String questionSetName;
    public List<Question> questionSet;
    private int questionNum = 0;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private int correctAns;

    private FirebaseFirestore mFirestore;
    FirebaseDatabase database;
    DatabaseReference roomNameRef;
    DatabaseReference roomsRef;
    DatabaseReference questionRef;

    TextView tvScorePlayer1; TextView tvScorePlayer2;
    TextView tvQuestion;
    Button btnA, btnB, btnC, btnD, btnNextQuestion;
    Button[] buttonList;
    TextView tvExplanation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_match);
        setToolbar();

        mFirestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        // get the player name from intent
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        role = intent.getStringExtra("role");
        roomName = intent.getStringExtra("roomName");
        usernamePlayer1 = intent.getStringExtra("usernamePlayer1");
        usernamePlayer2 = intent.getStringExtra("usernamePlayer2");
        Log.d(TAG, "Received category: " + category);
        Log.d(TAG, "Received role: " + role);

        // set view name
        tvScorePlayer1 = findViewById(R.id.tv_score_player_1); tvScorePlayer2 = findViewById(R.id.tv_score_player_2);
        tvQuestion = findViewById(R.id.tv_question);
        tvExplanation = findViewById(R.id.tv_explanation);
        btnA = findViewById(R.id.btn_choice_a); btnB = findViewById(R.id.btn_choice_b); btnC = findViewById(R.id.btn_choice_c); btnD = findViewById(R.id.btn_choice_d);
        btnNextQuestion = findViewById(R.id.btn_next_question);
        buttonList = new Button[]{btnA, btnB, btnC, btnD};

        // getSubcategoryRandom() -> getQuestionSet() -> setQuestionViews()
        getSubcategoryRandom();

        getRoomData();

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
    }

    private void getSubcategoryRandom() {
        DocumentReference categoryRef = mFirestore.collection("questions").document(category);

        categoryRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        // randomly get Question set name in category
                        List<String> documentList = (List<String>) document.get("subCategory");
                        Log.d(TAG, "documentList: " + documentList.toString());
                        Random rn = new Random();
                        int randomInt = rn.nextInt(documentList.size()); // rn.nextInt(max - min + 1) + min
                        questionSetName = documentList.get(randomInt);
                        Log.d(TAG, "questionSetName: " + questionSetName);
                        // getSubcategoryRandom() -> getQuestionSet() -> setQuestionViews()
                        getQuestionSet();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void getQuestionSet() {
        // questionSet will become list of questions
        Query query = mFirestore.collection("/questions/" + category + "/" + questionSetName);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    questionSet = task.getResult().toObjects(Question.class);
                    Log.d(TAG, questionSet.get(0).getQuestion());
                    // getSubcategoryRandom() -> getQuestionSet() -> setQuestionViews()
                    setQuestionViews();
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void getRoomData() {
        roomNameRef = database.getReference("battles/" + roomName);
        roomNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // set textview username with score data
                if (snapshot.child("player1Score").getValue() != null) {
                    scorePlayer1 = ((Long) snapshot.child("player1Score").getValue()).intValue();
                    Log.d(TAG, "usernamePlayer1: " + usernamePlayer1);
                    tvScorePlayer1.setText(usernamePlayer1 + ": " + scorePlayer1);
                }
                if (snapshot.child("player2Score").getValue() != null) {
                    scorePlayer2 = ((Long) snapshot.child("player2Score").getValue()).intValue();
                    Log.d(TAG, "usernamePlayer2: " + usernamePlayer2);
                    tvScorePlayer2.setText(usernamePlayer2 + ": " + scorePlayer2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setQuestionViews() {
        // get the question in questionSet in Class Question format
        Log.d(TAG, "setQuestionViews() is being called");
        Log.d(TAG, questionSet.toString());
        Question question = questionSet.get(questionNum);
        int displayNum = questionNum + 1;
        String[] falseAnsList= {question.getFalseAns1(), question.getFalseAns2(), question.getFalseAns3()};
        Random ran = new Random();
        correctAns = ran.nextInt(4); // correctAns will be 0, 1, 2, 3
        int falseAnsNum = 0;
        Log.d(TAG, "CorrectAns" + correctAns);

        // set text with 4 buttons
        for (int i = 0; i < buttonList.length; i++) {
            buttonList[i].setEnabled(true);
            buttonList[i].setBackgroundColor(getColor(R.color.purple_500));
            Log.d(TAG, falseAnsNum+"");
            // if i is the correctAns, set text with true answer
            // else set text with false answers
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

}