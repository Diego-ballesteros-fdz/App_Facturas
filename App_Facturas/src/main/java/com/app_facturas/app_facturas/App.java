package com.app_facturas.app_facturas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import objects.Entidad;

/**
 * JavaFX App
 * Clase principal de la aplicación JavaFX de gestión de facturas.
 * <p>
 * Me encargo de inicializar la aplicación, cargar la escena principal
 * y gestionar los cambios de vista entre distintos archivos FXML.
 * Además, centralizo el paso de parámetros entre controladores
 * (tipo, acción, entidad y empresa activa).
 * </p>
 */
public class App extends Application {

    /**
     * Escena principal de la aplicación.
     * La reutilizo para cambiar dinámicamente la raíz (root).
     */
    private static Scene scene;

    /**
     * Identificador de la empresa actualmente seleccionada.
     * Se utiliza para filtrar datos en distintas pantallas.
     */
    public static int empresaActualId = 0;

    /**
     * Nombre de la empresa actualmente seleccionada.
     */
    public static String nombreEmpresaActual = "";

    /**
     * Stage principal de la aplicación.
     * Lo guardo para poder modificar la escena desde cualquier punto.
     */
    public static Stage primaryStage;

    
    /**
     * Método de inicio de la aplicación JavaFX.
     * <p>
     * Aquí configuro la escena inicial, establezco tamaños mínimos,
     * asigno el icono de la aplicación y muestro la ventana principal.
     * </p>
     *
     * @param stage escenario principal proporcionado por JavaFX
     * @throws IOException si ocurre un error al cargar el FXML inicial
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("primary"), 750, 525);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/app_facturas/app_facturas/logo.png")));
        stage.show();
    }
    
    /**
     * Cambio la vista cargando un FXML y pasando parámetros simples
     * al controlador si este los soporta.
     * <p>
     * Uso reflexión para invocar los métodos {@code setTipo},
     * {@code setAccion} y {@code setEmpresa} solo si existen,
     * evitando dependencias directas entre controladores.
     * </p>
     *
     * @param fxml   nombre del archivo FXML (sin extensión)
     * @param tipo   tipo de entidad o vista que se va a manejar
     * @param accion acción a realizar (crear, editar, ver, etc.)
     * @throws IOException si el FXML no puede cargarse
     */ 
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
        primaryStage.setMinWidth(root.minWidth(-1));
        primaryStage.setMinHeight(root.minHeight(-1));
    }
     
     /**
     * Cambio la vista cargando un FXML y pasando además una entidad concreta
     * al controlador.
     * <p>
     * Este método lo utilizo principalmente en pantallas de edición
     * o visualización de datos.
     * </p>
     *
     * @param fxml    nombre del archivo FXML (sin extensión)
     * @param tipo    tipo de entidad
     * @param accion  acción a realizar
     * @param entidad entidad que se va a mostrar o modificar
     * @throws IOException si ocurre un error al cargar el FXML
     */
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
                System.out.println("No se pudo pasar 'accion': " + e.getMessage()+accion);
                e.printStackTrace();
            }
        }
        
        if (entidad != null) {
            try {
                System.out.println(entidad);
                controller.getClass()
                        .getMethod("setEntidad", Entidad.class)
                        .invoke(controller, entidad);
            } catch (Exception e) {
                System.out.println("No se pudo pasar 'entidad': " + e.getMessage()+e);
                e.printStackTrace();
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
     * Cambio la raíz de la escena cargando un archivo FXML por su nombre.
     *
     * @param fxml nombre del archivo FXML (sin extensión)
     * @throws IOException si el archivo no puede cargarse
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Cambio la raíz de la escena usando directamente un nodo {@link Parent}.
     * <p>
     * Este método me permite reutilizar un FXML ya cargado.
     * </p>
     *
     * @param root nodo raíz que se asignará a la escena
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    /**
     * Cargo un archivo FXML y devuelvo su nodo raíz.
     *
     * @param fxml nombre del archivo FXML (sin extensión)
     * @return nodo raíz del FXML cargado
     * @throws IOException si ocurre un error durante la carga
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método principal de entrada a la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
    
    

}
