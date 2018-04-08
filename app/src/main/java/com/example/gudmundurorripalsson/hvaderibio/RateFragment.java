package com.example.gudmundurorripalsson.hvaderibio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ómar on 04/04/2018.
 */
public class RateFragment extends Fragment {

    private View mView;
    private RadioGroup radiogroup1;
    RadioButton checkedButton;
    private int score;
    private int movieID;
    String username;
    String poster;
    ImageView movieposter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference moviesRef = database.getReference("Movies");


    public RateFragment() {

    }
    @SuppressLint("ValidFragment")
    public RateFragment(int movieID, String username, String poster) {
      this.movieID = movieID;
      this.username = username;
      this.poster = poster;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.rate, container, false);
        setupUIViews();


        Button rateButton = mView.findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkedButton = (RadioButton)mView.findViewById(radiogroup1.getCheckedRadioButtonId());
                score = Integer.parseInt(checkedButton.getText().toString());
                String comment = "Great movie";
                Review r = new Review(score, comment);
                moviesRef.child(Integer.toString(movieID)).child(username).child("score").setValue(score);
            }
        });


        return mView;
    }

    private void setupUIViews() {
        radiogroup1 = (RadioGroup) mView.findViewById(R.id.radiogroup1);
        movieposter = (ImageView) mView.findViewById(R.id.movieposter);
        Picasso.with(getContext()).load(poster).into(movieposter);
    }



}
