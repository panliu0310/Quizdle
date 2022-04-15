package edu.cuhk.csci3310.quizdle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(0);
    }

    HomeFragment homeFragment = new HomeFragment();
    LevelFragment levelFragment = new LevelFragment();
    BattleFragment battleFragment = new BattleFragment();
    DiscussFragment discussFragment = new DiscussFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, levelFragment).commit();
                return true;

            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, battleFragment).commit();
                return true;

            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, discussFragment).commit();
                return true;
        }
        return false;
    }

}