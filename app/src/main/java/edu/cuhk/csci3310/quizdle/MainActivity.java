package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private boolean linkedAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (linkedAccount) {
            setContentView(R.layout.activity_main);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.setSelectedItemId(R.id.home);
        }else{
            setContentView(R.layout.activity_login);
        }

    }

    HomeFragment homeFragment = new HomeFragment();
    LevelFragment levelFragment = new LevelFragment();
    BattleFragment battleFragment = new BattleFragment();
    DiscussFragment discussFragment = new DiscussFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("item.getItemId", String.valueOf(item.getItemId()));
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