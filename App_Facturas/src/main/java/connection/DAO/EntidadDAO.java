/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import connection.ConexionBD;
import connection.DAOController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objects.CliPro;
import objects.Direccion;
import objects.Empresa;
import objects.Entidad;

/**
 * DAO encargado de la gestión de entidades del sistema.
 * <p>
 * Una {@link Entidad} puede representar:
 * </p>
 * <ul>
 *   <li>Empresa (sin roles)</li>
 *   <li>Cliente</li>
 *   <li>Proveedor</li>
 *   <li>Cliente y Proveedor (CliPro)</li>
 * </ul>
 *
 * <p>
 * Este DAO se encarga exclusivamente del acceso a datos (CRUD y listados),
 * delegando la lógica de negocio en {@link DAOController}.
 * </p>
 *
 * @author roque
 */
public class EntidadDAO {
    
     /** DAO encargado de la gestión de roles */
    private RolDAO rolDAO = new RolDAO();

    /** Controlador general para acceso a otros DAOs */
    private DAOController dao;

    /**
     * Constructor por defecto.
     */
    public EntidadDAO() {
    }

    /**
     * Constructor que recibe el controlador principal.
     *
     * @param dao controlador general del sistema
     */
     public EntidadDAO(DAOController dao){
        this.dao=dao;
    }
    
    /**
     * Obtiene todas las entidades que tienen el rol CLIENTE.
     *
     * @return lista de entidades cliente
     */
     public List<Entidad> listarClientes() {
        List<Entidad> lista = new ArrayList<>();

        String sql =
            "SELECT e.* FROM ENTIDAD e " +
            "JOIN ROLES_ENTIDAD r ON e.idEntidad = r.idEntidad " +
            "WHERE r.rol = 'CLIENTE'";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Entidad e = mapear(rs);
                e.setRoles(rolDAO.obtenerRolesPorEntidad(e));
                lista.add(convertirSegunRol(e));   // IMPORTANTE
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al listar clientes: " + ex.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene todas las entidades que tienen el rol PROVEEDOR.
     *
     * @return lista de proveedores como {@link CliPro}
     */ 
    public List<CliPro> listarProveedores() {
        List<CliPro> lista = new ArrayList<>();

        String sql =
            "SELECT e.* FROM ENTIDAD e " +
            "JOIN ROLES_ENTIDAD r ON e.idEntidad = r.idEntidad " +
            "WHERE r.rol = 'PROVEEDOR'";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {     
                //creamos entidad
                Entidad e = mapear(rs);
                //creamos la dir
                List<Direccion> dir= dao.obtenerDireccionesDeEntidad(rs.getLong("idEntidad"));
                //creamos el cp
                CliPro cp=new CliPro(e,dir.get(0));
                //setea,os roles a CliPro
                cp.setRoles(rolDAO.obtenerRolesPorEntidad(rs.getLong("idEntidad"),cp));
                lista.add(cp);
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al listar proveedores: " + ex.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene todas las entidades que tengan rol CLIENTE y/o PROVEEDOR.
     *
     * @return lista de {@link CliPro}
     */
    public List<CliPro> listarClientesYProveedores() {
        List<CliPro> lista = new ArrayList<>();

        String sql =
            "SELECT DISTINCT e.* " +
            "FROM ENTIDAD e " +
            "JOIN ROLES_ENTIDAD r ON e.idEntidad = r.idEntidad " +
            "WHERE r.rol IN ('CLIENTE','PROVEEDOR')";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                //creamos entidad
                Entidad e = mapear(rs);
                //creamos la dir
                List<Direccion> dir= dao.obtenerDireccionesDeEntidad(rs.getLong("idEntidad"));
                System.out.println("direccion a añadir: "+dir.get(0));
                //creamos el cp
                CliPro cp=new CliPro(e,dir.get(0));
                //setea los roles a CliPro
                cp.setRoles(rolDAO.obtenerRolesPorEntidad(rs.getLong("idEntidad"),cp));
                System.out.println("Cp añadido en ListarProvYCli: "+cp);
                lista.add(cp);
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al listar cliente+proveedor: " + ex.getMessage());
        }

        return lista;
    }
    
    /**
     * Obtiene las entidades que no tienen roles asociados (empresas).
     *
     * @return lista de empresas
     */
    public List<Empresa> listarSoloEmpresas() {
        List<Empresa> lista = new ArrayList<>();

        String sql = "SELECT e.* FROM ENTIDAD e LEFT JOIN ROLES_ENTIDAD r ON e.idEntidad = r.idEntidad WHERE r.idEntidad IS NULL ORDER BY e.idEntidad";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Entidad e= mapear(rs);
                List<Direccion> dir= dao.obtenerDireccionesDeEntidad(rs.getLong("idEntidad"));
                Empresa em=new Empresa(e,dir.get(0));
                lista.add(em);
            }

        } catch (Exception ex) {
            System.out.println("❌ Error listando solo empresas: " + ex.getMessage());
        }

        return lista;
    }
    
    /**
     * Inserta una entidad en la base de datos.
     *
     * @param e entidad a crear
     * @return entidad creada con ID asignado
     */
    public Entidad crear(Entidad e) {

        String sql = "INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getNif());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getTelefono());
            ps.setString(5, e.getObservaciones());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) e.setIdEntidad(rs.getLong(1));

