package com.teamj.joseguaman.hitchusv2;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamj.joseguaman.hitchusv2.constantes.Constants;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;
import com.teamj.joseguaman.hitchusv2.utils.CommonUtils;
import com.teamj.joseguaman.hitchusv2.webservices.WSHitchUs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });

        startPreference();
        /*
        if (hasSavedPreferences()) {
            logInWithPreferences();
        } else {
            initialize();
        }*/
    }

    private void startPreference() {
        prefs = getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);
    }

    private void validarUsuario() {
        try {
            if (mEmailView.getText().toString().isEmpty() || mPasswordView.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ingrese información antes de continuar", Toast.LENGTH_LONG).show();
            } else {
                new LoginOkHttpAsyncTask().execute(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        } catch (Exception ex) {
            Log.e("Error", "error al traer " + Log.getStackTraceString(ex));
            ex.printStackTrace();
        }
    }

    /*
    public void logInWithPreferences() {
        final String userLogged = prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, PreferencesFile.$_VALUE_EMAIL);
        final String passwordLogged = prefs.getString(PreferencesFile.$_PREFERENCE_PASSWORD, PreferencesFile.$_VALUE_PASSWORD);
        new LoginOkHttpAsyncTask().execute(userLogged, passwordLogged);
    }
*/
    public class LoginOkHttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;

        @Override
        protected String doInBackground(String... params) {
            String resp = null;
            if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                try {
                    //resp = Constants.$PATH_PERFIL_USUARIO;
                    resp = WSHitchUs.signInUser(params[0], params[1]);
                    //} catch (IOException e) {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                resp = "00";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String response) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (response != null) {
                if (response.compareTo("00") != 0) {
                    if (response.compareTo("false") != 0) {
                        JSONObject mainObject = null;
                        String nickName = null;
                        String media = null;
                        System.out.println("Response " + response);
                        try {
                            //JSONObject uniObject = mainObject.getJSONObject("perfil");
                            mainObject = new JSONObject(response);
                            nickName = mainObject.getString("nickname");
                            JSONArray objImgs = mainObject.getJSONArray("imagenes");

                            //Iterator<String> iter = objImgs.;
                            System.out.println("objImgs: " + objImgs);

                            for (int i = 0; i < objImgs.length(); i++) {
                                if (objImgs.getJSONObject(i).getBoolean("perfil")) {
                                    media = objImgs.getJSONObject(i).getString("url");
                                } else {
                                    media = Constants.$URL + Constants.$PATH_IMAGES + PreferencesFile.$_VALUE_IMAGE_PROFILE;
                                }
                                System.out.println("La Img es : " + media);
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(PreferencesFile.$_PREFERENCE_EMAIL, mEmailView.getText().toString());
                        editor.putString(PreferencesFile.$_PREFERENCE_PASSWORD, mPasswordView.getText().toString());
                        editor.putString(PreferencesFile.$_PREFERENCE_NICK_NAME, nickName);
                        editor.putString(PreferencesFile.$_PREFERENCE_IMAGE_PROFILE, media);
                        editor.commit();
                        if (prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, "").compareTo(PreferencesFile.$_VALUE_EMAIL) == 0) {
                            Intent intent = new Intent(LoginActivity.this, ConfigurationActivity.class);
                            startActivity(intent);
                        } else {
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Hitch Us");
                        builder.setMessage("Sus credenciales son incorrectas.");
                        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog mAlertDialog = builder.create();
                        mAlertDialog.show();
                    }


                } else {
                    CommonUtils.showSimpleMessageDialog(LoginActivity.this, "Revise su conexión a internet.");
                }
            }
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Iniciando Sesión...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            super.onPreExecute();
        }
    }

    public Boolean hasSavedPreferences() {
        prefs = getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);

        String userLogged = "";
        String passwordLogged = "";

        // Restore or Initialize preferences
        userLogged = prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, PreferencesFile.$_VALUE_EMAIL);
        passwordLogged = prefs.getString(PreferencesFile.$_PREFERENCE_PASSWORD, PreferencesFile.$_VALUE_PASSWORD);
        if (userLogged.equals(PreferencesFile.$_VALUE_EMAIL) && passwordLogged.equals(PreferencesFile.$_VALUE_PASSWORD)) {
            return false;
        } else {
            return true;
        }
    }

}
