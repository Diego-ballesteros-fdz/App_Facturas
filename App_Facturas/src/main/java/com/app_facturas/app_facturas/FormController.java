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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    private Pane buttonsPane;
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
    
    public void initialize(){
        
    }

    @FXML
    /**
     * volver a la pantalla anterior
     */
    private void volverAction(ActionEvent event) throws IOException{
        App.setRoot("secondary"); 
    }

    @FXML
    /**
     * limpiar contenido
     */
    private void limpiarAction(ActionEvent event) {
        try {
            Parent nuevoRoot = FXMLLoader.load(getClass().getResource("/com/app_facturas/app_facturas/formulary.fxml"));
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(nuevoRoot);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    /**
     * boton de enviar
     */
    private void enviarAction(ActionEvent event) {
    }

    @FXML
    /**
     * listener del click en productos en el listview
     */
    private void prodSelection(MouseEvent event) {
    }
    
}
