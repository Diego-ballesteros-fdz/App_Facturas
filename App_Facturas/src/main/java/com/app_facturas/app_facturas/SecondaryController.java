package com.app_facturas.app_facturas;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class SecondaryController implements Initializable{

    @FXML
    private Label nombreEmpresaCabecera;
    @FXML
    private MenuButton provCliOptions;
    @FXML
    private MenuButton factOptions;
    @FXML
    private MenuButton prodOptions;
    
    @FXML 
    private MenuItem add;
    @FXML 
    private MenuItem delete;
    @FXML 
    private MenuItem modify;
    @FXML 
    private MenuItem search;
    @FXML
    private AnchorPane secondary;
    @FXML
    private ImageView logo;
    @FXML
    private Button boton;
    
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("formulary");
    }
    @FXML
    private void buscarCliente() {
        abrirBusqueda("cliente");
    }

    private void buscarProveedor() {
        abrirBusqueda("proveedor");
    }

    @FXML
    private void buscarProducto() {
        abrirBusqueda("producto");
    }

    @FXML
    private void buscarFactura() {
        abrirBusqueda("factura");
    }
    
    public String nombreEmp;
    public int idEmp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Guardamos nombre e id de la empresa para filtrar búsquedas
        nombreEmp = App.nombreEmpresaActual;
        idEmp = App.empresaActualId;
        nombreEmpresaCabecera.setText(nombreEmp);
        
    }

    private void abrirBusqueda(String tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Busqueda.fxml"));
            Parent root = loader.load();

            BuscarEntidadController controller = loader.getController();
            controller.setTipo(tipo);

            App.setRoot("Busqueda");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void accionesProductos(ActionEvent event) throws IOException{
        // Averiguamos qué item se pulsó
        MenuItem itemPulsado = (MenuItem) event.getSource();

        // Recogemos en un String el nombre del Menuitem pulsado para filtrar en el switch
        String accion = (String) itemPulsado.getUserData(); 

        int idEmpresa = App.empresaActualId; 

        switch (accion) {
            case "add":
                App.setRoot("formulary");
                break;

            case "delete":
                App.setRoot("formulary");
                break;

            case "modify":
                App.setRoot("formulary");
                break;

            case "search":
                App.setRoot("formulary");
                break;

            default:
                break;
        }
    }
}