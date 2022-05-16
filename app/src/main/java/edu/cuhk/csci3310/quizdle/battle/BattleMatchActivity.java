package edu.cuhk.csci3310.quizdle.battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Locale;
import java.util.Random;

import edu.cuhk.csci3310.quizdle.R;
import edu.cuhk.csci3310.quizdle.model.Question;

public class BattleMatchActivity extends AppCompatActivity {

    private String TAG = "BattleMatchActivity";

    private String usernamePlayer1 = ""; private String usernamePlayer2 = "";
    private String role = "";
    private String roomName = "";
    private String category;
    private String questionSetName;
    public List<Question> questionSet;
    private int questionNum = 0;
    private int scorePlayer1 = 0; private int scorePlayer2 = 0;
    private String choicePlayer1 = ""; private String choicePlayer2 = "";
    private int correctAns;

    private FirebaseFirestore mFirestore;
    FirebaseDatabase database;
    DatabaseReference roomNameRef;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;
    DatabaseReference questionRef;
    DatabaseReference scoreRef;

    TextView tvScorePlayer1; TextView tvScorePlayer2;
    TextView tvTimer;
    TextView tvQuestion;
    TextView tvChoicePlayer1; TextView tvChoicePlayer2;
    Button btnA, btnB, btnC, btnD, btnNextQuestion;
    Button[] buttonList;
    TextView tvExplanation;
    Toolbar toolbar;

    CountDownTimer countDownTimer15s;
    CountDownTimer countDownTimer3s;

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
        tvTimer = findViewById(R.id.tv_timer);
        tvQuestion = findViewById(R.id.tv_question);
        tvChoicePlayer1 = findViewById(R.id.tv_choice_player_1); tvChoicePlayer2 = findViewById(R.id.tv_choice_player_2);
        tvExplanation = findViewById(R.id.tv_explanation);
        btnA = findViewById(R.id.btn_choice_a); btnB = findViewById(R.id.btn_choice_b); btnC = findViewById(R.id.btn_choice_c); btnD = findViewById(R.id.btn_choice_d);
        btnNextQuestion = findViewById(R.id.btn_next_question);
        buttonList = new Button[]{btnA, btnB, btnC, btnD};

        createTimers();

        // getSubcategoryRandom() -> getQuestionSet() -> setQuestionViews()
        getSubcategoryRandom();

        getRoomData();

