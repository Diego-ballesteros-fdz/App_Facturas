/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import objects.Entidad;

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
    
    

    public void initialize() {
        System.out.println("Tipo "+tipo);
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
                case "Fac":
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
        System.out.println("accion "+accion);
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
        
        //Cambiamos el texto del SplitMenuButton en función del tipo de documento seleccionado (NIF, NIE o CIF)
        javafx.event.EventHandler<javafx.event.ActionEvent> accionCambio = e -> {
            MenuItem itemPulsado = (MenuItem) e.getSource();
            docType.setText(itemPulsado.getText());
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
        if(tipo.equals("Emp")){
            App.setRoot("primary");
        }else{
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
    
    public void setEntidad(Entidad e){
        entidad=e;  
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
    }

}
