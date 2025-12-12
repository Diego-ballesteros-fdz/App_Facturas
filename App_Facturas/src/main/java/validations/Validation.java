/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validations;

import java.time.LocalDate;
import javafx.scene.paint.Color;

/**
 * Clase para la validación de los datos introducidos por el usuario
 *
 * @author diego
 */
public class Validation {

    public static Error esVacio(String t) {
        if (t == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        } else if (t == "") {
            return new Error(true, "El valor introducido esta vacio", Color.RED);
        } else {
            return new Error(false, "", Color.GREEN);
        }
    }

    /**
     * validador de String
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esTexto(String t) {
        if (t == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        } else if (t == "") {
            return new Error(true, "El valor introducido esta vacio", Color.RED);
        } else {
            return new Error(false, "", Color.GREEN);
        }
    }

    /**
     * validador de decimal
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esDecimalPos(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            //verificamos si es un decimal realmente
            try {
                double num = Double.parseDouble(t);
                if (num > 0) {
                    return new Error(false, "", Color.GREEN);
                } else {
                    return new Error(true, "El valor es negativo", Color.RED);
                }
            } catch (Exception e) {
                return new Error(true, "el valor no es decimal", Color.RED);
            }
        }
    }
    
    /**
     * validador de decimal
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esEnteroPos(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            //verificamos si es un decimal realmente
            try {
                int num = Integer.parseInt(t);
                if (num > 0) {
                    return new Error(false, "", Color.GREEN);
                } else {
                    return new Error(true, "El valor es negativo", Color.RED);
                }
            } catch (Exception e) {
                return new Error(true, "el valor no es decimal", Color.RED);
            }
        }
    }

    /**
     * validador de long
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esLong(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            try {
                long l = Long.parseLong(t);
                return new Error(false, "", Color.GREEN);
            } catch (Exception e) {
                return new Error(true, "El valor introducido no es un long", Color.RED);
            }
        }
    }

    /**
     * validador de email
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esEmail(String t) {

        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            String regex = "^[\\w.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (t.matches(regex)) {
                return new Error(false, "", Color.GREEN);
            } else {
                return new Error(true, "El valor introducido no es un Email", Color.RED);
            }
        }
    }

    /**
     * validador de Nombre
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esNombre(String t) {

        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ .]+$";
            if (t.matches(regex)) {
                return new Error(false, "", Color.GREEN);
            } else {
                return new Error(true, "El valor introducido no es un Nombre valido: "+t, Color.RED);
            }
        }
    }

    /**
     * validador de fechafutura
     *
     * @param ld LocalDate recogido de los DatePicker
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esFechaFutura(LocalDate ld) {

        if (ld == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        } else {
            if (ld.isBefore(LocalDate.now())) {
                return new Error(true, "La fecha debe igual o posterior a hoy", Color.RED);
            } else {
                return new Error(false, "", Color.GREEN);
            }
        }
    }

    /**
     * validador de fechapasada
     *
     * @param ld LocalDate recogido de los DatePicker
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esFechaPasada(LocalDate ld) {
        if (ld == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        } else {
            if (ld.isAfter(LocalDate.now())) {
                return new Error(true, "La fecha debe ser igual o anterior a hoy", Color.RED);
            } else {
                return new Error(false, "", Color.GREEN);
            }
        }
    }

    /**
     * validador de Nif
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esNIF(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            t = t.toUpperCase();
            if (!t.matches("\\d{8}[A-Z]")) {
                return new Error(true, "Formato de NIF incorrecto", Color.RED);
            }
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * validador de Nie
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esNIE(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            t = t.toUpperCase();
            if (!t.matches("[XYZ]\\d{7}[A-Z]")) {
                return new Error(true, "Formato de NIE incorrecto", Color.RED);
            }
            return new Error(false, "", Color.GREEN);
        }
    }

    /**
     * validador de Cif
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esCIF(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {
            t = t.toUpperCase();
            if (!t.matches("[ABCDEFGHJNPQRSUVW]\\d{7}[0-9A-J]")) {
                return new Error(true, "Formato de CIF incorrecto", Color.RED);
            }
            return new Error(false, "", Color.GREEN);
        }
    }

    /**
     * validador de Telefono
     *
     * @param t String recogido de los TextArea
     * @return Error, inidcando true cuendo hay error y false cuando no lo hay,
     * aparte de contener el mensaje del error en concreto
     */
    public static Error esTelefono(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        } else {

            String digitos = t.replaceAll("[^0-9]", "");
            if (digitos.length() < 9) {
                return new Error(true, "El teléfono debe tener al menos 9 dígitos", Color.RED);
            }

            if (!t.matches("^[+\\d\\s\\-()]+$")) {
                return new Error(true, "El teléfono contiene caracteres inválidos", Color.RED);
            }
            return new Error(false, "", Color.GREEN);
        }

    }
}
