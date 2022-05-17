package edu.cuhk.csci3310.quizdle.battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnReady; Button btnStart;

    String username = "";
    String roomName = "";
    String category = "";
    String usernamePlayer1 = "";
    String usernamePlayer2 = "";
    String role = "";
    boolean player1Ready;
    boolean player2Ready;

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomNameRef;
    DatabaseReference readyRef;
    DatabaseReference startRef;
    DatabaseReference categoryRef;
    DatabaseReference battleRef;

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
        btnReady = findViewById(R.id.btn_ready); btnStart = findViewById(R.id.btn_start);

        setCategoryDropDownMenu();
        setCategoryItemListener();

        setButtonReadyListener();
        setButtonStartListener();

        addRoomDataEventListener();
    }

    @Override
    public void onBackPressed() {
        finish();
        if (role.equals("host")) {
            removePlayer1();
        } else {
            removePlayer2();
        }
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
                if (role.equals("host")) {
                    removePlayer1();
                } else {
                    removePlayer2();
                }
            }
        });
    }

    private void setCategoryDropDownMenu() {
        ddmvCategory.setSpinner(this);
        if (role.equals("guest")){
            // only host can set category of a room
            ddmvCategory.dropdown.setEnabled(false);
        }
    }

    private void setCategoryItemListener() {
        ddmvCategory.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Log.d(TAG, item.toString() + " category is picked");
                categoryRef = database.getReference("rooms/" + roomName + "/category");
                categoryRef.setValue(item.toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
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
                        // initially ready, when btn click, become not ready
                        readyRef.setValue("false");
                    } else {
                        // initially not ready, when btn click, become not ready
                        readyRef.setValue("true");
                    }
                } else {
                    readyRef = database.getReference("rooms/" + roomName + "/player2ready");
                    if (player2Ready) {
                        readyRef.setValue("false");
                    } else {
                        readyRef.setValue("true");
                    }
                }
            }
        });
    }

    private void setButtonStartListener(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRef = database.getReference("rooms/" + roomName + "/start");
                startRef.setValue("true");

                // delete the waiting ready room
                roomRef = database.getReference("rooms/" + roomName);
                roomRef.removeValue();

                // create battle
                battleRef = database.getReference("battles/" + roomName + "/player1");
                battleRef.setValue(usernamePlayer1);
                battleRef = database.getReference("battles/" + roomName + "/player2");
                battleRef.setValue(usernamePlayer2);
                battleRef = database.getReference("battles/" + roomName + "/category");
                battleRef.setValue(category);
                battleRef = database.getReference("battles/" + roomName + "/player1score");
                battleRef.setValue(0);
                battleRef = database.getReference("battles/" + roomName + "/player2score");
                battleRef.setValue(0);
            }
        });
    }

    private void addRoomDataEventListener(){
        roomNameRef = database.getReference("rooms/" + roomName);
        roomNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // set category data
                if (snapshot.child("category").getValue() != null) {
                    category = snapshot.child("category").getValue().toString();
                    Log.d(TAG, "category: " + category);
                    ddmvCategory.setItemByName(category);
                }

                // set textview usernames data
                if (snapshot.child("player1").getValue() != null) {
                    usernamePlayer1 = snapshot.child("player1").getValue().toString();
                    Log.d(TAG, "usernamePlayer1: " + usernamePlayer1);
                    tvUsernamePlayer1.setText(usernamePlayer1);
                    if (usernamePlayer1.equals("")) {
                        alertPlayer2ToLeave();
                    }
                }
                if (snapshot.child("player2").getValue() != null) {
                    usernamePlayer2 = snapshot.child("player2").getValue().toString();
                    Log.d(TAG, "usernamePlayer2: " + usernamePlayer2);
                    tvUsernamePlayer2.setText(usernamePlayer2);
                }

                // set imageview ready data
                if (snapshot.child("player1ready").getValue() != null) {
                    if (snapshot.child("player1ready").getValue().toString().equals("false")) {
                        player1Ready = false;
                        ivReadyPlayer1.setVisibility(View.INVISIBLE);
                    } else {
                        player1Ready = true;
                        ivReadyPlayer1.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child("player2ready").getValue() != null) {
                    if (snapshot.child("player2ready").getValue().toString().equals("false")) {
                        player2Ready = false;
                        ivReadyPlayer2.setVisibility(View.INVISIBLE);
                    } else {
                        player2Ready = true;
                        ivReadyPlayer2.setVisibility(View.VISIBLE);
                    }
                }

                // set button start visibility
                if (role.equals("host") && player1Ready && player2Ready){
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }

                // set button start onClick event
                if (snapshot.child("start").getValue() != null) {
                    if (snapshot.child("start").getValue().toString().equals("true")) {
                        Intent intent = new Intent(BattleReadyActivity.this, BattleMatchActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("role", role);
                        intent.putExtra("roomName", roomName);
                        intent.putExtra("usernamePlayer1", usernamePlayer1);
                        intent.putExtra("usernamePlayer2", usernamePlayer2);
                        startActivity(intent);
                        database.getReference("rooms/" + roomName + "/start").removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removePlayer1() {
        // player 1 exit
        roomRef = database.getReference("rooms/" + roomName + "/player1");
        roomRef.setValue("");
        roomRef = database.getReference("rooms/" + roomName + "/player1ready");
        roomRef.setValue("false");
        database.getReference("rooms/" + roomName).removeValue();
    }

    private void removePlayer2() {
        // player 2 exit
        roomRef = database.getReference("rooms/" + roomName + "/player2");
        roomRef.setValue("");
        roomRef = database.getReference("rooms/" + roomName + "/player2ready");
        roomRef.setValue("false");
    }

    private void alertPlayer2ToLeave() {
        if (role.equals("guest")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Player 1 leaves")
                    .setMessage("The host leaves the room.")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (!BattleReadyActivity.this.isFinishing()) {
                                finish();
                            }
                        }
                    })
                    .create();
            if (!BattleReadyActivity.this.isFinishing()) {
                alertDialog.show();
            }
        }
    }

}