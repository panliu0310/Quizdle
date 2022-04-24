package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";
    private static final int PICK_IMAGE_REQUEST = 1;

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SinglePlayerFragment singlePlayerFragment = new SinglePlayerFragment();
    BattleFragment battleFragment = new BattleFragment();
    DiscussFragment discussFragment = new DiscussFragment();

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
                && data != null && data.getData() != null){
            mImageUri = data.getData();


        }

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

            case R.id.discuss:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, discussFragment).commit();
                return true;
        }
        return false;
    }

}