/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author roque
 */
public class LineaFactura {
    
    private long idLinea;
    private Factura factura;
    private Producto producto;

    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public LineaFactura() {}

    public long getIdLinea() { return idLinea; }

    public void setIdLinea(long idLinea) { this.idLinea = idLinea; }

    public Factura getFactura() { return factura; }

    public void setFactura(Factura factura) { this.factura = factura; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }

    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
}
