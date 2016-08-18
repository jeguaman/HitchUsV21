package com.teamj.joseguaman.hitchusv2.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.teamj.joseguaman.hitchusv2.ConfigurationActivity;
import com.teamj.joseguaman.hitchusv2.MainActivity;
import com.teamj.joseguaman.hitchusv2.R;
import com.teamj.joseguaman.hitchusv2.clases.Usuario;
import com.teamj.joseguaman.hitchusv2.constantes.Constants;
import com.teamj.joseguaman.hitchusv2.constantes.PreferencesFile;
import com.teamj.joseguaman.hitchusv2.utils.CommonUtils;
import com.teamj.joseguaman.hitchusv2.utils.ContentUtils;
import com.teamj.joseguaman.hitchusv2.webservices.WSHitchUs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jose Guaman on 14/08/2016.
 */
public class FragmentTabPrincipal extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private FloatingActionButton fabDislike;
    private FloatingActionButton fabLike;
    private FloatingActionButton fabInfo;

    private EditText txtNombre;
    private EditText txtEdad;
    private EditText txtGenero;
    private EditText txtLugarOrigen;
    private EditText txtEstatura;
    private EditText txtContextura;
    private EditText txtEducacion;
    private EditText txtOcupacion;

    private TextView txtUserNickName;
    private TextView txtUserEdad;
    private TextView txtUserGenero;

    private String distancia;
    private String puntuacionTotal;

    private String jsonIndividual;
    //private String jsonListaGeneral;


    private JSONArray listaJsonObject;

    private SliderLayout mDemoSlider;
    private HashMap<String, String> url_maps;

    private SharedPreferences prefs;
    private List<Usuario> listaUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_principal, container, false);

        url_maps = new HashMap<String, String>();
        fabDislike = (FloatingActionButton) view.findViewById(R.id.fab_dislike);
        fabLike = (FloatingActionButton) view.findViewById(R.id.fab_like);
        fabInfo = (FloatingActionButton) view.findViewById(R.id.fab_info);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider_principal);
        txtUserNickName = (TextView) view.findViewById(R.id.txtUserNickName);
        txtUserEdad = (TextView) view.findViewById(R.id.txtUserEdad);
        txtUserGenero = (TextView) view.findViewById(R.id.txtUserGenero);
        loadSearchInitial();
