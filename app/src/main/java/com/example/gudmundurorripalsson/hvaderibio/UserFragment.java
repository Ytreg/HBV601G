package com.example.gudmundurorripalsson.hvaderibio;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class UserFragment extends Fragment implements
        View.OnClickListener {


    private static final String TAG = "UserFragment";
    private EditText username, email, password, password2;
    private TextView signupRedirection, info;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private Boolean loginState = true;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        setupUIViews();
        setLoginView();

        mView.findViewById(R.id.loginButton).setOnClickListener(this);
        mView.findViewById(R.id.signupRedirection).setOnClickListener(this);
        return mView;
    }



    private void updateUI(FirebaseUser user) {
        if (user != null) {
            setAccountView(user);
        } else {
            setLoginView();
        }
    }

    private void setLoginView(){
        signupRedirection.setText("New User? Sign Up");
        loginButton.setText("LOGIN");
        info.setVisibility(View.GONE);
        username.setVisibility(View.GONE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        signupRedirection.setVisibility(View.VISIBLE);
    }

    private void setSignupView(){
        signupRedirection.setText("Already Signed Up? Login");
        loginButton.setText("SIGN UP");
        info.setVisibility(View.GONE);
        username.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        signupRedirection.setVisibility(View.VISIBLE);
    }

    private void setAccountView(FirebaseUser user){
        info.setText("Welcome " + user.getEmail());
        info.setVisibility(View.VISIBLE);
        username.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        password2.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        signupRedirection.setVisibility(View.GONE);
    }

    private void setupUIViews() {
        username = (EditText) mView.findViewById(R.id.username);
        email = (EditText) mView.findViewById(R.id.email);
        password = (EditText) mView.findViewById(R.id.password);
        password2 = (EditText) mView.findViewById(R.id.password2);
        info = mView.findViewById(R.id.info);
        loginButton = mView.findViewById(R.id.loginButton);
        signupRedirection = mView.findViewById(R.id.signupRedirection);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        ;

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginButton) {
            String userName = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            System.out.println("info: " + userName + " " + userPassword);
            if(loginState) {
                if(validateLogin())
                    signIn(userName, userPassword);
            }
            else{
                if(validateSignup()) {
                    createAccount(userName, userPassword);
                }
            }
        }
        if (i == R.id.signupRedirection) {
            if(loginState) {
                setSignupView();
                loginState = false;
            }
            else{
                setLoginView();
                loginState = true;
            }

        }
    }

    private Boolean validateSignup(){
        Boolean result = false;
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String password2String = password2.getText().toString();
        String emailString = email.getText().toString();
        if(usernameString.isEmpty() || passwordString.isEmpty() || password2String.isEmpty() || emailString.isEmpty()){
            Toast.makeText(getContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
        } else if(!passwordString.equals(password2String)){
            Toast.makeText(getContext(), "The passwords have to match", Toast.LENGTH_SHORT).show();
        } else{
            result = true;
        }
        return result;
    }

    private Boolean validateLogin(){
        Boolean result = false;
        String username = email.getText().toString();
        String userPassword = password.getText().toString();
        if(username.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(getContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
        } else{
            result = true;
        }
        return result;
    }
}