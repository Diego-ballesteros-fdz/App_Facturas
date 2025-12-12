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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import objects.Direccion;
import objects.CliPro;
import objects.Entidad;
import objects.Factura;
import objects.LineaFactura;
import objects.Producto;
import java.util.stream.Collectors;
import objects.Empresa;

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

    public boolean insertarEmpresa(Empresa em) {
        boolean creadaE = false, creadaD = false;
        //añadir la entidad empresa
        String sql = "INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, em.getNombre());
            ps.setString(2, em.getNif());
            ps.setString(3, em.getEmail());
            ps.setString(4, em.getTelefono());
            ps.setString(5, em.getObservaciones());

            ps.executeUpdate();
            ps.close();

            creadaE = true;

        } catch (SQLException e) {
            System.out.println("fallo al añadir empresa: " + e.getMessage());
            creadaE = false;
        }
        //añadir la relacion con direccion
        sql = "INSERT INTO DIRECCION (idEntidad, via, numero, ciudad, provincia, cp, pais) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, em.getNombre());
            ps.setString(2, em.getDir().getVia());
            ps.setString(3, em.getDir().getNumero());
            ps.setString(4, em.getDir().getCiudad());
            ps.setString(5, em.getDir().getProvincia());
            ps.setString(6, em.getDir().getCp());
            ps.setString(7, em.getDir().getPais());
            ps.executeUpdate();
            ps.close();
            creadaD = true;
        } catch (SQLException e) {
            System.out.println("Problema al añadir empresa: " + e.getMessage());
            creadaD = false;
        }
        if (creadaE && creadaD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertarCliPro(CliPro cp, long idPadre) {
        boolean creadaR = false, creadaRe = false;
        //añadir Entidad
        //añadir direccion
        Empresa em = new Empresa(cp.getEntidad(), cp.getDir());
        if (insertarEmpresa(em)) {
            //añadrid relacion Rol
            if (cp.isCliente() && cp.isProveedor()) {
                //ambos
                String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?)";
                try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, em.getNombre());
                    ps.setString(2, "CLIENTE");
                    creadaR = true;
                } catch (SQLException e) {
                    System.out.println("Error añ insertar Rol: " + e.getMessage());
                    creadaR = false;
                }
                try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, em.getNombre());
                    ps.setString(2, "PROVEEDOR");
                    ps.executeUpdate();
                    creadaR = true;
                } catch (SQLException e) {
                    System.out.println("Error añ insertar Rol: " + e.getMessage());
                    creadaR = false;
                }
            } else if (cp.isProveedor()) {
                //solo prov
                String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?)";
                try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, em.getNombre());
                    ps.setString(2, "PROVEEDOR");
                    ps.executeUpdate();
                    creadaR = true;
                } catch (SQLException e) {
                    System.out.println("Error añ insertar Rol: " + e.getMessage());
                    creadaR = false;
                }

            } else {
                //solo cliente
                String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?)";
                try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, em.getNombre());
                    ps.setString(2, "CLIENTE");
                    ps.executeUpdate();
                    creadaR = true;
                } catch (SQLException e) {
                    System.out.println("Error añ insertar Rol: " + e.getMessage());
                    creadaR = false;
                }

                //añadir EmpresaRealcion
                sql = "INSERT INTO EMPRESA_RELACION (idPadre, idHija, tipoRelacion) VALUES (?,(SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?)";
                try (Connection con = ConexionBD.get(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setLong(1, idPadre);
                    System.out.println(cp.getEntidad().getNombre());
                    ps.setString(2, cp.getEntidad().getNombre());
                    ps.setString(3, "HOLAAA");
                    ps.executeUpdate();

                    creadaRe = true;
                } catch (SQLException e) {
                    System.out.println("Error al añadir CliPro-Relacion: " + e.getMessage());
                    creadaRe = false;
                }
            }
        } else {
            System.out.println("Algo salio mal en la insercion del CliPro como empresa");
            return false;
        }

        if (creadaR && creadaRe) {
            return true;
        } else {
            System.out.println("Algo salio mal al añadir CliPro");
            return false;
        }

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

    
    
        
    public boolean registrarFactura(Factura f, List<LineaFactura> lineas){
        Factura facturaGuardada = facturaDAO.crear(f);
        if (facturaGuardada == null || facturaGuardada.getIdFactura() == 0) {
            System.out.println("ERROR: La factura no ha podido añadirse");
            return false;
        }
        boolean esCorrecto = true;
        for (LineaFactura lf : lineas) {
            
            lf.setFactura(facturaGuardada);
            boolean insertado = lineaFacturaDAO.insertar(lf);
            
            if (!insertado) {
                esCorrecto = false;
                System.out.println("No se pudo guardar alguna línea de la factura");
            }
        }
        return esCorrecto;
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
