package com.example.gudmundurorripalsson.hvaderibio;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFragment extends Fragment implements
        View.OnClickListener {


    private static final String TAG = "UserFragment";
    private EditText username, email, password, password2;
    private TextView signupRedirection, welcome, info;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Boolean loginState = true;
    private String viewState = "login";
    private View mView;
    private int textColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        setupUIViews();
        updateUI(user);

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
        signupRedirection.setText(getString(R.string.new_user_sign_up));
        loginButton.setText(R.string.lOGIN);
        welcome.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        signupRedirection.setVisibility(View.VISIBLE);
        viewState = "login";
    }

    private void setSignupView(){
        signupRedirection.setText(R.string.already_Signed_Up);
        loginButton.setText(R.string.sIGNUP);
        welcome.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        username.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        signupRedirection.setVisibility(View.VISIBLE);
        viewState = "signup";
    }

    private void setAccountView(FirebaseUser user){
        loginButton.setText(getString(R.string.sIGN_OUT));
        welcome.setText(getString(R.string.welcome, user.getDisplayName()));
        welcome.setVisibility(View.VISIBLE);
        info.setText(getString(R.string.info));
        info.setVisibility(View.VISIBLE);
        username.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        signupRedirection.setVisibility(View.INVISIBLE);
        viewState = "account";
    }

    private void setupUIViews() {
        textColor = Color.rgb(131,148,150);
        username = (EditText) mView.findViewById(R.id.username);
        username.setTextColor(textColor);
        email = (EditText) mView.findViewById(R.id.email);
        email.setTextColor(textColor);
        password = (EditText) mView.findViewById(R.id.password);
        password.setTextColor(textColor);
        password2 = (EditText) mView.findViewById(R.id.password2);
        password2.setTextColor(textColor);
        welcome = mView.findViewById(R.id.welcome);
        info = mView.findViewById(R.id.info);
        loginButton = mView.findViewById(R.id.loginButton);
        signupRedirection = mView.findViewById(R.id.signupRedirection);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
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


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        updateUI(null);
    }


    private void createAccount(final String email,final String password) {
        Log.d(TAG, "createAccount:" + email);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates);

                            signIn(email, password);

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
            System.out.println("welcome: " + userName + " " + userPassword);
            if(viewState == "login") {
                if(validateLogin()) {
                    signIn(userName, userPassword);
                    viewState = "account";
                }
            }
            else if(viewState == "signup"){
                if(validateSignup()) {
                    createAccount(userName, userPassword);
                    viewState = "account";
                }
            }
            else{
                signOut();
                setLoginView();
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
            Toast.makeText(getContext(), R.string.please_enter_all_the_details, Toast.LENGTH_LONG).show();
        } else if(!passwordString.equals(password2String)){
            Toast.makeText(getContext(), R.string.the_passwords_have_to_match, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), R.string.please_enter_all_the_details, Toast.LENGTH_LONG).show();
        } else{
            result = true;
        }
        return result;
    }

    public FirebaseUser getUser(){
        return user;
    }
    public void setUser(FirebaseUser user){
        this.user = user;
    }
}
