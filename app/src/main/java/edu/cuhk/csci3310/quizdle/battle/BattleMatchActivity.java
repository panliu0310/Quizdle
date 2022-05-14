package edu.cuhk.csci3310.quizdle.battle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.cuhk.csci3310.quizdle.R;

public class BattleMatchActivity extends AppCompatActivity {

    String username = "";
    String usernamePlayer1 = "";
    String usernamePlayer2 = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_match);
    }
}