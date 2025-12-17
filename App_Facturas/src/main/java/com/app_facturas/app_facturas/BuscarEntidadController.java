package com.app_facturas.app_facturas;

import connection.DAOController;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import objects.CliPro;
import objects.Empresa;
import objects.Entidad;

/**
 * Controlador para la vista de búsqueda de entidades en la aplicación.
 * 
 * Permite buscar clientes/proveedores, productos o empresas según el tipo
 * especificado. También gestiona la selección de resultados para su
 * modificación o eliminación.
 * 
 * 
 * Utiliza un DAOController para acceder a la base de datos y obtener las
 * listas filtradas correspondientes al contexto de la empresa actual.
 * 
 * 
 * @author roque
 */
public class BuscarEntidadController {

    /** Etiqueta que muestra el título dinámico según el tipo de búsqueda */
    @FXML
    private Label lblTitulo;

    /** Campo de texto para ingresar el filtro o criterio de búsqueda */
    @FXML
    private TextField txtNombre;

    /** Lista donde se muestran los resultados de la búsqueda */
    @FXML
    private ListView listaResultados;

    /** Tipo de entidad que se va a buscar ("CliPro", "Prod", "Emp") */
    private String tipo;

    /** Controlador DAO para acceso a datos */
    private DAOController dao = new DAOController();

    /** Id de la empresa actualmente seleccionada, usado para filtrar búsquedas */
    private long idEmpresaActual;

    /** Botón para disparar la búsqueda */
    @FXML
    private Button btnBuscar;

    /** Botón para volver a la vista anterior */
    @FXML
    private Button btnVolver;

    /** Acción a realizar tras seleccionar un resultado ("modiffy", "delete", null) */
    private String accion;

    /**
     * Establece el identificador de la empresa actual para filtrar resultados.
     * 
     * @param idEmpresa id de la empresa seleccionada
     */
    public void setEmpresa(long idEmpresa) {
        this.idEmpresaActual = idEmpresa;
    }

    /**
     * Define la acción a realizar tras seleccionar un resultado en la lista.
     * 
     * @param accion cadena que indica la acción ("modiffy", "delete", "add".)
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * Define el tipo de búsqueda a realizar y actualiza el título de la ventana.
     * 
     * @param tipo tipo de entidad a buscar ("CliPro", "Prod", "Emp")
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
        lblTitulo.setText("Buscar " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
    }

    /**
     * Realiza la búsqueda según el tipo definido y el filtro introducido.
     * 
     * Dependiendo del tipo, consulta el DAO correspondiente para obtener
     * la lista filtrada y la muestra en la vista.
     * 
     */
    @FXML
    private void buscar() {
        String filtro = txtNombre.getText();
        if (filtro == null) {
            filtro = "";
        }
        final String filtrofinal = filtro.trim().toLowerCase();

        System.out.println("Filtro: " + filtro);

        switch (tipo) {

            case "CliPro":
                System.out.println("idEmpres:" + idEmpresaActual);
                List<CliPro> lista = dao.listarClientesYProveedores(idEmpresaActual);
                listaResultados.getItems().setAll(lista);
                break;

            case "Prod":
                listaResultados.getItems().setAll(
                        dao.listarProductosPorEmpresa(idEmpresaActual).stream()
                                .filter(p -> p.getNombre().toLowerCase().contains(filtrofinal))
                                .toList()
                );
                break;

            case "Emp":
                System.out.println("idEmpres:" + idEmpresaActual);
                List<Empresa> listaem = dao.listarSoloEmpresas();
                listaResultados.getItems().setAll(listaem);
                break;
        }
    }

    /**
     * Vuelve a la ventana anterior según el tipo de búsqueda.
     * 
     * Si el tipo es "Emp", vuelve a la ventana primaria, en otro caso a la secundaria.
     * 
     * 
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void volver() throws IOException {
        if (tipo.equals("Emp")) {
            App.setRoot("primary");
        } else {
            App.setRoot("secondary");
        }
    }

    /**
     * Método invocado al seleccionar un elemento de la lista de resultados.
     *
     * Según la acción configurada, redirige a la ventana de formulario para
     * modificar o eliminar la entidad seleccionada.
     *
     * 
     * @param event evento de clic sobre la lista
     */
    @FXML
    private void seleccionListaResult(MouseEvent event) {
        Entidad e;
        if (accion != null) {
            switch (accion) {
                case "modiffy":
                    e = (Entidad) listaResultados.getSelectionModel().getSelectedItem();
                    try {
                        App.setRootWithParam("formulary", tipo, "modiffy", e);
                    } catch (Exception ex) {
                        System.out.println("Error al modificar: " + ex);
                    }
                    break;
                case "delete":
                    e = (Entidad) listaResultados.getSelectionModel().getSelectedItem();
                    try {
                        App.setRootWithParam("formulary", tipo, "delete", e);
                    } catch (Exception ex) {
                        System.out.println("Error al eliminar: " + ex);
                    }
                    break;
                default:
                    System.out.println("Acción no reconocida.");
                    break;
            }
        }
    }
}
