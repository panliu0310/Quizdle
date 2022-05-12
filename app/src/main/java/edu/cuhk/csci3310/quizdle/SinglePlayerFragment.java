package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SinglePlayerFragment extends Fragment {

    private static final String TAG = "singlePlayerFragment";

    private String email = "";
    private String username = "";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    public SinglePlayerFragment() {
        // Required empty public constructor
    }

    public static SinglePlayerFragment newInstance(String param1, String param2) {
        return new SinglePlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        email = firebaseUser.getEmail();

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();

        getUserUsername(email);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level, container, false);

        View question_set_mode = view.findViewById(R.id.qsm_iv);
        question_set_mode.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d(TAG,"Clicked Question Set Mode");
                 Intent intent = new Intent(getContext(), QuestionSetActivity.class);
                 startActivity(intent);
             }
         });

        View endless_mode = view.findViewById(R.id.em_iv);
        endless_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Chosen Endless Mode");
                // TODO: Link to endless mode
            }
        });

        View create_mode = view.findViewById(R.id.cm_iv);
        create_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Chosen Create Mode");
                Intent intent = new Intent(getContext(), CreateQuestionActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });


        return view;
    }

    private void getUserUsername(String email){
        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                username = String.valueOf(document.getData().get("username"));
                            }
                        }
                        Log.d(TAG, "username: " + username);
                    }
                });
    }

}