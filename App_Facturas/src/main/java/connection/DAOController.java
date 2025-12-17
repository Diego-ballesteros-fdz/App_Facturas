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
 * Controlador central de acceso a datos (DAO Controller).
 * <p>
 * Esta clase actúa como fachada entre la aplicación y los distintos DAOs
 * del sistema, centralizando la lógica de creación, modificación, eliminación
 * y consulta de entidades, productos, facturas, direcciones y relaciones
 * empresariales.
 * </p>
 *
 * <p>
 * Permite coordinar operaciones complejas que requieren transacciones
 * (por ejemplo: crear un cliente/proveedor con roles, dirección y relación
 * con una empresa).
 * </p>
 *
 * @author roque
 */
public class DAOController {

    /* =======================
     * DAOs DEL SISTEMA
     * ======================= */
    private EntidadDAO entidadDAO = new EntidadDAO(this);
    private RolDAO rolDAO = new RolDAO();
    private DireccionDAO direccionDAO = new DireccionDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    private LineaFacturaDAO lineaFacturaDAO = new LineaFacturaDAO();
    private EmpresaRelacionDAO empresaRelacionDAO = new EmpresaRelacionDAO();

    /* ============================================================
     *                  ENTIDADES / EMPRESAS
     * ============================================================ */

    /**
     * Crea una entidad genérica en la base de datos.
     *
     * @param e Entidad a crear.
     * @return Entidad creada con su ID asignado.
     */
    public Entidad crearEntidad(Entidad e) {
        return entidadDAO.crear(e);
    }

    /**
     * Crea un cliente a partir de una entidad.
     *
     * @param e Entidad base.
     * @return Cliente creado como {@link CliPro}.
     */
    public CliPro crearCliente(Entidad e) {

        Entidad creada = crearEntidad(e);
        rolDAO.insertarRol(creada.getNombre(), "CLIENTE");
        return (CliPro) entidadDAO.buscarPorId(creada.getIdEntidad());
    }

    /**
     * Crea una empresa en la base de datos.
     *
     * @param e Entidad empresa.
     * @return Empresa creada.
     */
    public Entidad crearEmpresa(Entidad e) {
        return entidadDAO.crear(e);
    }

    /**
     * Inserta una empresa junto con su dirección usando transacción manual.
     *
     * @param em Empresa a insertar.
     * @return {@code true} si la operación es correcta, {@code false} en caso contrario.
     */
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

    /**
    * Inserta una empresa (Entidad) junto con su dirección asociada
    * dentro de una única transacción.
    * <p>
    * El proceso consta de dos pasos:
    * <ol>
    *   <li>Inserción de la entidad en la tabla {@code ENTIDAD}</li>
    *   <li>Inserción de la dirección asociada en la tabla {@code DIRECCION}</li>
    * </ol>
    * Si alguno de los pasos falla, la operación se considera incorrecta
    * y no se garantiza la persistencia completa de los datos.
    * </p>
    *
    * <p>
    * Este método se utiliza principalmente para la creación de entidades
    * que actuarán como Cliente y/o Proveedor dentro de una empresa padre.
    * </p>
    *
    * @param em Objeto {@link Empresa} que contiene los datos de la entidad
    *           y su dirección asociada.
    * @return {@code true} si la empresa y su dirección se insertan correctamente,
    *         {@code false} en caso contrario.
    */
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

     /**
     * Inserta un cliente/proveedor junto con roles, dirección y relación con empresa padre.
     *
     * @param cp Cliente/Proveedor.
     * @param idPadre ID de la empresa padre.
     * @return {@code true} si se completa correctamente.
     */
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

