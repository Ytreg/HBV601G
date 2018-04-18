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


public class SettingsFragment extends Fragment {

    private View mView;
    private Switch themeButton;
    private SeekBar imdbRatingSeekbar;
    private Button ageLimit0, ageLimit12, ageLimit16;
    private ArrayList<CheckBox> checkBoxes;

    private int imdbRating, ageLimit;
    private ArrayList<Boolean> cinemas;
    private Filter filter;

    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        setupUIViews();

        filter = new Filter(imdbRating, ageLimit, cinemas);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeButton.setChecked(true);
        }
        setButtonBackgrounds(ageLimit, themeButton.isChecked());
        themeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

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
                filter = new Filter(imdbRating, ageLimit, cinemas);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ageLimit0.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ageLimit = 0;
                setButtonBackgrounds(ageLimit, themeButton.isChecked());
                filter = new Filter(imdbRating, ageLimit, cinemas);
            }
        });

        ageLimit12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ageLimit = 12;
                setButtonBackgrounds(ageLimit, themeButton.isChecked());
                filter = new Filter(imdbRating, ageLimit, cinemas);
            }
        });

        ageLimit16.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ageLimit = 16;
                setButtonBackgrounds(ageLimit, themeButton.isChecked());
                filter = new Filter(imdbRating, ageLimit, cinemas);
            }
        });

        /*for(int i = 0; i < checkBoxes.size(); i++){
            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    getCheckedBoxes();
                    filter = new Filter(imdbRating, ageLimit, cinemas);
                }
            });
        }*/

        return mView;
    }

    private void setButtonBackgrounds(int ageLimit, boolean theme){
        if(theme) {
            if(ageLimit == 0) {
                ageLimit0.setBackgroundColor(Color.DKGRAY);
                ageLimit12.setBackgroundResource(R.color.backgroundColorDark);
                ageLimit16.setBackgroundResource(R.color.backgroundColorDark);
            }
            else if(ageLimit == 12){
                ageLimit12.setBackgroundColor(Color.DKGRAY);
                ageLimit0.setBackgroundResource(R.color.backgroundColorDark);
                ageLimit16.setBackgroundResource(R.color.backgroundColorDark);
            }else if(ageLimit == 16){
                ageLimit16.setBackgroundColor(Color.DKGRAY);
                ageLimit12.setBackgroundResource(R.color.backgroundColorDark);
                ageLimit0.setBackgroundResource(R.color.backgroundColorDark);
            }
        }
        else{
            if(ageLimit == 0) {
                ageLimit0.setBackgroundColor(Color.GRAY);
                ageLimit12.setBackgroundResource(R.color.backgroundColorLight);
                ageLimit16.setBackgroundResource(R.color.backgroundColorLight);
            }
            else if(ageLimit == 12){
                ageLimit12.setBackgroundColor(Color.GRAY);
                ageLimit0.setBackgroundResource(R.color.backgroundColorLight);
                ageLimit16.setBackgroundResource(R.color.backgroundColorLight);
            }
            else if(ageLimit == 16){
                ageLimit16.setBackgroundColor(Color.GRAY);
                ageLimit12.setBackgroundResource(R.color.backgroundColorLight);
                ageLimit0.setBackgroundResource(R.color.backgroundColorLight);
            }
        }
    }

    private void getCheckedBoxes(){
        for(int i = 0; i < checkBoxes.size(); i++)
            cinemas.add(checkBoxes.get(i).isChecked());
    }

    private void setupUIViews() {
        themeButton = (Switch) mView.findViewById(R.id.themeButton);
        imdbRatingSeekbar = (SeekBar) mView.findViewById(R.id.seekBarIMDbRating);

        ageLimit0 = (Button) mView.findViewById(R.id.buttonAgeLimit0);
        ageLimit12 = (Button) mView.findViewById(R.id.buttonAgeLimit12);
        ageLimit16 = (Button) mView.findViewById(R.id.buttonAgeLimit16);

        System.out.println("check " + mView.findViewById(R.id.checkBoxAlfabakki));
        /*checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxBioParadis));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxBorgarbio));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxEgilsholl));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxHaskolabio));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxKringlubio));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxLaugarasbio));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxAkureyri));
        checkBoxes.add((CheckBox) mView.findViewById(R.id.checkBoxKeflavik));*/
    }


    public Filter getFilter() {
        return filter;
    }

}
