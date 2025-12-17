/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection.DAO;

import connection.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    /**
     * DAO encargado de gestionar las relaciones entre empresas y sus entidades
     * asociadas (clientes y/o proveedores).
     *
     * <p>
     * La tabla {@code EMPRESA_RELACION} modela una relación jerárquica donde:
     * </p>
     * <ul>
     *   <li>{@code idPadre} → Empresa principal</li>
     *   <li>{@code idHija} → Cliente o Proveedor</li>
     *   <li>{@code tipoRelacion} → CLIENTE o PROVEEDOR</li>
     * </ul>
     *
     * <p>
     * Si una entidad es a la vez CLIENTE y PROVEEDOR de una empresa,
     * existirán <b>DOS FILAS</b> en la tabla:
     * </p>
     *
     * <pre>
     * idPadre | idHija | tipoRelacion
     * --------+--------+-------------
     *   1     |   5    | CLIENTE
     *   1     |   5    | PROVEEDOR
     * </pre>
     *
     * <p>
     * Esto permite consultas claras, simples y sin valores ambiguos.
     * </p>
     *
     * @author roque
     */
public class EmpresaRelacionDAO {

    /**
     * Obtiene los identificadores de las entidades (clientes y/o proveedores)
     * asociadas a una empresa.
     *
     * <p>
     * No filtra por tipo de relación. Devuelve todas las entidades relacionadas
     * con la empresa indicada.
     * </p>
     *
     * @param idPadre identificador de la empresa
     * @return lista de idEntidad relacionadas
     */
    public List<Long> obtenerProveedoresDeEmpresa(long idPadre) {

        List<Long> lista = new ArrayList<>();

        String sql = "SELECT idHija FROM EMPRESA_RELACION WHERE idPadre = ?";

        try (Connection con = ConexionBD.get();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setLong(1, idPadre);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    lista.add(rs.getLong("idHija"));
                }

            } catch (Exception e) {
                System.out.println("❌ Error obtenerProveedoresDeEmpresa(): " + e.getMessage());
            }

            return lista;
        }

    /**
     * Inserta una relación entre una empresa y una entidad.
     *
     * <p>
     * El parámetro {@code tipoRelacion} debe ser uno de:
     * </p>
     * <ul>
     *   <li>{@code CLIENTE}</li>
     *   <li>{@code PROVEEDOR}</li>
     * </ul>
     *
     * <p>
     * Si una entidad es cliente y proveedor, este método debe llamarse
     * dos veces con distinto tipo.
     * </p>
     *
     * @param idPadre identificador de la empresa
     * @param idHija identificador del cliente/proveedor
     * @param tipoRelacion CLIENTE o PROVEEDOR
     * @return {@code true} si la inserción fue correcta
     */
    public boolean insertarRelacion(long idPadre, long idHija, String tipo) {
        String sql = "INSERT INTO EMPRESA_RELACION (idPadre, idHija, tipoRelacion) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, idPadre);
            ps.setLong(2, idHija);
            ps.setString(3, tipo);

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("❌ Error insertando relación empresa-cliente: " + ex.getMessage());
            return false;
        }
    }

}
