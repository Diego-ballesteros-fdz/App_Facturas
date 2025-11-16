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
public class Factura {
    
    private long idFactura;
    private LocalDate fecha;
    private double total;
    private Entidad cliente; // relación real con ENTIDAD

    
    
     public long getIdFactura() { return idFactura; }

    public void setIdFactura(long idFactura) { this.idFactura = idFactura; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }

    public void setTotal(double total) { this.total = total; }

    public Entidad getCliente() { return cliente; }

    public void setCliente(Entidad cliente) { this.cliente = cliente; }
}
