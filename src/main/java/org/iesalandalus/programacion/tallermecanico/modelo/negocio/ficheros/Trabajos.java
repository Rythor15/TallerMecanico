package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {
    private static final String FICHERO_TRABAJOS = String.format("%s%s%s", "xml", File.separator, "trabajos.xml");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String MECANICO = "mecanico";
    private static final String REVISION = "revision";

    private static Trabajos instacia;
    private final List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    static Trabajos getInstancia() {
        if (instacia == null) {
            instacia = new Trabajos();
        }
        return instacia;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_TRABAJOS);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList trabajos = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0; i < trabajos.getLength(); i++) {
            Node trabajo = trabajos.item(i);
            try {
                if (trabajo.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getTrabajo((Element) trabajo));
                }
            } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
                System.out.printf("Error al leer el trabajo %d. --> %s%n", i, e.getMessage());
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) throws OperationNotSupportedException {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(REVISION)) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        } else if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            if (elemento.hasAttribute(PRECIO_MATERIAL)) {
                ((Mecanico) trabajo).anadirPrecioMaterial(Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL)));
            }
        }
        if (elemento.hasAttribute(HORAS) && trabajo != null) {
            int horas = Integer.parseInt(elemento.getAttribute(HORAS));
            trabajo.anadirHoras(horas);
        }
        if (elemento.hasAttribute(FECHA_FIN) && trabajo != null) {
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN), FORMATO_FECHA);
            trabajo.cerrar(fechaFin);
        }
        return trabajo;
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_TRABAJOS);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Trabajo trabajo : coleccionTrabajos) {
                Element elemento = getElemento(documentoXml, trabajo);
                if (elemento.getNodeType() == Node.ELEMENT_NODE) {
                    documentoXml.getDocumentElement().appendChild(elemento);
                }
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Trabajo trabajo) {
        Element elementoTrabajo = documentoXml.createElement(TRABAJO);
        elementoTrabajo.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        elementoTrabajo.setAttribute(VEHICULO, trabajo.getVehiculo().matricula());
        elementoTrabajo.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        if (trabajo.getFechaFin() != null) {
            elementoTrabajo.setAttribute(FECHA_FIN, trabajo.getFechaFin().format(FORMATO_FECHA));
        }
        if (trabajo.getHoras() != 0) {
            elementoTrabajo.setAttribute(HORAS, String.format("%d", trabajo.getHoras()));
        }
        if (trabajo instanceof Revision) {
            elementoTrabajo.setAttribute(TIPO, REVISION);
        } else if (trabajo instanceof Mecanico mecanico) {
            elementoTrabajo.setAttribute(TIPO, MECANICO);
            if (mecanico.getPrecioMaterial() != 0) {
                elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.format(Locale.US, "%f", mecanico.getPrecioMaterial()));
            }
        }
        return elementoTrabajo;
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> listaTrabajosCliente = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                listaTrabajosCliente.add(trabajo);
            }
        }
        return listaTrabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> listaTrabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                listaTrabajosVehiculo.add(trabajo);
            }
        }
        return listaTrabajosVehiculo;
    }

    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticas = inicializaEstadisticas();
        int ocurreciaMecanico = 0;
        int ocurrenciaRevision = 0;
        for (Trabajo trabajo : get()) {
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth()) && trabajo.getFechaInicio().getYear() == mes.getYear()) {
                if (trabajo instanceof Mecanico) {
                    ocurreciaMecanico++;
                } else {
                    ocurrenciaRevision++;
                }
            }
        }
        estadisticas.put(TipoTrabajo.MECANICO, ocurreciaMecanico);
        estadisticas.put(TipoTrabajo.REVISION, ocurrenciaRevision);
        return estadisticas;
    }

    private Map<TipoTrabajo, Integer> inicializaEstadisticas() {
        Map<TipoTrabajo, Integer> mapa = new EnumMap<>(TipoTrabajo.class);
        mapa.put(TipoTrabajo.MECANICO, 0);
        mapa.put(TipoTrabajo.REVISION, 0);

        return mapa;
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws OperationNotSupportedException {
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new OperationNotSupportedException("El vehículo está actualmente en el taller.");
                }
            } else if (trabajo.estaCerrado() && !fechaInicio.isAfter(trabajo.getFechaFin())) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo posterior.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new OperationNotSupportedException("El vehículo tiene otro trabajo posterior.");
                }
            }
        }
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajoEncontrado = buscar(getTrabajoAbierto(trabajo.getVehiculo()));
        if (trabajoEncontrado != null) {
            trabajoEncontrado.anadirHoras(horas);
        } else {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws OperationNotSupportedException {
        Trabajo trabajoAbierto = null;
        Iterator<Trabajo> iteradorTrabajo = get(vehiculo).iterator();

        while (iteradorTrabajo.hasNext() && trabajoAbierto == null) {
            Trabajo posibleTrabajo = iteradorTrabajo.next();
            if (!posibleTrabajo.estaCerrado()) {
                trabajoAbierto = posibleTrabajo;
            }
        }
        if (trabajoAbierto == null) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoAbierto;
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Mecanico) {
            ((Mecanico) trabajoEncontrado).anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (coleccionTrabajos.contains(trabajoEncontrado)) {
            trabajoEncontrado.cerrar(fechaFin);
        } else {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indiceListaTrabajo = coleccionTrabajos.indexOf(trabajo);
        return (indiceListaTrabajo == -1) ? null : coleccionTrabajos.get(indiceListaTrabajo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (coleccionTrabajos.contains(trabajo)) {
            coleccionTrabajos.remove(trabajo);
        } else {
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
    }
}
