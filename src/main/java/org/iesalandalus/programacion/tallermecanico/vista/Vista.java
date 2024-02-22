package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;


import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "El controlador no puede ser nulo");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        Consola.mostrarMenu();
        do {
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("El programa cerro correctamente.");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE -> insertarCliente();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_CLIENTE -> borrarCliente();
            case LISTAR_CLIENTES -> listarClientes();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case LISTAR_VEHICULOS -> listarVehiculos();
            case INSERTAR_REVISION -> insertarRevision();
            case BUSCAR_REVISION -> buscarRevision();
            case BORRAR_REVISION -> borrarRevision();
            case LISTAR_REVISIONES -> listarRevisiones();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
            case CERRAR_REVISION -> cerrar();
            case SALIR -> salir();
            default -> throw new IllegalArgumentException("La opción introducida no es válida");
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("Inserte un cliente");
        try {
            controlador.insertar(Consola.leerCliente());
            System.out.println("El cliente se introdujo correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("El cliente no se pudo insertar");
        }
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Inserte un vehículo");
        try {
            controlador.insertar(Consola.leerVehiculo());
            System.out.println("El vehículo se introdujo correctamente");
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println("El vehículo no se pudo insertar");
        }
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Inserte una revisión");
        try {
            controlador.insertar(Consola.leerRevision());
            System.out.println("La revisión se introdujo correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("La revision no se pudo insertar");
        }
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("Busque un cliente");
        try {
            System.out.println(controlador.buscar(Consola.leerClienteDni()));
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("El cliente buscado no existe.");
        }
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Busque un vehículo");
        try {
            System.out.println(controlador.buscar(Consola.leerVehiculoMatricula()));
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("El vehiculo buscado no existe.");
        }
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Busque una revisión");
        try {
            System.out.println(controlador.buscar(Consola.leerRevision()));
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("La revisión buscada no existe.");
        }
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modifique el cliente");
        boolean modificador = false;
        try {
            Cliente cliente = Consola.leerClienteDni();
            String nombre = Consola.leerNuevoNombre();
            String telefono = Consola.leerNuevoTelefono();
            controlador.modificar(cliente, nombre, telefono);
            modificador = controlador.modificar(cliente, nombre, telefono);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (modificador) {
            System.out.println("El cliente se ha modificado correctamente.");
        }
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("Añada horas");
        try {
            controlador.anadirHoras(Consola.leerRevision(), Consola.leerHoras());
            System.out.println("Las horas han sido añadidas correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("Las horas no han sido añadidas correctamente");
        }
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("Añade precio material");
        try {
            controlador.anadirPrecioMaterial(Consola.leerRevision(), Consola.leerPrecioMaterial());
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("El precio del material no ha sido agregado correctamente");
        }
    }

    private void cerrar() {
        Consola.mostrarCabecera("Cierre la revisión");
        try {
            controlador.cerrar(Consola.leerRevision(), Consola.leerFechaCierre());
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("La revision no pudo cerrarse");
        }
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("Borre cliente");
        try {
            controlador.borrar(Consola.leerClienteDni());
            System.out.println("El cliente fue eliminado correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("El cliente no pudo ser eliminado");
        }
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borre vehículo");
        try {
            controlador.borrar(Consola.leerVehiculoMatricula());
            System.out.println("El vehiculo fue eliminado correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("El vehiculo no pudo ser eliminado");
        }
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borre revisión");
        try {
            controlador.borrar(Consola.leerRevision());
            System.out.println("La revisión fue eliminada correctamente");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("La revisión no pudo ser eliminada");
        }
    }

    private void listarClientes() {
        Consola.mostrarCabecera("Listar clientes");
        List<Cliente> listaClientes = controlador.getClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("No se han encontrado clientes.");
        } else {
            System.out.println(listaClientes);
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listar vehículo");
        List<Vehiculo> listaVehiculos = controlador.getVehiculos();
        if (listaVehiculos.isEmpty()) {
            System.out.println("No se han encontrado vehículos.");
        } else {
            System.out.println(listaVehiculos);
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listar Revisiones");
        List<Revision> listaRevisiones = controlador.getRevisiones();
        if (listaRevisiones.isEmpty()) {
            System.out.println("No se ha encontrado ninguna revisión.");
        } else {
            System.out.println(listaRevisiones);
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listar revisiones cliente");
        List<Revision> listaRevisionesCliente = controlador.getRevisiones(Consola.leerClienteDni());
        if (listaRevisionesCliente.isEmpty()) {
            System.out.println("No hay ninguna revisión para dicho cliente.");
        } else {
            System.out.println(listaRevisionesCliente);
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listar revisiones vehiculo");
        List<Revision> listaRevisionesVehiculo = controlador.getRevisiones(Consola.leerVehiculoMatricula());
        if (listaRevisionesVehiculo.isEmpty()) {
            System.out.println("No hay ninguna revisión para dicho vehiculo.");
        } else {
            System.out.println(listaRevisionesVehiculo);
        }
    }

    private void salir() {
        Consola.mostrarCabecera("Salir");
        controlador.terminar();
    }
}