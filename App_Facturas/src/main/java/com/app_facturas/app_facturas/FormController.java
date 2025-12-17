/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import connection.ConexionBD;
import connection.DAOController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import objects.CliPro;
import objects.Direccion;
import objects.Empresa;
import objects.Entidad;
import objects.Factura;
import objects.LineaFactura;
import objects.Producto;
import validations.Validation;

/**
 * Controlador principal para la gestion de formularios en la aplicacion de
 * facturacion. Esta clase se encarga de administrar la logica de la vista para
 * la creacion, modificacion y eliminacion de diversas entidades como Empresas,
 * Clientes, Proveedores, Productos y Facturas. * Gestiona la visibilidad de los
 * paneles segun el tipo de operacion y coordina la comunicacion entre la
 * interfaz de usuario JavaFX y la capa de acceso a datos (DAO).
 *
 * @author diego
 * @version 1.0
 */
public class FormController {

    /* --- Componentes de la Interfaz Grafica (FXML) --- */
    /**
     * Panel contenedor para la gestion de Clientes y Proveedores.
     */
    @FXML
    private Pane cliProvPane;

    /**
     * Grilla para organizar los campos del formulario de Clientes.
     */
    @FXML
    private GridPane gridClientes;

    // Campos de texto para Clientes/Proveedores
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

    /**
     * Checkbox para marcar si la entidad es un Proveedor.
     */
    @FXML
    private CheckBox proveedorCPCheck;

    /**
     * Checkbox para marcar si la entidad es un Cliente.
     */
    @FXML
    private CheckBox clienteCPCheck;

    /**
     * Panel contenedor para la gestion de la Empresa propia.
     */
    @FXML
    private Pane EmpresaPane;

    @FXML
    private GridPane gridEmpresa;

    // Campos de texto para Empresa
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

    /**
     * Panel contenedor para la gestion de Productos.
     */
    @FXML
    private Pane productosPane;

    /**
     * Panel contenedor para la gestion de Facturas.
     */
    @FXML
    private Pane factPane;

    @FXML
    private HBox hboxFacturas;

    @FXML
    private TextField observacionesEmpField1;

    /* --- Variables de Estado y Control --- */
    /**
     * * Define el tipo de formulario que se esta mostrando. Valores posibles:
     * "Emp" (Empresa), "CliPro" (Cliente/Proveedor), "Prod" (Producto), "Vent"
     * (Venta), "Comp" (Compra).
     */
    private String tipo;

    /**
     * * Define la accion que se va a realizar sobre la entidad. Valores
     * posibles: "add" (Añadir), "modiffy" (Modificar), "delete" (Eliminar).
     */
    private String accion;

    // Paneles de botones segun la accion
    @FXML
    private Pane buttonsPaneAñadir;
    @FXML
    private Pane buttonsPaneModificar;
    @FXML
    private Pane buttonsPaneEliminar;

    // Etiquetas de titulos dinamicos
    @FXML
    private Label nomEmp;
    @FXML
    private Label nomEmpProd;
    @FXML
    private Label nomEmpCliProv;

    /**
     * Selector del tipo de documento (NIF, CIF, NIE) para Empresa.
     */
    @FXML
    private SplitMenuButton docType;

    @FXML
    private MenuItem nifType;
    @FXML
    private MenuItem nieType;
    @FXML
    private MenuItem cifType;

    /**
     * Nombre de la empresa actual en sesion.
     */
    private String nombreEmpresa = App.nombreEmpresaActual;

    /**
     * * Objeto generico que almacena la entidad que se esta editando o
     * visualizando. Puede ser casteado a CliPro, Empresa o Producto segun el
     * contexto.
     */
    private Entidad entidad;

    @FXML
    private Label labelError;

    /**
     * Controlador de Acceso a Datos para interactuar con la base de datos.
     */
    private DAOController dao = new DAOController();

    @FXML
    private ListView<Entidad> provList;

    // Campos de producto
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

    /**
     * Lista visual de productos disponibles para añadir a factura.
     */
    @FXML
    private ListView<Entidad> productosList;

    /**
     * Lista visual de productos ya añadidos a la factura actual.
     */
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

