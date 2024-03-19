package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
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
    INSERTAR_MECANICO(21, "Insertar mecánico"),
    BUSCAR_TRABAJO(22, "Buscar trabajo"),
    BORRAR_TRABAJO(23, "Borrar trabajo"),
    LISTAR_TRABAJOS(24, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(25, "Listar trabajos cliente"),
    LISTAR_TRABAJOS_VEHICULO(26, "Listar trabajos vehiculo"),
    ANADIR_HORAS_TRABAJO(27, "Añadir horas trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(28, "Añadir precio material trabajo"),
    CERRAR_TRABAJO(29, "Cerrar trabajo"),
    SALIR(30, "Salir");

    private final int codigo;
    private final String texto;
    private static final Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    Evento(int codigo, String mensaje) {
        this.codigo = codigo;
        this.texto = mensaje;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("ArgumentErrorNumeroOpción");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return String.format("%s , %s", codigo, texto);
    }
}



