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
import javafx.fxml.Initializable;
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
    
    private long idEmpresaActual;
    
    public void setEmpresa(long idEmpresa) {
        this.idEmpresaActual = idEmpresa;
    }

    private String tipo;
    private DAOController dao = new DAOController();

    public void setTipo(String tipo) {
        this.tipo = tipo;
        lblTitulo.setText("Buscar " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
    }

    @FXML
    private void buscar() {

        String filtro = txtNombre.getText();
        if (filtro == null) {
            filtro = "";
        }
        filtro = filtro.trim().toLowerCase();

        final String filtroFinal = filtro;  // 👈 necesario para lambdas en Java 11

        System.out.println("Filtro: " + filtroFinal);

        switch (tipo) {

            case "cliente":
                listaResultados.getItems().setAll(
                        dao.listarClientes().stream()
                                .filter(c -> c.getNombre() != null
                                && c.getNombre().toLowerCase().contains(filtroFinal))
                                .collect(java.util.stream.Collectors.toList())
                );
                break;

            case "proveedor":
                listaResultados.getItems().setAll(
                        dao.listarProveedores().stream()
                                .filter(p -> p.getNombre() != null
                                && p.getNombre().toLowerCase().contains(filtroFinal))
                                .collect(java.util.stream.Collectors.toList())
                );
                break;

            case "cli_prov":
                List<Entidad> datos = dao.listarClientesYProveedores(idEmpresaActual);

                listaResultados.getItems().setAll(
                    datos.stream()
                         .filter(e -> e.getNombre().toLowerCase().contains(filtroFinal))
                         .toList()
                );
                break;

            case "producto":
                listaResultados.getItems().setAll(
                        dao.listarProductos().stream()
                                .filter(prod -> prod.getNombre() != null
                                && prod.getNombre().toLowerCase().contains(filtroFinal))
                                .collect(java.util.stream.Collectors.toList())
                );
                break;

            case "factura":
                listaResultados.getItems().setAll(
                        dao.listarFacturas().stream()
                                .filter(f -> String.valueOf(f.getIdFactura()).contains(filtroFinal))
                                .collect(java.util.stream.Collectors.toList())
                );
                break;
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }
}
