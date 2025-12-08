/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author roque
 */
public class CliPro extends Entidad{
    
    private boolean isCliente;
    private boolean isProveedor;
    private Direccion dir;
    
    public CliPro() {}

    
    public CliPro(Entidad e,boolean cliente,boolean proveedor) {
        super();
        this.setIdEntidad(e.getIdEntidad());
        this.setNombre(e.getNombre());
        this.setNif(e.getNif());
        this.setEmail(e.getEmail());
        this.setTelefono(e.getTelefono());
        this.setObservaciones(e.getObservaciones());
        isCliente=cliente;
        isProveedor=proveedor;
    }

    public Direccion getDir() {
        return dir;
    }

    public void setDir(Direccion dir) {
        this.dir = dir;
    }

    public boolean isIsCliente() {
        return isCliente;
    }

    public void setIsCliente(boolean isCliente) {
        this.isCliente = isCliente;
    }

    public boolean isIsProveedor() {
        return isProveedor;
    }

    public void setIsProveedor(boolean isProveedor) {
        this.isProveedor = isProveedor;
    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + " (" + nif + ")";
    }
    
    
    
}
