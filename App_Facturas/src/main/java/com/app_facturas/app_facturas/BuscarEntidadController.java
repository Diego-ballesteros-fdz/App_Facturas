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
import javafx.scene.input.MouseEvent;
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

    private long idEmpresaActual;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnVolver;

    private String accion;

    public void setEmpresa(long idEmpresa) {
        this.idEmpresaActual = idEmpresa;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

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

            case "CliPro":
                System.out.println("idEmpres:"+idEmpresaActual);
                List<Entidad> datos = dao.listarClientesYProveedores(idEmpresaActual);

                listaResultados.getItems().setAll(
                        datos.stream()
                                .filter(e -> e.getNombre().toLowerCase().contains(filtroFinal))
                                .toList()
                );
                break;

            case "Prod":
                listaResultados.getItems().setAll(
                        dao.listarProductosPorEmpresa(idEmpresaActual).stream()
                                .filter(p -> p.getNombre().toLowerCase().contains(filtroFinal))
                                .toList()
                );
                break;

            case "Fac":
                listaResultados.getItems().setAll(
                        dao.listarProductosPorProveedor(idEmpresaActual).stream()
                                .filter(p -> p.getNombre().toLowerCase().contains(filtroFinal))
                                .toList()
                );
                break;
                
            case "Emp":
                listaResultados.getItems().setAll(
                    dao.listarSoloEmpresas().stream()
                        .filter(e -> e.getNombre().toLowerCase().contains(filtroFinal))
                        .toList()
                );
                break;

        }
    }

    @FXML
    private void volver() throws IOException {
           if(tipo.equals("Emp")){
            App.setRoot("primary");
        }else{
            App.setRoot("secondary");
        }
    }

    @FXML
    /**
     * metodo para seleccionar entidad y enviar a modificar o eliminar
     */
    private void seleccionListaResult(MouseEvent event) {
        Entidad e;
        if (accion != null) {
            switch (accion) {
                case "modiffy":
                    e = (Entidad) listaResultados.getSelectionModel().getSelectedItem();
                    try {
                        App.setRootWithParam("formulary", tipo, "modiffy", e);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                    break;
                case "delete":
                    e = (Entidad) listaResultados.getSelectionModel().getSelectedItem();
                    try {
                        App.setRootWithParam("formulary", tipo, "delete", e);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    break;
                default:
                    System.out.println("Algo ha fallado");
                    break;

            }
        }
    }

    private void wathEntidad() {

    }
}
