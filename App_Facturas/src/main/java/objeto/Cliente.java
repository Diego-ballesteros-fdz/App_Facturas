/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author roque
 */
public class Cliente extends Entidad{
    
    public Cliente() {}

    
    public Cliente(Entidad e) {
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
        return "Cliente: " + nombre + " (" + nif + ")";
    }
    
    
    
}
