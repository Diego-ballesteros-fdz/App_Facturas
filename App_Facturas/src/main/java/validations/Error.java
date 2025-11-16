/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validations;

import javafx.scene.paint.Color;

/**
 *Clase para tratar los errores
 * @author diego
 */
public class Error {
    private boolean error;
    private String mensaje;
    Color color;

    public Error(boolean error, String mensaje,Color color) {
        this.error = error; //true si ha fallado algo
        this.mensaje = mensaje; //mensaje del error especifico
        this.color=color;//rojo si ha fallado algo
    }

    public boolean isError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Color getColor() {
        return color;
    }
    
    
    
    
}
