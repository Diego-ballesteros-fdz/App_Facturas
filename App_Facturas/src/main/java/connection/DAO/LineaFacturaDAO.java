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
import objects.LineaFactura;

/**
 * DAO encargado de la gestión de las líneas de factura en la base de datos.
 * <p>
 * Cada línea representa un producto asociado a una factura con su cantidad
 * y precio unitario.
 * </p>
 *
 * @author roque
 */
public class LineaFacturaDAO {
    
     /**
     * Inserta una nueva línea de factura en la base de datos.
     * <p>
     * La línea debe tener asociada una factura y un producto válidos.
     * </p>
     *
     * @param lf Objeto {@link LineaFactura} a insertar.
     * @return {@code true} si se inserta correctamente, {@code false} en caso contrario.
     */
     public boolean insertar(LineaFactura lf) {

        String sql = "INSERT INTO LINEA_FACTURA (idFactura, idProducto, cantidad, precioUnitario) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, lf.getFactura().getIdFactura());
            ps.setLong(2, lf.getProducto().getIdProducto());
            ps.setInt(3, lf.getCantidad());
            ps.setDouble(4, lf.getPrecioUnitario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error insertando línea: " + e.getMessage());
            return false;
        }
    }

     /**
     * Obtiene todas las líneas asociadas a una factura concreta.
     *
     * @param idFactura Identificador de la factura.
     * @return Lista de {@link LineaFactura}.
     */ 
    public List<LineaFactura> obtenerPorFactura(long idFactura) {

        List<LineaFactura> lista = new ArrayList<>();

        String sql = "SELECT * FROM LINEA_FACTURA WHERE idFactura = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idFactura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LineaFactura lf = new LineaFactura();
                lf.setIdLinea(rs.getLong("idLinea"));
                lf.setCantidad(rs.getInt("cantidad"));
                lf.setPrecioUnitario(rs.getDouble("precioUnitario"));
                lf.setSubtotal(rs.getDouble("subtotal"));
                lista.add(lf);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo líneas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina todas las líneas asociadas a una factura.
     *
     * @param idFactura Identificador de la factura.
     * @return {@code true} si se eliminan correctamente, {@code false} en caso contrario.
     */
    public boolean eliminarPorFactura(long idFactura) {

        String sql = "DELETE FROM LINEA_FACTURA WHERE idFactura = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idFactura);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando líneas: " + e.getMessage());
            return false;
        }
    }
    
}
