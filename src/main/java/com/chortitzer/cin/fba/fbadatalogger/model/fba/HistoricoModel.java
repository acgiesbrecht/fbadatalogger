package com.chortitzer.cin.fba.fbadatalogger.model.fba;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class HistoricoModel {

    private LocalDateTime fechahora;
    private String archivo;
    private String evento;
    private String contenido;


    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
