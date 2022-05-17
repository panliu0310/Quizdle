package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";
    private static final int PICK_IMAGE_REQUEST = 1;

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SinglePlayerFragment singlePlayerFragment = new SinglePlayerFragment();
    BattleFragment battleFragment = new BattleFragment();
    SettingFragment settingFragment = new SettingFragment();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    private Uri mImageUri;
    private String username;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();

        setToolbar();

        setBottomNavigationView();
    }

    private void setToolbar() {
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quizdle");
        // using toolbar as ActionBar
        /*setSupportActionBar(toolbar);
        ImageButton ibSetting = (ImageButton) findViewById(R.id.ib_setting);
        ibSetting.setImageResource(R.drawable.settings);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                // start SettingActivity
                startSettingActivity();
            }
        });*/
    }

    private void setBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finishAffinity();
                            }
                        })
                .create().show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.singlePlayer:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, singlePlayerFragment).commit();
                return true;

            case R.id.battle:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, battleFragment).commit();
                return true;

            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingFragment).commit();
                return true;
        }
        return false;
    }

    private void checkUser() {
        // get current user
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            // user not logged in
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            // set email to fetch data
            email = firebaseUser.getEmail();
        }
    }

    private void startSettingActivity() {
        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                username = String.valueOf(document.getData().get("username"));
                            }
                            Intent intent = new Intent(MainActivity.this, SettingFragment.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}