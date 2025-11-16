/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author roque
 */
public class Rol {

    private long idRol;
    private String rol;   // CLIENTE o PROVEEDOR

    private Entidad entidad; // FK → ENTIDAD

    public Rol() {}

    public Rol(long idRol, String rol, Entidad entidad) {
        this.idRol = idRol;
        this.rol = rol;
        this.entidad = entidad;
    }

    // ==========================
    //       GETTERS / SETTERS
    // ==========================

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", rol='" + rol + '\'' +
                ", entidad=" + 
                  (entidad != null ? entidad.getNombre() + " (" + entidad.getNif() + ")" : "null") +
                '}';
    }
}

