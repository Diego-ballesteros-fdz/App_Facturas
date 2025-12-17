package validations;

import java.time.LocalDate;
import javafx.scene.paint.Color;

/**
 * Clase que proporciona métodos estáticos para validar datos introducidos por el usuario.
 * Cada método devuelve un objeto {@link Error} que indica si hay error, un mensaje y un color asociado.
 * 
 * Las validaciones incluyen:
 * 
 *   -Campos vacíos o nulos
 *   -Formato de texto simple
 *   -Números decimales y enteros positivos
 *   -Valores long
 *   -Formato de email
 *   -Formato de nombre válido (letras y espacios)
 *   -Fechas (pasadas o futuras)
 *   -Validación de NIF, NIE, CIF
 *   -Validación de teléfono
 *
 * 
 * @author diego
 */
public class Validation {

    /**
     * Valida que un texto no sea nulo ni vacío.
     * 
     * @param t texto a validar
     * @return Error con true si está vacío o nulo, false si es válido
     */
    public static Error esVacio(String t) {
        if (t == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        } else if (t.isEmpty()) {
            return new Error(true, "El valor introducido está vacío", Color.RED);
        } else {
            return new Error(false, "", Color.GREEN);
        }
    }

    /**
     * Valida que un texto sea no nulo y no vacío (texto simple).
     * 
     * @param t texto a validar
     * @return Error con true si está vacío o nulo, false si es válido
     */
    public static Error esTexto(String t) {
        return esVacio(t);
    }

    /**
     * Valida que un texto sea un número decimal positivo.
     * 
     * @param t texto que representa el número decimal
     * @return Error con true si no es decimal positivo, false si es válido
     */
    public static Error esDecimalPos(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        try {
            double num = Double.parseDouble(t);
            if (num > 0) {
                return new Error(false, "", Color.GREEN);
            } else {
                return new Error(true, "El valor es negativo", Color.RED);
            }
        } catch (NumberFormatException e) {
            return new Error(true, "El valor no es decimal", Color.RED);
        }
    }

    /**
     * Valida que un texto sea un número entero positivo.
     * 
     * @param t texto que representa el número entero
     * @return Error con true si no es entero positivo, false si es válido
     */
    public static Error esEnteroPos(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        try {
            int num = Integer.parseInt(t);
            if (num > 0) {
                return new Error(false, "", Color.GREEN);
            } else {
                return new Error(true, "El valor es negativo", Color.RED);
            }
        } catch (NumberFormatException e) {
            return new Error(true, "El valor no es entero", Color.RED);
        }
    }

    /**
     * Valida que un texto sea un número long válido.
     * 
     * @param t texto que representa el número long
     * @return Error con true si no es un long válido, false si es válido
     */
    public static Error esLong(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        try {
            Long.parseLong(t);
            return new Error(false, "", Color.GREEN);
        } catch (NumberFormatException e) {
            return new Error(true, "El valor introducido no es un long válido", Color.RED);
        }
    }

    /**
     * Valida que un texto tenga formato de email válido.
     * 
     * @param t texto con el email a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esEmail(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        String regex = "^[\\w.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (t.matches(regex)) {
            return new Error(false, "", Color.GREEN);
        } else {
            return new Error(true, "El valor introducido no es un email válido", Color.RED);
        }
    }

    /**
     * Valida que un texto tenga formato válido de nombre (solo letras, espacios y acentos).
     * 
     * @param t texto con el nombre a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esNombre(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ .]+$";
        if (t.matches(regex)) {
            return new Error(false, "", Color.GREEN);
        } else {
            return new Error(true, "El valor introducido no es un nombre válido: " + t, Color.RED);
        }
    }

    /**
     * Valida que una fecha sea igual o posterior a la fecha actual.
     * 
     * @param ld fecha a validar
     * @return Error con true si es nula o anterior a hoy, false si válida
     */
    public static Error esFechaFutura(LocalDate ld) {
        if (ld == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        }
        if (ld.isBefore(LocalDate.now())) {
            return new Error(true, "La fecha debe ser igual o posterior a hoy", Color.RED);
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * Valida que una fecha sea igual o anterior a la fecha actual.
     * 
     * @param ld fecha a validar
     * @return Error con true si es nula o posterior a hoy, false si válida
     */
    public static Error esFechaPasada(LocalDate ld) {
        if (ld == null) {
            return new Error(true, "El valor introducido es nulo", Color.RED);
        }
        if (ld.isAfter(LocalDate.now())) {
            return new Error(true, "La fecha debe ser igual o anterior a hoy", Color.RED);
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * Valida el formato de un NIF español (8 dígitos + letra).
     * 
     * @param t texto con el NIF a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esNIF(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        t = t.toUpperCase();
        if (!t.matches("\\d{8}[A-Z]")) {
            return new Error(true, "Formato de NIF incorrecto", Color.RED);
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * Valida el formato de un NIE español (X, Y o Z seguido de 7 dígitos y letra).
     * 
     * @param t texto con el NIE a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esNIE(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        t = t.toUpperCase();
        if (!t.matches("[XYZ]\\d{7}[A-Z]")) {
            return new Error(true, "Formato de NIE incorrecto", Color.RED);
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * Valida el formato de un CIF español (letra + 7 dígitos + letra/dígito).
     * 
     * @param t texto con el CIF a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esCIF(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
        t = t.toUpperCase();
        if (!t.matches("[ABCDEFGHJNPQRSUVW]\\d{7}[0-9A-J]")) {
            return new Error(true, "Formato de CIF incorrecto", Color.RED);
        }
        return new Error(false, "", Color.GREEN);
    }

    /**
     * Valida que un teléfono tenga al menos 9 dígitos y sólo caracteres válidos.
     * 
     * @param t texto con el teléfono a validar
     * @return Error con true si formato incorrecto, false si válido
     */
    public static Error esTelefono(String t) {
        Error er = esVacio(t);
        if (er.isError()) {
            return er;
        }
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
