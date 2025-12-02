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

public class SecondaryController implements Initializable {

    @FXML
    private Label nombreEmpresaCabecera;
    @FXML
    private MenuButton provCliOptions;
    @FXML
    private MenuButton comprasOptions;
    @FXML
    private MenuButton ventasOptions;
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
    private MenuItem addProvCli;
    @FXML
    private MenuItem deleteProvCli;
    @FXML
    private MenuItem modifyProvCli;
    @FXML
    private MenuItem searchProvCli;
    @FXML
    private MenuItem addP;
    @FXML
    private MenuItem deleteP;
    @FXML
    private MenuItem modifyP;
    @FXML
    private MenuItem searchP;

    private void buscarCliente() throws IOException {
        App.setRootWithParam("busqueda", "cliente", null);
    }

    private void buscarProveedor() throws IOException {
        App.setRootWithParam("busqueda", "proveedor", null);
    }

    @FXML
    private void buscarProducto() throws IOException {
        App.setRootWithParam("busqueda", "Prod", null);
    }

    @FXML
    private void buscarComp() throws IOException {
        App.setRootWithParam("busqueda", "Comp", null);
    }
    @FXML
    private void buscarVent() throws IOException {
        App.setRootWithParam("busqueda", "Vent", null);
    }

    @FXML
    private void buscarCliProv() throws IOException {
        App.setRootWithParam("busqueda", "CliPro", null);

    }
    public String nombreEmp;
    public int idEmp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Guardamos nombre e id de la empresa para filtrar búsquedas
        nombreEmp = App.nombreEmpresaActual;
        idEmp = App.empresaActualId;
        nombreEmpresaCabecera.setText(nombreEmp);

    }

    @FXML
    private void añadirCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "CliPro", "add");
    }

    @FXML
    private void eliminarCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "CliPro", "delete");
    }

    @FXML
    private void modificarCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "CliPro", "modiffy");
    }

    @FXML
    private void añadirComp(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Comp", "add");
    }

    @FXML
    private void eliminarComp(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Comp", "delete");
    }

    @FXML
    private void modificarComp(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Comp", "modiffy");
    }
    @FXML
    private void añadirVent(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Vent", "add");
    }

    @FXML
    private void eliminarVent(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Vent", "delete");
    }

    @FXML
    private void modificarVent(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Vent", "modiffy");
    }

    @FXML
    private void añadirProd(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Prod", "add");
    }

    @FXML
    private void elimianrProd(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Prod", "delete");
    }

    @FXML
    private void modificarProd(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Prod", "modiffy");
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
}
