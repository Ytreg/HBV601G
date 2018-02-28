package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView loginAttempts;
    private Button loginButton;
    private int counter = 5;
    private TextView signupRedirection;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        loginAttempts = (TextView)findViewById(R.id.loginAttempts);
        loginButton = (Button)findViewById(R.id.loginButton);
        signupRedirection = (TextView)findViewById(R.id.signupRedirection);

        signupRedirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    String username = email.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();
                    Log.d(TAG, "pw " + userPassword);

                    signIn(username, userPassword);
                }
            }
        });
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);



        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });

    }

    private Boolean validate(){
        Boolean result = false;
        String username = email.getText().toString();
        String userPassword = password.getText().toString();
        if(username.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_LONG).show();
        } else{
            result = true;
        }
        return result;
    }

    private void updateUI(FirebaseUser user){
        Toast.makeText(LoginActivity.this, "Logged in.",
                Toast.LENGTH_SHORT).show();
    }
}


