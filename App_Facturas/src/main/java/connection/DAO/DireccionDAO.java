/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import connection.ConexionBD;
import objects.Direccion;
import objects.Entidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roque
 */
public class DireccionDAO {
     
    // ==========================================
    // INSERTAR DIRECCIÓN
    // ==========================================
    public boolean insertar(Direccion d) {

        String sql = "INSERT INTO DIRECCION (idEntidad, via, numero, ciudad, provincia, cp, pais) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (d.getEntidad() == null) {
                System.out.println("❌ ERROR: La dirección NO tiene Entidad asignada");
                return false;
            }

            ps.setLong(1, d.getEntidad().getIdEntidad());
            ps.setString(2, d.getVia());
            ps.setString(3, d.getNumero());
            ps.setString(4, d.getCiudad());
            ps.setString(5, d.getProvincia());
            ps.setString(6, d.getCp());
            ps.setString(7, d.getPais());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error insertando dirección: " + e.getMessage());
            return false;
        }
    }

    // ==========================================
    // OBTENER DIRECCIONES POR ENTIDAD
    // ==========================================
    public List<Direccion> obtenerPorEntidad(long idEntidad) {

        List<Direccion> lista = new ArrayList<>();

        String sql = "SELECT * FROM DIRECCION WHERE idEntidad = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Direccion d = new Direccion();
                d.setIdDireccion(rs.getLong("idDireccion"));
                d.setVia(rs.getString("via"));
                d.setNumero(rs.getString("numero"));
                d.setCiudad(rs.getString("ciudad"));
                d.setProvincia(rs.getString("provincia"));
                d.setCp(rs.getString("cp"));
                d.setPais(rs.getString("pais"));
                
                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener direcciones: " + e.getMessage());
        }

        return lista;
    }

    // ==========================================
    // ELIMINAR TODAS LAS DIRECCIONES DE UNA ENTIDAD
    // ==========================================
    public boolean eliminarDireccionesPorEntidad(long idEntidad) {

        String sql = "DELETE FROM DIRECCION WHERE idEntidad = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando direcciones: " + e.getMessage());
            return false;
        }
    }
    
}
