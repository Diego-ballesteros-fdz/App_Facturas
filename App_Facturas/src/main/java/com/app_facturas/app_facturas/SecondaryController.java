package com.app_facturas.app_facturas;

import connection.ConexionBD;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import objects.ListadoImprimirFacturas;

/**
 * Controlador para la segunda ventana de la aplicación.
 *
 * Gestiona las acciones de búsqueda, alta, modificación y eliminación para
 * clientes, proveedores, productos, compras y ventas. Además, permite imprimir
 * facturas mediante JasperReports.
 *
 *
 * Utiliza el identificador y nombre de la empresa actualmente seleccionada para
 * filtrar búsquedas y mostrar información contextual.
 * 
 *
 * @author diego
 */
public class SecondaryController implements Initializable {

    /**
     * Etiqueta para mostrar el nombre de la empresa en la cabecera
     */
    @FXML
    private Label nombreEmpresaCabecera;

    /**
     * Menú desplegable para opciones de proveedor/cliente
     */
    @FXML
    private MenuButton provCliOptions;

    /**
     * Menú desplegable para opciones de compras
     */
    @FXML
    private MenuButton comprasOptions;

    /**
     * Menú desplegable para opciones de ventas
     */
    @FXML
    private MenuButton ventasOptions;

    /**
     * Menú desplegable para opciones de productos
     */
    @FXML
    private MenuButton prodOptions;

    /**
     * Items del menú para acciones genéricas
     */
    @FXML
    private MenuItem add, delete, modify, search;

    /**
     * Panel contenedor principal de esta vista
     */
    @FXML
    private AnchorPane secondary;

    /**
     * Imagen del logo visible en la ventana
     */
    @FXML
    private ImageView logo;

    /**
     * Botones varios de la interfaz
     */
    @FXML
    private Button boton, buttonFactura;

    /**
     * Items específicos para proveedor/cliente
     */
    @FXML
    private MenuItem addProvCli, deleteProvCli, modifyProvCli, searchProvCli;

    /**
     * Items específicos para productos
     */
    @FXML
    private MenuItem addP, deleteP, modifyP, searchP;

    /**
     * Nombre de la empresa actualmente seleccionada
     */
    public String nombreEmp;

    /**
     * Id de la empresa actualmente seleccionada
     */
    public int idEmp;

