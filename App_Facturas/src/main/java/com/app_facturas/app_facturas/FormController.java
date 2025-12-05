/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import connection.DAOController;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import objects.CliPro;
import objects.Direccion;
import objects.Empresa;
import objects.Entidad;
import objects.Factura;
import objects.Producto;
import validations.Validation;

/**
 *
 * @author diego
 */
public class FormController {

    @FXML
    private Pane cliProvPane;
    @FXML 
    private GridPane gridClientes;
    @FXML
    private TextField DocumentoCPField;
    @FXML
    private TextField NombreCPField;
    @FXML
    private TextField emailCPField;
    @FXML
    private TextField telefonoCPField;
    @FXML
    private TextField observacionesCPField;
    @FXML
    private TextField viaCPField;
    @FXML
    private TextField numCPField;
    @FXML
    private TextField ciudadCPField;
    @FXML
    private TextField provCPField;
    @FXML
    private TextField paisCPField;
    @FXML
    private TextField codigoPostalCPField;
    @FXML
    private CheckBox proveedorCPCheck;
    @FXML
    private CheckBox clienteCPCheck;
    @FXML
    private Pane EmpresaPane;
    @FXML 
    private GridPane gridEmpresa;
    @FXML
    private TextField DocumentoEmpField;
    @FXML
    private TextField NombreEmpField;
    @FXML
    private TextField emailEmpField;
    @FXML
    private TextField telefonoEmpField;
    @FXML
    private TextField observacionesEmpField;
    @FXML
    private TextField viaEmpField;
    @FXML
    private TextField numEmpField;
    @FXML
    private TextField ciudadEmpField;
    @FXML
    private TextField provEmpField;
    @FXML
    private TextField paisEmpField;
    @FXML
    private TextField codigoPostalEmpField;
    @FXML
    private Pane productosPane;

    @FXML
    private Pane factPane;
    @FXML 
    private HBox hboxFacturas;
    @FXML
    private TextField NombreEmpField1;
    @FXML
    private TextField observacionesEmpField1;

    private String tipo;
    private String accion;
    @FXML
    private Pane buttonsPaneAñadir;
    @FXML
    private Pane buttonsPaneModificar;
    @FXML
    private Pane buttonsPaneEliminar;
    @FXML
    private Label nomEmp;
    @FXML
    private Label nomEmpProd;
    @FXML
    private Label nomEmpCliProv;
    @FXML
    private SplitMenuButton docType;
    @FXML
    private MenuItem nifType;
    @FXML
    private MenuItem nieType;
    @FXML
    private MenuItem cifType;
    private String nombreEmpresa = App.nombreEmpresaActual;
    private Entidad entidad;
    @FXML
    private Label labelError;

    private DAOController dao = new DAOController();
    @FXML
    private ListView<Entidad> provList;
    @FXML
    private TextField nombreProField;
    @FXML
    private TextField descripProField;
    @FXML
    private TextField precioProField;
    @FXML
    private TextField stockProField;
    @FXML
    private DatePicker fechaEmisionDate;
    @FXML
    private ListView<Entidad> productosList;
    @FXML
    private ListView<Entidad> productosAddList;
    @FXML
    private SplitMenuButton splitMenuIVA;
    @FXML
    private MenuItem primero;
    @FXML
    private MenuItem segundo;
    @FXML
    private MenuItem tercero;

    private ArrayList<Entidad> productosAñadidos = new ArrayList<Entidad>();
    private ArrayList<Integer> productosCantidad = new ArrayList<Integer>();
    @FXML
    private SplitMenuButton doctype2;
    @FXML
    private TextField cantProductFact;
    @FXML
    private MenuItem nifType2;
    @FXML
    private MenuItem nieType2;
    @FXML
    private MenuItem cifType2;

