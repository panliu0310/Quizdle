package edu.cuhk.csci3310.quizdle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.LinkedList;

import edu.cuhk.csci3310.quizdle.adapter.CategoryListAdapter;
import edu.cuhk.csci3310.quizdle.model.Category;

public class QuestionSetActivity extends AppCompatActivity {

    private final String TAG = "QuestionSetActivity";
    private CategoryListAdapter mAdapter;
    private FirebaseFirestore mFirestore;

    final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.quizdle/drawable/";
    private RecyclerView mFirestoreList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set);

        setToolbar();

        mFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.rv_category);

        //Query
        Query query = mFirestore.collection("questions");
        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        Log.d(TAG, options.toString());
        mAdapter = new CategoryListAdapter(options);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(mAdapter);

        Log.d(TAG, "QuestionSetActivity");


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
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