     /**
     * Inserta una relación entre empresa padre e hija.
     *
     * @param con Conexión activa.
     * @param idPadre Empresa padre.
     * @param nombreEntidad Empresa hija.
     * @throws SQLException Error SQL.
     */
    public void insertarRelacion(Connection con, long idPadre, String nombreEntidad) throws SQLException {
        String sql = "INSERT INTO EMPRESA_RELACION (idPadre, idHija, tipoRelacion) "
                + "VALUES (?, (SELECT idEntidad FROM ENTIDAD WHERE nombre = ?), 'HOLAAA')";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idPadre);
            ps.setString(2, nombreEntidad);
            ps.executeUpdate();
        }
    }

     /**
     * Inserta los roles de un cliente/proveedor.
     *
     * @param con Conexión activa.
     * @param cp Cliente/Proveedor.
     * @param nombreEntidad Nombre de la entidad.
     * @throws SQLException Error SQL.
     */
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

    /**
    * Asigna un rol a una entidad existente.
    *
    * @param nombre Nombre de la entidad.
    * @param rol Rol a asignar (CLIENTE, PROVEEDOR, etc.).
    * @return {@code true} si el rol se inserta correctamente, {@code false} en caso contrario.
    */
    public boolean agregarRol(String nombre, String rol) {
        return rolDAO.insertarRol(nombre, rol);
    }

    /**
    * Elimina una entidad junto con toda su información asociada.
    * <p>
    * El orden de eliminación es:
    * <ol>
    *   <li>Roles asociados</li>
    *   <li>Direcciones</li>
    *   <li>Entidad</li>
    * </ol>
    * </p>
    *
    * @param nombre Nombre de la entidad a eliminar.
    * @return {@code true} si la entidad se elimina correctamente.
    */
    public boolean eliminarEntidad(String nombre) {
        rolDAO.eliminarRolesPorEntidad(nombre); // primero roles
        direccionDAO.eliminarDireccionesPorEntidad(nombre); // luego direcciones
        return entidadDAO.eliminar(nombre); // por último entidad
    }

    /**
    * Busca una entidad por su identificador.
    *
    * @param id Identificador de la entidad.
    * @return Entidad encontrada o {@code null} si no existe.
    */
    public Entidad buscarEntidadPorId(long id) {
        return entidadDAO.buscarPorId(id);
    }

    
    /**
     * Obtiene un listado con todas las entidades del sistema.
     *
     * @return Lista de entidades (Empresas, Clientes y Proveedores).
     */
    public List<Entidad> listarEntidades() {
        return entidadDAO.obtenerTodas();
    }

    
    /**
     * Obtiene todos los clientes y proveedores del sistema.
     *
     * @return Lista de {@link CliPro}.
     */
    public List<CliPro> listarClientesYProveedores() {
        return entidadDAO.listarClientesYProveedores();
    }

   /**
    * Obtiene los clientes y proveedores asociados a una empresa concreta.
    *
    * @param idEmpresa Identificador de la empresa padre.
    * @return Lista de {@link CliPro} relacionados con la empresa.
    */
    public List<CliPro> listarClientesYProveedores(long idEmpresa) {
        return entidadDAO.listarRelacionados(idEmpresa);
    }

    /**
    * Obtiene todas las entidades que actúan exclusivamente como empresas.
    *
    * @return Lista de {@link Empresa}.
    */
    public List<Empresa> listarSoloEmpresas() {
        return entidadDAO.listarSoloEmpresas();
    }

    /**
    * Modifica los datos de una entidad existente.
    *
    * @param e Entidad con los datos actualizados.
    * @return {@code true} si la modificación es correcta.
    */
    public boolean modificarEntidad(Entidad e) {
        return entidadDAO.modificar(e);
    }

    /**
    * Elimina todos los roles asociados a una entidad.
    *
    * @param nombre Nombre de la entidad.
    */
    public void eliminarRoles(String nombre) {
        if(!rolDAO.eliminarRolesPorEntidad(nombre)){
            System.out.println("Algo salio mal al eliminar roles");
        }
    }

    /**
    * Añade un rol adicional a una entidad existente.
    *
    * @param nombre Nombre de la entidad.
    * @param rol Rol a añadir.
    */
    public void agregarRolEntidad(String nombre, String rol) {
        rolDAO.insertarRol(nombre, rol);
    }
    
    /* ============================================================
     *                     EMPRESA RELACION
     * ============================================================ */

    /**
    * Crea una relación entre dos empresas.
    *
    * @param idPadre Identificador de la empresa padre.
    * @param idHija Identificador de la empresa hija.
    * @param tipo Tipo de relación (cliente, proveedor, etc.).
    * @return {@code true} si la relación se inserta correctamente.
    */
    public boolean agregarRelacionEmpresa(long idPadre, long idHija, String tipo) {
        return empresaRelacionDAO.insertarRelacion(idPadre, idHija, tipo);
    }

   /* ============================================================
    *                     DIRECCIONES
    * ============================================================ */

   /**
    * Añade una dirección asociada a una entidad.
    *
    * @param d Dirección a insertar.
    * @return {@code true} si la dirección se inserta correctamente.
    */
       public boolean agregarDireccion(Direccion d) {
        return direccionDAO.insertar(d);
    }
      
    /**
    * Obtiene todas las direcciones asociadas a una entidad.
    *
    * @param idEntidad Identificador de la entidad.
    * @return Lista de direcciones.
    */
    public List<Direccion> obtenerDireccionesDeEntidad(long idEntidad) {
        return direccionDAO.obtenerPorEntidad(idEntidad);
    }

    /* ============================================================
    *                     PRODUCTOS
    * ============================================================ */

   /**
    * Crea un nuevo producto.
    *
    * @param p Producto a crear.
    * @return Producto creado con su ID asignado.
    */
    public Producto crearProducto(Producto p) {
        return productoDAO.crear(p);
    }

    /**
    * Obtiene todos los productos registrados en el sistema.
    *
    * @return Lista de productos.
    */
    public List<Producto> listarProductos() {
        return productoDAO.obtenerTodos();
    }

    /**
    * Obtiene los productos de un proveedor concreto.
    *
    * @param idProveedor Identificador del proveedor.
    * @return Lista de productos.
    */
    public List<Producto> listarProductosPorProveedor(long idProveedor) {
        return productoDAO.obtenerPorProveedor(idProveedor);
    }

    /**
    * Obtiene todos los productos asociados a una empresa,
    * a través de sus proveedores relacionados.
    *
    * @param idProveedor Identificador de la empresa.
    * @return Lista de productos.
    */
    public List<Producto> listarProductosPorEmpresa(long idProveedor) {
        return productoDAO.listarProductosPorEmpresa(idProveedor);
    }

    /**
    * Elimina un producto por su nombre.
    *
    * @param nombre Nombre del producto.
    * @return {@code true} si se elimina correctamente.
    */
    public boolean eliminarProducto(String nombre) {
        return productoDAO.eliminar(nombre);
    }

   /* ============================================================
    *                     FACTURAS
    * ============================================================ */

   /**
    * Crea una factura.
    *
    * @param f Factura a crear.
    * @return Factura creada con ID asignado.
    */
    public Factura crearFactura(Factura f) {
        return facturaDAO.crear(f);
    }

    /**
    * Obtiene todas las facturas del sistema.
    *
    * @return Lista de facturas.
    */
    public List<Factura> listarFacturas() {
        return facturaDAO.obtenerTodas();
    }

    /**
    * Obtiene las facturas asociadas a una entidad concreta.
    *
    * @param idEntidad Identificador de la entidad.
    * @return Lista de facturas.
    */
    public List<Factura> listarFacturasPorEntidad(long idEntidad) {
        return facturaDAO.obtenerPorEntidad(idEntidad);
    }

    /**
    * Elimina una factura junto con todas sus líneas asociadas.
    *
    * @param idFactura Identificador de la factura.
    * @return {@code true} si la eliminación es correcta.
    */
    public boolean eliminarFactura(long idFactura) {
        lineaFacturaDAO.eliminarPorFactura(idFactura);
        return facturaDAO.eliminar(idFactura);
    }

    /**
    * Registra una factura junto con todas sus líneas.
    *
    * @param f Factura a registrar.
    * @param lineas Líneas de la factura.
    * @return {@code true} si todas las líneas se insertan correctamente.
    */
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

    /* ============================================================
    *                     LÍNEAS DE FACTURA
    * ============================================================ */

   /**
    * Añade una línea a una factura.
    *
    * @param lf Línea de factura.
    * @return {@code true} si se inserta correctamente.
    */
    public boolean agregarLineaFactura(LineaFactura lf) {
        return lineaFacturaDAO.insertar(lf);
    }

    /**
    * Obtiene todas las líneas asociadas a una factura.
    *
    * @param idFactura Identificador de la factura.
    * @return Lista de líneas de factura.
    */
    public List<LineaFactura> listarLineasDeFactura(long idFactura) {
        return lineaFacturaDAO.obtenerPorFactura(idFactura);
    }

}