    public void initialize() {
        gridClientes.prefWidthProperty().bind(cliProvPane.widthProperty().subtract(40));
        gridEmpresa.prefWidthProperty().bind(EmpresaPane.widthProperty().subtract(40));
        hboxFacturas.prefWidthProperty().bind(factPane.widthProperty().subtract(30));
        HBox.setHgrow(productosList, Priority.ALWAYS);
        HBox.setHgrow(productosAddList, Priority.ALWAYS);
        
        
        System.out.println("Tipo " + tipo);
        if (tipo != null) {
            switch (tipo) {
                case "Emp":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(true);
                    factPane.setVisible(false);
                    break;
                case "CliPro":
                    cliProvPane.setVisible(true);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(false);
                    nomEmpCliProv.setText(nombreEmpresa);
                    break;
                case "Prod":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(true);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(false);
                    nomEmpProd.setText(nombreEmpresa);
                    actualizarProvList();
                    break;
                case "Vent":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(true);
                    nomEmp.setText(nombreEmpresa);
                    Platform.runLater(() -> {
                        System.out.println("RunLater ejecutado");
                        actualizarListProd();
                    });

                    break;
                case "Comp":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(true);
                    nomEmp.setText(nombreEmpresa);
                    Platform.runLater(() -> {
                        System.out.println("RunLater ejecutado");
                        actualizarListProd();
                    });

                    break;
                default:
                    System.out.println("Algo salio mal al iniciar el formulario");
                    break;
            }
            if(tipo!=null && entidad!=null && accion!=null){
               mostrarDatos();
            }
        }
        System.out.println("accion " + accion);
        if (accion != null) {
            System.out.println("Entra al segundo switch");
            switch (accion) {
                case "add":
                    System.out.println("Entra al add");
                    buttonsPaneAñadir.setVisible(true);
                    buttonsPaneEliminar.setVisible(false);
                    buttonsPaneModificar.setVisible(false);
                    break;
                case "modiffy":
                    buttonsPaneAñadir.setVisible(false);
                    buttonsPaneEliminar.setVisible(false);
                    buttonsPaneModificar.setVisible(true);
                    break;
                case "delete":
                    buttonsPaneAñadir.setVisible(false);
                    buttonsPaneEliminar.setVisible(true);
                    buttonsPaneModificar.setVisible(false);
                    break;
                default:
                    System.out.println("Algo salio mal al iniciar los botones del formulario");
                    break;

            }
        }
        System.out.println("Controlador cargado: " + this);
        System.out.println("productosList es: " + productosList);

    }

    @FXML
    public void establecerTipoDoc() {

        //Cambiamos el texto del SplitMenuButton en función del tipo de documento seleccionado (NIF, NIE o CIF)
        javafx.event.EventHandler<javafx.event.ActionEvent> accionCambio = e -> {
            MenuItem itemPulsado = (MenuItem) e.getSource();
            docType.setText(itemPulsado.getText());
            System.out.println("Se asigna el tipo");
        };
        //Asigno la acción a los 3 documentos
        nifType.setOnAction(accionCambio);
        nieType.setOnAction(accionCambio);
        cifType.setOnAction(accionCambio);
    }

    @FXML
    public void establecerTipoDoc2() {

        //Cambiamos el texto del SplitMenuButton en función del tipo de documento seleccionado (NIF, NIE o CIF)
        javafx.event.EventHandler<javafx.event.ActionEvent> accionCambio = e -> {
            MenuItem itemPulsado = (MenuItem) e.getSource();
            doctype2.setText(itemPulsado.getText());
            System.out.println("Se asigna el tipo");
        };
        //Asigno la acción a los 3 documentos
        nifType2.setOnAction(accionCambio);
        nieType2.setOnAction(accionCambio);
        cifType2.setOnAction(accionCambio);
    }

    @FXML
    /**
     * volver a la pantalla anterior
     */
    private void volverAction(ActionEvent event) throws IOException {
        if (tipo.equals("Emp")) {
            App.setRoot("primary");
        } else {
            App.setRoot("secondary");
        }

    }

