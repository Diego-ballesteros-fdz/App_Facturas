package objects;

/**
 * Representa un elemento del listado de impresión de facturas.
 * Esta clase se utiliza como objeto auxiliar para mostrar
 * opciones o registros relacionados con la impresión de facturas.
 *
 * Contiene un identificador interno y una descripción textual
 * que se utiliza como representación visible.
 *
 * @author marcos
 */
public class ListadoImprimirFacturas {

    /** Identificador del elemento del listado */
    private int id;

    /** Descripción asociada al elemento */
    private String descripcion;

    /**
     * Constructor que inicializa el identificador y la descripción
     * del elemento del listado.
     *
     * @param id identificador del elemento
     * @param descripcion texto descriptivo del elemento
     * @throws NullPointerException si {@code descripcion} es {@code null}
     */
    public ListadoImprimirFacturas(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el identificador del elemento del listado.
     *
     * @return el identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la descripción del elemento.
     *
     * @return la descripción del elemento
     */
    @Override
    public String toString() {
        return descripcion;
    }
}
