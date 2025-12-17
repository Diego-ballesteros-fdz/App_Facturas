package objects;

/**
 * Representa una empresa dentro del sistema.
 * Esta clase extiende {@link Entidad} y añade la información específica
 * relacionada con la dirección de la empresa.
 *
 * Se utiliza principalmente como una especialización de Entidad
 * cuando esta representa una empresa con una dirección asociada.
 *
 * @author roque
 */
public class Empresa extends Entidad {

    /** Dirección asociada a la empresa */
    public Direccion dir;

    // =======================
    // CONSTRUCTORES
    // =======================

    /**
     * Constructor por defecto.
     * Crea una empresa sin inicializar sus atributos.
     */
    public Empresa() {
    }

    /**
     * Constructor que crea una empresa a partir de una entidad existente
     * y una dirección asociada.
     *
     * Copia los datos básicos de la entidad recibida y asigna la dirección.
     *
     * @param e entidad base de la que se copian los datos
     * @param d dirección asociada a la empresa
     * @throws NullPointerException si {@code e} o {@code d} son {@code null}
     */
    public Empresa(Entidad e, Direccion d) {
        super();
        this.setIdEntidad(e.getIdEntidad());
        this.setNombre(e.getNombre());
        this.setNif(e.getNif());
        this.setEmail(e.getEmail());
        this.setTelefono(e.getTelefono());
        this.setObservaciones(e.getObservaciones());
        this.dir = d;
    }

    // =======================
    // GETTERS Y SETTERS
    // =======================

    /**
     * Devuelve la dirección asociada a la empresa.
     *
     * @return la dirección de la empresa
     */
    public Direccion getDir() {
        return dir;
    }

    /**
     * Establece la dirección asociada a la empresa.
     *
     * @param dir dirección a asignar
     */
    public void setDir(Direccion dir) {
        this.dir = dir;
    }

    /**
     * Devuelve el identificador de la entidad empresa.
     *
     * @return el identificador de la entidad
     */
    public long getIdEntidad() {
        return idEntidad;
    }

    /**
     * Establece el identificador de la entidad empresa.
     *
     * @param idEntidad identificador a asignar
     */
    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * Devuelve el nombre o razón social de la empresa.
     *
     * @return el nombre de la empresa
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre o razón social de la empresa.
     *
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el NIF de la empresa.
     *
     * @return el NIF
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF de la empresa.
     *
     * @param nif NIF a asignar
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve el correo electrónico de la empresa.
     *
     * @return el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de la empresa.
     *
     * @param email email a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono de contacto de la empresa.
     *
     * @return el teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de contacto de la empresa.
     *
     * @param telefono teléfono a asignar
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve las observaciones asociadas a la empresa.
     *
     * @return las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones asociadas a la empresa.
     *
     * @param observaciones observaciones a asignar
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Devuelve una representación textual de la empresa,
     * compuesta por su nombre y NIF.
     *
     * @return cadena descriptiva de la empresa
     */
    @Override
    public String toString() {
        return "Empresa: " + nombre + " (" + nif + ")";
    }

}
