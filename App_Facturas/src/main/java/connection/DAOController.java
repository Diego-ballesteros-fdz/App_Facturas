/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import connection.DAO.RolDAO;
import connection.DAO.EntidadDAO;
import connection.DAO.FacturaDAO;
import connection.DAO.ProductoDAO;
import connection.DAO.DireccionDAO;
import connection.DAO.EmpresaRelacionDAO;
import connection.DAO.LineaFacturaDAO;
import java.util.List;
import objects.Direccion;
import objects.CliPro;
import objects.Entidad;
import objects.Factura;
import objects.LineaFactura;
import objects.Producto;
import java.util.stream.Collectors;

/**
 *
 * @author roque
 */
public class DAOController {

    // DAOs del sistema
    private EntidadDAO entidadDAO = new EntidadDAO();
    private RolDAO rolDAO = new RolDAO();
    private DireccionDAO direccionDAO = new DireccionDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    private LineaFacturaDAO lineaFacturaDAO = new LineaFacturaDAO();
    private EmpresaRelacionDAO empresaRelacionDAO = new EmpresaRelacionDAO();

    
    // ============================================================
    //            ENTIDADES (CliPro, Proveedor, Empresa)
    // ============================================================
    public Entidad crearEntidad(Entidad e) {
        return entidadDAO.crear(e);
    }

    public CliPro crearCliente(Entidad e) {

        Entidad creada = crearEntidad(e);
        rolDAO.insertarRol(creada.getIdEntidad(), "CLIENTE");
        return (CliPro) entidadDAO.buscarPorId(creada.getIdEntidad());
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

    // Clientes + Proveedores
    public List<Entidad> listarClientesYProveedores() {
    return entidadDAO.listarClientesYProveedores();
    }

    //Busqueda solo por Id de Clientes y Provedores
    public List<Entidad> listarClientesYProveedores(long idEmpresa) {
        return entidadDAO.listarRelacionados(idEmpresa);
    }
    
    public List<Entidad> listarSoloEmpresas() {
        return entidadDAO.listarSoloEmpresas();
    }
    
    public boolean modificarEntidad(Entidad e) {
        return entidadDAO.modificar(e);
    }
    public void eliminarRoles(long idEntidad) {
        rolDAO.eliminarRolesPorEntidad(idEntidad);
    }
    
    public void agregarRolEntidad(long idEntidad, String rol) {
        rolDAO.insertarRol(idEntidad, rol);
    }
    
    // ============================================================
    //                     EMPRESA RELACION
    // ============================================================

  public boolean agregarRelacionEmpresa(long idPadre, long idHija, String tipo) {
        return empresaRelacionDAO.insertarRelacion(idPadre, idHija, tipo);
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
    
    public List<Producto> listarProductosPorEmpresa(long idProveedor) {
        return productoDAO.listarProductosPorEmpresa(idProveedor);
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
