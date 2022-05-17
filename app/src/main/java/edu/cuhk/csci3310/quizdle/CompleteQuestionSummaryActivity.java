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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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
    private int expBound = 0;
    private int victory = 0;
    private int totalMatch = 0;
    private int coins = 0;
    private String winStatus = "";

    Toolbar tvToolbar;
    TextView tvCongrats;
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
        Log.d(TAG, intent.getStringExtra(QuestionActivity.SCORE));
        //score = Integer.parseInt(intent.getStringExtra(QuestionActivity.SCORE));
        score = 100;
        winStatus = intent.getStringExtra("winStatus");
        Log.d(TAG, "win status: " + winStatus);

        tvToolbar = findViewById(R.id.toolbar);
        tvCongrats = findViewById(R.id.tv_congrats);
        tvExp = findViewById(R.id.tv_exp);
        tvLevel = findViewById(R.id.tv_level);
        tvCoin = findViewById(R.id.tv_coin);
        btnReturn = findViewById(R.id.btn_main_menu);

        tvToolbar.setTitle("Completed " + category + " - " + questionSetName);

        // get user data from firebase
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
                    String documentId = task.getResult().getDocuments().get(0).getId();
                    user = task.getResult().toObjects(User.class).get(0);

                    switch (winStatus) {
                        case "win":
                            // if user is winner, he gets more coins and experiences
                            tvCongrats.setText("You Win!");
                            coins = 20 + score / 10 + user.getCoin(); // user get 20 bonus coins
                            score = 100 + score + user.getExperience(); // user get 100 bonus experiences
                            break;
                        case "tie":
                            tvCongrats.setText("Tie!");
                            coins = 10 + score / 10 + user.getCoin(); // user get 10 bonus coins
                            score = 50 + score + user.getExperience(); // user get 50 bonus experiences
                            break;
                        case "lose":
                            tvCongrats.setText("You Lose...");
                            coins = 5 + score / 10 + user.getCoin(); // user get 5 bonus coins
                            score = 20 + score + user.getExperience(); // user get 20 bonus experiences
                            break;
                        case "":
                            // this part is for single player
                            tvCongrats.setText("Congratulations!!");
                            coins = score / 10 + user.getCoin();
                            score = score + user.getExperience();
                    }

                    level = (int) Math.floor(Math.log(score/100)/Math.log(2)) + 1;
                    expBound = (int) (100 * Math.pow(2,level-1));
                    while (score >= expBound){
                        level++;
                        expBound *= 2;
                    }

                    tvExp.setText(user.getExperience() + " -> " + score);
                    tvCoin.setText(user.getCoin() + " -> " + coins);
                    tvLevel.setText(user.getLevel() + " -> " + level);
                    /* user.setExperience(score);
                    user.setLevel(level);
                    user.setCoin(coins);*/
                    Map<String, Object> userDetail = new HashMap<>();
                    userDetail.put("coin", coins);
                    userDetail.put("experience", score);
                    userDetail.put("level", level);
                    userDetail.put("expBound", expBound);

                    mFirestore.collection("users").document(documentId)
                            .update(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "updated user info");
                        }
                    });

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
