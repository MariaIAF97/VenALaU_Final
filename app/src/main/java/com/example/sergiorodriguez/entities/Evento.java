package com.example.sergiorodriguez.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by gfghfh on 26/04/2018.
 */

public class Evento  implements Serializable{

    private int IdEvento;
    private String Nombre;
    private String TipoEvento;
    private Date Fecha;
    private String Lugar;
    private String Detalle;

    public int getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(int idEvento) {
        IdEvento = idEvento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipoEvento() {
        return TipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        TipoEvento = tipoEvento;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }
}
