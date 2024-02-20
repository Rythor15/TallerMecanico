package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola(){

    }
    public static void mostrarCabecera (String mensaje) {
        System.out.print(mensaje);
        for(int i = 0; i < mensaje.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    public static void mostrarMenu(){
        mostrarCabecera("Bienvenido al taller de reparaciones.");
        for (Opcion opcion : Opcion.values()){
            System.out.println(opcion);
        }
    }
    public static Opcion elegirOpcion() {
        int numeroOpcion;
        do {
            numeroOpcion = leerEntero("Introduzca el número de la opción");
        } while (!Opcion.esValida(numeroOpcion));
        return Opcion.get(numeroOpcion);
    }
    private static int leerEntero(String mensaje){
        System.out.println(mensaje);
        return Entrada.entero();
    }
    private static float leerReal(String mensaje){
        System.out.println(mensaje);
        return Entrada.real();
    }
    private static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();
    }
    private static LocalDate leerFecha(String mensaje){
        System.out.println(mensaje);
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
          String fechaString = Entrada.cadena();
          fecha = LocalDate.parse(fechaString, formatoFecha);
        return fecha;
    }
    public static Cliente leerCliente(){
        String dni = leerCadena("Introduce un dni");
        String nombre = leerNuevoNombre();
        String telefono = leerNuevoTelefono();
        return new Cliente(nombre, dni, telefono);
    }
    public static Cliente leerClienteDni(){
        String dni = leerCadena("Introduce un dni.");
        return Cliente.get(dni);
    }
    public static String leerNuevoNombre(){
        return leerCadena("Introduce un nombre.");
    }
    public static String leerNuevoTelefono(){
        return leerCadena("Introduce un teléfono.");
    }
    public static Vehiculo leerVehiculo(){
        String marca = leerCadena("Introduce la marca del vehículo.");
        String modelo = leerCadena("Introduce el modelo del vehículo.");
        String matricula = leerCadena("Introduzca la matricula del vehículo");
        return new Vehiculo(marca, modelo, matricula);
    }
    public static Vehiculo leerVehiculoMatricula(){
        String matricula = leerCadena("Introduzca la matricula del vehículo");
        return Vehiculo.get(matricula);
    }
    public static Revision leerRevision(){
        return new Revision(leerCliente(), leerVehiculo(), leerFecha("Introduzca la fecha del inicio de la revisión"));
    }
    public static int leerHoras(){
        return leerEntero("Introduzca el numero de horas trabajadas.");
    }
    public static float leerPrecioMaterial(){
        return leerReal("Introduzca el precio del material usado.");
    }
    public static LocalDate leerFechaCierre(){
       return leerFecha("Introduzca la fecha de cuando se ha cerrado la revision.");
    }
}

