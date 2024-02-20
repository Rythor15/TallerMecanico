package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum Opcion {
    INSENTAR_CLIENTE(1, "Insertar cliente"), BUSCAR_CLIENTE(2, "Buscar cliente"), BORRAR_CLIENTE(3, "Borrar cliente"),
    LISTAR_CLIENTES(4, "Listar clientes"), MODIFICAR_CLIENTE(5, "Modificar cliente"), INSENTAR_VEHICULO(6, "Insertar vehículo"),
    BUSCAR_VEHICULO(7, "Buscar vehículo"), BORRAR_VEHICULO(8, "Borrar vehículo"), LISTAR_VEHICULOS(9, "Listar vehículos"),
    INSENTAR_REVISION(10, "Insertar revisión"), BUSCAR_REVISION(11, "Buscar revisión"), BORRAR_REVISION(12, "Borrar revisión"),
    LISTAR_REVISIONES(13, "Listar revisiones"), LISTAR_REVISIONES_CLIENTE(14, "Listar revisiones cliente"),
    LISTAR_REVISIONES_VEHICULO(15, "Listar revisiones vehiculo"), ANADIR_HORAS_REVISION(16, "Añadir horas revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(17, "Añadir precio material revisión"), CERRAR_REVISION(18, "Cerrar revisión"),
    SALIR(19, "Salir");

    Integer numeroOpcion;
    String mensaje;
    private static Map<Integer, Opcion> opciones = new HashMap<>();
    static{
        for (Opcion opcion : Opcion.values()){
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Opcion(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }
    public static boolean esValida(int numeroOpcion){
        return opciones.containsValue(numeroOpcion);
    }
    public static Opcion get(int numeroOpcion){
        Opcion[] arrayOpciones = Opcion.values();
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("ArgumentErrorNumeroOpción");
        }
        return arrayOpciones[numeroOpcion];
    }

    @Override
    public String toString() {
        return String.format("%s , %s", numeroOpcion, mensaje);
    }
}



