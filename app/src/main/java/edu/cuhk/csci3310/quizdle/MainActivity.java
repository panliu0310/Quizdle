package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "HomePage";

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    LevelFragment levelFragment = new LevelFragment();
    BattleFragment battleFragment = new BattleFragment();
    DiscussFragment discussFragment = new DiscussFragment();

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.level:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, levelFragment).commit();
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