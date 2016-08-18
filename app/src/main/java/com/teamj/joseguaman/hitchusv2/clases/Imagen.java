package com.teamj.joseguaman.hitchusv2.clases;

import java.io.Serializable;

/**
 * Created by Jose Guaman on 17/08/2016.
 */
public class Imagen implements Serializable {

    private Boolean publica;
    private Boolean perfil;
    private String descripcion;
    private String url;
    private String name;

    public Imagen() {
    }

    public Boolean getPublica() {
        return publica;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }

    public Boolean getPerfil() {
        return perfil;
    }

    public void setPerfil(Boolean perfil) {
        this.perfil = perfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "publica=" + publica +
                ", perfil=" + perfil +
                ", descripcion='" + descripcion + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
