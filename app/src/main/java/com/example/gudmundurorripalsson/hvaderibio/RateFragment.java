package com.example.gudmundurorripalsson.hvaderibio;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

/**
 * Created by Ómar on 04/04/2018.
 */
public class RateFragment extends Fragment {

    private View mView;
    private RadioGroup radiogroup1;
    RadioButton checkedButton;
    private int score;
    private int movieID;
    private String arg;
    String username;
    String poster;
    ImageView movieposter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference moviesRef = database.getReference("Movies");
    DatabaseReference usersRef = database.getReference("Users");


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
        mView = inflater.inflate(R.layout.fragment_rate, container, false);
        arg = getArguments().getString("movie");
        setupUIViews();


        Button rateButton = mView.findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkedButton = (RadioButton)mView.findViewById(radiogroup1.getCheckedRadioButtonId());
                if(checkedButton != null) {
                    score = Integer.parseInt(checkedButton.getText().toString());
                    String comment = "Great movie";
                    Review r = new Review(score, comment);
                    moviesRef.child(Integer.toString(movieID)).child(username).child("score").setValue(score);
                    usersRef.child(username).child(Integer.toString(movieID)).child("score").setValue(score);

                    getActivity().onBackPressed();
                }
                else{
                    Toast.makeText(getContext(), "Vinsamlegast veljið einkunn",
                            Toast.LENGTH_SHORT).show();
                }
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
