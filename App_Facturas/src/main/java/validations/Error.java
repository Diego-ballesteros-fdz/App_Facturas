package validations;

import javafx.scene.paint.Color;

/**
 * Clase que representa el resultado de una validación.
 * Contiene información sobre si hay error, el mensaje asociado y el color para mostrar.
 * 
 * @author diego
 */
public class Error {

    /** Indica si existe un error (true) o no (false) */
    private boolean error;

    /** Mensaje descriptivo del error, o cadena vacía si no hay error */
    private String mensaje;

    /** Color asociado al estado del error (normalmente rojo o verde) */
    private Color color;

    /**
     * Constructor de la clase Error.
     * 
     * @param error  true si hay error, false en caso contrario
     * @param mensaje mensaje descriptivo del error o vacío si no hay error
     * @param color color para indicar el estado (e.g. rojo para error, verde para válido)
     */
    public Error(boolean error, String mensaje, Color color) {
        this.error = error;
        this.mensaje = mensaje;
        this.color = color;
    }

    /**
     * Indica si hay error.
     * 
     * @return true si hay error, false si no
     */
    public boolean isError() {
        return error;
    }

    /**
     * Obtiene el mensaje descriptivo del error.
     * 
     * @return mensaje del error o cadena vacía si no hay error
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene el color asociado al estado del error.
     * 
     * @return color (normalmente rojo o verde)
     */
    public Color getColor() {
        return color;
    }
}
