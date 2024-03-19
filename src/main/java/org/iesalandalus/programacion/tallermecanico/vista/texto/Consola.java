package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola(){

    }
    static void mostrarCabecera (String mensaje) {
        System.out.printf("%n%s%n", mensaje);
        for(int i = 0; i < mensaje.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    static void mostrarMenu(){
        mostrarCabecera("Bienvenido al taller de reparaciones.");
        for (Evento evento : Evento.values()){
            System.out.println(evento);
        }
    }
    static Evento elegirOpcion() {
        Evento evento = null;
        do {
            try{
                evento = Evento.get(leerEntero("Introduzca el número de la opción"));
            } catch (IllegalArgumentException e){
                System.out.printf("%n%s", e.getMessage());
            }
        } while (evento == null);
        return evento;
    }
    static int leerEntero(String mensaje){
        System.out.println(mensaje);
        return Entrada.entero();
    }
    static float leerReal(String mensaje){
        System.out.println(mensaje);
        return Entrada.real();
    }
    static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();
    }
    static LocalDate leerFecha(String mensaje){
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        System.out.printf("%s (%s)", mensaje, CADENA_FORMATO_FECHA);
        try {
            fecha = LocalDate.parse(leerCadena(mensaje), formatoFecha);
        } catch (DateTimeParseException e){
            fecha = null;
        }
        return fecha;
    }
}

