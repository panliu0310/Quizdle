package edu.cuhk.csci3310.quizdle.dialogfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import edu.cuhk.csci3310.quizdle.R;

public class ModifyUsernameDialogFragment extends DialogFragment {

    String TAG = "ModifyUsernameDialogFragment";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    String username;
    String email;

    public static ModifyUsernameDialogFragment newInstance(String username) {
        ModifyUsernameDialogFragment fragment = new ModifyUsernameDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("username");

        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // get current user
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_username_dialog, container, false);
        EditText etUsername = view.findViewById(R.id.et_username);
        etUsername.setText(username);
        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUsername(String.valueOf(etUsername.getText()));
            }
        });
        return view;
    }

    private void validateUsername(String username){
        // before modify username, first check validity
        if (username.length() <= 5 || username.length() >= 31) {
            // check username length
            Log.d(TAG, "username length invalid");
            this.dismiss();
        }

        // check existence of the username in database
        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "task success");
                            if (task.getResult().isEmpty()){
                                // no such username, so the user can use this username
                                Log.d(TAG, "no such username exist, the username can be used");
                                updateUsername(username);
                            } else {
                                Log.d(TAG, "username exist");
                                dismiss();
                            }
                        }
                    }
                });
    }

    private void updateUsername(String username){
        // create a HashMap to store username
        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("username", username);

        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            mFirestore.collection("users")
                                    .document(documentId)
                                    .update(userDetail)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "successfully update username");
                                            dismiss();
                                        }
                                    });
                        }
                    }
                });


    }
}
