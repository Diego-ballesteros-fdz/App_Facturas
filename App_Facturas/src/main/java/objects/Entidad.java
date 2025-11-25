/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roque
 */
public class Entidad {
    
     protected long idEntidad;
    protected String nombre;
    protected String nif;
    protected String email;
    protected String telefono;
    protected String observaciones;

    public Entidad( String nombre, String nif, String email, String telefono, String observaciones) {
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.telefono = telefono;
        this.observaciones = observaciones;
    }
    
    

  
    private List<Rol> roles = new ArrayList<>();

    public Entidad() {}

    public Entidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    // =======================
    // GETTERS Y SETTERS BASE
    // =======================

    public long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNif() { return nif; }

    public void setNif(String nif) { this.nif = nif; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // =======================
    //       ROLES
    // =======================

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void addRol(Rol r) {
        if (!roles.contains(r)) roles.add(r);
    }

    public void removeRol(Rol r) {
        roles.remove(r);
    }

    public boolean isCliente() {
        return roles.stream().anyMatch(r -> r.getRol().equalsIgnoreCase("CLIENTE"));
    }

    public boolean isProveedor() {
        return roles.stream().anyMatch(r -> r.getRol().equalsIgnoreCase("PROVEEDOR"));
    }

     @Override
    public String toString() {
        return getEtiquetaRol() + ": " + nombre + " (" + nif + ")";
    }
    
    public String getEtiquetaRol() {
        if (isCliente() && isProveedor()) return "Cliente y Proveedor";
        if (isCliente()) return "Cliente";
        if (isProveedor()) return "Proveedor";
        return "Entidad";
    }
    
    
    
}
