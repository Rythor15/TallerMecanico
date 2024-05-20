package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;

public class ModeloCascada implements Modelo{
    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos,"La factoría de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        clientes.comenzar();
        vehiculos.comenzar();
        trabajos.comenzar();
        System.out.print("El modelo ha comenzado");
    }

    @Override
    public void terminar() {
        clientes.terminar();
        vehiculos.terminar();
        trabajos.terminar();
        System.out.print("El modelo ha terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajo = new Revision(cliente, vehiculo, trabajo.getFechaInicio());
        } else {
            trabajo = new Mecanico(cliente, vehiculo, trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual.");
        return new Cliente(cliente);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehiculo igual.");
        return vehiculo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No existe un trabajo igual.");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(cliente)) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaClientes.add(new Cliente(cliente));
        }
        return listaClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> listaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            listaTrabajos.add(Trabajo.copiar(trabajo));
        }
        return listaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> listaTrabajosConClientes = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            listaTrabajosConClientes.add(Trabajo.copiar(trabajo));
        }
        return listaTrabajosConClientes;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> listaTrabajosConVehiculo = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            listaTrabajosConVehiculo.add(Trabajo.copiar(trabajo));
        }
        return listaTrabajosConVehiculo;
    }
    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        return trabajos.getEstadisticasMensuales(mes);
    }
}