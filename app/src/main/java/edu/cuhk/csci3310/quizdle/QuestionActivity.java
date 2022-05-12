package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import edu.cuhk.csci3310.quizdle.adapter.CategoryListAdapter;
import edu.cuhk.csci3310.quizdle.adapter.SubCategoryListAdapter;

public class QuestionActivity extends AppCompatActivity {

    private String category;
    private String subCategory;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        category = intent.getStringExtra(SubCategoryListAdapter.SubCategoryViewHolder.CATEGORY);
        subCategory = intent.getStringExtra(SubCategoryListAdapter.SubCategoryViewHolder.SUBCATEGORY);
        setToolbar(category + " - " + subCategory + ": Question 1");


    }



    private void setToolbar(String title){
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        // using toolbar as ActionBar
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });*/
    }
}
