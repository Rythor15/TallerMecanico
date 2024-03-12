package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class Mecanico extends Trabajo {
    private static final float FACTOR_HORA = 30F;
    private static final float FACTOR_MATERIAL = 1.5F;
    protected float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
        precioMaterial = 0;
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws OperationNotSupportedException {
        if (estaCerrado()) {
            throw new OperationNotSupportedException("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        } else if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial = this.precioMaterial + precioMaterial;
    }

    public float getPrecioEspecifico() {
      return ((getHoras() * FACTOR_HORA) + (getPrecioMaterial() * FACTOR_MATERIAL));
    }

    @Override
    public String toString() {
        return (estaCerrado()) ? String.format("Mecánico -> %s - %s (%s - %s): %s horas, %4.2f € en material, %5.2f € total", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), fechaFin.format(FORMATO_FECHA),
                horas, getPrecioEspecifico(), getPrecio()) : String.format("Mecánico -> %s - %s (%s - ): %s horas, %4.2f € en material", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), horas, getPrecioEspecifico());
    }
}
