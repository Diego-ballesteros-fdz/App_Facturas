/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app_facturas.app_facturas;

import java.util.List;
import objeto.Direccion;
import objeto.Cliente;
import objeto.Entidad;
import objeto.Factura;
import objeto.LineaFactura;
import objeto.Producto;
import objeto.Proveedor;

/**
 *
 * @author roque
 */
public class SistemaService {
    
    // DAOs del sistema
    private EntidadDAO entidadDAO = new EntidadDAO();
    private RolDAO rolDAO = new RolDAO();
    private DireccionDAO direccionDAO = new DireccionDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    private LineaFacturaDAO lineaFacturaDAO = new LineaFacturaDAO();

    // ============================================================
    //            ENTIDADES (Cliente, Proveedor, Empresa)
    // ============================================================

    public Entidad crearEntidad(Entidad e) {
        return entidadDAO.crear(e);
    }

    public Cliente crearCliente(Entidad e) {
        Entidad creada = crearEntidad(e);
        rolDAO.insertarRol(creada.getIdEntidad(), "CLIENTE");
        return (Cliente) entidadDAO.buscarPorId(creada.getIdEntidad());
    }

    public Proveedor crearProveedor(Entidad e) {
        Entidad creada = crearEntidad(e);
        rolDAO.insertarRol(creada.getIdEntidad(), "PROVEEDOR");
        return (Proveedor) entidadDAO.buscarPorId(creada.getIdEntidad());
    }

    public Entidad crearEmpresa(Entidad e) {
        return entidadDAO.crear(e);
    }

    public boolean agregarRol(long idEntidad, String rol) {
        return rolDAO.insertarRol(idEntidad, rol);
    }

    public boolean eliminarEntidad(long idEntidad) {
        rolDAO.eliminarRolesPorEntidad(idEntidad); // primero roles
        direccionDAO.eliminarDireccionesPorEntidad(idEntidad); // luego direcciones
        return entidadDAO.eliminar(idEntidad); // por último entidad
    }

    public Entidad buscarEntidadPorId(long id) {
        return entidadDAO.buscarPorId(id);
    }

    public List<Entidad> listarEntidades() {
        return entidadDAO.obtenerTodas();
    }

    public List<Entidad> listarClientes() {
        return entidadDAO.obtenerTodas().stream().filter(e -> e.isCliente()).toList();
    }

    public List<Entidad> listarProveedores() {
        return entidadDAO.obtenerTodas().stream().filter(e -> e.isProveedor()).toList();
    }

    // ============================================================
    //                     DIRECCIONES
    // ============================================================

    public boolean agregarDireccion(Direccion d) {
        return direccionDAO.insertar(d);
    }

    public List<Direccion> obtenerDireccionesDeEntidad(long idEntidad) {
        return direccionDAO.obtenerPorEntidad(idEntidad);
    }

    // ============================================================
    //                     PRODUCTOS
    // ============================================================

    public Producto crearProducto(Producto p) {
        return productoDAO.crear(p);
    }

    public List<Producto> listarProductos() {
        return productoDAO.obtenerTodos();
    }

    public List<Producto> listarProductosPorProveedor(long idProveedor) {
        return productoDAO.obtenerPorProveedor(idProveedor);
    }

    public boolean eliminarProducto(long idProducto) {
        return productoDAO.eliminar(idProducto);
    }

    // ============================================================
    //                     FACTURAS
    // ============================================================

    public Factura crearFactura(Factura f) {
        return facturaDAO.crear(f);
    }

    public List<Factura> listarFacturas() {
        return facturaDAO.obtenerTodas();
    }

    public List<Factura> listarFacturasPorEntidad(long idEntidad) {
        return facturaDAO.obtenerPorEntidad(idEntidad);
    }

    public boolean eliminarFactura(long idFactura) {
        lineaFacturaDAO.eliminarPorFactura(idFactura);
        return facturaDAO.eliminar(idFactura);
    }

    // ============================================================
    //                     LINEAS DE FACTURA
    // ============================================================

    public boolean agregarLineaFactura(LineaFactura lf) {
        return lineaFacturaDAO.insertar(lf);
    }

    public List<LineaFactura> listarLineasDeFactura(long idFactura) {
        return lineaFacturaDAO.obtenerPorFactura(idFactura);
    }
    
}
