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
import objects.Entidad;
import objects.Rol;

/**
 *
 * @author roque
 */
public class RolDAO {
    
    // =============================
    //   INSERTAR ROL
    // =============================
    public boolean insertarRol(long idEntidad, String rol) {

        String sql = "INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES (?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            ps.setString(2, rol);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al insertar rol: " + ex.getMessage());
            return false;
        }
    }

    // =============================
    //   OBTENER ROLES DE UNA ENTIDAD
    // =============================
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

    // =============================
    //   ELIMINAR TODOS LOS ROLES DE UNA ENTIDAD
    // =============================
    public boolean eliminarRolesPorEntidad(long idEntidad) {

        String sql = "DELETE FROM ROLES_ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar roles: " + ex.getMessage());
            return false;
        }
    }

    // =============================
    //   ELIMINAR ROL ESPECÍFICO
    // =============================
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
