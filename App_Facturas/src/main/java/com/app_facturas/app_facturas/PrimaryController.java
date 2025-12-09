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
    @FXML
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

        // SOLO empresas NO asociadas
        String sql =
            "SELECT idEntidad, nombre " +
            "FROM ENTIDAD " +
            "WHERE idEntidad NOT IN (SELECT idEntidad FROM ROLES_ENTIDAD)";

        try (Connection con = new ConexionBD().get();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

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
            }
            
             rs.close();
            pst.close();
            con.close();

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    @FXML
    private void crearEmpresa(ActionEvent event) throws IOException {
         App.setRootWithParam("formulary", "Emp","add");
    }

    @FXML
    private void borrarEmpresa(ActionEvent event)  throws IOException {
         App.setRootWithParam("busqueda", "Emp","delete");
    }

    @FXML
    private void modificarEmpresa(ActionEvent event)  throws IOException {
         App.setRootWithParam("busqueda", "Emp","modiffy");
    }
}
