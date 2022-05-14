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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ready);
        setToolbar();

        database = FirebaseDatabase.getInstance();

        // get the player name
        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        username = intent.getStringExtra("username");
        Log.d(TAG, "Received roomName: " + roomName);
        Log.d(TAG, "Received username: " + username);

        // set view names
        ddmvCategory = findViewById(R.id.ddmv_category);
        tvPlayer1 = findViewById(R.id.tv_player_1); tvPlayer2 = findViewById(R.id.tv_player_2);
        tvUsernamePlayer1 = findViewById(R.id.tv_username_player_1); tvUsernamePlayer2 = findViewById(R.id.tv_username_player_2);
        ivReadyPlayer1 = findViewById(R.id.iv_ready_player_1); ivReadyPlayer2 = findViewById(R.id.iv_ready_player_2);

        roomRef = database.getReference("rooms/" + roomName);

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



}