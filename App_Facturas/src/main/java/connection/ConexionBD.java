
package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static final Properties PROPIEDADES = new Properties();
    private static String URL;
    private static String USER;
    private static String PASS;

    // Bloque estático para cargar las propiedades cuando se carga la clase
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
     * Devuelve una nueva conexión a la base de datos utilizando las propiedades
     * cargadas.
     *
     * @return Objeto Connection.
     * @throws SQLException Si ocurre un error de conexión a la BD.
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
