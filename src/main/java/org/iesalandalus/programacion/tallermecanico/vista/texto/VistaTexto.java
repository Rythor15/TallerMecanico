package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

import static org.iesalandalus.programacion.tallermecanico.vista.texto.Consola.*;

public class VistaTexto implements Vista {
    private GestorEventos gestorEventos = new GestorEventos(Evento.values());
    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento evento;
        Consola.mostrarMenu();
        do {
            evento = Consola.elegirOpcion();
            ejecutar(evento);
        } while (evento != Evento.SALIR);
    }

    @Override
    public void terminar() {
        System.out.println("El programa cerro correctamente.");
    }

    private void ejecutar(Evento opcion) {
        mostrarCabecera("Ha elegido la opción: " + opcion.toString());
        gestorEventos.notificar(opcion);
    }
    @Override
    public Cliente leerCliente() {
        String dni = leerCadena("Introduce un dni: ");
        String nombre = leerNuevoNombre();
        String telefono = leerNuevoTelefono();
        return new Cliente(nombre, dni, telefono);
    }
    @Override
    public Cliente leerClienteDni() {
        String dni = leerCadena("Introduce un dni: ");
        return Cliente.get(dni);
    }
    @Override
    public String leerNuevoNombre() {
        return leerCadena("Introduce un nuevo nombre: ");
    }
    @Override
    public String leerNuevoTelefono() {
        return leerCadena("Introduce un nuevo teléfono: ");
    }
    @Override
    public Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca del vehículo: ");
        String modelo = leerCadena("Introduce el modelo del vehículo: ");
        String matricula = leerCadena("Introduzca la matricula del vehículo: ");
        return new Vehiculo(marca, modelo, matricula);
    }
    @Override
    public Vehiculo leerVehiculoMatricula() {
        String matricula = leerCadena("Introduzca la matricula del vehículo: ");
        return Vehiculo.get(matricula);
    }
    @Override
    public Trabajo leerRevision() {
        return new Revision(leerClienteDni(), leerVehiculoMatricula(), leerFecha("Introduzca la fecha del inicio de la revisión: "));
    }
    @Override
    public Trabajo leerMecanico() {
        return new Mecanico(leerClienteDni(), leerVehiculoMatricula(), leerFecha("Introduzca la fecha del inicio del mecánico: "));
    }
    @Override
    public Trabajo leerTrabajoVehiculo() {
        return new Mecanico(new Cliente("Ruben","23810454A","717705283"),leerVehiculoMatricula(), LocalDate.now());
    }
    @Override
    public int leerHoras() {
        return leerEntero("Introduzca el numero de horas trabajadas: ");
    }
    @Override
    public float leerPrecioMaterial() {
        return leerReal("Introduzca el precio del material usado: ");
    }
    @Override
    public LocalDate leerFechaCierre() {
        return leerFecha("Introduzca la fecha de cuando se ha cerrado la revision: ");
    }
    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        System.out.printf("%s, %s, %s \n", evento, texto, exito);
    }
    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.print(cliente);
    }
    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.print(vehiculo);
    }
    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.print(trabajo);
    }
    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }
    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }
    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        for (Trabajo trabajo : trabajos) {
            System.out.println(trabajo);
        }
    }
}