            return e;

        } catch (SQLException ex) {
            System.out.println("❌ Error al crear entidad: " + ex.getMessage());
            return null;
        }
    }

    
    /**
     * Actualiza una entidad existente.
     *
     * @param e entidad a modificar
     * @return true si se modificó correctamente
     */
    public boolean modificar(Entidad e) {

        String sql = "UPDATE ENTIDAD SET nombre = ?, nif = ?, email = ?, "
                + "telefono = ?, observaciones = ? WHERE nombre = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getNif());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getTelefono());
            ps.setString(5, e.getObservaciones());
            ps.setString(6, e.getNombre());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al modificar entidad: " + ex.getMessage());
            return false;
        }
    }

   
     /**
     * Elimina una entidad por nombre.
     *
     * @param nombre nombre de la entidad
     * @return true si se eliminó
     */

    public boolean eliminar(String nombre) {

        String sql = "DELETE FROM ENTIDAD WHERE nombre = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar entidad: " + ex.getMessage());
            return false;
        }
    }

    /**
    * Busca una entidad por su identificador.
    * <p>
    * Devuelve únicamente la entidad base sin cargar roles ni direcciones.
    * Si se necesita la información completa, debe completarse en capas superiores.
    * </p>
    *
    * @param id identificador de la entidad
    * @return entidad encontrada o {@code null} si no existe
    */
    public Entidad buscarPorId(long id) {

        String sql = "SELECT * FROM ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Entidad e = mapear(rs);

                return e;
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al buscar entidad: " + ex.getMessage());
        }
        return null;
    }

    /**
    * Obtiene todas las entidades del sistema.
    * <p>
    * Para cada entidad se cargan sus roles y se convierte automáticamente
    * al subtipo correspondiente:
    * </p>
    * <ul>
    *   <li>{@link CliPro} si tiene rol CLIENTE y/o PROVEEDOR</li>
    *   <li>{@link Entidad} si no tiene roles (empresa)</li>
    * </ul>
    *
    * @return lista completa de entidades del sistema
    */
    public List<Entidad> obtenerTodas() {

        List<Entidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM ENTIDAD";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Entidad e = mapear(rs);
                e.setRoles(rolDAO.obtenerRolesPorEntidad(e));
                lista.add(convertirSegunRol(e));
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al obtener entidades: " + ex.getMessage());
        }

        return lista;
    }

    /**
     * Convierte un {@link ResultSet} en una {@link Entidad}.
     */
    private Entidad mapear(ResultSet rs) throws SQLException {

        Entidad e = new Entidad();

        e.setIdEntidad(rs.getLong("idEntidad"));
        e.setNombre(rs.getString("nombre"));
        e.setNif(rs.getString("nif"));
        e.setEmail(rs.getString("email"));
        e.setTelefono(rs.getString("telefono"));
        e.setObservaciones(rs.getString("observaciones"));
        
        System.out.println("Entidad creada en Mapear: "+e);
        return e;
    }
    
    
   /**
    * Mapea una fila del {@link ResultSet} a un objeto {@link Entidad}
    * sin cargar roles ni relaciones adicionales.
    * <p>
    * Este método se utiliza cuando solo se necesita la información
    * básica de la entidad (por ejemplo, para empresas sin roles).
    * </p>
    *
    * @param rs ResultSet posicionado en una fila válida
    * @return entidad creada a partir de los datos de la BD
    * @throws SQLException si ocurre un error al acceder a los datos
    */
    private Entidad mapearEntidad(ResultSet rs) throws SQLException {

        Entidad e = new Entidad();

        e.setIdEntidad(rs.getLong("idEntidad"));
        e.setNombre(rs.getString("nombre"));
        e.setNif(rs.getString("nif"));
        e.setEmail(rs.getString("email"));
        e.setTelefono(rs.getString("telefono"));
        e.setObservaciones(rs.getString("observaciones"));

        return e;
    }


    /**
     * Convierte una entidad base en su subtipo correspondiente según roles.
     */
    private Entidad convertirSegunRol(Entidad e) {

        boolean cli = e.isCliente();
        boolean prov = e.isProveedor();

        // Si es cliente o proveedor → CliPro
        if (cli || prov) {
            return new CliPro(e, cli, prov, null);
        }

        // Si no tiene roles → Empresa
        return e;
    }
    
    /**
    * Obtiene los clientes y/o proveedores asociados a una empresa concreta.
    * <p>
    * La relación se obtiene desde la tabla {@code EMPRESA_RELACION}, donde:
    * </p>
    * <ul>
    *   <li>{@code idPadre} representa la empresa</li>
    *   <li>{@code idHija} representa el cliente o proveedor</li>
    * </ul>
    *
    * <p>
    * Por cada entidad relacionada:
    * </p>
    * <ul>
    *   <li>Se crea la {@link Entidad} base</li>
    *   <li>Se carga su {@link Direccion} principal (si existe)</li>
    *   <li>Se transforma a {@link CliPro}</li>
    *   <li>Se cargan y asignan sus roles (CLIENTE / PROVEEDOR)</li>
    * </ul>
    *
    * @param idEmpresa identificador de la empresa padre
    * @return lista de {@link CliPro} asociados a la empresa
    */
    public List<CliPro> listarRelacionados(long idEmpresa) {

        List<CliPro> lista = new ArrayList<>();

        String sql =
            "SELECT e.* FROM EMPRESA_RELACION er " +
            "JOIN ENTIDAD e ON e.idEntidad = er.idHija " +
            "WHERE er.idPadre = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEmpresa);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                 //creamos entidad
                Entidad e = mapear(rs);
                //creamos la dir
                List<Direccion> dir= dao.obtenerDireccionesDeEntidad(rs.getLong("idEntidad"));
                //creamos el cp
                CliPro cp=new CliPro(e,dir.get(0));
                System.out.println("CliPro: "+cp);
                //setea,os roles a CliPro
                cp.setRoles(rolDAO.obtenerRolesPorEntidad(rs.getLong("idEntidad"),cp));
                System.out.println("CliPro: "+cp);
                lista.add(cp);

            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al listar relacionados: " + ex.getMessage());
        }

        return lista;
    }

}