/*
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        url_maps.put("Default", "http://hitchus.ddns.net:8000/media/profiles/default.jpg");
*/
        fabDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB dislike", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB like", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog;
                dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_information);
                dialog.setTitle("Información General");
                loadInformacionUserSelected(dialog);
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_regresar);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        if (!listaUsuarios.isEmpty()) {
            //posicion = 0;
            presentaacionSlider();
        }

        return view;
    }

    private void presentaacionSlider() {

        for (String name : listaUsuarios.get(posicion).getUrl_maps().keySet()) {
            //TextSliderView textSliderView = new TextSliderView(view.getContext());
            TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());
            // initialize a SliderLayout
            textSliderView.description(name).image(listaUsuarios.get(posicion).getUrl_maps().get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        txtUserNickName.setText(listaUsuarios.get(posicion).getPerfil().getNickName() + ", ");
        if (listaUsuarios.get(posicion).getPerfil().getAnioNacimiento().compareTo(0) != 0) {
            txtUserEdad.setText(edadPersona(listaUsuarios.get(posicion).getPerfil().getAnioNacimiento()) + ", ");
        } else {
            txtUserEdad.setText(",18+");
        }
        if (listaUsuarios.get(posicion).getPerfil().getGenero().compareTo("FEM") == 0) {
            txtUserGenero.setText("Femenino");
        } else if (listaUsuarios.get(posicion).getPerfil().getGenero().compareTo("MAS") == 0) {
            txtUserGenero.setText("Masculino");
        } else {
            txtUserGenero.setText("Otro");
        }
    }

    private void loadSearchInitial() {
        listaUsuarios = new ArrayList<>();
        prefs = getContext().getSharedPreferences(PreferencesFile.$SETTINGS_FILE_NAME, PreferencesFile.$MODE_PRIVATE);

        String genero = "ALL";
        if (prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_FEMALE, PreferencesFile.$_VALUE_ACTIVATE)) {
            genero = "FEM";
        } else if (prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_MALE, PreferencesFile.$_VALUE_ACTIVATE)) {
            genero = "MAS";
        } else if (prefs.getBoolean(PreferencesFile.$_PREFERENCE_CHECK_OTHER, PreferencesFile.$_VALUE_ACTIVATE)) {
            genero = "OTH";
        }
        //prefs.getString(PreferencesFile.$_PREFERENCE_NICK_NAME, PreferencesFile.$_VALUE_NICK_NAME);
        new SendConfigurationInitialPeticionHttpAsyncTask().execute(
                prefs.getString(PreferencesFile.$_PREFERENCE_NICK_NAME, PreferencesFile.$_VALUE_NICK_NAME),
                prefs.getString(PreferencesFile.$_PREFERENCE_EMAIL, PreferencesFile.$_VALUE_EMAIL),
                "MAS",
                prefs.getString(PreferencesFile.$_PREFERENCE_AGE_START, String.valueOf(PreferencesFile.$_VALUE_START)),
                prefs.getString(PreferencesFile.$_PREFERENCE_AGE_END, String.valueOf(PreferencesFile.$_VALUE_END)),
                "-0.3944",
                "-78.4911",
                new BigDecimal("1000").multiply(new BigDecimal(prefs.getString(PreferencesFile.$_PREFERENCE_DISTANCE, String.valueOf(PreferencesFile.$_VALUE_DISTANCE)))).toPlainString());
    }

    private int posicion;

    private void processJsonGeneral(String response) {
        listaUsuarios = ContentUtils.getListUsuarioJson(response);
        posicion = 0;

        for (Usuario user : listaUsuarios) {
            for (int i = 0; i < user.getPerfil().getListaImagenes().size(); i++) {
                user.getUrl_maps().put(user.getPerfil().getListaImagenes().get(i).getDescripcion(), Constants.$URL + Constants.$PATH_IMAGES + user.getPerfil().getListaImagenes().get(i).getUrl());
            }
        }
        presentaacionSlider();

    }

    private void loadInformacionUserSelected(Dialog dialog) {
        txtNombre = (EditText) dialog.findViewById(R.id.txt_nombre);
        txtNombre.setText(listaUsuarios.get(posicion).getPerfil().getNickName());
        txtEdad = (EditText) dialog.findViewById(R.id.txt_edad);
        txtEdad.setText(edadPersona(listaUsuarios.get(posicion).getPerfil().getAnioNacimiento()));
        txtGenero = (EditText) dialog.findViewById(R.id.txt_genero);
        txtGenero.setText(listaUsuarios.get(posicion).getPerfil().getGenero());
        txtLugarOrigen = (EditText) dialog.findViewById(R.id.txt_lugar_origen);
        txtLugarOrigen.setText(listaUsuarios.get(posicion).getPerfil().getPaisOrigen());
        txtEstatura = (EditText) dialog.findViewById(R.id.txt_estatura);
        txtEstatura.setText(String.valueOf(listaUsuarios.get(posicion).getPerfil().getEstatura()));
        txtContextura = (EditText) dialog.findViewById(R.id.txt_contextura);
        txtContextura.setText(listaUsuarios.get(posicion).getPerfil().getContextura());
        txtEducacion = (EditText) dialog.findViewById(R.id.txt_educacion);
        txtEducacion.setText(listaUsuarios.get(posicion).getPerfil().getNivelEducacion());
        txtOcupacion = (EditText) dialog.findViewById(R.id.txt_ocupacion);
        txtOcupacion.setText(String.valueOf(listaUsuarios.get(posicion).getPerfil().getTrabajo()));
    }

    private String edadPersona(Integer anio) {
        Calendar fecha = Calendar.getInstance();
        Integer anioActual = fecha.get(Calendar.YEAR);
        return String.valueOf(anioActual - anio);
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Slider Demo", "Page Scrolled: " + position + " float Position: " + positionOffset + " positionOffsetPixels " + positionOffsetPixels);
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPagerEx#SCROLL_STATE_IDLE
     * @see ViewPagerEx#SCROLL_STATE_DRAGGING
     * @see ViewPagerEx#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public class SendConfigurationInitialPeticionHttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;

        @Override
        protected String doInBackground(String... params) {
            String resp = null;
            if (CommonUtils.isNetworkAvailable(getActivity())) {
                try {
                    resp = WSHitchUs.enviarConfiguracionesConsulta(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
                    //resp = Constants.$PATH_LISTA_USUARIOS;
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
                        processJsonGeneral(response);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Hitch Us");
                        builder.setMessage("No tiene usuarios cerca.");
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
                    CommonUtils.showSimpleMessageDialog(getActivity().getApplicationContext(), "Revise su conexión a internet.");
                }
            }
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

      /*  @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity().getApplicationContext());
            mProgressDialog.setMessage("Buscando personas cerca a tí...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            super.onPreExecute();
        }*/
    }

}
