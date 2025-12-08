module com.app_facturas.app_facturas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.base;
    requires java.desktop;
    requires jasperreports;

    opens com.app_facturas.app_facturas to javafx.fxml;
    exports com.app_facturas.app_facturas;
}