    /**
     * Inicializa el controlador tras cargar la vista. Recupera el nombre e id
     * de la empresa seleccionada en la aplicación y los muestra en la cabecera
     * para contexto del usuario.
     *
     * @param url ubicación usada para resolver rutas relativas (no utilizada)
     * @param rb recursos utilizados para la internacionalización (no
     * utilizados)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreEmp = App.nombreEmpresaActual;
        idEmp = App.empresaActualId;
        nombreEmpresaCabecera.setText(nombreEmp);
    }

    // Métodos privados para navegación y búsquedas específicas
    /**
     * Navega a la vista de búsqueda de clientes.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    private void buscarCliente() throws IOException {
        App.setRootWithParam("busqueda", "cliente", null);
    }

    /**
     * Navega a la vista de búsqueda de proveedores.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    private void buscarProveedor() throws IOException {
        App.setRootWithParam("busqueda", "proveedor", null);
    }

    /**
     * Navega a la vista de búsqueda de productos.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void buscarProducto() throws IOException {
        App.setRootWithParam("busqueda", "Prod", null);
    }

    /**
     * Navega a la vista de búsqueda de compras.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void buscarComp() throws IOException {
        App.setRootWithParam("busqueda", "Comp", null);
    }

    /**
     * Navega a la vista de búsqueda de ventas.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void buscarVent() throws IOException {
        App.setRootWithParam("busqueda", "Vent", null);
    }

    /**
     * Navega a la vista de búsqueda de clientes/proveedores combinados.
     *
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void buscarCliProv() throws IOException {
        App.setRootWithParam("busqueda", "CliPro", null);
    }

    /**
     * Te lleva a un formulario para añadir nuevos clipro.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void añadirCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "CliPro", "add");
    }
/**
     * Te lleva a un formulario de busqueda para posterior eliminación de clipro.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void eliminarCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "CliPro", "delete");
    }
/**
     * Te lleva a un formulario de busqueda para despues modificar el clipro.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void modificarCliente(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "CliPro", "modiffy");
    }
/**
     * Te lleva a un formulario de creacion de compras.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void añadirComp(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Comp", "add");
    }
/**
     * Te lleva a un formulario de busqueda para despues eliminar una compra.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void eliminarComp(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Comp", "delete");
    }
/**
     * Te lleva a un formulario de busqueda para modificar una compra.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void modificarComp(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Comp", "modiffy");
    }
/**
     * Te lleva a un formulario para añadir una venta.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void añadirVent(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Vent", "add");
    }
/**
     * Te lleva a un formulario de busqueda para eliminar una venta.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void eliminarVent(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Vent", "delete");
    }
/**
     * Te lleva a un formulario de busqueda para modificar una venta.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void modificarVent(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Vent", "modiffy");
    }
/**
     * Te lleva a un formulario para añadir un producto.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void añadirProd(ActionEvent event) throws IOException {
        App.setRootWithParam("formulary", "Prod", "add");
    }
/**
     * Te lleva a un formulario de busqueda para luego borrar un producto.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void elimianrProd(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Prod", "delete");
    }
/**
     * Te lleva a un formulario de busqueda para modificar un producto.
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void modificarProd(ActionEvent event) throws IOException {
        App.setRootWithParam("busqueda", "Prod", "modiffy");
    }

    /**
     * Vuelve a la vista principal (primary).
     *
     * @param event evento disparador del botón
     * @throws IOException si ocurre un error al cambiar la escena
     */
    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    /**
     * Método que permite al usuario seleccionar una factura para imprimirla en
     * PDF.
     *
     * Realiza la conexión a la base de datos, recupera las facturas
     * disponibles, presenta un diálogo para seleccionar una, genera el reporte
     * con JasperReports, exporta a PDF y abre el archivo generado
     * automáticamente.
     *
     * Maneja errores de conexión, consulta, generación del reporte y apertura
     * del archivo, mostrando mensajes adecuados al usuario.
     *
     * @param event evento disparador del botón para imprimir factura
     */
    @FXML
    private void imprimirFactura(ActionEvent event) {
        int idFacturaSeleccionada;
        Connection con = null;

        try {
            // Establecer conexión con la base de datos
            con = ConexionBD.get();
            if (con == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: No hay conexión con la base de datos.");
                return;
            }

            // Recuperar facturas ordenadas por id descendente
            Vector<ListadoImprimirFacturas> listaFacturas = new Vector<>();
            String sql = "SELECT f.idFactura, f.fecha, e.nombre "
                    + "FROM FACTURA f "
                    + "INNER JOIN ENTIDAD e ON f.idEntidad = e.idEntidad "
                    + "ORDER BY f.idFactura DESC";

            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idFactura");
                java.sql.Date fecha = rs.getDate("fecha");
                String cliente = rs.getString("nombre");

                String texto = "Factura Nº " + id + " - " + cliente + " (" + fecha + ")";
                listaFacturas.add(new ListadoImprimirFacturas(id, texto));
            }
            rs.close();

            if (listaFacturas.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "No hay facturas registradas.");
                return;
            }

            // Mostrar diálogo para seleccionar factura a imprimir
            javax.swing.JComboBox<ListadoImprimirFacturas> comboBox = new javax.swing.JComboBox<>(listaFacturas);
            int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    comboBox,
                    "Seleccione la factura a imprimir",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacion != javax.swing.JOptionPane.OK_OPTION) {
                return;
            }

            // Obtener id de la factura seleccionada
            ListadoImprimirFacturas seleccion = (ListadoImprimirFacturas) comboBox.getSelectedItem();
            idFacturaSeleccionada = seleccion.getId();

            // Cargar archivo .jrxml del reporte
            String rutaArchivo = "/reports/Factura.jrxml";
            InputStream reporteStream = SecondaryController.class.getResourceAsStream(rutaArchivo);

            if (reporteStream == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: No se encuentra el archivo " + rutaArchivo);
                return;
            }

            // Compilar el reporte Jasper
            System.out.println("Compilando reporte...");
            JasperReport reporteCompilado = JasperCompileManager.compileReport(reporteStream);

            // Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idFactura", idFacturaSeleccionada);

            // Llenar el reporte con datos y conexión
            System.out.println("Llenando datos...");
            JasperPrint print = JasperFillManager.fillReport(reporteCompilado, parametros, con);

            // Exportar a archivo PDF temporal
            File pdfFile = File.createTempFile("Factura_Final_", ".pdf");
            JasperExportManager.exportReportToPdfFile(print, pdfFile.getAbsolutePath());
            System.out.println("PDF Creado en: " + pdfFile.getAbsolutePath());

            // Informar al usuario del éxito
            javax.swing.JOptionPane.showMessageDialog(null,
                    "¡Factura generada correctamente!\n\nRuta: " + pdfFile.getAbsolutePath(),
                    "Éxito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            // Abrir automáticamente el PDF si el sistema lo permite
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Ocurrió un error:\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ex) {
                // Ignorar excepción al cerrar conexión
            }
        }
    }
}
