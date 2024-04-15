package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.FuenteDatos;

public enum FabricaFuenteDatos {
    FICHEROS{
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatos();
        }
    };

    public abstract IFuenteDatos crear();
}
