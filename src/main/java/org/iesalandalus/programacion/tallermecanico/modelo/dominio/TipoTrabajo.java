package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    MECANICO("Mecánico"),
    REVISION("Revisión");

    public final String nombre;

    TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }
    public static TipoTrabajo get(Trabajo trabajo) {
        TipoTrabajo tipoTrabajo = REVISION;
        if (trabajo instanceof Mecanico) {
            tipoTrabajo = MECANICO;
        }
        return tipoTrabajo;
    }
}
