package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import edu.cuhk.csci3310.quizdle.model.Question;
import edu.cuhk.csci3310.quizdle.model.User;

public class CompleteQuestionSummaryActivity extends AppCompatActivity {

    public final String TAG = "CompleteQuestionSummaryActivity";

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mFirebaseAuth;
    private String email = "";

    private String category;
    private String questionSetName;
    private User user;
    private int score = 0;
    private int level = 0;
    private int coins = 0;

    Toolbar tvToolbar;
    TextView tvExp;
    TextView tvLevel;
    TextView tvCoin;
    Button btnReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_question_summary);

        // get information from intent
        Intent intent = getIntent();
        category = intent.getStringExtra(QuestionActivity.CATEGORY);
        questionSetName = intent.getStringExtra(QuestionActivity.QUESTIONSET);
        score = Integer.parseInt(intent.getStringExtra(QuestionActivity.SCORE));

        tvToolbar = findViewById(R.id.toolbar);
        tvExp = findViewById(R.id.tv_exp);
        tvLevel = findViewById(R.id.tv_level);
        tvCoin = findViewById(R.id.tv_coin);
        btnReturn = findViewById(R.id.btn_main_menu);

        tvToolbar.setTitle("Completed " + category + " - " + questionSetName + "!!");

        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        email = firebaseUser.getEmail();

        Query userQuery = mFirestore.collection("users").whereEqualTo("email", email);
        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, task.getResult().toString());
                    user = task.getResult().toObjects(User.class).get(0);

                    coins  = score / 10 + user.getCoin();
                    score += user.getExperience();
                    level = (int) Math.floor(Math.log(score/100)/Math.log(2)) + 1;
                    tvExp.setText(user.getExperience() + " -> " + score);
                    tvCoin.setText(user.getCoin() + " -> " + coins);
                    tvLevel.setText(user.getLevel() + " -> " + level);
                    user.setExperience(score);
                    user.setLevel(level);
                    user.setCoin(coins);


                }else {
                    Log.d(TAG, "Error getting user data: ", task.getException());
                }
            }
        });

        tvLevel.setText("1 -> 2");
        setMenuButtonOnClickListener();
    }

    private void setMenuButtonOnClickListener(){
        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        };

        btnReturn.setOnClickListener(btnOnClickListener);

    }


}
