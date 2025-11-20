package com.app_facturas.app_facturas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static int empresaActualId = 0;
    public static String nombreEmpresaActual = "";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
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
