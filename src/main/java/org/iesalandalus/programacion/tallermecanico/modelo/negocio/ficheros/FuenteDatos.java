package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

public class FuenteDatos implements IFuenteDatos {

    @Override
    public IClientes crearClientes(){
        return new Clientes();
        // return getInstancia;
    }
    @Override
    public IVehiculos crearVehiculos(){
        return new Vehiculos();
    }
    @Override
    public ITrabajos crearTrabajos(){
        return new Trabajos();
    }
}
