/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import connection.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objects.Factura;

/**
 * DAO encargado de la gestión de Facturas en la base de datos.
 * <p>
 * Permite crear, eliminar y consultar facturas asociadas a entidades
 * (clientes o proveedores).
 * </p>
 *
 * @author roque
 */
public class FacturaDAO {
    
    /**
     * Crea una nueva factura en la base de datos.
     * <p>
     * La factura debe tener asignado un cliente válido con un idEntidad existente.
     * </p>
     *
     * @param f Objeto {@link Factura} a insertar.
     * @return La factura con el id generado, o {@code null} si ocurre un error.
     */
     public Factura crear(Factura f) {

        if (f.getCliente() == null){
            System.out.println("No existe cliente para este factura");
             return null;
        }
         
        String sql = "INSERT INTO FACTURA (fecha, idEntidad, total) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(f.getFechaEmision()));
            ps.setLong(2, f.getCliente().getIdEntidad());
            ps.setDouble(3, f.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) f.setIdFactura(rs.getLong(1));

            return f;

        } catch (SQLException e) {
            System.out.println("❌ Error creando factura: " + e.getMessage());
            return null;
        }
    }
     

     /**
     * Elimina una factura de la base de datos por su identificador.
     *
     * @param idFactura Identificador de la factura.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario.
     */
    public boolean eliminar(long idFactura) {

        String sql = "DELETE FROM FACTURA WHERE idFactura = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idFactura);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todas las facturas almacenadas en la base de datos.
     *
     * @return Lista de {@link Factura}.
     */
    public List<Factura> obtenerTodas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM FACTURA";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getLong("idFactura"));
                f.setFechaEmision(rs.getDate("fecha").toLocalDate());
                f.setTotal(rs.getDouble("total"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo facturas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene todas las facturas asociadas a una entidad concreta
     * (cliente o proveedor).
     *
     * @param idEntidad Identificador de la entidad.
     * @return Lista de {@link Factura} asociadas a la entidad.
     */
    public List<Factura> obtenerPorEntidad(long idEntidad) {

        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM FACTURA WHERE idEntidad = ?";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idEntidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getLong("idFactura"));
                f.setFechaEmision(rs.getDate("fecha").toLocalDate());
                f.setTotal(rs.getDouble("total"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo facturas por entidad: " + e.getMessage());
        }

        return lista;
    }
    
}
