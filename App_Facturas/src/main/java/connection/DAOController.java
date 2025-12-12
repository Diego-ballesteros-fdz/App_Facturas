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
    private EntidadDAO entidadDAO = new EntidadDAO(this);
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
        try {
            Connection con = ConexionBD.get();
            //añadir la entidad empresa
            String sql = "INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql);) {
                con.setAutoCommit(false);

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
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                ps.setString(1, em.getNombre());
                ps.setString(2, em.getDir().getVia());
                ps.setString(3, em.getDir().getNumero());
                ps.setString(4, em.getDir().getCiudad());
                ps.setString(5, em.getDir().getProvincia());
                ps.setString(6, em.getDir().getCp());
                ps.setString(7, em.getDir().getPais());
                ps.executeUpdate();
                con.commit();
                ps.close();
                creadaD = true;
            } catch (SQLException e) {
                System.out.println("Problema al añadir empresa: " + e.getMessage());
                creadaD = false;
            }
            if (creadaE && creadaD) {
                try {
                    con.commit();
                    con.setAutoCommit(true);
                } catch (SQLException e) {

                }
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean insertarEmpresaCP(Empresa em) {
        boolean creadaE = false, creadaD = false;
        try {
            Connection con = ConexionBD.get();
            con.setAutoCommit(false);
            //añadir la entidad empresa
            String sql = "INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql);) {
                con.setAutoCommit(false);

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
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                ps.setString(1, em.getNombre());
                ps.setString(2, em.getDir().getVia());
                ps.setString(3, em.getDir().getNumero());
                ps.setString(4, em.getDir().getCiudad());
                ps.setString(5, em.getDir().getProvincia());
                ps.setString(6, em.getDir().getCp());
                ps.setString(7, em.getDir().getPais());
                ps.executeUpdate();
                con.commit();
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
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean insertarCliPro(CliPro cp, long idPadre) {
        try (Connection con = ConexionBD.get()) {

            con.setAutoCommit(false); // INICIO TRANSACCIÓN

            Empresa em = new Empresa(cp.getEntidad(), cp.getDir());

            // 1. Insertar entidad + dirección
            insertarEmpresaCP(em);

            // 2. Insertar roles
            insertarRoles(con, cp, em.getNombre());

            // 3. Insertar relación empresa
            insertarRelacion(con, idPadre, em.getNombre());

            // TODO OK
            con.commit();
            con.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertarRelacion(Connection con, long idPadre, String nombreEntidad) throws SQLException {
        String sql = "INSERT INTO EMPRESA_RELACION (idPadre, idHija, tipoRelacion) "
                + "VALUES (?, (SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), 'HOLAAA')";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idPadre);
            ps.setString(2, nombreEntidad);
            ps.executeUpdate();
        }
    }

    public void insertarRoles(Connection con, CliPro cp, String nombreEntidad) throws SQLException {
        String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            if (cp.isIsCliente()) {
                ps.setString(1, nombreEntidad);
                ps.setString(2, "CLIENTE");
                ps.executeUpdate();
            }

            if (cp.isIsProveedor()) {
                ps.setString(1, nombreEntidad);
                ps.setString(2, "PROVEEDOR");
                ps.executeUpdate();
            }
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
    public List<CliPro> listarClientesYProveedores() {
        return entidadDAO.listarClientesYProveedores();
    }

    //Busqueda solo por Id de Clientes y Provedores
    public List<CliPro> listarClientesYProveedores(long idEmpresa) {
        return entidadDAO.listarRelacionados(idEmpresa);
    }

    public List<Empresa> listarSoloEmpresas() {
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

    public boolean registrarFactura(Factura f, List<LineaFactura> lineas) {
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
