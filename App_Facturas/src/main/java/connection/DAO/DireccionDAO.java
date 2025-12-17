/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
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
 * DAO encargado de la gestión de direcciones asociadas a una entidad.
 *
 * <p>
 * Cada dirección pertenece a una {@link Entidad} mediante la clave foránea
 * {@code idEntidad}.
 * </p>
 *
 * <p>
 * Este DAO permite:
 * </p>
 * <ul>
 *   <li>Insertar direcciones</li>
 *   <li>Listar direcciones por entidad</li>
 *   <li>Eliminar direcciones de una entidad</li>
 * </ul>
 *
 * @author roque
 */
public class DireccionDAO {
     
    /**
     * Inserta una nueva dirección en la base de datos.
     *
     * <p>
     * La {@link Direccion} debe tener una {@link Entidad} asociada
     * con un {@code idEntidad} válido.
     * </p>
     *
     * @param d Dirección a insertar
     * @return {@code true} si se insertó correctamente
     */
    public boolean insertar(Direccion d) {

        String sql = "INSERT INTO DIRECCION (idEntidad, via, numero, ciudad, provincia, cp, pais) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (d.getEntidad() == null) {
                System.out.println("❌ ERROR: La dirección NO tiene Entidad asignada");
                return false;
            }

            System.out.println("IdEntidad para añadir dir: "+obtenerId(d.getEntidad()));
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
    
    /**
    * Obtiene el idEntidad de una entidad a partir de su nombre.
    *
    * <p><b>ATENCIÓN:</b> Este método no es recomendable si el nombre
    * no es único. Es preferible trabajar siempre con {@code idEntidad}
    * directamente.</p>
    *
    * @param e Entidad cuyo nombre se usará para buscar el ID
    * @return idEntidad si existe, -1 si no se encuentra
    */
    public long obtenerId(Entidad e){
        String sql="SELECT idEntidad" +
                    "FROM ENTIDAD" +
                    "WHERE nombre = ?;";
        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, e.getNombre());
            
            ResultSet r= ps.executeQuery(sql);
            return r.getLong("idEntidad");
        }catch (SQLException ex) {
            System.out.println("❌ Error insertando dirección: " + ex.getMessage());
            return -1;
        }
    }

    /**
     * Obtiene todas las direcciones asociadas a una entidad.
     *
     * @param idEntidad identificador de la entidad
     * @return lista de direcciones
     */
    public List<Direccion> obtenerPorEntidad(long idEntidad) {

        List<Direccion> lista = new ArrayList<>();

        String sql = "SELECT * FROM DIRECCION WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
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

    /**
     * Elimina todas las direcciones asociadas a una entidad.
     *
     * @param idEntidad identificador de la entidad
     * @return {@code true} si se eliminaron correctamente
     */
    public boolean eliminarDireccionesPorEntidad(String nombre) {

        String sql = "DELETE FROM DIRECCION WHERE idEntidad = (SELECT idEntidad FROM ENTIDAD WHERE nombre = ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando direcciones: " + e.getMessage());
            return false;
        }
    }
    
}
