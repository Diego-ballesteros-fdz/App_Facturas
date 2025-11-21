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
    private MenuItem addProvCli;
    @FXML
    private MenuItem deleteProvCli;
    @FXML
    private MenuItem modifyProvCli;
    @FXML
    private MenuItem searchProvCli;

    @FXML
    private void buscarCliente() throws IOException{
        App.setRootWithParam("busqueda","cliente",null);
    }
    private void buscarProveedor() throws IOException{
        App.setRootWithParam("busqueda","proveedor",null);
    }

    @FXML
    private void buscarProducto() throws IOException{
        App.setRootWithParam("busqueda","producto",null);
    }

    @FXML
    private void buscarFactura() throws IOException{
        App.setRootWithParam("busqueda","factura",null);
    }
    @FXML
    private void buscarCliProv() throws IOException{
        App.setRootWithParam("busqueda","cli_prov",null);
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

    private void abrirBusqueda(String tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Busqueda.fxml"));
            Parent root = loader.load();

            BuscarEntidadController controller = loader.getController();
            controller.setTipo(tipo);

            App.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void añadirCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "CliPro", "add");
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
    }

    @FXML
    private void modificarCliente(ActionEvent event) {
    }

    @FXML
    private void añadirFact(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Fac", "add");
    }

    @FXML
    private void eliminarFact(ActionEvent event) {
    }

    @FXML
    private void modificarFact(ActionEvent event) {
    }

    @FXML
    private void añadirProd(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Prod", "add");
    }

    @FXML
    private void elimianrProd(ActionEvent event) {
    }

    @FXML
    private void modificarProd(ActionEvent event) {
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
}
