package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import jdk.jfr.Event;

import java.util.List;
import java.util.Map;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores;

    public GestorEventos(Evento... eventos){

    }
    public void subcribir(ReceptorEventos receptor, Evento... eventos){

    }
    public void desuscribir(ReceptorEventos receptor, Evento... eventos){

    }
    public void notificar(Evento evento){

    }
}
