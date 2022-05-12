package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.cuhk.csci3310.quizdle.adapter.CategoryListAdapter;
import edu.cuhk.csci3310.quizdle.adapter.SubCategoryListAdapter;

public class SubCategorySelectActivity extends AppCompatActivity {

    final String TAG = "SubCategory";
    private String category;
    private ArrayList<String> subCategory;
    private RecyclerView mRecyclerView;
    private SubCategoryListAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_select);

        Intent intent = getIntent();
        category = intent.getStringExtra(CategoryListAdapter.CategoryViewHolder.CATEGORY);
        subCategory = intent.getStringArrayListExtra(CategoryListAdapter.CategoryViewHolder.SUBCATEGORY);

        mAdapter = new SubCategoryListAdapter(this, category, subCategory);
        mRecyclerView = findViewById(R.id.rv_subcategory);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setToolbar(category);

        Log.d(TAG, "create " + category);
        Log.d(TAG, subCategory.toString());
    }

    private void setToolbar(String cate){
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Question Set - " + cate);
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
