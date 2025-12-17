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
 * DAO encargado de la gestión de los productos en la base de datos.
 * <p>
 * Permite crear, listar, buscar y eliminar productos, así como obtener
 * los productos asociados a un proveedor o a una empresa concreta.
 * </p>
 *
 * @author roque
 */
public class ProductoDAO {
    
    private EmpresaRelacionDAO empresaRelacionDAO = new EmpresaRelacionDAO();

    
    /**
     * Inserta un nuevo producto en la base de datos.
     * <p>
     * El producto debe tener asociado un proveedor válido.
     * </p>
     *
     * @param p Objeto {@link Producto} a crear.
     * @return El producto con su {@code idProducto} asignado o {@code null} si ocurre un error.
     */
    public Producto crear(Producto p) {

        String sql = "INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) "
                   + "VALUES (?, ?, ?, ?, (SELECT idEntidad FROM ENTIDAD WHERE nombre = ?))";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5,p.getProveedor().getNombre());
            

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) p.setIdProducto(rs.getLong(1));

            return p;

        } catch (SQLException e) {
            System.out.println("❌ Error creando producto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todos los productos existentes en la base de datos.
     *
     * @return Lista de {@link Producto}.
     */
    public List<Producto> obtenerTodos() {

        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO";

        try (Connection con = ConexionBD.get();
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

    /**
     * Obtiene todos los productos asociados a un proveedor concreto.
     *
     * @param idProveedor Identificador del proveedor.
     * @return Lista de {@link Producto}.
     */
    public List<Producto> obtenerPorProveedor(long idProveedor) {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM PRODUCTO WHERE idProveedor = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idProveedor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            System.out.println("❌ Error productos por proveedor: " + e.getMessage());
        }

        return lista;
    }
    
    /**
     * Obtiene todos los productos asociados a una empresa.
     * <p>
     * Primero se obtienen los proveedores relacionados con la empresa
     * y posteriormente los productos de cada proveedor.
     * </p>
     *
     * @param idEmpresa Identificador de la empresa.
     * @return Lista de {@link Producto}.
     */
    public List<Producto> listarProductosPorEmpresa(long idEmpresa) {

        List<Producto> lista = new ArrayList<>();

        try {
            // 1️⃣ Obtener proveedores asociados a la empresa
            List<Long> proveedores = empresaRelacionDAO.obtenerProveedoresDeEmpresa(idEmpresa);

            if (proveedores.isEmpty()) {
                System.out.println("⚠ La empresa " + idEmpresa + " no tiene proveedores asociados.");
                return lista;
            }

            // 2️⃣ Por cada proveedor obtener sus productos
            for (Long idProv : proveedores) {
                lista.addAll(obtenerPorProveedor(idProv));
            }

        } catch (Exception e) {
            System.out.println("❌ Error en listarProductosPorEmpresa(): " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina un producto de la base de datos por su nombre.
     *
     * @param nombre Nombre del producto a eliminar.
     * @return {@code true} si se elimina correctamente, {@code false} en caso contrario.
     */
    public boolean eliminar(String nombre) {

        String sql = "DELETE FROM PRODUCTO WHERE nombre = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mapea un {@link ResultSet} a un objeto {@link Producto}.
     *
     * @param rs ResultSet con los datos del producto.
     * @return Objeto {@link Producto}.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private Producto mapear(ResultSet rs) throws SQLException {
        
        Producto p = new Producto();

        p.setIdProducto(rs.getLong("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));
        p.setIdProveedor(rs.getLong("idProveedor"));

            return p;
    }

}
