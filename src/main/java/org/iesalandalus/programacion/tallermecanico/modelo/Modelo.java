package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public Modelo() {

    }

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.print("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision,"AlquilerNuloErrorInsertar");
        clientes.buscar(revision.getCliente());
        vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(revision));
    }

    public Cliente buscar(Cliente cliente) {
        clientes.buscar(cliente);
        return new Cliente(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculos.buscar(vehiculo);
        return new Vehiculo(vehiculo.marca(), vehiculo.modelo(), vehiculo.matricula());
    }

    public Revision buscar(Revision revision) {
        revisiones.buscar(revision);
        return new Revision(revision);
    }
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }
    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        revisiones.anadirHoras(revision, horas);
    }
    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }
    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        revisiones.cerrar(revision, fechaFin);
    }
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Revision revision : revisiones.get(cliente)){
            borrar(revision);
        }
        clientes.borrar(cliente);
    }
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revision : revisiones.get(vehiculo)){
            borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }
    public void borrar(Revision revision) throws OperationNotSupportedException {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaClientes.add(new Cliente(cliente));
        }
        return listaClientes;
    }
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }
    public List<Revision> getRevisiones() {
        List<Revision> listaRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            listaRevisiones.add(new Revision(revision));
        }
        return listaRevisiones;
    }
    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> listaRevisionesConCliente = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            listaRevisionesConCliente.add(new Revision(revision));
        }
        return listaRevisionesConCliente;
    }
    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> listaRevisionesConVehiculo = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            listaRevisionesConVehiculo.add(new Revision(revision));
        }
        return listaRevisionesConVehiculo;
    }
}
