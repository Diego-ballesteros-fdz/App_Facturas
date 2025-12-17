package objects;

/**
 * Representa un producto del sistema.
 * Un producto contiene información descriptiva, su precio,
 * el stock disponible y el proveedor asociado.
 *
 * Esta clase extiende {@link Entidad} para reutilizar información común,
 * aunque mantiene sus propios atributos específicos.
 *
 * Puede relacionarse con un proveedor tanto mediante una referencia
 * completa a {@link Entidad} como únicamente mediante su identificador.
 *
 * @author roque
 */
public class Producto extends Entidad {

    /** Identificador único del producto */
    private long idProducto;

    /** Nombre del producto */
    private String nombre;

    /** Descripción del producto */
    private String descripcion;

    /** Precio unitario del producto */
    private double precio;

    /** Cantidad disponible en stock */
    private int stock;

    /** Proveedor asociado al producto */
    private Entidad proveedor;

    /** Identificador del proveedor cuando solo se maneja la referencia por id */
    private long idProveedor;

    // =======================
    // CONSTRUCTORES
    // =======================

    /**
     * Constructor que inicializa los datos principales del producto.
     *
     * @param nombre       nombre del producto
     * @param descripcion descripción del producto
     * @param precio       precio unitario
     * @param stock        stock disponible
     * @param proveedor    proveedor asociado al producto
     * @throws NullPointerException si {@code nombre}, {@code descripcion} o {@code proveedor} son {@code null}
     */
    public Producto(String nombre, String descripcion, double precio, int stock, Entidad proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    /**
     * Constructor por defecto.
     * Crea un producto sin inicializar sus atributos.
     */
    public Producto() {
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================

    /**
     * Devuelve el identificador del producto.
     *
     * @return el identificador del producto
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idProducto identificador a asignar
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve el nombre del producto.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la descripción del producto.
     *
     * @return la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion descripción a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio unitario del producto.
     *
     * @return el precio unitario
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio precio a asignar
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el stock disponible del producto.
     *
     * @return el stock disponible
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del producto.
     *
     * @param stock stock a asignar
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve el proveedor asociado al producto.
     *
     * @return el proveedor
     */
    public Entidad getProveedor() {
        return proveedor;
    }

    /**
     * Establece el proveedor asociado al producto.
     *
     * @param proveedor proveedor a asignar
     */
    public void setProveedor(Entidad proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Devuelve el identificador del proveedor del producto.
     *
     * @return el identificador del proveedor
     */
    public long getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el identificador del proveedor del producto.
     *
     * @param idProveedor identificador del proveedor a asignar
     */
    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Devuelve una representación textual simplificada del producto,
     * compuesta por su nombre y su precio.
     *
     * @return cadena descriptiva del producto
     */
    @Override
    public String toString() {
        return nombre + " | " + precio + "€";
    }

}
