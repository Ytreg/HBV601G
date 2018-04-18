package com.example.gudmundurorripalsson.hvaderibio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Ómar on 24/03/2018.
 */

/**
 * SettingsFragment birtist þegar notandi velur "Settings" í Bottom Navigation. Í Settings getur
 * notandi valið Dark mode theme fyrir appið og valið hvaða myndir sjást á forsíðunni með tilliti
 * til imdb einkunnar og í hvaða bíóhúsum myndir eru sýndar.
 */

public class SettingsFragment extends Fragment {

    private View mView;
    private Switch themeButton;
    private SeekBar imdbRatingSeekbar;

    private CheckBox[] checkBoxes;

    private int imdbRating;
    private Boolean[] cinemas = new Boolean[10];
    private Filter filter = new Filter();

    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        setupUIViews();
        getCheckedBoxes();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString("nightMode", "false");
        if (theme.equals("true")) {
            themeButton.setChecked(true);
        }


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeButton.setChecked(true);
        }

        themeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putString("nightMode", "true");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putString("nightMode", "false");
                }
                editor.apply();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        imdbRatingSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 0) {
                    seekBar.setProgress(1);
                    i = 1;
                }
                imdbRating = i;
                filter.setImdbRating(imdbRating);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        for(int i = 0; i < checkBoxes.length; i++){
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    getCheckedBoxes();
                }
            });
        }

        return mView;
    }


    private void getCheckedBoxes(){
        for(int i = 0; i < checkBoxes.length; i++) {
            cinemas[i] = checkBoxes[i].isChecked();
        }
        filter.setCinemas(cinemas);
    }

    private void setupUIViews() {
        themeButton = (Switch) mView.findViewById(R.id.themeButton);
        imdbRatingSeekbar = (SeekBar) mView.findViewById(R.id.seekBarIMDbRating);

        checkBoxes = new CheckBox[10];
        checkBoxes[0] = (CheckBox) mView.findViewById(R.id.checkBoxAlfabakki);
        checkBoxes[1] = (CheckBox) mView.findViewById(R.id.checkBoxBioParadis);
        checkBoxes[2] = (CheckBox) mView.findViewById(R.id.checkBoxBorgarbio);
        checkBoxes[3] = (CheckBox) mView.findViewById(R.id.checkBoxEgilsholl);
        checkBoxes[4] = (CheckBox) mView.findViewById(R.id.checkBoxHaskolabio);
        checkBoxes[5] = (CheckBox) mView.findViewById(R.id.checkBoxKringlubio);
        checkBoxes[6] = (CheckBox) mView.findViewById(R.id.checkBoxLaugarasbio);
        checkBoxes[7] = (CheckBox) mView.findViewById(R.id.checkBoxAkureyri);
        checkBoxes[8] = (CheckBox) mView.findViewById(R.id.checkBoxKeflavik);
        checkBoxes[9] = (CheckBox) mView.findViewById(R.id.checkBoxSmarabio);

    }


    public Filter getFilter() {
        return filter;
    }

}
