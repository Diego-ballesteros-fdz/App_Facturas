package objects;

/**
 * Representa una dirección física asociada a una entidad.
 * Esta clase se utiliza para almacenar información postal como vía,
 * número, ciudad, provincia, código postal y país.
 * La dirección está relacionada con una {@link Entidad}.
 *
 * El atributo país tiene un valor por defecto que coincide con el
 * establecido en la base de datos.
 *
 * @author roque
 */
public class Direccion {

    /** Identificador único de la dirección (clave primaria) */
    private long idDireccion;

    /** Entidad asociada a la dirección (clave foránea hacia ENTIDAD) */
    private Entidad entidad;

    /** Nombre de la vía (calle, avenida, etc.) */
    private String via;

    /** Número de la vía */
    private String numero;

    /** Ciudad de la dirección */
    private String ciudad;

    /** Provincia de la dirección */
    private String provincia;

    /** Código postal */
    private String cp;

    /** País de la dirección */
    private String pais;

    // ============================
    // CONSTRUCTORES
    // ============================

    /**
     * Constructor por defecto.
     * Inicializa el país con el valor "ES", de acuerdo con el valor
     * por defecto definido en la base de datos.
     */
    public Direccion() {
        this.pais = "ES";
    }

    /**
     * Constructor que inicializa todos los atributos de la dirección.
     *
     * @param entidad   entidad asociada a la dirección
     * @param via       nombre de la vía
     * @param numero    número de la vía
     * @param ciudad    ciudad de la dirección
     * @param provincia provincia de la dirección
     * @param cp        código postal
     * @param pais      país de la dirección
     * @throws NullPointerException si alguno de los parámetros obligatorios es {@code null}
     */
    public Direccion(Entidad entidad, String via, String numero,
                     String ciudad, String provincia, String cp, String pais) {
        this.entidad = entidad;
        this.via = via;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.cp = cp;
        this.pais = pais;
    }

    // ============================
    // GETTERS y SETTERS
    // ============================

    /**
     * Devuelve el identificador de la dirección.
     *
     * @return el identificador de la dirección
     */
    public long getIdDireccion() {
        return idDireccion;
    }

    /**
     * Establece el identificador de la dirección.
     *
     * @param idDireccion identificador único a asignar
     */
    public void setIdDireccion(long idDireccion) {
        this.idDireccion = idDireccion;
    }

    /**
     * Devuelve la entidad asociada a la dirección.
     *
     * @return la entidad asociada, o {@code null} si no está definida
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * Establece la entidad asociada a la dirección.
     *
     * @param entidad entidad a asociar
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    /**
     * Devuelve el nombre de la vía.
     *
     * @return la vía de la dirección
     */
    public String getVia() {
        return via;
    }

    /**
     * Establece el nombre de la vía.
     *
     * @param via nombre de la vía
     */
    public void setVia(String via) {
        this.via = via;
    }

    /**
     * Devuelve el número de la vía.
     *
     * @return el número de la vía
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la vía.
     *
     * @param numero número de la vía
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Devuelve la ciudad de la dirección.
     *
     * @return la ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad de la dirección.
     *
     * @param ciudad ciudad a asignar
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Devuelve la provincia de la dirección.
     *
     * @return la provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece la provincia de la dirección.
     * @param provincia 
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    

    /**
     * Devuelve el código postal de la dirección.
     *
     * @return el código postal
     */
    public String getCp() {
        return cp;
    }

    /**
     * Establece el código postal de la dirección.
     *
     * @param cp código postal
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * Devuelve el país de la dirección.
     *
     * @return el país
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país de la dirección.
     *
     * @param pais país a asignar
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Devuelve una representación textual simplificada de la dirección,
     * compuesta por la vía, el número, la ciudad y la provincia.
     *
     * @return cadena descriptiva de la dirección
     */
    @Override
    public String toString() {
        return via + " " + numero + ", " + ciudad + " (" + provincia + ")";
    }

}
