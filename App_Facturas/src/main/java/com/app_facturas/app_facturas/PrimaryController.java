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
 * Controlador principal para la interfaz FXML.
 * Gestiona la carga de empresas en un menú desplegable y 
 * proporciona las acciones para crear, borrar y modificar empresas.
 * 
 * La clase implementa {@link Initializable} para realizar acciones
 * de inicialización cuando se carga la vista.
 * 
 * Se conecta a la base de datos para obtener las empresas que no tienen
 * roles asociados y las muestra en un {@link SplitMenuButton}.
 * 
 * @author diego
 */
public class PrimaryController implements Initializable {

    /** Contenedor principal de la interfaz */
    @FXML
    private AnchorPane primary;

    /** Botón para crear una nueva empresa */
    @FXML
    private Button createButton;

    /** Botón para borrar una empresa existente */
    @FXML
    private Button deleteButton;

    /** Botón para modificar una empresa existente */
    @FXML
    private Button modifyButton;

    /** Menú desplegable para mostrar las empresas disponibles */
    @FXML
    private SplitMenuButton menuEmpresas;

    /** Identificador de la empresa seleccionada actualmente, inicializado a -1 (ninguna seleccionada) */
    private int idEmpresaSeleccionada = -1;

    /**
     * Método llamado automáticamente tras cargar el controlador.
     * Se encarga de inicializar la vista, en particular cargar las empresas
     * desde la base de datos para mostrarlas en el menú.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpresas();
    }    
    
    /**
     * Carga las empresas desde la base de datos que no tienen ningún rol asociado.
     * 
     * Para cada empresa obtenida, se crea un {@link MenuItem} en el {@link SplitMenuButton}.
     * Al seleccionar un item, se actualizan las variables estáticas {@code App.empresaActualId} y
     * {@code App.nombreEmpresaActual} y se cambia la escena a "secondary".
     * 
     * El método maneja excepciones SQL y errores de E/S al cambiar la vista.
     * 
     * @throws SQLException Si ocurre un error al consultar la base de datos
     * @throws IOException Si ocurre un error al cambiar la ventana
     */
    private void cargarEmpresas() {
        menuEmpresas.getItems().clear();

        // Consulta para obtener solo las empresas sin roles asociados
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
            
        } catch (SQLException e) {
            System.err.println("Error SQL al cargar empresas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Acción disparada al pulsar el botón para crear una empresa.
     * Cambia la escena a un formulario de creación con parámetros indicados.
     * 
     * @param event evento disparador del botón
     * @throws IOException Si hay fallo al cargar la nueva ventana
     */
    @FXML
    private void crearEmpresa(ActionEvent event) throws IOException {
         App.setRootWithParam("formulary", "Emp", "add");
    }

    /**
     * Acción disparada al pulsar el botón para borrar una empresa.
     * Cambia la escena a una búsqueda para seleccionar y borrar empresa.
     * 
     * @param event evento disparador del botón
     * @throws IOException Si hay fallo al cargar la nueva ventana
     */
    @FXML
    private void borrarEmpresa(ActionEvent event)  throws IOException {
         App.setRootWithParam("busqueda", "Emp", "delete");
    }

    /**
     * Acción disparada al pulsar el botón para modificar una empresa.
     * Cambia la escena a una búsqueda para seleccionar y modificar empresa.
     * 
     * @param event evento disparador del botón
     * @throws IOException Si hay fallo al cargar la nueva ventana
     */
    @FXML
    private void modificarEmpresa(ActionEvent event)  throws IOException {
         App.setRootWithParam("busqueda", "Emp", "modiffy");
    }
}
