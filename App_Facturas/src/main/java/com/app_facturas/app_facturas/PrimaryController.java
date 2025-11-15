package com.app_facturas.app_facturas;

import connection.ConexionBD;
import java.io.IOException;
import java.sql.Connection;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        try (Connection con = ConexionBD.get()) {
            System.out.println("Conectados!!!");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
