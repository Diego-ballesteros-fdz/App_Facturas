package com.app_facturas.app_facturas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;
import objects.Entidad;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static int empresaActualId = 0;
    public static String nombreEmpresaActual = "";
    public static Stage primaryStage;

    

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("primary"), 700, 500);
        stage.setScene(scene);
        stage.show();
    }
     public static void setRootWithParam(String fxml, String tipo, String accion) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = loader.load();

        Object controller = loader.getController();

        // ==========================================================
        // 1️⃣ PASAR "tipo" (si el controller tiene ese método)
        // ==========================================================
        try {
            controller.getClass()
                    .getMethod("setTipo", String.class)
                    .invoke(controller, tipo);
        } catch (Exception e) {
            System.out.println("No se pudo pasar 'tipo': " + e.getMessage());
        }

        // ==========================================================
        // 2️⃣ PASAR “accion” (si existe en el controller)
        // ==========================================================
        if (accion != null) {
            try {
                controller.getClass()
                        .getMethod("setAccion", String.class)
                        .invoke(controller, accion);
            } catch (Exception e) {
                System.out.println("No se pudo pasar 'accion': " + e.getMessage());
            }
        }
        

        // ==========================================================
        // 3️⃣ PASAR **idEmpresaActual** SOLO si el FXML es “busqueda”
        // ==========================================================
        if (fxml.equals("busqueda")) {

            try {
                controller.getClass()
                        .getMethod("setEmpresa", long.class)
                        .invoke(controller, App.empresaActualId);

                System.out.println("➡️ Empresa enviada al buscador: " + App.empresaActualId);

            } catch (Exception e) {
                System.out.println("No se pudo pasar 'idEmpresaActual': " + e.getMessage());
            }
        }

        // ==========================================================
        // CAMBIAR LA ESCENA
        // ==========================================================
        primaryStage.getScene().setRoot(root);
    }
    public static void setRootWithParam(String fxml, String tipo, String accion,Entidad entidad) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = loader.load();

        Object controller = loader.getController();

        // ==========================================================
        // 1️⃣ PASAR "tipo" (si el controller tiene ese método)
        // ==========================================================
        try {
            controller.getClass()
                    .getMethod("setTipo", String.class)
                    .invoke(controller, tipo);
        } catch (Exception e) {
            System.out.println("No se pudo pasar 'tipo': " + e.getMessage());
        }

        // ==========================================================
        // 2️⃣ PASAR “accion” (si existe en el controller)
        // ==========================================================
        if (accion != null) {
            try {
                controller.getClass()
                        .getMethod("setAccion", String.class)
                        .invoke(controller, accion);
            } catch (Exception e) {
                System.out.println("No se pudo pasar 'accion': " + e.getMessage());
            }
        }
        
        if (entidad != null) {
            try {
                controller.getClass()
                        .getMethod("setEntidad", Entidad.class)
                        .invoke(controller, entidad);
            } catch (Exception e) {
                System.out.println("No se pudo pasar 'entidad': " + e.getMessage()+e);
            }
        }

        // ==========================================================
        // 3️⃣ PASAR **idEmpresaActual** SOLO si el FXML es “busqueda”
        // ==========================================================
        if (fxml.equals("busqueda")) {

            try {
                controller.getClass()
                        .getMethod("setEmpresa", long.class)
                        .invoke(controller, App.empresaActualId);

                System.out.println("➡️ Empresa enviada al buscador: " + App.empresaActualId);

            } catch (Exception e) {
                System.out.println("No se pudo pasar 'idEmpresaActual': " + e.getMessage());
            }
        }

        // ==========================================================
        // CAMBIAR LA ESCENA
        // ==========================================================
        primaryStage.getScene().setRoot(root);
    }


    /**
     * Cambia la raíz de la escena cargando un archivo FXML por su nombre.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * NUEVO → Cambia la raíz usando un nodo Parent directamente.
     * Esto permite hacer:
     * 
     *    FXMLLoader loader = new FXMLLoader(...);
     *    Parent root = loader.load();
     *    App.setRoot(root);
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    /**
     * Carga un archivo FXML y devuelve el nodo raíz.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
