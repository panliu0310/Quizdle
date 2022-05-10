package edu.cuhk.csci3310.quizdle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

import edu.cuhk.csci3310.quizdle.CategoryListAdapter;
import edu.cuhk.csci3310.quizdle.model.Category;

public class QuestionSetActivity extends AppCompatActivity {

    private final String TAG = "QuestionSetActivity";
    private LinkedList<String> category = new LinkedList<String>();
    private RecyclerView mRecyclerView;
    private CategoryListAdapter mAdapter;
    private FirebaseFirestore mFirestore;

    final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.quizdle/drawable/";
    private RecyclerView mFirestoreList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set);

        setToolbar();

        /*mFirestore = FirebaseFirestore.getInstance();
        mRecyclerView.setAdapter(mAdapter);

        //Query
        Query query = mFirestore.collection("questions");
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<>();


        // get question categories from database
        /*CollectionReference questionSetRef = mFirestore.collection("questions");
        questionSetRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                category.add(document.getId());
                                Log.d(TAG, document.getId());
                            }
                        }else {
                            Log.d(TAG, "Error getting category documents: ", task.getException());
                        }
                    }
                });

        mAdapter = new CategoryListAdapter(this, category);
        mRecyclerView = findViewById(R.id.category_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        Log.d(TAG, "QuestionSetActivity");


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
