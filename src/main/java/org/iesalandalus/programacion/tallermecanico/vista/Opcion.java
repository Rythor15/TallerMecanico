package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum Opcion {
    INSERTAR_CLIENTE(1, "Insertar cliente"),
    BUSCAR_CLIENTE(2, "Buscar cliente"),
    BORRAR_CLIENTE(3, "Borrar cliente"),
    LISTAR_CLIENTES(4, "Listar clientes"),
    MODIFICAR_CLIENTE(5, "Modificar cliente"),
    INSERTAR_VEHICULO(10, "Insertar vehículo"),
    BUSCAR_VEHICULO(11, "Buscar vehículo"),
    BORRAR_VEHICULO(12, "Borrar vehículo"),
    LISTAR_VEHICULOS(13, "Listar vehículos"),
    INSERTAR_REVISION(20, "Insertar revisión"),
    BUSCAR_REVISION(21, "Buscar revisión"),
    BORRAR_REVISION(22, "Borrar revisión"),
    LISTAR_REVISIONES(23, "Listar revisiones"),
    LISTAR_REVISIONES_CLIENTE(24, "Listar revisiones cliente"),
    LISTAR_REVISIONES_VEHICULO(25, "Listar revisiones vehiculo"),
    ANADIR_HORAS_REVISION(26, "Añadir horas revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(27, "Añadir precio material revisión"),
    CERRAR_REVISION(28, "Cerrar revisión"),
    SALIR(30, "Salir");

    private final int numeroOpcion;
    private final String mensaje;
    private static final Map<Integer, Opcion> opciones = new HashMap<>();

    static {
        for (Opcion opcion : values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Opcion(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }

    public static Opcion get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("ArgumentErrorNumeroOpción");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%s , %s", numeroOpcion, mensaje);
    }
}



