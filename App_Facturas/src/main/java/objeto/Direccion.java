/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author roque
 */
public class Direccion {
    
     private long idDireccion;   // PK
    private Entidad entidad;    // FK → ENTIDAD
    private String via;
    private String numero;
    private String ciudad;
    private String provincia;
    private String cp;
    private String pais;

    // ============================
    // CONSTRUCTORES
    // ============================
    public Direccion() {
        this.pais = "ES";  // valor por defecto igual que en la BD
    }

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

    public long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return via + " " + numero + ", " + ciudad + " (" + provincia + ")";
    }
    
}
