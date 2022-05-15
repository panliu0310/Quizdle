package edu.cuhk.csci3310.quizdle.battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cuhk.csci3310.quizdle.R;
import edu.cuhk.csci3310.quizdle.customview.DropDownMenuView;

public class BattleReadyActivity extends AppCompatActivity {

    private String TAG = "BattleReadyActivity";

    DropDownMenuView ddmvCategory;
    TextView tvPlayer1; TextView tvPlayer2;
    TextView tvUsernamePlayer1; TextView tvUsernamePlayer2;
    ImageView ivReadyPlayer1; ImageView ivReadyPlayer2;
    Button btnReady;

    String username = "";
    String roomName = "";
    String usernamePlayer1 = "";
    String usernamePlayer2 = "";
    String role = "";
    boolean player1Ready;
    boolean player2Ready;

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;
    DatabaseReference roomNameRef;
    DatabaseReference readyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ready);
        setToolbar();

        database = FirebaseDatabase.getInstance();

        // get the player name and room name
        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        username = intent.getStringExtra("username");
        Log.d(TAG, "Received roomName: " + roomName);
        Log.d(TAG, "Received username: " + username);
        if (username.equals(roomName.substring(0, roomName.length() - 7))) {
            role = "host";
        } else {
            role = "guest";
        }


        // set view names
        ddmvCategory = findViewById(R.id.ddmv_category);
        tvPlayer1 = findViewById(R.id.tv_player_1); tvPlayer2 = findViewById(R.id.tv_player_2);
        tvUsernamePlayer1 = findViewById(R.id.tv_username_player_1); tvUsernamePlayer2 = findViewById(R.id.tv_username_player_2);
        ivReadyPlayer1 = findViewById(R.id.iv_ready_player_1); ivReadyPlayer2 = findViewById(R.id.iv_ready_player_2);
        btnReady = findViewById(R.id.btn_ready);

        setButtonReadyListener();

        addRoomDataEventListener();
    }

    private void setToolbar(){
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

    private void setButtonReadyListener(){
        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role.equals("host")) {
                    readyRef = database.getReference("rooms/" + roomName + "/player1ready");
                    if (player1Ready) {
                        readyRef.setValue("true");
                    } else {
                        readyRef.setValue("false");
                    }
                } else {
                    readyRef = database.getReference("rooms/" + roomName + "/player2ready");
                    if (player2Ready) {
                        readyRef.setValue("true");
                    } else {
                        readyRef.setValue("false");
                    }
                }
            }
        });
    }

    private void addRoomDataEventListener(){
        roomNameRef = database.getReference("rooms/" + roomName);
        roomNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // set textview usernames data
                if (snapshot.child("player1").getValue() != null){
                    usernamePlayer1 = snapshot.child("player1").getValue().toString();
                    Log.d(TAG, "usernamePlayer1: " + usernamePlayer1);
                    tvUsernamePlayer1.setText(usernamePlayer1);
                }
                if (snapshot.child("player2").getValue() != null){
                    usernamePlayer2 = snapshot.child("player2").getValue().toString();
                    Log.d(TAG, "usernamePlayer2: " + usernamePlayer2);
                    tvUsernamePlayer2.setText(usernamePlayer2);
                }
                // set imageview ready data
                if (snapshot.child("player1ready").getValue().toString().equals("false")){
                    player1Ready = true;
                    ivReadyPlayer1.setVisibility(View.INVISIBLE);
                } else {
                    player1Ready = false;
                    ivReadyPlayer1.setVisibility(View.VISIBLE);
                }
                if (snapshot.child("player2ready").getValue().toString().equals("false")){
                    player2Ready = true;
                    ivReadyPlayer2.setVisibility(View.INVISIBLE);
                } else {
                    player2Ready = false;
                    ivReadyPlayer2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}