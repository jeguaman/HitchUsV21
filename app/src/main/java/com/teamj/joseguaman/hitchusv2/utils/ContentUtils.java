package com.teamj.joseguaman.hitchusv2.utils;

import android.text.TextUtils;
import android.util.Log;

import com.teamj.joseguaman.hitchusv2.clases.Imagen;
import com.teamj.joseguaman.hitchusv2.clases.Perfil;
import com.teamj.joseguaman.hitchusv2.clases.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jose Guaman on 17/08/2016.
 */
public class ContentUtils {

    public static HashMap<String, String> getImagesPerfil(JSONObject perfil) {
        HashMap<String, String> valor = new HashMap<String, String>();
        //url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
/*
        for (int j = 0; j < objImgs.length(); j++) {
            if (objImgs.getJSONObject(j).getBoolean("publica")) {
                url_maps.put(objImgs.getJSONObject(j).getString("descripcion"), Constants.$URL + Constants.$PATH_IMAGES + objImgs.getJSONObject(j).getString("url"));
                //listaImagenes.add(Constants.$URL + Constants.$PATH_IMAGES + objImgs.getJSONObject(j).getString("url"));
            }
        }
        */

        return null;
    }

    public static List<Usuario> getListUsuarioJson(String json) {

        List<Usuario> lista = new ArrayList<>();
        JSONArray listaJsonObject;

        try {
            listaJsonObject = new JSONArray(json);
            for (int i = 0; i < listaJsonObject.length(); i++) {
                System.out.println("Perfil : " + listaJsonObject.getJSONObject(i));
                JSONObject object = listaJsonObject.getJSONObject(i);
                Usuario user = new Usuario();
                String distancia = object.getString("distancia");
                String puntuacion = object.getString("puntuacion_total");
                JSONObject perfil = object.getJSONObject("perfil");
                user.setDistancia(new Float(distancia));
                user.setPuntuacionTotal(new Float(puntuacion));
                user.setPerfil(obtenerPerfilPorUsuario(perfil));
                lista.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static Perfil obtenerPerfilPorUsuario(JSONObject perfilObj) {
        Perfil perfilRegreso = new Perfil();
        try {
            perfilRegreso.setUserId(perfilObj.getInt("user_id"));
            perfilRegreso.setNickName(perfilObj.getString("nickname"));
            perfilRegreso.setEmail(perfilObj.getString("email"));
            String paisOrigen = perfilObj.getString("pais_origen");
            if (!TextUtils.isEmpty(paisOrigen)) {
                perfilRegreso.setPaisOrigen(paisOrigen);
            } else {
                perfilRegreso.setPaisOrigen("");
            }
            String ciudadResidencia = perfilObj.getString("ciudad_residencia");
            if (!TextUtils.isEmpty(ciudadResidencia)) {
                perfilRegreso.setCiudadResidencia(ciudadResidencia);
            } else {
                perfilRegreso.setCiudadResidencia("");
            }
            //perfilRegreso.setPremium(perfilObj.getBoolean("premium"));
            perfilRegreso.setPremium(Boolean.FALSE);
            Double estatura = perfilObj.getDouble("estatura");
            if (!TextUtils.isEmpty(String.valueOf(estatura))) {
                perfilRegreso.setEstatura(new Float(estatura));
            } else {
                perfilRegreso.setEstatura(new Float(estatura));
            }
            Boolean trabajo = perfilObj.getBoolean("trabajo");
            try {
                if (trabajo != null) {
                    perfilRegreso.setTrabajo(Boolean.TRUE);
                } else {
                    perfilRegreso.setTrabajo(Boolean.FALSE);
                }
            } catch (Exception e) {
                Log.e("Perfil usuario ", e.getMessage());
            }
            perfilRegreso.setAnioNacimiento(Integer.parseInt(String.valueOf(perfilObj.getInt("anio_nacimiento"))));
            String contextura = perfilObj.getString("contextura");
            if (!TextUtils.isEmpty(contextura)) {
                perfilRegreso.setContextura(contextura);
            } else {
                perfilRegreso.setContextura("");
            }
            String genero = perfilObj.getString("genero");
            if (!TextUtils.isEmpty(genero)) {
                perfilRegreso.setGenero(genero);
            } else {
                perfilRegreso.setGenero("");
            }
            String nivelEdu = perfilObj.getString("nivel_educacion");
            if (!TextUtils.isEmpty(nivelEdu)) {
                perfilRegreso.setNivelEducacion(nivelEdu);
            } else {
                perfilRegreso.setNivelEducacion("");
            }
            String idiomas = perfilObj.getString("idiomas");
            if (!TextUtils.isEmpty(idiomas)) {
                perfilRegreso.setIdiomas(idiomas);
            } else {
                perfilRegreso.setIdiomas("");
            }
            String interes = perfilObj.getString("interes");
            if (!TextUtils.isEmpty(interes)) {
                perfilRegreso.setInteres(interes);
            } else {
                perfilRegreso.setInteres("");
            }

            perfilRegreso.setListaImagenes(obtenerImagenesPerfil(perfilObj.getJSONArray("imagenes")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return perfilRegreso;
    }

    private static List<Imagen> obtenerImagenesPerfil(JSONArray imgJsonList) {
        List<Imagen> lista = new ArrayList<>();
        try {
            for (int i = 0; i < imgJsonList.length(); i++) {
                JSONObject object = imgJsonList.getJSONObject(i);
                Imagen img = new Imagen();
                img.setPublica(object.getBoolean("publica"));
                img.setPerfil(object.getBoolean("perfil"));
                img.setDescripcion(object.getString("descripcion"));
                img.setUrl(object.getString("url"));
                img.setName(object.getString("name"));
                lista.add(img);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
