package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes {

    private final List<Cliente> coleccionClientes;

    public Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    public List<Cliente> get() {
        return new ArrayList<>(coleccionClientes);
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteBuscado = buscar(cliente);
        boolean modificado = false;
        if (clienteBuscado == null) {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()) {
            clienteBuscado.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteBuscado.setTelefono(telefono);
            modificado = true;
        }
        return modificado;
    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indiceListaClientes = coleccionClientes.indexOf(cliente);
        return (indiceListaClientes == -1) ? null : coleccionClientes.get(indiceListaClientes);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            coleccionClientes.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
