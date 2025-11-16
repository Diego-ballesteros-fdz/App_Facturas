/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author roque
 */
public class Empresa extends Entidad{
    
   
    public Empresa() {}

    
    
    public Empresa(Entidad e) {
        super();
        this.setIdEntidad(e.getIdEntidad());
        this.setNombre(e.getNombre());
        this.setNif(e.getNif());
        this.setEmail(e.getEmail());
        this.setTelefono(e.getTelefono());
        this.setObservaciones(e.getObservaciones());
    }

    @Override
    public String toString() {
        return "Empresa: " + nombre + " (" + nif + ")";
    }
    
}
