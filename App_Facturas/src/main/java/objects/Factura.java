/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.time.LocalDate;

/**
 *
 * @author roque
 */
public class Factura extends Entidad {
    
    private long idFactura;
    private LocalDate fechaEmision;
    private LocalDate fechaEntrega;
    private double total;
    private Entidad cliente; // relación real con ENTIDAD
    private String tipo;

    
    public Factura(LocalDate fechaEmision, LocalDate fechaEntrega, double total, Entidad cliente, String tipo) {
        this.fechaEmision = fechaEmision;
        this.fechaEntrega = fechaEntrega;
        this.total = total;
        this.cliente = cliente;
        this.tipo = tipo;
    }
    
    public Factura(){
        
    }

     public long getIdFactura() { return idFactura; }

    public void setIdFactura(long idFactura) { this.idFactura = idFactura; }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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
               " | " + fechaEntrega +
               " | Cliente: " + (cliente != null ? cliente.getNombre() : "N/A");
    }

}
