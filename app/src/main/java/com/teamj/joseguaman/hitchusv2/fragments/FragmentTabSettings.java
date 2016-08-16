package com.teamj.joseguaman.hitchusv2.fragments;

/**
 * Created by Jose Guaman on 14/08/2016.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.teamj.joseguaman.hitchusv2.ConfigurationActivity;
import com.teamj.joseguaman.hitchusv2.LoginActivity;
import com.teamj.joseguaman.hitchusv2.R;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class FragmentTabSettings extends Fragment {

    private Button btnConfig;
    private Button btnCerrarSesion;
    private SharedPreferences prefs;
    private EditText txtNickName;
    private EditText txtEdad;
    private EditText txtEmail;
    private ImageView imgPerfil;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment_settings, container, false);

        btnConfig = (Button) view.findViewById(R.id.btn_configuracion);
        btnCerrarSesion = (Button) view.findViewById(R.id.btn_cerrar_session);
        txtNickName = (EditText) view.findViewById(R.id.txt_nick_name);
        txtEdad = (EditText) view.findViewById(R.id.txt_edad);
        txtEmail = (EditText) view.findViewById(R.id.txt_email);
        imgPerfil = (ImageView) view.findViewById(R.id.logo);


        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfigurationActivity.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSession();
            }
        });

        initializeSettings();
        return view;
    }

    private void cerrarSession() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(getContext().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void initializeSettings() {
        prefs = getActivity().getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);
        txtNickName.setText(prefs.getString(PreferencesFile.$_PREFERENCE_NICK_NAME, ""));
        txtEmail.setText(prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, ""));
/*
        URL newurl = null;
        try {
            newurl = new URL(prefs.getString(PreferencesFile.$_PREFERENCE_IMAGE_PROFILE, PreferencesFile.$_VALUE_IMAGE_PROFILE));
            imgPerfil.setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        //imgPerfil.setImageResource(this.getResources().getIdentifier(prefs.getString(), null, null));
        System.out.println("IMG Frafgment " + prefs.getString(PreferencesFile.$_PREFERENCE_IMAGE_PROFILE, PreferencesFile.$_VALUE_IMAGE_PROFILE));
    }


}