        setChoiceButtonOnclickListener();

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
    }

    private void createTimers() {
        countDownTimer15s = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                sendResultOfOneQuestion();
                Log.d(TAG, "timer finish");
            }
        };

        countDownTimer3s = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                questionNum += 1;
                tvChoicePlayer1.setText(""); tvChoicePlayer2.setText("");
                choicePlayer1 = ""; choicePlayer2 = "";
                setQuestionViews();
            }
        };
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
                Log.d(TAG, "onDataChange!");
                // set textview username with score data
                if (snapshot.child("player1score").getValue() != null) {
                    scorePlayer1 = ((Long) snapshot.child("player1score").getValue()).intValue();
                    Log.d(TAG, "usernamePlayer1: " + usernamePlayer1);
                    tvScorePlayer1.setText(usernamePlayer1 + ": " + scorePlayer1);
                }
                if (snapshot.child("player2score").getValue() != null) {
                    scorePlayer2 = ((Long) snapshot.child("player2score").getValue()).intValue();
                    Log.d(TAG, "usernamePlayer2: " + usernamePlayer2);
                    tvScorePlayer2.setText(usernamePlayer2 + ": " + scorePlayer2);
                }

                // after calling sendResultOfOneQuestion()
                // add score to players
                if (snapshot.child("player1correct").getValue() != null) {
                    tvChoicePlayer1.setText(snapshot.child("player1correct").getValue().toString());
                    Log.d(TAG, "set tvChoicePlayer1 to: " + snapshot.child("player1correct").getValue().toString());
                    if (tvChoicePlayer1.getText().equals("correct") && role.equals("host")) {
                        scorePlayer1 += 50;
                        roomRef = database.getReference("battles/" + roomName + "/player1score");
                        roomRef.setValue(scorePlayer1);
                    }
                    // after getting the score, clear the record in database
                    clearDatabase();
                }
                if (snapshot.child("player2correct").getValue() != null) {
                    tvChoicePlayer2.setText(snapshot.child("player2correct").getValue().toString());
                    Log.d(TAG, "set tvChoicePlayer2 to: " + snapshot.child("player2correct").getValue().toString());
                    if (tvChoicePlayer2.getText().equals("correct") && role.equals("guest")) {
                        scorePlayer2 += 50;
                        roomRef = database.getReference("battles/" + roomName + "/player2score");
                        roomRef.setValue(scorePlayer2);
                    }
                    // after getting the score, clear the record in database
                    clearDatabase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setQuestionViews() {
        // get the question in questionSet in Class Question format
        Log.d(TAG, questionSet.toString());
        Question question = questionSet.get(questionNum);
        int displayNum = questionNum + 1;
        String[] falseAnsList= {question.getFalseAns1(), question.getFalseAns2(), question.getFalseAns3()};
        Random ran = new Random();
        correctAns = ran.nextInt(4); // correctAns will be 0, 1, 2, 3
        int falseAnsNum = 0;
        Log.d(TAG, "CorrectAns: " + correctAns);

        // set text with 4 buttons
        for (int i = 0; i < buttonList.length; i++) {
            buttonList[i].setEnabled(true);
            buttonList[i].setBackgroundColor(getColor(R.color.purple_500));
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
        countDownTimer15s.start();
    }

    private void setChoiceButtonOnclickListener(){
        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (role.equals("host")) {
                    choicePlayer1 = (String) btn.getText();
                    roomRef = database.getReference("battles/" + roomName + "/player1choice");
                    roomRef.setValue(btn.getText());
                    if (buttonList[0].equals(btn)) {
                        tvChoicePlayer1.setText("A");
                    } else if (buttonList[1].equals(btn)) {
                        tvChoicePlayer1.setText("B");
                    } else if (buttonList[2].equals(btn)) {
                        tvChoicePlayer1.setText("C");
                    } else {
                        tvChoicePlayer1.setText("D");
                    }
                } else if (role.equals("guest")) {
                    choicePlayer2 = (String) btn.getText();
                    roomRef = database.getReference("battles/" + roomName + "/player2choice");
                    roomRef.setValue(btn.getText());
                    if (buttonList[0].equals(btn)) {
                        tvChoicePlayer2.setText("A");
                    } else if (buttonList[1].equals(btn)) {
                        tvChoicePlayer2.setText("B");
                    } else if (buttonList[2].equals(btn)) {
                        tvChoicePlayer2.setText("C");
                    } else {
                        tvChoicePlayer2.setText("D");
                    }
                }
            }
        };

        for (Button button : buttonList) {
            button.setOnClickListener(btnOnClickListener);
        }

    }

    private void sendResultOfOneQuestion() {
        if (choicePlayer1.equals(questionSet.get(questionNum).getTrueAns()) && role.equals("host")) {
            roomRef = database.getReference("battles/" + roomName + "/player1correct");
            roomRef.setValue("correct");
            Log.d(TAG, "set player1correct!");
        } else if (!choicePlayer1.equals(questionSet.get(questionNum).getTrueAns()) && role.equals("host")) {
            roomRef = database.getReference("battles/" + roomName + "/player1correct");
            roomRef.setValue("wrong");
            Log.d(TAG, "set player1wrong!");
        }

        if (choicePlayer2.equals(questionSet.get(questionNum).getTrueAns()) && role.equals("guest")) {
            roomRef = database.getReference("battles/" + roomName + "/player2correct");
            roomRef.setValue("correct");
            Log.d(TAG, "set player2correct!");
        } else if (!choicePlayer1.equals(questionSet.get(questionNum).getTrueAns()) && role.equals("guest")) {
            roomRef = database.getReference("battles/" + roomName + "/player2correct");
            roomRef.setValue("wrong");
            Log.d(TAG, "set player2wrong!");
        }
        tvExplanation.setText(questionSet.get(questionNum).getExplanation());
        if (questionNum == questionSet.size()) {
            // finish all questions
            // TODO: end game
        } else {
            for (Button choice_btn: buttonList ){
                choice_btn.setEnabled(false);
            }
            countDownTimer3s.start();
        }
    }

    private void clearDatabase() {
        Log.d(TAG, "clearDatabase() is called!");
        Log.d(TAG, "score of player 1: " + scorePlayer1);
        Log.d(TAG, "score of player 2: " + scorePlayer2);
        roomRef = database.getReference("battles/" + roomName + "/player1correct");
        roomRef.removeValue();
        Log.d(TAG, "removed player1correct!");
        roomRef = database.getReference("battles/" + roomName + "/player2correct");
        roomRef.removeValue();
        Log.d(TAG, "removed player2correct!");
        roomRef = database.getReference("battles/" + roomName + "/player1choice");
        roomRef.removeValue();
        Log.d(TAG, "removed player1choice!");
        roomRef = database.getReference("battles/" + roomName + "/player2choice");
        roomRef.removeValue();
        Log.d(TAG, "removed player2choice!");
    }

}