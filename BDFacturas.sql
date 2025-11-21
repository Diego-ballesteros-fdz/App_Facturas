-- ===========================================================
-- REINICIAR BASE DE DATOS
-- ===========================================================
DROP DATABASE IF EXISTS GESFAC;
CREATE DATABASE GESFAC;
USE GESFAC;

-- ===========================================================
-- TABLAS PRINCIPALES
-- ===========================================================
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
    idEntidad BIGINT NOT NULL,
    total DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad)
);

CREATE TABLE LINEA_FACTURA (
    idLinea BIGINT AUTO_INCREMENT PRIMARY KEY,
    idFactura BIGINT NOT NULL,
    idProducto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,

    subtotal DECIMAL(10,2) GENERATED ALWAYS AS (cantidad * precioUnitario) STORED,

    FOREIGN KEY (idFactura) REFERENCES FACTURA(idFactura)
        ON DELETE CASCADE,

    FOREIGN KEY (idProducto) REFERENCES PRODUCTO(idProducto)
);

-- ===========================================================
-- DATOS ORIGINALES
-- ===========================================================
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
(1, 2, 1, 30.00),
(2, 1, 5, 15.00),
(2, 3, 3, 8.00);

-- ===========================================================
-- DESASOCIAR LAS 3 EMPRESAS INICIALES (BORRAR ROLES)
-- ===========================================================
DELETE FROM ROLES_ENTIDAD WHERE idEntidad IN (1,2,3);

-- ===========================================================
-- CREAR 5 EMPRESAS NUEVAS
-- ===========================================================
INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES
('TecnoHogar S.L.', 'T98765432', 'contacto@tecnohogar.com', '911222333', 'Nueva empresa cliente'),
('Logística Express S.A.', 'L54321987', 'info@logiexpress.com', '913456789', 'Proveedor logístico'),
('Frutas del Sur S.L.', 'F87651234', 'ventas@frutassur.com', '955667788', 'Cliente y proveedor agrícola'),
('ElectroWorld Iberia S.L.', 'E11223344', 'contacto@electroworld.es', '917334455', 'Distribuidor de electrónica'),
('Consultora Nova S.A.', 'N55667788', 'info@novaconsult.com', '910998877', 'Consultoría empresarial');

-- ===========================================================
-- ASIGNAR ROLES A LAS NUEVAS
-- ===========================================================
INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES
((SELECT idEntidad FROM ENTIDAD WHERE nif='T98765432'), 'CLIENTE'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'CLIENTE'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'), 'CLIENTE');

-- ===========================================================
-- TABLA DE RELACIONES ENTRE EMPRESAS
-- ===========================================================
CREATE TABLE EMPRESA_RELACION (
    idPadre BIGINT NOT NULL,
    idHija BIGINT NOT NULL,
    tipoRelacion VARCHAR(50) NOT NULL,
    PRIMARY KEY(idPadre, idHija),
    FOREIGN KEY(idPadre) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE,
    FOREIGN KEY(idHija) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE
);

-- ===========================================================
-- ASOCIAR LAS 5 EMPRESAS NUEVAS A LAS 3 ORIGINALES
-- ===========================================================

-- Empresa 1 se relaciona con dos empresas nuevas
INSERT INTO EMPRESA_RELACION VALUES
(1, (SELECT idEntidad FROM ENTIDAD WHERE nif='T98765432'), 'ASOCIADA'),
(1, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987'), 'ASOCIADA');

-- Empresa 2 se relaciona con dos empresas nuevas
INSERT INTO EMPRESA_RELACION VALUES
(2, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'COLABORA'),
(2, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344'), 'COLABORA');

-- Empresa 3 se relaciona con una empresa nueva
INSERT INTO EMPRESA_RELACION VALUES
(3, (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'), 'CLIENTE DE');

