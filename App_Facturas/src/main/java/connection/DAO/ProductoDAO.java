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
import objects.Producto;

/**
 *
 * @author roque
 */
public class ProductoDAO {
    
    public Producto crear(Producto p) {

        String sql = "INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setLong(5, p.getProveedor().getIdEntidad());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) p.setIdProducto(rs.getLong(1));

            return p;

        } catch (SQLException e) {
            System.out.println("❌ Error creando producto: " + e.getMessage());
            return null;
        }
    }

    public List<Producto> obtenerTodos() {

        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getLong("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo productos: " + e.getMessage());
        }

        return lista;
    }

    public List<Producto> obtenerPorProveedor(long idProveedor) {

        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO WHERE idProveedor = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idProveedor);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getLong("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo productos por proveedor: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminar(long idProducto) {

        String sql = "DELETE FROM PRODUCTO WHERE idProducto = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando producto: " + e.getMessage());
            return false;
        }
    }
    
}
