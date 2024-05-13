package org.iesalandalus.programacion.tallermecanico.ventanas;

import javafx.application.Application;
import javafx.stage.Stage;
import org.iesalandalus.programacion.tallermecanico.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;

public class LanzadoraVentanaPrincipal extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Controlador ventanaPrincipal = Controladores.get("/vistas/VistaGrafica.fxml", "Taller Mecánico", null);
        ventanaPrincipal.addIcono("/imagenes/Icon.png");
        VistaGrafica.getInstancia().setVentanaPrincipal(ventanaPrincipal);
        ventanaPrincipal.getEscenario().show();

    }

    public static void comenzar() { launch(LanzadoraVentanaPrincipal.class); }
}

