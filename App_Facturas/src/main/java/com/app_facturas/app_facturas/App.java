<<<<<<< HEAD
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
=======
package com.app_facturas.app_facturas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage primaryStage;

    
    public static void setRootWithParam(String fxml, String tipo) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    Parent root = loader.load();

    // Obtener el controller de la vista
    Object controller = loader.getController();

    // Comprobar si el controller tiene el método setTipo
    try {
        controller.getClass().getMethod("setTipo", String.class).invoke(controller, tipo);
    } catch (Exception e) {
        System.out.println("No se pudo pasar el parámetro al controlador: " + e.getMessage());
    }

    // Cambiar la escena
    Scene scene = App.primaryStage.getScene();
    scene.setRoot(root);
}

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
>>>>>>> c0c1d89 (actualiza pom.xml y añade metodo setRootWithParam en App)
