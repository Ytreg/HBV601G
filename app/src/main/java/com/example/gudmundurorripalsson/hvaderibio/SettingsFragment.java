package com.example.gudmundurorripalsson.hvaderibio;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class SettingsFragment extends Fragment {

    private View mView;
    private ToggleButton themeButton;
    private boolean lightTheme = true;

    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_settings, container, false);

        System.out.println("theme " + lightTheme);
        themeButton = mView.findViewById(R.id.nightModeButton);

        themeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("checked " + isChecked);
                   getActivity().setTheme(!isChecked ? R.style.LightTheme : R.style.DarkTheme);
                System.out.println("kappa " + getActivity().getTheme().toString());
            }
        });


        return mView;
    }


}
