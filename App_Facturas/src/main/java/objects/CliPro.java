package objects;

import java.util.List;

/**
 * Representa una entidad que puede actuar como Cliente, Proveedor o ambos.
 *
 * Esta clase extiende {@link Entidad} y añade información adicional relacionada
 * con los roles comerciales (cliente/proveedor) y la dirección asociada.
 *
 * El comportamiento como cliente o proveedor se determina mediante atributos
 * booleanos o a partir de una lista de roles.
 *
 *
 * @author roque
 */
public class CliPro extends Entidad {

    /**
     * Indica si la entidad actúa como cliente
     */
    private boolean isCliente;

    /**
     * Indica si la entidad actúa como proveedor
     */
    private boolean isProveedor;

    /**
     * Dirección asociada a la entidad
     */
    private Direccion dir;

    /**
     * Referencia a la entidad base asociada
     */
    private Entidad entidad;

    /**
     * Devuelve la entidad asociada a este cliente/proveedor.
     *
     * @return la entidad asociada, o {@code null} si no se ha definido
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * Establece la entidad asociada a este cliente/proveedor.
     *
     * @param entidad la entidad a asociar
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    /**
     * Constructor por defecto.
     *
     * Crea una instancia sin inicializar sus atributos.
     *
     */
    public CliPro() {
    }

    /**
     * Constructor que inicializa la entidad base y la dirección.
     *
     * @param e entidad base a asociar
     * @param dir dirección de la entidad
     * @throws NullPointerException si {@code e} es {@code null}
     */
    public CliPro(Entidad e, Direccion dir) {
        super(e);
        this.entidad = e;
        this.dir = dir;
    }

    /**
     * Constructor que inicializa la entidad, los roles y la dirección.
     *
     * @param e entidad base a asociar
     * @param cliente indica si actúa como cliente
     * @param proveedor indica si actúa como proveedor
     * @param dir dirección asociada
     * @throws NullPointerException si {@code e} es {@code null}
     */
    public CliPro(Entidad e, boolean cliente, boolean proveedor, Direccion dir) {
        super();
        this.entidad = e;
        this.isCliente = cliente;
        this.isProveedor = proveedor;
        this.dir = dir;
    }

    /**
     * Constructor que inicializa la entidad únicamente con su identificador.
     *
     * @param id identificador único de la entidad
     */
    public CliPro(long id) {
        super();
        this.setIdEntidad(id);
    }

    /**
     * Devuelve la dirección asociada a la entidad.
     *
     * @return la dirección, o {@code null} si no está definida
     */
    public Direccion getDir() {
        return dir;
    }

    /**
     * Establece la dirección asociada a la entidad.
     *
     * @param dir dirección a asignar
     */
    public void setDir(Direccion dir) {
        this.dir = dir;
    }

    /**
     * Indica si la entidad actúa como cliente.
     *
     * @return {@code true} si es cliente, {@code false} en caso contrario
     */
    public boolean isIsCliente() {
        return isCliente;
    }

    /**
     * Establece si la entidad actúa como cliente.
     *
     * @param isCliente {@code true} si es cliente, {@code false} en caso
     * contrario
     */
    public void setIsCliente(boolean isCliente) {
        this.isCliente = isCliente;
    }

    /**
     * Indica si la entidad actúa como proveedor.
     *
     * @return {@code true} si es proveedor, {@code false} en caso contrario
     */
    public boolean isIsProveedor() {
        return isProveedor;
    }

    /**
     * Establece si la entidad actúa como proveedor.
     *
     * @param isProveedor {@code true} si es proveedor, {@code false} en caso
     * contrario
     */
    public void setIsProveedor(boolean isProveedor) {
        this.isProveedor = isProveedor;
    }

    /**
     * Asigna los roles de cliente y/o proveedor a partir de una lista de roles.
     * Si existe un rol con nombre {@code "CLIENTE"}, se marcará como cliente.
     * Si existe un rol con nombre {@code "PROVEEDOR"}, se marcará como
     * proveedor.
     *
     * @param roles lista de roles asociados a la entidad
     * @throws NullPointerException si {@code roles} es {@code null} o contiene
     * elementos nulos
     */
    public void setRoles(List<Rol> roles) {
        for (Rol r : roles) {
            if (r.getRol().equalsIgnoreCase("CLIENTE")) {
                isCliente = true;
            } else if (r.getRol().equalsIgnoreCase("PROVEEDOR")) {
                isProveedor = true;
            }
        }
    }

    /**
     * Devuelve una representación en texto de la entidad indicando si actúa
     * como cliente, proveedor o ambos.
     *
     * @return una cadena descriptiva de la entidad
     */
    @Override
    public String toString() {
        if (isProveedor && isCliente) {
            return "Cliente y Proveedor " + nombre + " (" + nif + ")";
        } else if (isProveedor) {
            return "Proveedor " + nombre + " (" + nif + ")";
        } else {
            return "Cliente " + nombre + " (" + nif + ")";
        }
    }

}
