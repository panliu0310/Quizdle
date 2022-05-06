package edu.cuhk.csci3310.quizdle.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

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

    private FragmentActivity activity;

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
        activity = getActivity();

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
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_modify_username_dialog, null);
        EditText etUsername = view.findViewById(R.id.et_username);
        etUsername.setText(username);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        validateUsername(String.valueOf(etUsername.getText()));
                    }
                });
        return builder.create();
    }

    private void validateUsername(String username){
        // before modify username, first check validity
        if (username.length() <= 5 || username.length() >= 31) {
            // check username length
            Log.d(TAG, "username length invalid");
            this.dismiss();
            MessageDialogFragment fragment = MessageDialogFragment.newInstance("Username length invalid! Make sure the " +
                    "length of your name is between 6 to 30!");
            fragment.show(activity.getSupportFragmentManager(), TAG);
            return;
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
                                MessageDialogFragment fragment = MessageDialogFragment.newInstance("Username exists in database! " +
                                        "Please use another username!");
                                // cannot directly use getActivity().getSupportFragmentManager() since this is in an override method
                                fragment.show(activity.getSupportFragmentManager(), TAG);
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
                                            MessageDialogFragment fragment = MessageDialogFragment.newInstance("Success!");
                                            // cannot directly use getActivity().getSupportFragmentManager() since this is in an override method
                                            fragment.show(activity.getSupportFragmentManager(), TAG);
                                            dismiss();
                                        }
                                    });
                        }
                    }
                });
    }
}
