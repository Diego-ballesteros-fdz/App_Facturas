package objects;

/**
 * Representa un rol asociado a una entidad del sistema.
 * Un rol define el comportamiento de una entidad dentro del sistema,
 * indicando si actúa como cliente o proveedor.
 *
 * Cada rol está vinculado a una única {@link Entidad}.
 *
 * @author roque
 */
public class Rol {

    /** Identificador único del rol */
    private long idRol;

    /** Tipo de rol asociado a la entidad */
    private String rol;

    /** Entidad asociada al rol */
    private Entidad entidad;

    // ==========================
    // CONSTRUCTORES
    // ==========================

    /**
     * Constructor por defecto.
     * Crea un rol sin inicializar sus atributos.
     */
    public Rol() {
    }

    /**
     * Constructor que inicializa todos los atributos del rol.
     *
     * @param idRol   identificador del rol
     * @param rol     tipo de rol
     * @param entidad entidad asociada al rol
     * @throws NullPointerException si {@code rol} o {@code entidad} son {@code null}
     */
    public Rol(long idRol, String rol, Entidad entidad) {
        this.idRol = idRol;
        this.rol = rol;
        this.entidad = entidad;
    }

    // ==========================
    // GETTERS / SETTERS
    // ==========================

    /**
     * Devuelve el identificador del rol.
     *
     * @return el identificador del rol
     */
    public long getIdRol() {
        return idRol;
    }

    /**
     * Establece el identificador del rol.
     *
     * @param idRol identificador a asignar
     */
    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    /**
     * Devuelve el tipo de rol asociado.
     *
     * @return el rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el tipo de rol asociado.
     *
     * @param rol tipo de rol a asignar
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Devuelve la entidad asociada al rol.
     *
     * @return la entidad asociada
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * Establece la entidad asociada al rol.
     *
     * @param entidad entidad a asignar
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    /**
     * Devuelve una representación textual del rol,
     * incluyendo su identificador, tipo y la entidad asociada.
     *
     * @return cadena descriptiva del rol
     */
    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", rol='" + rol + '\'' +
                ", entidad=" +
                (entidad != null
                        ? entidad.getNombre() + " (" + entidad.getNif() + ")"
                        : "null") +
                '}';
    }
}

