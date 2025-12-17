
package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * <p>
 * Esta clase carga automáticamente las credenciales de conexión desde
 * el archivo {@code db.properties} situado en el classpath del proyecto
 * y proporciona un método estático para obtener conexiones JDBC.
 * </p>
 *
 * <p>
 * El archivo {@code db.properties} debe contener las siguientes claves:
 * </p>
 * <ul>
 *   <li>{@code db.url}</li>
 *   <li>{@code db.user}</li>
 *   <li>{@code db.password}</li>
 * </ul>
 *
 * <p>
 * Ejemplo de uso:
 * </p>
 * <pre>
 * try (Connection con = ConexionBD.get()) {
 *     // operaciones con la base de datos
 * }
 * </pre>
 *
 * @author roque
 */
public class ConexionBD {

    /** Propiedades cargadas desde el archivo db.properties */
    private static final Properties PROPIEDADES = new Properties();

    /** URL de conexión JDBC */
    private static String URL;

    /** Usuario de la base de datos */
    private static String USER;

    /** Contraseña de la base de datos */
    private static String PASS;

      /**
     * Bloque estático encargado de cargar las propiedades de conexión
     * en el momento en que la clase es cargada por la JVM.
     * <p>
     * Si el archivo {@code db.properties} no se encuentra en el classpath
     * o ocurre un error durante su lectura, la aplicación se detiene
     * lanzando un {@link ExceptionInInitializerError}.
     * </p>
     */
    static {
        // El nombre del archivo debe coincidir con el que creaste
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream( "db.properties")) {
            
            if (input == null) {
                // Esto ayuda a diagnosticar si el archivo no se encuentra en el classpath
                throw new RuntimeException("Error: Archivo 'db.properties' no encontrado en el classpath.");

            }

            // Cargar el archivo
            PROPIEDADES.load(input);

            // Asignar valores a las variables estáticas para facilitar el uso
            URL = PROPIEDADES.getProperty("db.url");
            USER = PROPIEDADES.getProperty("db.user");
            PASS = PROPIEDADES.getProperty("db.password");
            System.out.println("url " + URL);
            System.out.println("USER " + USER);
            System.out.println("PASS ******");

        } catch (Exception e) {
            // Manejar la excepción de carga de archivo
            System.err.println("Fallo al cargar las propiedades de la base de datos.");
            e.printStackTrace();
            // Es buena práctica relanzar la excepción para detener la aplicación si falla la configuración esencial
            throw new ExceptionInInitializerError(e);
        }
    }

     /**
     * Obtiene una nueva conexión a la base de datos utilizando JDBC.
     * <p>
     * El método fuerza la carga del driver de MySQL para evitar problemas
     * de inicialización en algunos entornos.
     * </p>
     *
     * @return una instancia válida de {@link Connection}
     * @throws SQLException si ocurre un error al conectar con la base de datos
     */
    public static Connection get() throws SQLException {
        //cargamos el jdbc de manera manual para evitar errores.
        try {
        // Forzar la carga del driver manualmente
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("Error: No se encontró el driver de MySQL.", e);
    }
        // DriverManager.getConnection usa las variables ya cargadas
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
