package com.teamj.joseguaman.hitchusv2.webservices;




import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.teamj.joseguaman.hitchusv2.constantes.Constants;

import java.io.IOException;


/**
 * Created by Jose Guaman on 14/08/2016.
 */
public class WSHitchUs {
    public static String signInUser(String userName, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder().add("email", userName).add("password", password).build();
        Request request = new Request.Builder()
                .url(Constants.$URL + Constants.$PATH_LOGIN_USER)
                .post(formBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
        }
    }

    public static String enviarConfiguracionesConsulta(String usuario, String correo, String genero, String edadMin, String latitud, String longitud, String edadMax, String distancia) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder().add("nickname", usuario).add("correo", correo)
                .add("genero", genero).add("edad_min", edadMin).add("edad_max", edadMax)
                .add("distancia", distancia).add("latitud", latitud).add("longitud", longitud).build();
        Request request = new Request.Builder()
                .url(Constants.$URL + Constants.$PATH_BUSCAR_PREFERENCIAS)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
        }
    }

    public static String sendLikeUser(String correoEmisor, String correoReceptor) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder().add("nickname1", correoEmisor).add("nickname2", correoReceptor)
                .add("acepta", "true").build();
        Request request = new Request.Builder()
                .url(Constants.$URL + Constants.$PATH_SEND_LIKE)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
        }
    }

    public static String sendRefuzeUser(String correoEmisor, String correoReceptor) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder().add("nickname1", correoEmisor).add("nickname2", correoReceptor)
                .add("acepta", "false").build();
        Request request = new Request.Builder()
                .url(Constants.$URL + Constants.$PATH_SEND_REFUZE)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
        }
    }
}
