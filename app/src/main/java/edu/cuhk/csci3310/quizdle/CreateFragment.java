package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CreateFragment extends Fragment {

    private String TAG = "CreateFragment";

    private String email = "";
    private String username = "";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance() {
        return new CreateFragment();
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
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        Button btnCreate = view.findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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