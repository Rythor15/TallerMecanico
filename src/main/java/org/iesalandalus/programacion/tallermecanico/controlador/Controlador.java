package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(FabricaModelo fabricaModelo, FabricaFuenteDatos fabricaFuenteDatos, FabricaVista fabricaVista) {
        Objects.requireNonNull(fabricaModelo, "El modelo no puede ser nulo.");
        Objects.requireNonNull(fabricaFuenteDatos, "La vista no puede ser nula.");
        Objects.requireNonNull(fabricaVista, "La vista no puede ser nula.");
        this.modelo = fabricaModelo.crear(fabricaFuenteDatos);
        this.vista = fabricaVista.crear();
        vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            String resultado = "";
            switch  (evento) {
                case INSERTAR_CLIENTE -> {modelo.insertar(vista.leerCliente()); resultado = "El cliente se ha insertado correctamente";}
                case INSERTAR_VEHICULO -> {modelo.insertar(vista.leerVehiculo()); resultado = "El vehículo se ha insertado correctamente";}
                case INSERTAR_REVISION -> {modelo.insertar(vista.leerRevision()); resultado = "La revisión se ha insertado correctamente";}
                case INSERTAR_MECANICO -> {modelo.insertar(vista.leerMecanico()); resultado = "El trabajo mecánico se ha insertado correctamente";}
                case BUSCAR_CLIENTE ->  vista.mostrarCliente(modelo.buscar(vista.leerClienteDni()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerTrabajoVehiculo()));
                case MODIFICAR_CLIENTE -> {resultado = (modelo.modificar(vista.leerCliente(),vista.leerNuevoNombre(), vista.leerNuevoTelefono())) ? "El cliente ha sido modificado correctamente" : "El cliente no se ha modificado"; }
                case CERRAR_TRABAJO -> {modelo.cerrar(vista.leerTrabajoVehiculo(),vista.leerFechaCierre()); resultado = "El trabajo se ha cerrado correctamente";}
                case BORRAR_CLIENTE -> {modelo.borrar(vista.leerClienteDni()); resultado = "El cliente se ha borrado correctamente";}
                case BORRAR_VEHICULO -> {modelo.borrar(vista.leerVehiculoMatricula()); resultado = "El vehiculo se ha borrado correctamente";}
                case BORRAR_TRABAJO -> {modelo.borrar(vista.leerTrabajoVehiculo()); resultado = "El trabajo se ha borrado correctamente";}
                case ANADIR_HORAS_TRABAJO -> {modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras()); resultado = "Se han añadido las horas correctamente";}
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial()); resultado = "Se ha añadido el precio del material correctamente";}
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));
                case MOSTRAR_ESTADISTICAS_MENSUALES -> vista.mostrarEstadisticasMensuales(modelo.getEstadisticasMensuales(vista.leerMes()));
                case SALIR -> terminar();
            }
            if (!resultado.isBlank()){
                vista.notificarResultado(evento, resultado, true);
            }

        }catch (Exception e){
            vista.notificarResultado(evento, e.getMessage(),false);
        }

    }
}