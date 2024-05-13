package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHERO_CLIENTES = String.format("%s%s%s", "xml", File.separator, "clientes.xml");
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    private static Clientes instacia = null;
    private final List<Cliente> coleccionClientes;


    public Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instacia == null) {
            instacia = new Clientes();
        }
        return instacia;
    }

    public void comenzar() {
        Document documentoXmlClientes = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXmlClientes != null) {
            procesarDocumentoXml(documentoXmlClientes);
            System.out.printf("Fichero %s leído correctamente.", FICHERO_CLIENTES);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList clientes = documentoXml.getElementsByTagName(RAIZ);
        for (int i = 0; i < clientes.getLength(); i++) {
            Node cliente = clientes.item(i);
            try {
                if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getCliente((Element) cliente));
                }
            } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
                System.out.printf("Error: No se pudo leer el cliente %s", e.getMessage());
                ;
            }
        }
    }

    private Cliente getCliente(Element elemento) {
        String nombre = elemento.getAttribute(NOMBRE);
        String dni = elemento.getAttribute(DNI);
        String telefono = elemento.getAttribute(TELEFONO);
        return new Cliente(nombre, dni, telefono);
    }

    public void terminar() {
        Document documetnoXmlClientes = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documetnoXmlClientes, FICHERO_CLIENTES);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXmlClientes = null;
        if (constructor != null) {
            documentoXmlClientes = constructor.newDocument();
            documentoXmlClientes.appendChild(documentoXmlClientes.createElement(RAIZ));
            for (Cliente cliente : coleccionClientes) {
                Element elemento = getElemento(documentoXmlClientes, cliente);
                if (elemento.getNodeType() == Node.ELEMENT_NODE) {
                    documentoXmlClientes.getDocumentElement().appendChild(elemento);
                }
            }
        }
        return documentoXmlClientes;
    }

    private Element getElemento(Document documentoXml, Cliente cliente) {
        Element elemento = documentoXml.createElement(CLIENTE);
        elemento.setAttribute(NOMBRE, cliente.getNombre());
        elemento.setAttribute(DNI, cliente.getDni());
        elemento.setAttribute(TELEFONO, cliente.getTelefono());
        return elemento;
    }

    @Override
    public List<Cliente> get() {
        return new ArrayList<>(coleccionClientes);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteBuscado = buscar(cliente);
        boolean modificado = false;
        if (clienteBuscado == null) {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()) {
            clienteBuscado.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteBuscado.setTelefono(telefono);
            modificado = true;
        }
        return modificado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indiceListaClientes = coleccionClientes.indexOf(cliente);
        return (indiceListaClientes == -1) ? null : coleccionClientes.get(indiceListaClientes);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            coleccionClientes.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
