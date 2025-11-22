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
 *
 * @author roque
 */
public class EmpresaRelacionDAO {

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
}
