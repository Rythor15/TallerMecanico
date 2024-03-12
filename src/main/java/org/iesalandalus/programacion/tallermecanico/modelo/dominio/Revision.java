package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 35F;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }
    public Revision(Revision revision) {
        super(revision);
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras() * FACTOR_HORA;
    }

    @Override
    public String toString() {
        return (estaCerrado()) ? String.format("Revisión -> %s - %s (%s - %s): %s horas, %5.2f € total", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), fechaFin.format(FORMATO_FECHA),
                horas, getPrecio()) : String.format("Revisión -> %s - %s (%s - ): %s horas", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), horas);
    }
}
