package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.FuenteDatos;

public enum FabricaFuenteDatos {
    MEMORIA{
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatos();
        }
    };

    public abstract IFuenteDatos crear();
}
