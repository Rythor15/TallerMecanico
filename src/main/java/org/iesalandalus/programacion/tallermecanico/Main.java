package org.iesalandalus.programacion.tallermecanico;

import javafx.util.Pair;
import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;



public class Main {
    public static void main(String[] args) {
        Pair<FabricaVista, FabricaFuenteDatos> fabrica = procesarArgumento(args);
        IControlador controlador = new Controlador(FabricaModelo.CASCADA,fabrica.getValue(),fabrica.getKey());
        controlador.comenzar();
    }

    public static Pair<FabricaVista, FabricaFuenteDatos> procesarArgumento(String[] args){
        FabricaVista fabricaVista = FabricaVista.GRAFICA;
        FabricaFuenteDatos fabricaFuenteDatos = FabricaFuenteDatos.MARIADB;
        for(String argumento : args) {
            if (argumento.equalsIgnoreCase("-vventanas")) {
                fabricaVista = FabricaVista.GRAFICA;
            } else if (argumento.equalsIgnoreCase("-vtexto")) {
                fabricaVista = FabricaVista.TEXTO;
            } else if (argumento.equalsIgnoreCase("-fdficheros")) {
                fabricaFuenteDatos = FabricaFuenteDatos.FICHEROS;
            } else if (argumento.equalsIgnoreCase("-fdmariadb")) {
                fabricaFuenteDatos = FabricaFuenteDatos.MARIADB;
            }
        }
        return new Pair<>(fabricaVista,fabricaFuenteDatos);
    }
}