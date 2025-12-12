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
    private Entidad entidad;

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    
    public CliPro() {}

    
    public CliPro(Entidad e,boolean cliente,boolean proveedor,Direccion dir) {
        super();
        this.entidad=e;
        isCliente=cliente;
        isProveedor=proveedor;
        this.dir=dir;
    }
    public CliPro(long id) {
        super();
        this.setIdEntidad(id);

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
