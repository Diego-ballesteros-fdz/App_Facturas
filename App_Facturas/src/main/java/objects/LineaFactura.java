package objects;

/**
 * Representa una línea de una factura.
 * Una línea de factura relaciona una factura con un producto concreto,
 * indicando la cantidad, el precio unitario y el subtotal correspondiente.
 *
 * Esta clase se utiliza para desglosar los productos incluidos
 * dentro de una factura.
 *
 * @author roque
 */
public class LineaFactura {

    /** Identificador único de la línea de factura */
    private long idLinea;

    /** Factura a la que pertenece la línea */
    private Factura factura;

    /** Producto asociado a la línea de factura */
    private Producto producto;

    /** Cantidad del producto facturado */
    private int cantidad;

    /** Precio unitario del producto en el momento de la facturación */
    private double precioUnitario;

    /** Subtotal de la línea de factura */
    private double subtotal;

    // =======================
    // CONSTRUCTORES
    // =======================

    /**
     * Constructor por defecto.
     * Crea una línea de factura sin inicializar sus atributos.
     */
    public LineaFactura() {
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================

    /**
     * Devuelve el identificador de la línea de factura.
     *
     * @return el identificador de la línea
     */
    public long getIdLinea() {
        return idLinea;
    }

    /**
     * Establece el identificador de la línea de factura.
     *
     * @param idLinea identificador a asignar
     */
    public void setIdLinea(long idLinea) {
        this.idLinea = idLinea;
    }

    /**
     * Devuelve la factura asociada a la línea.
     *
     * @return la factura asociada
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * Establece la factura asociada a la línea.
     *
     * @param factura factura a asignar
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /**
     * Devuelve el producto asociado a la línea de factura.
     *
     * @return el producto asociado
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a la línea de factura.
     *
     * @param producto producto a asignar
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Devuelve la cantidad del producto facturado.
     *
     * @return la cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto facturado.
     *
     * @param cantidad cantidad a asignar
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el precio unitario del producto.
     *
     * @return el precio unitario
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precioUnitario precio unitario a asignar
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Devuelve el subtotal de la línea de factura.
     *
     * @return el subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal de la línea de factura.
     *
     * @param subtotal subtotal a asignar
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
