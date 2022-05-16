package edu.cuhk.csci3310.quizdle;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.cuhk.csci3310.quizdle.model.Category;
import edu.cuhk.csci3310.quizdle.model.Question;


public class EndlessQuestionActivity extends AppCompatActivity {

    public String TAG = "EndlessQuestionActivity";

    private FirebaseFirestore mFirestore;

    private List<Category> categorySet;

    private String category;
    private String questionSetName;
    public List<Question> questionSet = new ArrayList<>();
    Random ran = new Random();
    private int questionNum = 0;
    private int score = 0;
    private int correctAns;

    TextView tvScore;
    TextView tvQuestion;
    Button btnA, btnB, btnC, btnD, btnNextQuestion;
    Button[] buttonList;
    TextView tvExplanation;
    Toolbar toolbar;
    ImageView ivHeart1, ivHeart2, ivHeart3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_question);

        tvScore = findViewById(R.id.tv_score);
        tvQuestion = findViewById(R.id.tv_question);
        tvExplanation = findViewById(R.id.tv_explanation);
        btnA = findViewById(R.id.btn_choice_a);
        btnB = findViewById(R.id.btn_choice_b);
        btnC = findViewById(R.id.btn_choice_c);
        btnD = findViewById(R.id.btn_choice_d);
        btnNextQuestion = findViewById(R.id.btn_next_question);
        buttonList = new Button[]{btnA, btnB, btnC, btnD};
        toolbar = findViewById(R.id.toolbar);
        ivHeart1 = findViewById(R.id.iv_heart1);
        ivHeart2 = findViewById(R.id.iv_heart2);
        ivHeart3 = findViewById(R.id.iv_heart3);

        mFirestore = FirebaseFirestore.getInstance();

        // get questions from different categories (except category: "Customize")
        Query categoryQuery = mFirestore.collection("questions");
        categoryQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                        categorySet = task.getResult().toObjects(Category.class);   // get all categories
                        int i = 1;
                        for (Category cat : categorySet){
                            String categoryName = cat.getName();
                            Log.d(TAG, categoryName);
                            if(!categoryName.equals("Customize")) {
                                int j = 1;

                                // get questions
                                for (String subCategory : cat.getSubCategory()) {
                                    Query questionQuery = mFirestore.collection("questions")
                                            .document(categoryName).collection(subCategory);
                                    int finalI = i;
                                    int finalJ = j;
                                    questionQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            Log.d(TAG, subCategory);
                                            if (task.getResult() != null){
                                                questionSet.addAll(task.getResult().toObjects(Question.class));
                                                Log.d(TAG, questionSet.get(questionSet.size()-1).getQuestion());
                                                if((finalI == categorySet.size()) && (finalJ == cat.getSubCategory().size())){
                                                    Log.d(TAG, "get all questions");
                                                }
                                            }
                                        }
                                    });
                                    j++;
                                }

                            }
                            i++;
                        }
                }else {
                    Log.d(TAG, "Error getting categories: ", task.getException());
                }
            }


        });
    }

    private void updateQuestion(){

    }

}
