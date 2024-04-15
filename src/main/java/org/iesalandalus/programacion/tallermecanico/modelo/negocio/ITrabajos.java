package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ITrabajos {
    List<Trabajo> get();

    List<Trabajo> get(Cliente cliente);

    List<Trabajo> get(Vehiculo vehiculo);
    Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes);

    void insertar(Trabajo trabajo) throws OperationNotSupportedException;

    void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException;

    void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException;

    void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException;

    Trabajo buscar(Trabajo trabajo);

    void borrar(Trabajo trabajo) throws OperationNotSupportedException;
}
