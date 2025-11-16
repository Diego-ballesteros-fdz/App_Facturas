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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objects.Cliente;
import objects.Entidad;
import objects.Proveedor;

/**
 *
 * @author roque
 */
public class EntidadDAO {
    
    private RolDAO rolDAO = new RolDAO();

    // ==================================
    //   CREAR ENTIDAD
    // ==================================
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

    // ==================================
    //   MODIFICAR ENTIDAD
    // ==================================
    public boolean modificar(Entidad e) {

        String sql = "UPDATE ENTIDAD SET nombre = ?, nif = ?, email = ?, "
                + "telefono = ?, observaciones = ? WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getNif());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getTelefono());
            ps.setString(5, e.getObservaciones());
            ps.setLong(6, e.getIdEntidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al modificar entidad: " + ex.getMessage());
            return false;
        }
    }

    // ==================================
    //   ELIMINAR ENTIDAD
    // ==================================
    public boolean eliminar(long idEntidad) {

        String sql = "DELETE FROM ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar entidad: " + ex.getMessage());
            return false;
        }
    }

    // ==================================
    //   BUSCAR POR ID
    // ==================================
    public Entidad buscarPorId(long id) {

        String sql = "SELECT * FROM ENTIDAD WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Entidad e = mapear(rs);

                // 🌟 Cargar roles
                e.setRoles(rolDAO.obtenerRolesPorEntidad(e));

                // 🌟 Convertir a Cliente / Proveedor / Empresa
                return convertirSegunRol(e);
            }

        } catch (SQLException ex) {
            System.out.println("❌ Error al buscar entidad: " + ex.getMessage());
        }
        return null;
    }

    // ==================================
    //   LISTAR TODAS
    // ==================================
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

    // ==================================
    //   MAPPER
    // ==================================
    private Entidad mapear(ResultSet rs) throws SQLException {

        Entidad e = new Entidad();

        e.setIdEntidad(rs.getLong("idEntidad"));
        e.setNombre(rs.getString("nombre"));
        e.setNif(rs.getString("nif"));
        e.setEmail(rs.getString("email"));
        e.setTelefono(rs.getString("telefono"));
        e.setObservaciones(rs.getString("observaciones"));

        return e;
    }

    // ==================================
    //   CREAR CLASE HIJO SEGÚN EL ROL
    // ==================================
    private Entidad convertirSegunRol(Entidad e) {

        if (e.isCliente()) return new Cliente(e);
        if (e.isProveedor()) return new Proveedor(e);

        return e; // Empresa o entidad sin rol
    }
}