    /**
     * Lista en memoria de los productos seleccionados para la factura.
     */
    private ArrayList<Entidad> productosAñadidos = new ArrayList<Entidad>();

    /**
     * Lista paralela que almacena las cantidades de los productos añadidos.
     */
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

    @FXML
    private SplitMenuButton NombreEmpFactSplit;

    /**
     * Inicializa el controlador y configura el estado inicial de la vista. Este
     * metodo es llamado automaticamente por JavaFX despues de cargar el FXML. *
     * Realiza las siguientes tareas: 1. Vincula propiedades de tamaño (binding)
     * para diseño responsivo. 2. Determina que paneles mostrar basandose en la
     * variable 'tipo'. 3. Carga listas de datos necesarias (proveedores,
     * productos) mediante hilos secundarios si es necesario. 4. Si existen
     * datos previos ('entidad' y 'accion'), llama a mostrarDatos(). 5.
     * Configura la visibilidad de los botones segun la 'accion' (añadir,
     * modificar, eliminar).
     */
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
                    generarEmpresas();
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
                    generarEmpresas();
                    Platform.runLater(() -> {
                        System.out.println("RunLater ejecutado");
                        actualizarListProd();
                    });

                    break;
                default:
                    System.out.println("Algo salio mal al iniciar el formulario");
                    break;
            }
            if (tipo != null && entidad != null && accion != null) {
                mostrarDatos();
            }
        }
        System.out.println("accion " + accion);
        if (accion != null) {
            System.out.println("Entra al segundo switch");
            switch (accion) {
                case "add":
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

    /**
     * Maneja el evento de seleccion de un tipo de documento en el menu
     * desplegable para Empresas. Actualiza el texto del boton principal con la
     * opcion seleccionada.
     *
     * @param event El evento de accion generado por el MenuItem.
     */
    @FXML
    public void establecerTipoDoc(ActionEvent event) {

        MenuItem itemPulsado = (MenuItem) event.getSource();

        // Establecemos el texto del SplitMenuButton al texto del MenuItem
        docType.setText(itemPulsado.getText());

        System.out.println("Se asigna el tipo: " + itemPulsado.getText());
    }

    /**
     * Maneja el evento de seleccion de un tipo de documento en el menu
     * desplegable secundario (Clientes/Prov). Actualiza el texto del boton
     * principal con la opcion seleccionada.
     *
     * @param event El evento de accion generado por el MenuItem.
     */
    @FXML
    public void establecerTipoDoc2(ActionEvent event) {

        MenuItem itemPulsado = (MenuItem) event.getSource();

        // Establecemos el texto del SplitMenuButton al texto del MenuItem
        doctype2.setText(itemPulsado.getText());

        System.out.println("Se asigna el tipo: " + itemPulsado.getText());
    }

    /**
     * Ejecuta la accion de volver a la pantalla anterior. Determina la ruta de
     * navegacion basandose en si el usuario esta en modo Empresa u otro.
     *
     * @param event El evento del boton volver.
     * @throws IOException Si ocurre un error al cargar la vista FXML de destino
     * (primary o secondary).
     */
    @FXML
    private void volverAction(ActionEvent event) throws IOException {
        if (tipo.equals("Emp")) {
            App.setRoot("primary");
        } else {
            App.setRoot("secondary");
        }

    }

    /**
     * Limpia el contenido del formulario actual recargando la vista. Utiliza
     * los parametros actuales de 'tipo' y 'accion' para mantener el contexto.
     *
     * @param event El evento del boton limpiar.
     * @see App#setRootWithParam(String, String, String)
     */
    @FXML
    private void limpiarAction(ActionEvent event) {
        try {
            App.setRootWithParam("formulary", tipo, accion);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Establece el tipo de entidad con la que trabajara el formulario.
     *
     * @param tipo Cadena que representa el tipo ("Emp", "CliPro", etc.).
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece la accion a realizar y reinicializa la vista si es necesario.
     * Si no hay una entidad cargada previamente, fuerza una reinicializacion
     * para actualizar la UI.
     *
     * @param accion Cadena que representa la accion ("add", "modiffy",
     * "delete").
     */
    public void setAccion(String accion) {
        this.accion = accion;
        if (entidad == null) {
            initialize();//actualizamos la vista
        }

    }

    /**
     * Inyecta una entidad existente en el controlador para su edicion o
     * eliminacion. Al establecer la entidad, se llama a initialize() para
     * reflejar los datos en la vista.
     *
     * @param e Objeto que implementa la clase base Entidad.
     */
    public void setEntidad(Entidad e) {
        entidad = e;
        initialize();
    }

    /**
     * Ejecuta la logica para modificar una entidad existente en la base de
     * datos. Valida los datos del formulario creando un objeto temporal, y si
     * es valido, invoca al metodo correspondiente del DAO segun el tipo de
     * entidad. * Para Clientes/Proveedores, tambien gestiona la actualizacion
     * de roles en la BD.
     *
     * @param event El evento del boton modificar.
     */
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
                CliPro cp = (CliPro) obj;

                System.out.println(cp.getEntidad().getNombre());
                // actualizar roles
                dao.eliminarRoles(cp.getEntidad().getNombre());

                if (cp.isIsCliente()) {
                    dao.agregarRolEntidad(cp.getEntidad().getNombre(), "CLIENTE");
                }
                if (cp.isIsProveedor()) {
                    dao.agregarRolEntidad(cp.getEntidad().getNombre(), "PROVEEDOR");
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

    /**
     * Ejecuta la logica para eliminar una entidad de la base de datos. Utiliza
     * el nombre de la entidad como clave para la eliminacion.
     *
     * @param event El evento del boton eliminar.
     */
    @FXML
    private void eliminarAction(ActionEvent event) {

        if (entidad == null) {
            return;
        }

        switch (tipo) {

            case "Emp":
            case "CliPro":
                dao.eliminarEntidad(entidad.getNombre());
                break;

            case "Prod":
                dao.eliminarProducto(entidad.getNombre());
                break;
        }

        volverAtras();
    }

    /**
     * Accion principal para enviar/guardar un nuevo registro. Recopila los
     * datos del formulario, crea la entidad y llama al DAO para la insercion. *
     * Maneja logica especifica para: - Empresas: Insercion estandar. -
     * Clientes/Proveedores: Vinculacion con la empresa actual. - Facturas
     * (Ventas/Compras): Verifica que existan lineas de factura antes de guardar
     * y gestiona la transaccion completa (cabecera + lineas).
     *
     * @param event El evento del boton enviar.
     */
    @FXML
    private void enviarAction(ActionEvent event) {

        Entidad obj = crearEntidad();

        if (obj != null) {
            boolean exito = true;
            switch (tipo) {
                case "Emp":

                    Empresa emp = (Empresa) obj;
                    //dao.crearEmpresa(emp);
                    //dao.agregarDireccion(emp.getDir());
                    if (dao.insertarEmpresa(emp)) {
                        System.out.println("Empresa creada");
                        volverAtras();
                    } else {
                        System.out.println("Algo salio mal al crear empresa");
                        validations.Error er = new validations.Error(true, "No se pudo añadir la empresa", Color.RED);
                        mostrarMensaje(er);
                    }
                    break;

                case "CliPro":

                    CliPro cp = (CliPro) obj;

                    if (dao.insertarCliPro(cp, App.empresaActualId)) {
                        System.out.println("CliPro añadido");
                        volverAtras();
                    } else {
                        validations.Error er = new validations.Error(true, "No se pudo añadir el Cliente/Proveedor", Color.RED);
                        mostrarMensaje(er);

                    }

                    break;

                case "Prod":
                    Producto prod = (Producto) obj;

                    dao.crearProducto((Producto) obj);
                    volverAtras();
                    break;

                case "Comp":
                    Factura fac = (Factura) obj;

                    if (productosAñadidos.isEmpty()) {
                        mostrarMensaje(new validations.Error(true, "No se puede guardar una factura sin productos", Color.RED));
                        return;
                    }

                    List<LineaFactura> lineasParaGuardar = new ArrayList<>();

                    for (int i = 0; i < productosAñadidos.size(); i++) {
                        LineaFactura lf = new LineaFactura();

                        Producto p = (Producto) productosAñadidos.get(i);
                        Integer cantidad = productosCantidad.get(i);

                        lf.setProducto(p);
                        lf.setCantidad(cantidad);
                        lf.setPrecioUnitario(p.getPrecio());

                        lineasParaGuardar.add(lf);
                    }

                    System.out.println("Factura a añadir: " + fac);
                    if (dao.registrarFactura(fac, lineasParaGuardar)) {
                        System.out.println("Factura guardada correctamente con sus líneas.");
                    } else {
                        System.out.println("Error al guardar la factura completa.");
                        exito = false;
                    }

                    volverAtras();
                    //dao.crearFactura(fac);
                    break;

                case "Vent":
                    fac = (Factura) obj;

                    if (productosAñadidos.isEmpty()) {
                        mostrarMensaje(new validations.Error(true, "No se puede guardar una factura sin productos", Color.RED));
                        return;
                    }

                    lineasParaGuardar = new ArrayList<>();

                    for (int i = 0; i < productosAñadidos.size(); i++) {
                        LineaFactura lf = new LineaFactura();

                        Producto p = (Producto) productosAñadidos.get(i);
                        Integer cantidad = productosCantidad.get(i);

                        lf.setProducto(p);
                        lf.setCantidad(cantidad);
                        lf.setPrecioUnitario(p.getPrecio());

                        lineasParaGuardar.add(lf);
                    }

                    System.out.println("Factura a añadir: " + fac);
                    if (dao.registrarFactura(fac, lineasParaGuardar)) {
                        System.out.println("Factura guardada correctamente con sus líneas.");
                    } else {
                        System.out.println("Error al guardar la factura completa.");
                        exito = false;
                    }

                    volverAtras();
                    //dao.crearFactura(fac);
                    break;
            }

        } else {

        }
    }

    /**
     * Metodo factoria que construye una instancia de Entidad basandose en los
     * datos del formulario. Realiza validaciones exhaustivas de todos los
     * campos (NIF, email, telefono, etc.) antes de crear el objeto. * Este
     * metodo es crucial ya que centraliza la extraccion y validacion de datos
     * de la UI. Si alguna validacion falla, muestra un mensaje de error en la
     * interfaz y retorna null.
     *
     * @return Una instancia de Empresa, CliPro, Producto o Factura segun el
     * 'tipo' actual, o null si alguna validacion falla.
     */
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
                iva = "",
                id = "";
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
                    case "Documento":
                        error = new validations.Error(true, "debe seleccionar un tipo de documento", Color.RED);
                        break;

                }
                //si no ha habido fallos almacenamos la info
                if (!mostrarMensaje(error, DocumentoEmpField)) {
                    tipoDocumento = DocumentoEmpField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el nombre de la empresa
                error = Validation.esNombre(NombreEmpField.getText());
                if (!mostrarMensaje(error, NombreEmpField)) {
                    nombre = NombreEmpField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el email de la empresa
                if (!emailEmpField.getText().equals("")) {
                    error = Validation.esEmail(emailEmpField.getText());
                    if (!mostrarMensaje(error, emailEmpField)) {
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
                    if (!mostrarMensaje(error, telefonoEmpField)) {
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
                    if (!mostrarMensaje(error, viaEmpField)) {
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
                    if (!mostrarMensaje(error, numEmpField)) {
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
                    if (!mostrarMensaje(error, ciudadEmpField)) {
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
                    if (!mostrarMensaje(error, provEmpField)) {
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
                    if (!mostrarMensaje(error, codigoPostalCPField)) {
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
                    if (!mostrarMensaje(error, paisEmpField)) {
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
                    Empresa empresa = new Empresa(em, dir);
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
                if (!mostrarMensaje(error, DocumentoCPField)) {
                    tipoDocumento = DocumentoCPField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el nombre de la empresa
                error = Validation.esNombre(NombreCPField.getText());
                if (!mostrarMensaje(error, NombreCPField)) {
                    nombre = NombreCPField.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }
                //el email de la empresa
                if (!emailCPField.getText().equals("")) {
                    error = Validation.esEmail(emailCPField.getText());
                    if (!mostrarMensaje(error, emailCPField)) {
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
                    if (!mostrarMensaje(error, telefonoCPField)) {
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
                    if (!mostrarMensaje(error, viaCPField)) {
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
                    if (!mostrarMensaje(error, numCPField)) {
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
                    if (!mostrarMensaje(error, ciudadCPField)) {
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
                    if (!mostrarMensaje(error, provCPField)) {
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
                    if (!mostrarMensaje(error, codigoPostalCPField)) {
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
                    if (!mostrarMensaje(error, paisCPField)) {
                        pais = paisCPField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                if (sePuede) {
                    Entidad em = new Entidad(nombre, tipoDocumento, email, telefono, obser);
                    Direccion dir = new Direccion(em, nombreVia, numero, ciudad, provincia, codigoPostal, pais);
                    CliPro CliPro = new CliPro(em, clienteCPCheck.isSelected(), proveedorCPCheck.isSelected(), dir);
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
                    if (!mostrarMensaje(error, nombreProField)) {
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
                    if (!mostrarMensaje(error, descripProField)) {
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
                    if (!mostrarMensaje(error, precioProField)) {
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
                    if (!mostrarMensaje(error, stockProField)) {
                        Stockpro = stockProField.getText();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                }
                //creamos la entidad producto
                if (sePuede) {

                    // ... (Lógica interna del case "Comp") ...
                    System.out.println("Creando Entidad para factura");
                    Entidad fact = new Factura(fecha, "comp", nombreFiscFact, productosCantidad, productosAñadidos);
                    System.out.println("obj factura creada: " + fact);
                    return fact;
                }
                break;

            case "Vent":
                // Validaciones para Venta (Nombre fiscal, Observaciones, IVA, Fecha)
                if (!NombreEmpFactSplit.getText().equals("Seleccione Empresa")) {
                    nombreFiscFact = NombreEmpFactSplit.getText();
                    sePuede = true;
                } else {
                    mostrarMensaje(new validations.Error(true, "debe seleccionar un nombre de empresa.", Color.RED));
                    sePuede = false;
                    break;
                }

                error = Validation.esTexto(observacionesEmpField1.getText());
                if (!mostrarMensaje(error, observacionesEmpField1)) {
                    observFact = observacionesEmpField1.getText();
                    sePuede = true;
                } else {
                    sePuede = false;
                    break;
                }

                iva = splitMenuIVA.getText();
                if (iva.equalsIgnoreCase("--Tipo I.V.A--")) {
                    iva = "";
                    labelError.setTextFill(Color.RED);
                    labelError.setText("seleccione el tipo de I.V.A");
                    break;
                }

                if (fechaEmisionDate.getValue() != null) {
                    error = Validation.esFechaFutura(fechaEmisionDate.getValue());
                    if (!mostrarMensaje(error)) {
                        fecha = fechaEmisionDate.getValue();
                        sePuede = true;
                    } else {
                        sePuede = false;
                        break;
                    }
                } else {
                    mostrarMensaje(new validations.Error(true, "debe rellenar el campo de la fecha", Color.RED));
                    sePuede = false;
                    break;
                }

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

    /**
     * Recorre recursivamente un contenedor (Pane) y sus hijos para deshabilitar
     * la edición de todos los campos de texto (TextField) encontrados. Útil
     * para bloquear la interfaz en modos de visualización o eliminación.
     *
     * @param root El nodo contenedor raíz (Pane) desde donde comenzar la
     * búsqueda.
     */
    public void bloquearTextFields(Pane root) {
        for (Node node : root.getChildren()) {

            if (node instanceof TextField) {
                ((TextField) node).setEditable(false);
            }

            // Si el nodo es otro contenedor, lo recorremos también (recursividad)
            if (node instanceof Pane) {
                bloquearTextFields((Pane) node);
            }
        }
    }

    /**
     * Rellena los campos de la interfaz gráfica con los datos de la entidad
     * actual. Determina qué campos rellenar basándose en el tipo de entidad
     * (Empresa, CliPro, Prod, Vent/Comp). Si la acción es "delete", bloquea los
     * campos para prevenir edición.
     */
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
                    Empresa em = (Empresa) entidad;
                    DocumentoEmpField.setText(em.getNif());
                    NombreEmpField.setText(em.getNombre());
                    emailEmpField.setText(em.getEmail());
                    telefonoEmpField.setText(em.getTelefono());
                    observacionesEmpField.setText(em.getObservaciones());
                    viaEmpField.setText(em.getDir().getVia());
                    numEmpField.setText(String.valueOf(em.getDir().getNumero()));
                    ciudadEmpField.setText(em.getDir().getCiudad());
                    provEmpField.setText(em.getDir().getProvincia());
                    paisEmpField.setText(em.getDir().getPais());
                    codigoPostalEmpField.setText(em.getDir().getCp());
                    break;

                case "CliPro":
                    CliPro cp = (CliPro) entidad; // casteo directo
                    DocumentoCPField.setText(cp.getNif());
                    NombreCPField.setText(cp.getNombre());
                    emailCPField.setText(cp.getEmail());
                    telefonoCPField.setText(cp.getTelefono());
                    observacionesCPField.setText(cp.getObservaciones());
                    viaCPField.setText(cp.getDir().getVia());
                    numCPField.setText(String.valueOf(cp.getDir().getNumero()));
                    ciudadCPField.setText(cp.getDir().getCiudad());
                    provCPField.setText(cp.getDir().getProvincia());
                    paisCPField.setText(cp.getDir().getPais());
                    codigoPostalCPField.setText(cp.getDir().getCp());
                    proveedorCPCheck.setSelected(cp.isIsProveedor());
                    clienteCPCheck.setSelected(cp.isIsCliente());
                    break;

                case "Prod":
                    Producto p = (Producto) entidad;
                    nombreProField.setText(p.getNombre());
                    descripProField.setText(p.getDescripcion());
                    precioProField.setText(String.valueOf(p.getPrecio()));
                    stockProField.setText(String.valueOf(p.getStock()));

                    if (p.getProveedor() != null) {
                        provList.getSelectionModel().select(p.getProveedor());
                    } else {
                        provList.getSelectionModel().clearSelection();
                    }
                    break;

                default:
                    System.out.println("Algo salio mal al rellenar los datos");
                    break;
            }
        }
    }

    /**
     * Obtiene la lista de clientes y proveedores desde la base de datos, filtra
     * solo aquellos que son proveedores y actualiza el ListView
     * correspondiente.
     */
    private void actualizarProvList() {
        System.out.println("idEmpres:" + App.empresaActualId);
        List<CliPro> datos = dao.listarClientesYProveedores(App.empresaActualId);

        ArrayList<CliPro> mostrar = new ArrayList<CliPro>();
        // Filtramos para obtener solo proveedores
        for (CliPro x : datos) {
            if (x.isIsCliente() && x.isIsProveedor()) {
                mostrar.add(x);
            } else if (x.isIsProveedor()) {
                mostrar.add(x);
            }
        }
        // Mostrar los datos en el listview provList
        ObservableList<Entidad> observableList = FXCollections.observableArrayList(mostrar);
        provList.setItems(observableList);
    }

    /**
     * Muestra un mensaje de error o éxito en la interfaz y aplica un estilo
     * visual al campo afectado. Si hay error, el borde del campo se vuelve
     * rojo.
     *
     * @param error Objeto que contiene el estado del error, el mensaje y el
     * color.
     * @param field El TextField que se resaltará en caso de error.
     * @return true si existe un error, false en caso contrario.
     */
    private boolean mostrarMensaje(validations.Error error, TextField field) {
        if (error.isError()) {
            System.out.println("Algo salio mal en el form");
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            labelError.setTextFill(error.getColor());
            labelError.setText(error.getMensaje());
        } else {
            labelError.setTextFill(Color.GREEN);
            labelError.setText("");
            field.setStyle("");
        }
        return error.isError();
    }

    /**
     * Sobrecarga del método mostrarMensaje. Muestra un mensaje en la etiqueta
     * de error sin modificar el estilo de ningún campo de texto específico.
     *
     * @param error Objeto que contiene el estado del error, el mensaje y el
     * color.
     * @return true si existe un error, false en caso contrario.
     */
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

    /**
     * Configura los eventos de acción para los elementos del menú de IVA. Al
     * seleccionar una opción, actualiza el texto del botón principal
     * (SplitMenuButton).
     */
    @FXML
    private void establecerIVA() {
        javafx.event.EventHandler<javafx.event.ActionEvent> accionCambio = e -> {
            MenuItem itemPulsado = (MenuItem) e.getSource();
            splitMenuIVA.setText(itemPulsado.getText());
        };
        // Asigno la acción a los 3 documentos (opciones de IVA)
        primero.setOnAction(accionCambio);
        segundo.setOnAction(accionCambio);
        tercero.setOnAction(accionCambio);
    }

    /**
     * Manejador de evento para añadir un producto a la lista temporal de la
     * factura actual. Valida que la cantidad sea un entero positivo antes de
     * añadir.
     *
     * @param event El evento de acción disparado por el botón.
     */
    @FXML
    private void añadirProd(ActionEvent event) {
        int cantidad = 0;
        validations.Error err = Validation.esEnteroPos(cantProductFact.getText());
        if (!err.isError()) {
            // Es numero entero positivo
            cantidad = Integer.parseInt(cantProductFact.getText());
            productosCantidad.add(cantidad);
            // Añadimos el producto seleccionado
            productosAñadidos.add(productosList.getSelectionModel().getSelectedItem());
            // Actualizamos la vista
            actualizarListAdd();
        } else {
            mostrarMensaje(err, cantProductFact);
        }
    }

    /**
     * Manejador de evento para quitar un producto seleccionado de la lista
     * temporal de la factura. Elimina tanto el producto como su cantidad
     * asociada.
     *
     * @param event El evento de acción disparado por el botón.
     */
    @FXML
    private void quitarProd(ActionEvent event) {
        productosAñadidos.remove(productosAddList.getSelectionModel().getSelectedItem());
        productosCantidad.remove(productosAddList.getSelectionModel().getSelectedIndex());
        actualizarListAdd();
    }

    /**
     * Manejador para eventos de ratón en la selección de proveedores.
     * (Actualmente vacío).
     *
     * @param event El evento del ratón.
     */
    @FXML
    private void provSelection(MouseEvent event) {
        // Pendiente de implementación o uso futuro
    }

    /**
     * Actualiza el ListView de productos añadidos a la factura actual.
     * Convierte la lista local `productosAñadidos` en una ObservableList.
     */
    public void actualizarListAdd() {
        ObservableList<Entidad> observableLista = FXCollections.observableArrayList(productosAñadidos);
        productosAddList.setItems(observableLista);
    }

    /**
     * Actualiza la lista principal de productos disponibles recuperándolos de
     * la base de datos y aplicando un filtro por nombre (actualmente filtro
     * vacío).
     */
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

    /**
     * Navega de vuelta a la pantalla anterior dependiendo del tipo de entidad
     * actual. Si es "Emp" vuelve a primary, en caso contrario a secondary.
     */
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

    /**
     * Genera y rellena el menú desplegable de selección de empresas
     * (NombreEmpFactSplit). Realiza una consulta SQL compleja para obtener
     * entidades relacionadas (Clientes/Proveedores) con la empresa actual.
     */
    private void generarEmpresas() {

        NombreEmpFactSplit.getItems().clear();
        CliPro cp = null;

        // SQL: Selecciona entidades hijas relacionadas con roles de CLIENTE o PROVEEDOR
        String sql = "SELECT "
                + "e.idEntidad, "
                + "e.nombre, "
                + "e.nif, "
                + "r.rol, "
                + "er.tipoRelacion "
                + "FROM EMPRESA_RELACION er "
                + "JOIN ENTIDAD e ON e.idEntidad = er.idHija "
                + "LEFT JOIN ROLES_ENTIDAD r ON r.idEntidad = e.idEntidad "
                + "WHERE er.idPadre = " + App.empresaActualId
                + " AND r.rol IN ('CLIENTE', 'PROVEEDOR');";

        try (Connection con = new ConexionBD().get(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idEntidad");
                String nombre = rs.getString("nombre");

                MenuItem item = new MenuItem(nombre);
                cp = new CliPro(id); // Nota: Se instancia pero no se guarda en una lista aquí

                item.setOnAction(evento -> {
                    NombreEmpFactSplit.setText(id + "-" + nombre);
                });

                NombreEmpFactSplit.getItems().add(item);
            }
            // Los recursos se cierran automáticamente gracias al try-with-resources
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    /**
     * Evento que dispara la generación de la lista de empresas disponibles para
     * facturación.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void provSelectionAction(ActionEvent event) {
        generarEmpresas();
    }
}
