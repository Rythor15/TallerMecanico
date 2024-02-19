package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {
    private final List<Revision> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Revision> get(Cliente cliente) {

        List<Revision> listaCliente = new ArrayList<>();

        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                listaCliente.add(revision);
            }
        }
        return listaCliente;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> listaVehiculo = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                listaVehiculo.add(revision);
            }
        }
        return listaVehiculo;
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        coleccionRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws OperationNotSupportedException {
        for (Revision revision : coleccionRevisiones) {
            if (!revision.estaCerrada()) {
                if (revision.getCliente().equals(cliente)) {
                    throw new OperationNotSupportedException("El cliente tiene otra revisión en curso.");
                }
                if (revision.getVehiculo().equals(vehiculo)) {
                    throw new OperationNotSupportedException("El vehículo está actualmente en revisión.");
                }
            } else if (!fechaRevision.isAfter(revision.getFechaFin())) {
                if (revision.getCliente().equals(cliente)) {
                    throw new OperationNotSupportedException("El cliente tiene una revisión posterior.");
                }
                if (revision.getVehiculo().equals(vehiculo)) {
                    throw new OperationNotSupportedException("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        getRevision(revision).anadirHoras(horas);
    }

    private Revision getRevision(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        if (!coleccionRevisiones.contains(revision)) {
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        }
        return revision;
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        getRevision(revision).anadirPrecioMaterial(precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        getRevision(revision).cerrar(fechaFin);
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        int indiceListaRevisiones = coleccionRevisiones.indexOf(revision);
        return (coleccionRevisiones.contains(revision)) ? coleccionRevisiones.get(indiceListaRevisiones) : null;
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        if (coleccionRevisiones.contains(revision)) {
            coleccionRevisiones.remove(revision);
        } else {
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        }
    }
}