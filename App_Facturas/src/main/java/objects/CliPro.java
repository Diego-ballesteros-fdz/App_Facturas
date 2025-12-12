/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.util.List;

/**
 *
 * @author roque
 */
public class CliPro extends Entidad {

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

    public CliPro() {
    }

    public CliPro(Entidad e, Direccion dir) {
        super(e);
        this.entidad = e;
        this.dir = dir;
    }

    public CliPro(Entidad e, boolean cliente, boolean proveedor, Direccion dir) {
        super();
        this.entidad = e;
        isCliente = cliente;
        isProveedor = proveedor;
        this.dir = dir;
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

    public void setRoles(List<Rol> roles) {
        for(Rol r:roles){
           if(r.getRol().equalsIgnoreCase("CLIENTE")){
                 isCliente=true;       
           }else if(r.getRol().equalsIgnoreCase("PROVEEDOR")){
               isProveedor=true;
           }
        }
    }

    @Override
    public String toString() {
        if (isProveedor && isCliente) {
            return "Cliente y Proveedor " + nombre + " (" + nif + ")";
        } else if (isProveedor) {
            return "Proveedor " + nombre + " (" + nif + ")";
        } else {
            return "Cliente " + nombre + " (" + nif + ")";
        }
    }

}
