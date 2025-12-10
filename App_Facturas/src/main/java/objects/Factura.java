/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import connection.DAOController;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 *
 * @author roque
 */
public class Factura extends Entidad {
    
    private long idFactura;
    private LocalDate fechaEmision;
    private double total;
    private Entidad cliente; // relación real con ENTIDAD
    private String tipo;

    
    public Factura(LocalDate fechaEmision,String tipo, String nombre,ArrayList<Integer> lista,ArrayList<Entidad> listaProd) {
        this.fechaEmision = fechaEmision;
        this.cliente = encontrarCliente(nombre);
        this.tipo = tipo;      
        this.total=calcularTotal(lista,listaProd);
        
    }
    
    public Factura(){
        
    }
    
    public Entidad encontrarCliente(String nombre){
        DAOController dao=new DAOController();
        //dividimos el nombre para quedarnos con el id
        String[] partes = nombre.split("-");
        Entidad e=dao.buscarEntidadPorId(Long.valueOf(partes[0]));
        return e;
    }
    
    public double calcularTotal(ArrayList<Integer> lista,ArrayList<Entidad> listaProd){
        double total=0;
        
        for(Entidad x: listaProd){
            Producto p=(Producto)x;
            for(int y:lista){
                total=total+(p.getPrecio()*y);
            }
        }
        return total;
    }

     public long getIdFactura() { return idFactura; }

    public void setIdFactura(long idFactura) { this.idFactura = idFactura; }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    

    public double getTotal() { return total; }

    public void setTotal(double total) { this.total = total; }

    public Entidad getCliente() { return cliente; }

    public void setCliente(Entidad cliente) { this.cliente = cliente; }
    
    
    @Override
    public String toString() {
        return "Factura #" + idFactura +
               " | " + fechaEmision +
               " | Cliente: " + (cliente != null ? cliente.getNombre() : "N/A");
    }

}
