package com.teamj.joseguaman.hitchusv2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.teamj.joseguaman.hitchusv2.adapters.PagerAdapter;
import com.teamj.joseguaman.hitchusv2.constantes.Constants;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;
import com.teamj.joseguaman.hitchusv2.utils.CommonUtils;
import com.teamj.joseguaman.hitchusv2.webservices.WSHitchUs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasSavedPreferences()) {
            logInWithPreferences();
        }
        initialize();
    }

    public void logInWithPreferences() {
        String userLogged = prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, PreferencesFile.$_VALUE_EMAIL);
        String passwordLogged = prefs.getString(PreferencesFile.$_PREFERENCE_PASSWORD, PreferencesFile.$_VALUE_PASSWORD);
        new LoginOkHttpAsyncTask().execute(userLogged, passwordLogged);
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Principal").setIcon(R.drawable.ic_group));
        tabLayout.addTab(tabLayout.newTab().setText("Mensajes"));
        tabLayout.addTab(tabLayout.newTab().setText("Configuración"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    public class LoginOkHttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;

        @Override
        protected String doInBackground(String... params) {
            String resp = null;
            if (CommonUtils.isNetworkAvailable(MainActivity.this)) {
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
                        //estan guardadas las preferencias anteriores por eso no se modifican
                        //aun que se puede solo poner == 0 y elimnar el caso contrario
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Hitch Us");
                        builder.setMessage("AL parecer sus credenciales cambiaron, vuelva a ingresar.");
                        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.commit();
                                dialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog mAlertDialog = builder.create();
                        mAlertDialog.show();
                    }


                } else {
                    CommonUtils.showSimpleMessageDialog(MainActivity.this, "Revise su conexión a internet.");
                }
            }
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Iniciando Sesión...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            super.onPreExecute();
        }


    }
}
