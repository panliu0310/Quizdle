package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BattleLobbyActivity extends AppCompatActivity {

    private String TAG = "BattleHobbyActivity";

    ListView lvRooms;
    Button btnCreateRoom;

    List<String> roomsList;

    String username = "";
    String roomName = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_lobby);
        setToolbar();

        database = FirebaseDatabase.getInstance();

        // get the player name
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("TAG", "Received username: " + username);
        // assign player's name to the room name
        roomName = username;

        lvRooms = findViewById(R.id.lv_rooms);
        btnCreateRoom = findViewById(R.id.btn_create_room);

        // later show all existing available rooms
        roomsList = new ArrayList<>();

        setButtonCreateRoomListener(username);

        setListViewRoomsListener(username);

        // show if new room is available
        // someone created rooms
        addRoomsEventListener();
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

    private void setButtonCreateRoomListener(String username){
        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnCreateRoom clicked");
                btnCreateRoom.setText("CREATING ROOM");
                btnCreateRoom.setEnabled(false);
                roomName = username;
                roomRef = database.getReference("rooms/" + roomName + "/player1");
                addRoomEventListener();
                roomRef.setValue(username);
            }
        });
    }

    private void setListViewRoomsListener(String username){
        lvRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "lvRooms clicked");
                roomName = roomsList.get(position);
                roomRef = database.getReference("rooms/" + roomName + "/player2");
                addRoomEventListener();
                roomRef.setValue(username);
            }
        });
    }

    // this listener will be triggered when user created room or join other room
    // which means when btnCreateRoom or lvRooms are being clicked
    private void addRoomEventListener(){
        Log.d(TAG, "addRoomEventListener being called");
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // join the room
                btnCreateRoom.setText("CREATE ROOM");
                btnCreateRoom.setEnabled(true);
                Intent intent = new Intent(BattleLobbyActivity.this, BattleMatchActivity.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                btnCreateRoom.setText("CREATE ROOM");
                btnCreateRoom.setEnabled(true);
            }
        });
    }

    private void addRoomsEventListener(){
        roomsRef = database.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // show list of rooms
                roomsList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for (DataSnapshot snapshot1 : rooms){
                    roomsList.add(snapshot1.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BattleLobbyActivity.this,
                            android.R.layout.simple_list_item_1, roomsList);
                    lvRooms.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // error
            }
        });
    }
}