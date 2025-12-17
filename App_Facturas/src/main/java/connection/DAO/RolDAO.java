/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import connection.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import objects.CliPro;
import objects.Entidad;
import objects.Rol;

/**
 * DAO encargado de la gestión de los roles asociados a una entidad.
 * <p>
 * Un rol define el comportamiento de una {@link Entidad} dentro del sistema
 * (CLIENTE, PROVEEDOR, etc.).  
 * Esta clase permite insertar, obtener y eliminar roles.
 * </p>
 *
 * @author roque
 */
public class RolDAO {
    
    /**
     * Inserta un rol para una entidad identificada por su nombre.
     * <p>
     * El id de la entidad se obtiene mediante una subconsulta.
     * </p>
     *
     * @param nombre Nombre de la entidad.
     * @param rol Rol a insertar (CLIENTE, PROVEEDOR, etc.).
     * @return {@code true} si el rol se inserta correctamente, {@code false} en caso contrario.
     */
    public boolean insertarRol(String nombre, String rol) {

        String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nombre= ?), ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, rol);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al insertar rol: " + ex.getMessage());
            return false;
        }
    }

     /**
     * Obtiene todos los roles asociados a una entidad.
     *
     * @param e Entidad de la que se quieren obtener los roles.
     * @return Lista de {@link Rol} asociados a la entidad.
     */
    public List<Rol> obtenerRolesPorEntidad(Entidad e) {

        List<Rol> roles = new ArrayList<>();

        String sql = "SELECT * FROM ROLES_ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, e.getIdEntidad());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Rol r = new Rol();
                r.setIdRol(rs.getLong("idRol"));
                r.setRol(rs.getString("rol"));
                r.setEntidad(e);

                roles.add(r);
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al obtener roles: " + ex.getMessage());
        }

        return roles;
    }
    
     /**
     * Obtiene los roles asociados a una entidad usando su identificador.
     * <p>
     * Variante utilizada principalmente para objetos {@link CliPro}.
     * </p>
     *
     * @param idEntidad Identificador de la entidad.
     * @param cp Objeto CliPro (no se usa directamente, pero mantiene coherencia del modelo).
     * @return Lista de {@link Rol}.
     */
    public List<Rol> obtenerRolesPorEntidad(long e,CliPro cp) {

        List<Rol> roles = new ArrayList<>();

        String sql = "SELECT * FROM ROLES_ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, e);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Rol r = new Rol();
                r.setIdRol(rs.getLong("idRol"));
                r.setRol(rs.getString("rol"));
                roles.add(r);
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al obtener roles: " + ex.getMessage());
        }

        return roles;
    }


    /**
     * Elimina todos los roles asociados a una entidad identificada por su nombre.
     *
     * @param nombre Nombre de la entidad.
     * @return {@code true} si se eliminan correctamente, {@code false} en caso contrario.
     */
    public boolean eliminarRolesPorEntidad(String nombre) {
        System.out.println(nombre);
        String sql = "DELETE FROM ROLES_ENTIDAD WHERE idEntidad IN (SELECT idEntidad FROM ENTIDAD WHERE nombre=?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar roles: " + ex.getMessage());
            return false;
        }
    }

     /**
     * Elimina un rol específico usando su identificador.
     *
     * @param idRol Identificador del rol.
     * @return {@code true} si se elimina correctamente, {@code false} en caso contrario.
     */
    public boolean eliminarRol(long idRol) {

        String sql = "DELETE FROM ROLES_ENTIDAD WHERE idRol = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idRol);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar rol: " + ex.getMessage());
            return false;
        }
    }
    
}
