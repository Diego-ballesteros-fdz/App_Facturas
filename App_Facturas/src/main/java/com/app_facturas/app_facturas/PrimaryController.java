/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.app_facturas.app_facturas;

import connection.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class PrimaryController implements Initializable {

    @FXML
    private AnchorPane primary;
    @FXML
    private Button createButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;
    private SplitMenuButton menuEmpresas;
    private int idEmpresaSeleccionada = -1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpresas();
    }    
    
    private void cargarEmpresas() {
    menuEmpresas.getItems().clear();
    
    String sql = "SELECT idEntidad, nombre FROM entidad";

    try {
        Connection con = new ConexionBD().get();
        if (con == null) {
            System.out.println("Error: Conexión nula");
            return;
        }
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
             int id = rs.getInt("idEntidad"); 
            String nombre = rs.getString("nombre");

            MenuItem item = new MenuItem(nombre);
            
            item.setOnAction(event -> {
            try {
                App.empresaActualId = id;
                App.nombreEmpresaActual = nombre;
                App.setRoot("secondary");
            } catch (IOException e) {
                System.err.println("Error al cambiar de ventana: " + e.getMessage());
                e.printStackTrace();
            }
            });
            
            menuEmpresas.getItems().add(item);
            
        };
        rs.close();
        pst.close();
        con.close();
        } catch (SQLException e) {
            // Esto es lo que estaba pasando antes: "Column 'id' not found"
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchToSecondary(ActionEvent event) throws IOException{
        App.setRootWithParam("formulary", "Emp");   
    }

    @FXML
    private void crearEmpresa(ActionEvent event) {
    }
}
