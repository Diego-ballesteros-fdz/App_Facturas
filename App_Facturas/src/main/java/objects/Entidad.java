package objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una entidad genérica del sistema.
 * Esta clase actúa como clase base para otras entidades más específicas
 * y contiene los datos comunes como identificador, nombre, NIF,
 * información de contacto y observaciones.
 *
 * Además, gestiona una colección de roles asociados a la entidad,
 * permitiendo determinar si actúa como cliente, proveedor o ambos.
 *
 * @author roque
 */
public class Entidad {

    /** Identificador único de la entidad */
    protected long idEntidad;

    /** Nombre o razón social de la entidad */
    protected String nombre;

    /** NIF o documento identificativo de la entidad */
    protected String nif;

    /** Dirección de correo electrónico */
    protected String email;

    /** Número de teléfono de contacto */
    protected String telefono;

    /** Observaciones adicionales asociadas a la entidad */
    protected String observaciones;

    /** Lista de roles asociados a la entidad */
    private List<Rol> roles = new ArrayList<>();

    // =======================
    // CONSTRUCTORES
    // =======================

    /**
     * Constructor que inicializa los datos básicos de la entidad.
     *
     * @param nombre        nombre o razón social
     * @param nif           NIF de la entidad
     * @param email         correo electrónico
     * @param telefono      teléfono de contacto
     * @param observaciones observaciones adicionales
     * @throws NullPointerException si alguno de los parámetros obligatorios es {@code null}
     */
    public Entidad(String nombre, String nif, String email, String telefono, String observaciones) {
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.telefono = telefono;
        this.observaciones = observaciones;
    }

    /**
     * Constructor de copia.
     * Inicializa la entidad a partir de otra entidad existente.
     *
     * @param e entidad de la que se copian los datos
     * @throws NullPointerException si {@code e} es {@code null}
     */
    public Entidad(Entidad e) {
        this.nombre = e.getNombre();
        this.nif = e.getNif();
        this.email = e.getEmail();
        this.telefono = e.getTelefono();
        this.observaciones = e.getObservaciones();
    }

    /**
     * Constructor que inicializa la entidad con identificador, nombre y NIF.
     *
     * @param id     identificador de la entidad
     * @param nombre nombre o razón social
     * @param nif    NIF de la entidad
     */
    public Entidad(long id, String nombre, String nif) {
        this.nombre = nombre;
        this.nif = nif;
    }

    /**
     * Constructor por defecto.
     * Crea una entidad sin inicializar sus atributos.
     */
    public Entidad() {
    }

    /**
     * Constructor que inicializa únicamente el identificador de la entidad.
     *
     * @param idEntidad identificador único de la entidad
     */
    public Entidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    // =======================
    // GETTERS Y SETTERS BASE
    // =======================

    /**
     * Devuelve el identificador de la entidad.
     *
     * @return el identificador de la entidad
     */
    public long getIdEntidad() {
        return idEntidad;
    }

    /**
     * Establece el identificador de la entidad.
     *
     * @param idEntidad identificador a asignar
     */
    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * Devuelve el nombre o razón social de la entidad.
     *
     * @return el nombre de la entidad
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre o razón social de la entidad.
     *
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el NIF de la entidad.
     *
     * @return el NIF
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF de la entidad.
     *
     * @param nif NIF a asignar
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve el correo electrónico de la entidad.
     *
     * @return el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de la entidad.
     *
     * @param email email a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono de contacto de la entidad.
     *
     * @return el teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de contacto de la entidad.
     *
     * @param telefono teléfono a asignar
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve las observaciones asociadas a la entidad.
     *
     * @return las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones asociadas a la entidad.
     *
     * @param observaciones observaciones a asignar
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // =======================
    // ROLES
    // =======================

    /**
     * Devuelve la lista de roles asociados a la entidad.
     *
     * @return lista de roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * Establece la lista de roles de la entidad.
     *
     * @param roles lista de roles a asignar
     * @throws NullPointerException si {@code roles} es {@code null}
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     * Añade un rol a la entidad si no existe previamente.
     *
     * @param r rol a añadir
     * @throws NullPointerException si {@code r} es {@code null}
     */
    public void addRol(Rol r) {
        if (!roles.contains(r)) {
            roles.add(r);
        }
    }

    /**
     * Elimina un rol de la entidad.
     *
     * @param r rol a eliminar
     */
    public void removeRol(Rol r) {
        roles.remove(r);
    }

    /**
     * Indica si la entidad tiene el rol de cliente.
     *
     * @return {@code true} si la entidad es cliente, {@code false} en caso contrario
     */
    public boolean isCliente() {
        return roles.stream()
                .anyMatch(r -> r.getRol().equalsIgnoreCase("CLIENTE"));
    }

    /**
     * Indica si la entidad tiene el rol de proveedor.
     *
     * @return {@code true} si la entidad es proveedor, {@code false} en caso contrario
     */
    public boolean isProveedor() {
        return roles.stream()
                .anyMatch(r -> r.getRol().equalsIgnoreCase("PROVEEDOR"));
    }

    /**
     * Devuelve una representación textual de la entidad,
     * indicando su rol principal, nombre y NIF.
     *
     * @return cadena descriptiva de la entidad
     */
    @Override
    public String toString() {
        return getEtiquetaRol() + ": " + nombre + " (" + nif + ")";
    }

    /**
     * Devuelve una etiqueta descriptiva basada en los roles de la entidad.
     *
     * @return "Cliente y Proveedor", "Cliente", "Proveedor" o "Entidad"
     */
    public String getEtiquetaRol() {
        if (isCliente() && isProveedor()) {
            return "Cliente y Proveedor";
        }
        if (isCliente()) {
            return "Cliente";
        }
        if (isProveedor()) {
            return "Proveedor";
        }
        return "Entidad";
    }

}
