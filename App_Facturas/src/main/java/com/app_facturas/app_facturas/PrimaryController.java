/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.app_facturas.app_facturas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException{
        App.setRoot("secondary");
    }
    
}
