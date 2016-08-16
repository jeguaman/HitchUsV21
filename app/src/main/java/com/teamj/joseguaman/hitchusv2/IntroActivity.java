package com.teamj.joseguaman.hitchusv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;
    private SharedPreferences prefs;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
/*
        initializeSettings();
        if (!validateUserLogger()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

*/
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Principal", "Podrás generar encuentros de manera rápida", R.drawable.ic_phonelink_ring);
        OnboarderPage onboarderPage2 = new OnboarderPage("Mensajes", "Podrás revisar tus mensajes", R.drawable.ic_chat);
        OnboarderPage onboarderPage3 = new OnboarderPage("Configuración", "Podrás configurar tus encuentros y preferencias", R.drawable.ic_build);

        onboarderPage1.setTitleColor(R.color.colorPrimary);
        onboarderPage1.setDescriptionColor(R.color.colorAccent);
        onboarderPage1.setBackgroundColor(R.color.colorTileSecond);

        onboarderPage2.setTitleColor(R.color.colorPrimary);
        onboarderPage2.setDescriptionColor(R.color.colorAccent);
        onboarderPage2.setBackgroundColor(R.color.white);


        onboarderPage3.setTitleColor(R.color.colorPrimary);
        onboarderPage3.setDescriptionColor(R.color.colorAccent);
        onboarderPage3.setBackgroundColor(R.color.colorTileSecond);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

    }

    /*
    private void initializeSettings() {
        prefs = getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);
        email = prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, PreferencesFile.$_VALUE_EMAIL);
        password = prefs.getString(PreferencesFile.$_PREFERENCE_PASSWORD, PreferencesFile.$_VALUE_PASSWORD);
    }

    private Boolean validateUserLogger() {
        Boolean success = false;
        if (email.equals(PreferencesFile.$_VALUE_EMAIL) && password.equals(PreferencesFile.$_VALUE_PASSWORD)) {
            success = true;
        }
        return success;
    }
*/
    @Override
    public void onSkipButtonPressed() {
        // Optional: by default it skips onboarder to the end
        super.onSkipButtonPressed();
        // Define your actions when the user press 'Skip' button


        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFinishButtonPressed() {
        // Define your actions when the user press 'Finish' button
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


}
