package com.teamj.joseguaman.hitchusv2.clases;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jose Guaman on 17/08/2016.
 */
public class Perfil implements Serializable {

    private Integer userId;
    private String nickName;
    private String email;
    private String paisOrigen;
    private String ciudadResidencia;
    private Boolean premium;
    private Float estatura;
    private Boolean trabajo;
    private Integer anioNacimiento;
    private String contextura;
    private String genero;
    private String nivelEducacion;
    private String idiomas;
    private String interes;
    private List<Imagen> listaImagenes;

    public Perfil() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Float getEstatura() {
        return estatura;
    }

    public void setEstatura(Float estatura) {
        this.estatura = estatura;
    }

    public Boolean getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Boolean trabajo) {
        this.trabajo = trabajo;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getContextura() {
        return contextura;
    }

    public void setContextura(String contextura) {
        this.contextura = contextura;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNivelEducacion() {
        return nivelEducacion;
    }

    public void setNivelEducacion(String nivelEducacion) {
        this.nivelEducacion = nivelEducacion;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    public List<Imagen> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(List<Imagen> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", paisOrigen='" + paisOrigen + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                ", premium=" + premium +
                ", estatura=" + estatura +
                ", trabajo=" + trabajo +
                ", anioNacimiento=" + anioNacimiento +
                ", contextura='" + contextura + '\'' +
                ", genero='" + genero + '\'' +
                ", nivelEducacion='" + nivelEducacion + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", interes='" + interes + '\'' +
                ", listaImagenes=" + listaImagenes +
                '}';
    }
}
