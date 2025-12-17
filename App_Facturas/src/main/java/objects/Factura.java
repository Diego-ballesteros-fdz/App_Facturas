package objects;

import connection.DAOController;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa una factura del sistema.
 * Una factura contiene información sobre la fecha de emisión,
 * el cliente asociado, el tipo de factura y el importe total.
 *
 * Esta clase extiende {@link Entidad} para reutilizar información común,
 * aunque mantiene una relación explícita con la entidad cliente.
 *
 * El total de la factura se calcula a partir de una lista de productos
 * y una lista de cantidades asociadas.
 *
 * @author roque
 */
public class Factura extends Entidad {

    /** Identificador único de la factura */
    private long idFactura;

    /** Fecha de emisión de la factura */
    private LocalDate fechaEmision;

    /** Importe total de la factura */
    private double total;

    /** Cliente asociado a la factura */
    private Entidad cliente;

    /** Tipo de factura */
    private String tipo;

    // =======================
    // CONSTRUCTORES
    // =======================

    /**
     * Constructor que crea una factura inicializando sus datos principales
     * y calculando el total automáticamente.
     *
     * @param fechaEmision fecha de emisión de la factura
     * @param tipo         tipo de factura
     * @param nombre       cadena que contiene el identificador del cliente
     * @param lista        lista de cantidades asociadas a los productos
     * @param listaProd    lista de productos facturados
     * @throws NullPointerException si alguno de los parámetros es {@code null}
     * @throws NumberFormatException si el identificador del cliente no es numérico
     * @throws ClassCastException si algún elemento de {@code listaProd} no es un {@link Producto}
     */
    public Factura(LocalDate fechaEmision, String tipo, String nombre,
                   ArrayList<Integer> lista, ArrayList<Entidad> listaProd) {
        this.fechaEmision = fechaEmision;
        this.cliente = encontrarCliente(nombre);
        this.tipo = tipo;
        this.total = calcularTotal(lista, listaProd);
    }

    /**
     * Constructor por defecto.
     * Crea una factura sin inicializar sus atributos.
     */
    public Factura() {
    }

    // =======================
    // MÉTODOS DE NEGOCIO
    // =======================

    /**
     * Busca y devuelve la entidad cliente a partir de una cadena
     * que contiene su identificador.
     *
     * La cadena se divide utilizando el carácter '-' para extraer
     * el identificador de la entidad.
     *
     * @param nombre cadena que contiene el identificador del cliente
     * @return la entidad cliente encontrada
     * @throws NullPointerException si {@code nombre} es {@code null}
     * @throws NumberFormatException si el identificador no es numérico
     */
    public Entidad encontrarCliente(String nombre) {
        DAOController dao = new DAOController();
        String[] partes = nombre.split("-");
        Entidad e = dao.buscarEntidadPorId(Long.valueOf(partes[0]));
        return e;
    }

    /**
     * Calcula el importe total de la factura a partir de una lista
     * de productos y una lista de cantidades.
     *
     * Para cada producto se multiplica su precio por la cantidad
     * correspondiente y se suma al total.
     *
     * @param lista     lista de cantidades
     * @param listaProd lista de productos
     * @return el importe total calculado
     * @throws NullPointerException si alguna de las listas es {@code null}
     * @throws ClassCastException si algún elemento de {@code listaProd} no es un {@link Producto}
     */
    public double calcularTotal(ArrayList<Integer> lista, ArrayList<Entidad> listaProd) {
        double total = 0;

        for (Entidad x : listaProd) {
            Producto p = (Producto) x;
            for (int y : lista) {
                total = total + (p.getPrecio() * y);
            }
        }
        return total;
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================

    /**
     * Devuelve el identificador de la factura.
     *
     * @return el identificador de la factura
     */
    public long getIdFactura() {
        return idFactura;
    }

    /**
     * Establece el identificador de la factura.
     *
     * @param idFactura identificador a asignar
     */
    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * Devuelve la fecha de emisión de la factura.
     *
     * @return la fecha de emisión
     */
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Establece la fecha de emisión de la factura.
     *
     * @param fechaEmision fecha a asignar
     */
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Devuelve el tipo de factura.
     *
     * @return el tipo de factura
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de factura.
     *
     * @param tipo tipo a asignar
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el importe total de la factura.
     *
     * @return el total de la factura
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el importe total de la factura.
     *
     * @param total importe a asignar
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Devuelve el cliente asociado a la factura.
     *
     * @return la entidad cliente
     */
    public Entidad getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la factura.
     *
     * @param cliente cliente a asignar
     */
    public void setCliente(Entidad cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve una representación textual de la factura
     * con sus datos principales.
     *
     * @return cadena descriptiva de la factura
     */
    @Override
    public String toString() {
        return "Factura{" +
                "fechaEmision=" + fechaEmision +
                ", total=" + total +
                ", cliente=" + cliente +
                ", tipo=" + tipo +
                '}';
    }

}
