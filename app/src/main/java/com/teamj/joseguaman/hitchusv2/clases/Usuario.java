package com.teamj.joseguaman.hitchusv2.clases;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Jose Guaman on 17/08/2016.
 */
public class Usuario implements Serializable {

    private Perfil perfil;
    private Float distancia;
    private Float puntuacionTotal;
    private HashMap<String, String> url_maps;

    public Usuario() {
        url_maps = new HashMap<String, String>();
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }

    public Float getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(Float puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public HashMap<String, String> getUrl_maps() {
        return url_maps;
    }

    public void setUrl_maps(HashMap<String, String> url_maps) {
        this.url_maps = url_maps;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "perfil=" + perfil +
                ", distancia='" + distancia + '\'' +
                ", puntuacionTotal='" + puntuacionTotal + '\'' +
                '}';
    }
}
