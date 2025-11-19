package com.app_facturas.app_facturas;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("formulary");
    }
    @FXML
    private void buscarCliente() {
        abrirBusqueda("cliente");
    }

    @FXML
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
}