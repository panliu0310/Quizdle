package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import edu.cuhk.csci3310.quizdle.customview.ProfilePairView;

public class HomeFragment extends Fragment {

    private String email = "";
    private static final String TAG = "HomeFragment";
    private static final int PICK_IMAGE_REQUEST = 1;

    ProfilePairView ppvUsername;
    ProfilePairView ppvLevel;
    ProfilePairView ppvExperience;
    ProfilePairView ppvVictory;
    ProfilePairView ppvCoin;

    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseFirestore mFirestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso);

        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton ibIcon = view.findViewById(R.id.ib_icon);
        ibIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccess();
            }
        });
        ppvUsername = view.findViewById(R.id.ppv_username);
        ppvLevel = view.findViewById(R.id.ppv_level);
        ppvExperience = view.findViewById(R.id.ppv_experience);
        ppvVictory = view.findViewById(R.id.ppv_victory);
        ppvCoin = view.findViewById(R.id.ppv_coin);
        setField();
        return view;
    }

    private void checkUser(){
        // get current user
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            // user not logged in
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            // set email to fetch data
            email = firebaseUser.getEmail();
        }
    }

    // disconnect google account
    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "successfully sign out");
                        mFirebaseAuth.signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });
    }

    // pick image from device
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // set ppv content
    private void setField(){
        CollectionReference usersRef = mFirestore.collection("users");
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ppvUsername.setContent(String.valueOf(document.getData().get("username")));
                                ppvLevel.setContent(String.valueOf(document.getData().get("level")));
                                ppvExperience.setContent(
                                        String.valueOf(document.getData().get("experience")),
                                        String.valueOf(document.getData().get("upgradeRequired"))
                                );
                                ppvVictory.setContent(
                                        String.valueOf(document.getData().get("victory")),
                                        String.valueOf(document.getData().get("totalMatch"))
                                );
                                ppvCoin.setContent(String.valueOf(document.getData().get("coin")));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}