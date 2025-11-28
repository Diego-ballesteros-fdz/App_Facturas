/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import connection.DAOController;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import objects.CliPro;
import objects.Direccion;
import objects.Empresa;
import objects.Entidad;
import validations.Validation;

/**
 *
 * @author diego
 */
public class FormController {

    @FXML
    private Pane cliProvPane;
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
    private TextField DocumentoEmpField11;
    @FXML
    private TextField NombreEmpField11;
    @FXML
    private TextField emailEmpField11;
    @FXML
    private TextField telefonoEmpField11;

    @FXML
    private Pane factPane;
    @FXML
    private TextField NombreEmpField1;
    @FXML
    private TextField observacionesEmpField1;
    @FXML
    private TextField provEmpField1;
    @FXML
    private TextField paisEmpField1;

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
    
    private DAOController dao=new DAOController();
    @FXML
    private ListView<?> provList;

    public void initialize() {
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
                    break;
                case "Vent":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(true);
                    nomEmp.setText(nombreEmpresa);
                    break;  
                case "Comp":
                    cliProvPane.setVisible(false);
                    productosPane.setVisible(false);
                    EmpresaPane.setVisible(false);
                    factPane.setVisible(true);
                    nomEmp.setText(nombreEmpresa);
                    break;    
                default:
                    System.out.println("Algo salio mal al iniciar el formulario");
                    break;
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

    @FXML
    /**
     * listener del click en productos en el listview
     */
    private void prodSelection(MouseEvent event) {
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAccion(String accion) {
        this.accion = accion;
        initialize();//actualizamos la vista
    }

    public void setEntidad(Entidad e) {
        entidad = e;
    }

    @FXML
    private void modificarAction(ActionEvent event) {
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
    }

    @FXML
    /**
     * boton de enviar
     */
    private void enviarAction(ActionEvent event) {
       Entidad e= crearEntidad();
        System.out.println(e);
    }

    public Entidad crearEntidad() {
        Entidad e = null;
        validations.Error error = null;
        boolean sePuede = false;
        String tipoDocumento="",
                 nombre="",
                 email="",
                 telefono="",
                 obser="",
                 nombreVia="",
                 numero="",
                 ciudad="",
                 provincia="",
                 codigoPostal="",
                 pais="";

        switch (tipo) {
            case "Emp":
                //creamos el obj empresa de los campos field
                
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
                        error=new validations.Error(true,"debe seleccionar un tipo de documento",Color.RED);
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
                if(!emailEmpField.getText().equals("")){
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
                if(!telefonoEmpField.getText().equals("")){
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
                if(!viaEmpField.getText().equals("")){
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
                if(!numEmpField.getText().equals("")){
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
                if(!ciudadEmpField.getText().equals("")){
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
                if(!provEmpField.getText().equals("")){
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
                if(!codigoPostalEmpField.getText().equals("")){
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
                if(!paisEmpField.getText().equals("")){
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
                    Direccion dir= new Direccion(em,nombreVia,numero,ciudad,provincia,codigoPostal,pais);
                    Empresa empresa=new Empresa(em);
                    System.out.println("obj Empresa creada");
                    return empresa;
                }

                break;
            case "CliPro":
                //creamos el obj clipro de los campos field
                //verificamos el tipo de documento
                tipoDoc = docType.getText();

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
                        error=new validations.Error(true,"debe seleccionar un tipo de documento",Color.RED);
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
                if(!emailEmpField.getText().equals("")){
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
                if(!telefonoEmpField.getText().equals("")){
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
                if(!viaEmpField.getText().equals("")){
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
                if(!numEmpField.getText().equals("")){
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
                if(!ciudadEmpField.getText().equals("")){
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
                if(!provEmpField.getText().equals("")){
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
                if(!codigoPostalEmpField.getText().equals("")){
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
                if(!paisEmpField.getText().equals("")){
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
                    Direccion dir= new Direccion(em,nombreVia,numero,ciudad,provincia,codigoPostal,pais);
                    CliPro CliPro=new CliPro(em, clienteCPCheck.isSelected() ,proveedorCPCheck.isSelected());
                    System.out.println("obj CliPro creada");
                    return CliPro;
                }
                
                break;
            case "Prod":
                //mostramos en el listview los proovedores para asociar dicho producto
                System.out.println("idEmpres:"+App.empresaActualId);
                List<Entidad> datos = dao.listarClientesYProveedores(App.empresaActualId);
                //mostrar los dagtos en el listview provList
                //creamos el obj producto
                
                break;
            case "Fac":
                //creamos el obj factura
                break;
            default:
                System.out.println("Algo salio mal al añadir");
                break;
        }
        return e;
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
}
