package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Vehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModeloCascada implements Modelo {
    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {

    }

    @Override
    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        trabajos = new Trabajos();
    }

    @Override
    public void terminar() {
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
        trabajos.insertar(new Revision(cliente, vehiculo, trabajo.getFechaInicio()));
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
        Trabajo trabajoBuscado = null;
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No existe un trabajo igual.");
        if (trabajo instanceof Mecanico){
            trabajoBuscado = new Mecanico((Mecanico) trabajo);
        } else {
            trabajoBuscado = new Revision((Revision) trabajo);
        }
        return trabajoBuscado;
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
            listaTrabajos.add(new Revision((Revision) trabajo));
        }
        return listaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> listaTrabajosConClientes = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            listaTrabajosConClientes.add(new Revision((Revision) trabajo));
        }
        return listaTrabajosConClientes;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> listaTrabajosConVehiculo = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            listaTrabajosConVehiculo.add(new Revision((Revision) trabajo));
        }
        return listaTrabajosConVehiculo;
    }
    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        return getEstadisticasMensuales(mes);
    }
}