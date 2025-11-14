DROP DATABASE IF EXISTS GESFAC;
CREATE DATABASE GESFAC;

USE GESFAC;

CREATE TABLE ENTIDAD (
    idEntidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(150),
    telefono VARCHAR(30),
    observaciones TEXT
);

CREATE TABLE ROLES_ENTIDAD (
    idRol BIGINT AUTO_INCREMENT PRIMARY KEY,
    idEntidad BIGINT NOT NULL,
    rol ENUM('CLIENTE','PROVEEDOR') NOT NULL,

    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad)
        ON DELETE CASCADE
);

CREATE TABLE DIRECCION (
    idDireccion BIGINT AUTO_INCREMENT PRIMARY KEY,
    idEntidad BIGINT NOT NULL,
    via VARCHAR(200),
    numero VARCHAR(50),
    ciudad VARCHAR(100),
    provincia VARCHAR(100),
    cp VARCHAR(10),
    pais VARCHAR(100) DEFAULT 'ES',

    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad)
        ON DELETE CASCADE
);

CREATE TABLE PRODUCTO (
    idProducto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    idProveedor BIGINT NOT NULL,

    FOREIGN KEY (idProveedor) REFERENCES ENTIDAD(idEntidad)
);

CREATE TABLE FACTURA (
    idFactura BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    idEntidad BIGINT NOT NULL,   -- cliente
    total DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad)
);

CREATE TABLE LINEA_FACTURA (
    idLinea BIGINT AUTO_INCREMENT PRIMARY KEY,
    idFactura BIGINT NOT NULL,
    idProducto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,

    -- MySQL NO admite columnas computadas persistidas
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS (cantidad * precioUnitario) STORED,

    FOREIGN KEY (idFactura) REFERENCES FACTURA(idFactura)
        ON DELETE CASCADE,

    FOREIGN KEY (idProducto) REFERENCES PRODUCTO(idProducto)
);

INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES
('Empresa Uno S.L.', 'A12345678', 'contacto@empresauno.com', '912345678', 'Cliente y proveedor potencial'),
('Distribuciones Beta S.A.', 'B87654321', 'info@betadistrib.com', '934567890', 'Proveedor de productos'),
('Cliente Gamma S.L.', 'C11111111', 'gamma@clientes.com', '600111222', 'Cliente habitual');

INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES
(1, 'CLIENTE'),
(2, 'PROVEEDOR'),
(3, 'CLIENTE');

INSERT INTO DIRECCION (idEntidad, via, numero, ciudad, provincia, cp, pais) VALUES
(1, 'Calle Principal', '10', 'Madrid', 'Madrid', '28001', 'ES'),
(2, 'Polígono Industrial Norte', '5', 'Barcelona', 'Barcelona', '08010', 'ES'),
(3, 'Av. Libertad', '22', 'Valencia', 'Valencia', '46001', 'ES');

INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Producto A', 'Producto básico A', 15.00, 100, 2),
('Producto B', 'Producto premium B', 30.00, 50, 2),
('Producto C', 'Accesorio C', 8.00, 200, 2);

INSERT INTO FACTURA (fecha, idEntidad, total) VALUES
('2025-11-13', 1, 0),
('2025-11-14', 3, 0);

INSERT INTO LINEA_FACTURA (idFactura, idProducto, cantidad, precioUnitario) VALUES
(1, 1, 2, 15.00),
(1, 2, 1, 30.00);

INSERT INTO LINEA_FACTURA (idFactura, idProducto, cantidad, precioUnitario) VALUES
(2, 1, 5, 15.00),
(2, 3, 3, 8.00);

