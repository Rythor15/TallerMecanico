package org.iesalandalus.programacion.tallermecanico.modelo.negocio.mariadb;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.util.List;

public class Vehiculos implements IVehiculos {

    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";
    private Connection conexion;

    private static Vehiculos instacia;

    static Vehiculos getInstancia() {
        if (instacia == null) {
            instacia = new Vehiculos();
        }
        return instacia;
    }

    private Vehiculos(){};

    @Override
    public void comenzar() {
        conexion = MariaDB.getConexion();
    }

    @Override
    public void terminar() {
        MariaDB.cerrarConexion();
    }

    @Override
    public List<Vehiculo> get() {
        return null;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {

    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        return null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {

    }
}