    @FXML
    /**
     * limpiar contenido
     */
    private void limpiarAction(ActionEvent event) {
        try {
            App.setRootWithParam("formulary", tipo, accion);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAccion(String accion) {
        this.accion = accion;
        if(entidad==null){
            initialize();//actualizamos la vista
        }
        
    }

    public void setEntidad(Entidad e) {
        entidad = e;
        initialize();
    }

    @FXML
    private void modificarAction(ActionEvent event) {

        Entidad obj = crearEntidad();  // crea la entidad con los nuevos datos

        if (obj == null) {
            return; // error en validaciones
        }
        switch (tipo) {

            case "Emp":
                dao.modificarEntidad(obj);
                break;

            case "CliPro":
                // modificar datos base
                dao.modificarEntidad(obj);

                // actualizar roles
                dao.eliminarRoles(obj.getIdEntidad());

                CliPro cp = (CliPro) obj;

                if (cp.isCliente()) {
                    dao.agregarRolEntidad(obj.getIdEntidad(), "CLIENTE");
                }
                if (cp.isProveedor()) {
                    dao.agregarRolEntidad(obj.getIdEntidad(), "PROVEEDOR");
                }

                break;

            case "Prod":
                // Producto ya viene con los campos y proveedor
                Producto p = (Producto) obj;
                dao.crearProducto(p); // si tuvieras “modificarProducto”, úsalo
                break;
        }

        volverAtras();
    }

    @FXML
    private void eliminarAction(ActionEvent event) {

        if (entidad == null) {
            return;
        }

        switch (tipo) {

            case "Emp":
            case "CliPro":
                dao.eliminarEntidad(entidad.getIdEntidad());
                break;

            case "Prod":
                dao.eliminarProducto(entidad.getIdEntidad());
                break;
        }

        volverAtras();
    }

    @FXML
    /**
     * boton de enviar
     */
    private void enviarAction(ActionEvent event) {

        Entidad obj = crearEntidad();

        if (obj != null) {
            switch (tipo) {

                case "Emp":
                    dao.crearEmpresa(obj);
                    break;

                case "CliPro":
                    Entidad creada = dao.crearEntidad(obj);

                    CliPro cp = (CliPro) obj;

                    if (cp.isCliente()) {
                        dao.agregarRolEntidad(creada.getIdEntidad(), "CLIENTE");
                    }
                    if (cp.isProveedor()) {
                        dao.agregarRolEntidad(creada.getIdEntidad(), "PROVEEDOR");
                    }

                    break;

                case "Prod":
                    dao.crearProducto((Producto) obj);
                    break;
            }
            volverAtras();
        } else {

        }
    }

    public Entidad crearEntidad() {
        Entidad e = null;
        validations.Error error = null;
        boolean sePuede = false;
        String tipoDocumento = "",
                nombre = "",
                email = "",
                telefono = "",
                obser = "",
                nombreVia = "",
                numero = "",
                ciudad = "",
                provincia = "",
                codigoPostal = "",
                pais = "",
                nompro = "",
                descpro = "",
                preciopro = "",
                Stockpro = "",
                nombreFiscFact = "",
                observFact = "",
                iva = "";
        LocalDate fecha = null;

        switch (tipo) {
            case "Emp":
                sePuede = false;

                //verificamos el tipo de documento
                String tipoDoc = docType.getText();

                switch (tipoDoc) {
                    case "N.I.F":
                        error = Validation.esNIF(DocumentoEmpField.getText());
                        break;
                    case "N.I.E":
                        error = Validation.esNIE(DocumentoEmpField.getText());
                        break;
                    case "C.I.F":
                        error = Validation.esCIF(DocumentoEmpField.getText());
                        break;
                    case "Tipo de documento":
                        error = new validations.Error(true, "debe seleccionar un tipo de documento", Color.RED);
                        break;

                }
                //si no ha habido fallos almacenamos la info
                if (!mostrarMensaje(error)) {
                    tipoDocumento = DocumentoEmpField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el nombre de la empresa
                error = Validation.esNombre(NombreEmpField.getText());
                if (!mostrarMensaje(error)) {
                    nombre = NombreEmpField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el email de la empresa
                if (!emailEmpField.getText().equals("")) {
                    error = Validation.esEmail(emailEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        email = emailEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el telefono de la empresa
                if (!telefonoEmpField.getText().equals("")) {
                    error = Validation.esTelefono(telefonoEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        telefono = telefonoEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el observaciones de la empresa
                obser = observacionesEmpField.getText();

                //el nombre de via de la empresa
                if (!viaEmpField.getText().equals("")) {
                    error = Validation.esNombre(viaEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        nombreVia = viaEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el numero de la calle de la empresa
                if (!numEmpField.getText().equals("")) {
                    error = Validation.esEnteroPos(numEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        numero = numEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //la ciudad de la empresa
                if (!ciudadEmpField.getText().equals("")) {
                    error = Validation.esNombre(ciudadEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        ciudad = ciudadEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //la provincia de la empresa
                if (!provEmpField.getText().equals("")) {
                    error = Validation.esNombre(provEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        provincia = provEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el codigopostal de la empresa
                if (!codigoPostalEmpField.getText().equals("")) {
                    error = Validation.esTexto(codigoPostalEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        codigoPostal = codigoPostalCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el pais de la empresa
                if (!paisEmpField.getText().equals("")) {
                    error = Validation.esTexto(paisEmpField.getText());
                    if (!mostrarMensaje(error)) {
                        pais = paisEmpField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                if (sePuede) {
                    System.out.println("Creando Entidad para empresa");
                    Entidad em = new Entidad(nombre, tipoDocumento, email, telefono, obser);
                    Direccion dir = new Direccion(em, nombreVia, numero, ciudad, provincia, codigoPostal, pais);
                    Empresa empresa = new Empresa(em,dir);
                    System.out.println("obj Empresa creada");
                    return empresa;
                }

                break;
            case "CliPro":
                //creamos el obj clipro de los campos field
                //verificamos el tipo de documento
                tipoDoc = doctype2.getText();

                switch (tipoDoc) {
                    case "N.I.F":
                        error = Validation.esNIF(DocumentoCPField.getText());
                        break;
                    case "N.I.E":
                        error = Validation.esNIE(DocumentoCPField.getText());
                        break;
                    case "C.I.F":
                        error = Validation.esCIF(DocumentoCPField.getText());
                        break;
                    case "Documento":
                        error = new validations.Error(true, "debe seleccionar un tipo de documento", Color.RED);
                        break;

                }
                //si no ha habido fallos almacenamos la info
                if (!mostrarMensaje(error)) {
                    tipoDocumento = DocumentoCPField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el nombre de la empresa
                error = Validation.esNombre(NombreCPField.getText());
                if (!mostrarMensaje(error)) {
                    nombre = NombreCPField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el email de la empresa
                if (!emailCPField.getText().equals("")) {
                    error = Validation.esEmail(emailCPField.getText());
                    if (!mostrarMensaje(error)) {
                        email = emailCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el telefono de la empresa
                if (!telefonoCPField.getText().equals("")) {
                    error = Validation.esTelefono(telefonoCPField.getText());
                    if (!mostrarMensaje(error)) {
                        telefono = telefonoCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el observaciones de la empresa
                obser = observacionesCPField.getText();

                //el nombre de via de la empresa
                if (!viaCPField.getText().equals("")) {
                    error = Validation.esNombre(viaCPField.getText());
                    if (!mostrarMensaje(error)) {
                        nombreVia = viaCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el numero de la calle de la empresa
                if (!numCPField.getText().equals("")) {
                    error = Validation.esEnteroPos(numCPField.getText());
                    if (!mostrarMensaje(error)) {
                        numero = numCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //la ciudad de la empresa
                if (!ciudadCPField.getText().equals("")) {
                    error = Validation.esNombre(ciudadCPField.getText());
                    if (!mostrarMensaje(error)) {
                        ciudad = ciudadCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //la provincia de la empresa
                if (!provCPField.getText().equals("")) {
                    error = Validation.esNombre(provCPField.getText());
                    if (!mostrarMensaje(error)) {
                        provincia = provCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el codigopostal de la empresa
                if (!codigoPostalCPField.getText().equals("")) {
                    error = Validation.esTexto(codigoPostalCPField.getText());
                    if (!mostrarMensaje(error)) {
                        codigoPostal = codigoPostalCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //el pais de la empresa
                if (!paisCPField.getText().equals("")) {
                    error = Validation.esTexto(paisCPField.getText());
                    if (!mostrarMensaje(error)) {
                        pais = paisCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                if (sePuede) {
                    System.out.println("Creando Entidad para empresa");
                    Entidad em = new Entidad(nombre, tipoDocumento, email, telefono, obser);
                    Direccion dir = new Direccion(em, nombreVia, numero, ciudad, provincia, codigoPostal, pais);
                    CliPro CliPro = new CliPro(em, clienteCPCheck.isSelected(), proveedorCPCheck.isSelected());
                    System.out.println("obj CliPro creada");
                    return CliPro;
                }

                break;
            case "Prod":
                sePuede = false;
                //creamos el obj producto

                //cogemos y comprobamos el dato nompro
                if (!nombreProField.getText().equals("")) {
                    error = Validation.esNombre(nombreProField.getText());
                    if (!mostrarMensaje(error)) {
                        nompro = nombreProField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //cogemos y comprovamos el descpro
                if (!descripProField.getText().equals("")) {
                    error = Validation.esTexto(descripProField.getText());
                    if (!mostrarMensaje(error)) {
                        descpro = descripProField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //cogemos y comprovamos el precio
                if (!precioProField.getText().equals("")) {
                    error = Validation.esDecimalPos(precioProField.getText());
                    if (!mostrarMensaje(error)) {
                        preciopro = precioProField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //cogemos y validamos el stock
                if (!stockProField.getText().equals("")) {
                    error = Validation.esEnteroPos(stockProField.getText());
                    if (!mostrarMensaje(error)) {
                        Stockpro = stockProField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //creamos la entidad producto
                if (sePuede) {
                    //recojemos el prov en concreto al que va asociado
                    Entidad prov = provList.getSelectionModel().getSelectedItem();
                    if (prov != null) {
                        System.out.println("Creando Entidad para producto");
                        Entidad prod = new Producto(nompro, descpro, Double.parseDouble(preciopro), Integer.parseInt(Stockpro), prov);
                        System.out.println("obj producto creada");
                        return prod;
                    }
                }
                break;
            case "Comp":
                //cogemos y validamos el nombre fiscal
                if (!NombreEmpField1.getText().equals("")) {
                    error = Validation.esNombre(NombreEmpField1.getText());
                    if (!mostrarMensaje(error)) {
                        nombreFiscFact = NombreEmpField1.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //ahora las observaciones
                observFact = observacionesEmpField1.getText();
                sePuede = true;

                //ahora cogemos el iva
                iva = splitMenuIVA.getText();
                if (iva.equalsIgnoreCase("--Tipo I.V.A--")) {
                    iva = "";
                    labelError.setTextFill(Color.RED);
                    labelError.setText("seleccione el tipo de I.V.A");
                    break;
                }
                //ahora la fecha de emision
                if (fechaEmisionDate.getValue() != null) {
                    error = Validation.esFechaFutura(fechaEmisionDate.getValue());
                    if (!mostrarMensaje(error)) {
                        fecha = fechaEmisionDate.getValue();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //creamos la entidad factura
                if (sePuede) {

                    System.out.println("Creando Entidad para factura");
                    Entidad fact = new Factura(fecha, "comp", nombreFiscFact, productosCantidad, productosAñadidos);
                    System.out.println("obj producto creada");
                    return fact;

                }
                break;
            case "Vent":
                //cogemos y validamos el nombre fiscal
                if (!NombreEmpField1.getText().equals("")) {
                    error = Validation.esNombre(NombreEmpField1.getText());
                    if (!mostrarMensaje(error)) {
                        nombreFiscFact = NombreEmpField1.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //ahora las observaciones
                error = Validation.esTexto(NombreEmpField1.getText());
                if (!mostrarMensaje(error)) {
                    observFact = observacionesEmpField1.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }

                //ahora cogemos el iva
                iva = splitMenuIVA.getText();
                if (iva.equalsIgnoreCase("--Tipo I.V.A--")) {
                    iva = "";
                    labelError.setTextFill(Color.RED);
                    labelError.setText("seleccione el tipo de I.V.A");
                    break;
                }
                //ahora la fecha de emision
                if (fechaEmisionDate.getValue() != null) {
                    error = Validation.esFechaFutura(fechaEmisionDate.getValue());
                    if (!mostrarMensaje(error)) {
                        fecha = fechaEmisionDate.getValue();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //creamos la entidad factura
                if (sePuede) {
                    System.out.println("Creando Entidad para factura");
                    Entidad fact = new Factura(fecha, "vent", nombreFiscFact, productosCantidad, productosAñadidos);
                    System.out.println("obj producto creada");
                    return fact;
                }
                break;
            default:
                System.out.println("Algo salio mal al añadir");
                break;
        }
        return e;
    }

    public void bloquearTextFields(Pane root) {
        for (Node node : root.getChildren()) {

            if (node instanceof TextField) {
                ((TextField) node).setEditable(false);
            }

            // Si el nodo es otro contenedor, lo recorremos también
            if (node instanceof Pane) {
                bloquearTextFields((Pane) node);
            }
        }
    }

    public void mostrarDatos() {
        if (accion.equals("delete")) {
            switch (tipo) {
                case "Emp":
                    bloquearTextFields(EmpresaPane);
                    break;
                case "CliPro":
                    bloquearTextFields(cliProvPane);
                    break;
                case "Prod":
                    bloquearTextFields(productosPane);
                    break;
                case "Vent":
                    bloquearTextFields(factPane);
                    break;
                case "Comp":
                    bloquearTextFields(factPane);
                    break;
                default:
                    System.out.println("Algo salio mal al rellenar los datos");
                    break;
            }
        }
        if (tipo != null) {
            switch (tipo) {
                case "Emp":
                    

                    DocumentoEmpField.setText(entidad.getNif());
                    NombreEmpField.setText(entidad.getNombre());
                    emailEmpField.setText(entidad.getEmail());
                    telefonoEmpField.setText(entidad.getTelefono());
                    observacionesEmpField.setText(entidad.getObservaciones());
                    //viaEmpField.setText(emp.getDir().getVia());
                    //numEmpField.setText(String.valueOf(emp.getDir().getNumero()));
                    //ciudadEmpField.setText(emp.getDir().getCiudad());
                    //provEmpField.setText(emp.getDir().getProvincia());
                   // paisEmpField.setText(emp.getDir().getPais());
                    //codigoPostalEmpField.setText(emp.getDir().getCp());
                    break;
                case "CliPro":

                    break;
                case "Prod":

                    break;
                case "Vent":

                    break;
                case "Comp":

                    break;
                default:
                    System.out.println("Algo salio mal al rellenar los datos");
                    break;
            }
        }
    }

    private void actualizarProvList() {
        //mostramos en el listview los proovedores para asociar dicho producto
        System.out.println("idEmpres:" + App.empresaActualId);
        List<Entidad> datos = dao.listarClientesYProveedores(App.empresaActualId);

        ArrayList<Entidad> mostrar = new ArrayList<Entidad>();
        //eliminamos los clientes de la lista
        for (Entidad x : datos) {
            if (x.isCliente() && x.isProveedor()) {
                mostrar.add(x);
            } else if (x.isProveedor()) {
                mostrar.add(x);
            }
        }
        //mostrar los dagtos en el listview provList
        ObservableList<Entidad> observableList = FXCollections.observableArrayList(mostrar);
        provList.setItems(observableList);
    }

    private boolean mostrarMensaje(validations.Error error) {
        if (error.isError()) {
            System.out.println("Algo salio mal en el form");
            labelError.setTextFill(error.getColor());
            labelError.setText(error.getMensaje());
        } else {
            labelError.setTextFill(Color.GREEN);
            labelError.setText("");
        }
        return error.isError();
    }

    @FXML
    private void establecerIVA() {
        javafx.event.EventHandler<javafx.event.ActionEvent> accionCambio = e -> {
            MenuItem itemPulsado = (MenuItem) e.getSource();
            splitMenuIVA.setText(itemPulsado.getText());

        };
        //Asigno la acción a los 3 documentos
        primero.setOnAction(accionCambio);
        segundo.setOnAction(accionCambio);
        tercero.setOnAction(accionCambio);
    }

    @FXML
    private void añadirProd(ActionEvent event) {

        int cantidad = 0;
        validations.Error err = Validation.esEnteroPos(cantProductFact.getText());
        if (!err.isError()) {
            //es numero entero pos
            cantidad = Integer.parseInt(cantProductFact.getText());
            productosCantidad.add(cantidad);
            //añadimos el prod
            productosAñadidos.add(productosList.getSelectionModel().getSelectedItem());
            //actualizamos
            actualizarListAdd();
        } else {
            mostrarMensaje(err);
        }
    }

    @FXML
    private void quitarProd(ActionEvent event) {
        productosAñadidos.remove(productosAddList.getSelectionModel().getSelectedItem());
        productosCantidad.remove(productosAddList.getSelectionModel().getSelectedIndex());
        actualizarListAdd();

    }

    @FXML
    private void provSelection(MouseEvent event) {

    }

    public void actualizarListAdd() {
        ObservableList<Entidad> observableLista = FXCollections.observableArrayList(productosAñadidos);
        productosAddList.setItems(observableLista);
    }

    public void actualizarListProd() {
        String filtro = "";
        productosList.getItems().setAll(
                dao.listarProductosPorEmpresa(Long.valueOf(App.empresaActualId)).stream()
                        .filter(p -> p.getNombre().toLowerCase().contains(filtro))
                        .collect(Collectors.toList())
        );
        System.out.println("Lista productos actualizada");
        System.out.println("Productos del DAO: " + dao.listarProductosPorEmpresa(Long.valueOf(App.empresaActualId)).size());

    }

    private void volverAtras() {
        try {
            if (tipo.equals("Emp")) {
                App.setRoot("primary");
            } else {
                App.setRoot("secondary");
            }
        } catch (IOException ex) {
            System.out.println("❌ Error al volver: " + ex.getMessage());
        }
    }

}
