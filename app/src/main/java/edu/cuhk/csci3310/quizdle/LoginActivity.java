package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.cuhk.csci3310.quizdle.model.User;
import edu.cuhk.csci3310.quizdle.util.UserUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SignInButton signInButton;

    private String email = "";
    private static final String TAG = "LoginActivity";

    public static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        // initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // Google SignInButton: Click to begin Google SignIn
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void checkUser() {
        // if user is already signed in then go to MainActivity directly
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG, "checkUser: Already logged in");
            // start MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        // begin Google SignIn
        Log.d(TAG, "onClick: begin Google SignIn");
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                // google sign in success, now auth with firebase
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e){
                // failed google sign in
                Log.d(TAG, "onActivityResult: "+e.getMessage());
            }
        }

    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // login success
                        Log.d(TAG, "onSuccess: Logged In");

                        // get logged in user
                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        // get user info
                        String uid = firebaseUser.getUid();
                        email = firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: Email: " + email);
                        Log.d(TAG, "onSuccess: UID: " + uid);

                        // check if user is new or existing
                        if (authResult.getAdditionalUserInfo().isNewUser()){
                            // user is new - Account Created
                            Log.d(TAG, "onSuccess: Account Created...\n" + email);
                            createAccount();
                            Toast.makeText(LoginActivity.this, "Account Created...\n" + email, Toast.LENGTH_SHORT).show();
                        } else {
                            // existing user - Logged In
                            Log.d(TAG, "onSuccess: Existing user...\n" + email);
                            Toast.makeText(LoginActivity.this, "Existing user...\n" + email, Toast.LENGTH_SHORT).show();
                        }

                        // start MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // login failed
                        Log.d(TAG, "onFailure: Log in falied" + e.getMessage());
                    }
                });
    }

    private void createAccount(){
        // create "User" data set in firebase
        CollectionReference newUser = mFirestore.collection("users");
        User user = UserUtil.createNewUser(email);
        newUser.add(user)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.d(TAG, "New user successfully added on Firestore!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding new user", e);
                    }
                });
    }
}
