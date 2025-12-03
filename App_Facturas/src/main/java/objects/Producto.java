/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author roque
 */
public class Producto extends Entidad {
    
    private long idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Entidad proveedor;  // relación con ENTIDAD (proveedor)
    

    public Producto(String nombre, String descripcion, double precio, int stock, Entidad proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }
    
    public Producto(){
        
    }
    private long idProveedor; //relacion con proveedor (solo id)
    
    
    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public Entidad getProveedor() {
        return proveedor;
    }

    public void setProveedor(Entidad proveedor) {
        this.proveedor = proveedor;
    }

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    @Override
    public String toString() {
        return nombre + " | " + precio + "€";
    }
    

}
    

