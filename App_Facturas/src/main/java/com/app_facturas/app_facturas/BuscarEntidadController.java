/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import connection.DAOController;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Entidad;
import objects.Factura;
import objects.Producto;

/**
 *
 * @author roque
 */
public class BuscarEntidadController {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNombre;

    @FXML
    private ListView listaResultados;

    private String tipo;
    private DAOController dao = new DAOController();

    public void setTipo(String tipo) {
        this.tipo = tipo;
        lblTitulo.setText("Buscar " +  tipo.substring(0,1).toUpperCase() + tipo.substring(1));
    }
    
    @FXML
private void buscar() {

    System.out.println("Buscando tipo: " + tipo);

    String filtro = txtNombre.getText().trim().toLowerCase();
    System.out.println("Filtro: " + filtro);

    switch (tipo) {

        case "cliente":
            List<Entidad> clientes = dao.listarClientes();
            System.out.println("Clientes cargados: " + clientes.size());

            listaResultados.getItems().setAll(
                    clientes.stream()
                            .filter(c -> c.getNombre().toLowerCase().contains(filtro))
                            .collect(Collectors.toList())
            );
            break;
            

            List<Entidad> proveedores = dao.listarProveedores();
            System.out.println("Proveedores cargados: " + proveedores.size());

            listaResultados.getItems().setAll(
                    proveedores.stream()
                            .filter(p -> p.getNombre().toLowerCase().contains(filtro))
                            .collect(Collectors.toList())
            );
            break;

        case "producto":
            List<Producto> productos = dao.listarProductos();
            System.out.println("Productos cargados: " + productos.size());

            listaResultados.getItems().setAll(
                    productos.stream()
                            .filter(pr -> pr.getNombre().toLowerCase().contains(filtro))
                            .collect(Collectors.toList())
            );
            break;

        case "factura":
            List<Factura> facturas = dao.listarFacturas();
            System.out.println("Facturas cargadas: " + facturas.size());

            listaResultados.getItems().setAll(
                    facturas.stream()
                            .filter(f -> f.getCliente() != null
                                    && f.getCliente().getNombre().toLowerCase().contains(filtro))
                            .collect(Collectors.toList())
            );
            break;
    }
}


    
    
    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }
}