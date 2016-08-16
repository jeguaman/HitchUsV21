package com.teamj.joseguaman.hitchusv2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.teamj.joseguaman.hitchusv2.constantes.Constants;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;

import io.apptik.widget.MultiSlider;

public class ConfigurationActivity extends AppCompatActivity {


    private MultiSlider slider_edad;
    private MultiSlider slider_hitch;
    private MultiSlider slider_distancia;
    private TextView valorEdadInicial;
    private TextView valorEdadFinal;
    private TextView valorHitch;
    private TextView valorDistancia;

    private CheckBox cbMasculino;
    private CheckBox cbFemenino;
    private CheckBox cbOtro;
    private CheckBox cbTodos;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        slider_edad = (MultiSlider) findViewById(R.id.range_slider_edad);
        slider_hitch = (MultiSlider) findViewById(R.id.range_slider_hitch);
        slider_distancia = (MultiSlider) findViewById(R.id.range_slider_distancia);

        valorEdadInicial = (TextView) findViewById(R.id.txtValorEdadInicial);
        valorEdadFinal = (TextView) findViewById(R.id.txtValorEdadFinal);
        valorHitch = (TextView) findViewById(R.id.txtHitch);
        valorDistancia = (TextView) findViewById(R.id.txtDistancia);

        cbMasculino = (CheckBox) findViewById(R.id.checkbox_male);
        cbFemenino = (CheckBox) findViewById(R.id.checkbox_female);
        cbOtro = (CheckBox) findViewById(R.id.checkbox_other);
        cbTodos = (CheckBox) findViewById(R.id.checkbox_all);
        initializeSettings();
        slider_edad.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {

                SharedPreferences.Editor editor = prefs.edit();
                if (thumbIndex == 0) {
                    valorEdadInicial.setText(String.valueOf(value));
                    editor.putInt(PreferencesFile.$_PREFERENCE_AGE_START, value);
                } else {
                    valorEdadFinal.setText(String.valueOf(value));
                    editor.putInt(PreferencesFile.$_PREFERENCE_AGE_END, value);
                }
                editor.commit();
            }
        });

        slider_hitch.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                SharedPreferences.Editor editor = prefs.edit();
                if (thumbIndex == 0) {
                    editor.putInt(PreferencesFile.$_PREFERENCE_LEVEL_HITCH, value);
                    valorHitch.setText(String.valueOf(value));
                }
                editor.commit();
            }
        });

        slider_distancia.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                SharedPreferences.Editor editor = prefs.edit();
                if (thumbIndex == 0) {
                    editor.putInt(PreferencesFile.$_PREFERENCE_DISTANCE, value);
                    valorDistancia.setText(String.valueOf(value));
                }
                editor.commit();
            }
        });


        cbTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean activate = cbTodos.isChecked();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_FEMALE, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_MALE, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_OTHER, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_ALL, activate);
                editor.commit();

                cbMasculino.setChecked(activate);
                cbFemenino.setChecked(activate);
                cbOtro.setChecked(activate);
            }
        });

        cbMasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean activate = cbMasculino.isChecked();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_MALE, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_ALL, activate);
                editor.commit();
                cbTodos.setChecked(false);
            }
        });
        cbFemenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean activate = cbFemenino.isChecked();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_FEMALE, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_ALL, activate);
                editor.commit();
                cbTodos.setChecked(false);
            }
        });
        cbOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean activate = cbOtro.isChecked();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_OTHER, activate);
                editor.putBoolean(PreferencesFile.$_PREFERENCE_CHECK_ALL, activate);
                editor.commit();
                cbTodos.setChecked(false);
            }
        });
    }

    private void initializeSettings() {
        prefs = getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);
        cbFemenino.setChecked(prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_FEMALE, Boolean.TRUE));
        cbMasculino.setChecked(prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_MALE, Boolean.TRUE));
        cbOtro.setChecked(prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_OTHER, Boolean.FALSE));
        cbTodos.setChecked(prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_ALL, Boolean.FALSE));

        slider_edad.getThumb(0).setValue(prefs.getInt(PreferencesFile.$_PREFERENCE_AGE_START, PreferencesFile.$_VALUE_START));
        slider_edad.getThumb(1).setValue(prefs.getInt(PreferencesFile.$_PREFERENCE_AGE_END, PreferencesFile.$_VALUE_END));

        valorEdadInicial.setText(prefs.getInt(PreferencesFile.$_PREFERENCE_AGE_START, PreferencesFile.$_VALUE_START) + "");
        valorEdadFinal.setText(prefs.getInt(PreferencesFile.$_PREFERENCE_AGE_END, PreferencesFile.$_VALUE_END) + "");

        slider_hitch.getThumb(0).setValue(prefs.getInt(PreferencesFile.$_PREFERENCE_LEVEL_HITCH, PreferencesFile.$_VALUE_LEVEL_HITCH));
        valorHitch.setText(prefs.getInt(PreferencesFile.$_PREFERENCE_LEVEL_HITCH, PreferencesFile.$_VALUE_LEVEL_HITCH) + "");

        slider_distancia.getThumb(0).setValue(prefs.getInt(PreferencesFile.$_PREFERENCE_DISTANCE, PreferencesFile.$_VALUE_DISTANCE));
        valorDistancia.setText(prefs.getInt(PreferencesFile.$_PREFERENCE_DISTANCE, PreferencesFile.$_VALUE_DISTANCE) + "");

    }


}
