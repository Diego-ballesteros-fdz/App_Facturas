package com.app_facturas.app_facturas;

import static com.google.protobuf.TextFormat.print;
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
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


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
    private Button buttonFactura;
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
    
    @FXML
    private void imprimirFactura(ActionEvent event) {
        int idFacturaSeleccionada = 1; 

        Connection con = null;

        try {
            // 1. CONEXIÓN
            con = ConexionBD.get(); // Asegúrate de que este método existe en tu clase ConexionBD
            if (con == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: No hay conexión con la base de datos.");
                return;
            }

            // 2. CARGAR ARCHIVO .JRXML
            String rutaArchivo = "/reports/Factura.jrxml";
            InputStream reporteStream = SecondaryController.class.getResourceAsStream(rutaArchivo);

            if (reporteStream == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: No se encuentra el archivo " + rutaArchivo);
                return;
            }

            // 3. COMPILAR EL REPORTE
            System.out.println("Compilando reporte...");
            JasperReport reporteCompilado = JasperCompileManager.compileReport(reporteStream);

            // 4. PARÁMETROS
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idFactura", idFacturaSeleccionada); 

            // 5. LLENAR EL REPORTE (Crear el objeto de impresión)
            System.out.println("Llenando datos...");
            JasperPrint print = JasperFillManager.fillReport(reporteCompilado, parametros, con);

            // 6. EXPORTAR A PDF (Creando archivo temporal)
            // Si prefieres que se guarde siempre en el mismo sitio, cambia createTempFile por: new File("Factura.pdf");
            File pdfFile = File.createTempFile("Factura_Final_", ".pdf");
            
            JasperExportManager.exportReportToPdfFile(print, pdfFile.getAbsolutePath());
            System.out.println("PDF Creado en: " + pdfFile.getAbsolutePath());

            // --- AQUI ESTÁ EL CAMBIO QUE PEDISTE ---
            // 7. MOSTRAR MENSAJE EN JOPTIONPANE
            javax.swing.JOptionPane.showMessageDialog(null, 
                "¡Factura generada correctamente!\n\nRuta: " + pdfFile.getAbsolutePath(), 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

            // 8. ABRIR EL ARCHIVO AUTOMÁTICAMENTE
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Mostrar error en ventana también es buena práctica
            javax.swing.JOptionPane.showMessageDialog(null, "Ocurrió un error:\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            if (con != null) try { con.close(); } catch (Exception ex) {}
        }
    }
}
