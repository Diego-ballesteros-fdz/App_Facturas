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
    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE
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
    FOREIGN KEY (idEntidad) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE
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
    FOREIGN KEY (idFactura) REFERENCES FACTURA(idFactura) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTO(idProducto)
);

-- ===========================================================
-- EMPRESAS INICIALES
-- ===========================================================
INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES
('Empresa Uno S.L.',         'A12345678', 'contacto@empresauno.com',  '912345678', 'Cliente y proveedor potencial'),
('Distribuciones Beta S.A.', 'B87654321', 'info@betadistrib.com',     '934567890', 'Proveedor de productos'),
('Cliente Gamma S.L.',       'C11111111', 'gamma@clientes.com',       '600111222', 'Cliente habitual');

INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES 
(1, 'CLIENTE'),
(2, 'PROVEEDOR'),
(3, 'CLIENTE');

-- Direcciones
INSERT INTO DIRECCION (idEntidad, via, numero, ciudad, provincia, cp, pais) VALUES
(1, 'Calle Principal', '10', 'Madrid', 'Madrid', '28001', 'ES'),
(2, 'Polígono Industrial Norte', '5', 'Barcelona', 'Barcelona', '08010', 'ES'),
(3, 'Av. Libertad', '22', 'Valencia', 'Valencia', '46001', 'ES');

-- Productos iniciales del proveedor idEntidad=2
INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Producto A', 'Producto básico A', 15.00, 100, 2),
('Producto B', 'Producto premium B', 30.00, 50, 2),
('Producto C', 'Accesorio C',  8.00, 200, 2);

-- Facturas
INSERT INTO FACTURA (fecha, idEntidad, total) VALUES
('2025-11-13', 1, 0),
('2025-11-14', 3, 0);

INSERT INTO LINEA_FACTURA (idFactura, idProducto, cantidad, precioUnitario) VALUES
(1, 1, 2, 15.00),
(1, 2, 1, 30.00),
(2, 1, 5, 15.00),
(2, 3, 3, 8.00);

-- ===========================================================
-- REINICIAR ROLES DE EMPRESAS INICIALES
-- ===========================================================
DELETE FROM ROLES_ENTIDAD WHERE idEntidad IN (1,2,3);

-- ===========================================================
-- CREAR 5 EMPRESAS NUEVAS
-- ===========================================================
INSERT INTO ENTIDAD (nombre, nif, email, telefono, observaciones) VALUES
('TecnoHogar S.L.',          'T98765432', 'contacto@tecnohogar.com',   '911222333', 'Nueva empresa cliente'),
('Logística Express S.A.',   'L54321987', 'info@logiexpress.com',      '913456789', 'Proveedor logístico'),
('Frutas del Sur S.L.',      'F87651234', 'ventas@frutassur.com',      '955667788', 'Cliente y proveedor agrícola'),
('ElectroWorld Iberia S.L.', 'E11223344', 'contacto@electroworld.es',  '917334455', 'Distribuidor de electrónica'),
('Consultora Nova S.A.',     'N55667788', 'info@novaconsult.com',      '910998877', 'Consultoría empresarial');

-- Roles
INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES
((SELECT idEntidad FROM ENTIDAD WHERE nif='T98765432'), 'CLIENTE'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'CLIENTE'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344'), 'PROVEEDOR'),
((SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'), 'CLIENTE');

-- ===========================================================
-- RELACIONES ENTRE EMPRESAS
-- ===========================================================
CREATE TABLE EMPRESA_RELACION (
    idPadre BIGINT NOT NULL,
    idHija BIGINT NOT NULL,
    tipoRelacion VARCHAR(50) NOT NULL,
    PRIMARY KEY(idPadre, idHija),
    FOREIGN KEY(idPadre) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE,
    FOREIGN KEY(idHija) REFERENCES ENTIDAD(idEntidad) ON DELETE CASCADE
);

INSERT INTO EMPRESA_RELACION VALUES
(1, (SELECT idEntidad FROM ENTIDAD WHERE nif='T98765432'), 'ASOCIADA'),
(1, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987'), 'ASOCIADA'),

(2, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'), 'COLABORA'),
(2, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344'), 'COLABORA'),

(3, (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'), 'CLIENTE DE');

-- ===========================================================
-- PRODUCTOS EMPRESA 1 (Logística Express)
-- ===========================================================
INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Caja de Envío XL', 'Caja reforzada para grandes volúmenes', 4.50, 1200, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Papel Burbuja Premium', 'Rollo de burbuja 50m para embalaje', 12.90, 500, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Palet Europeo Tipo A', 'Palet de madera 120x80 cm homologado', 22.00, 300, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Cinta Adhesiva UltraGrip', 'Cinta adhesiva industrial 48mm x 66m', 1.60, 2000, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Carrito de Transporte 300kg', 'Carrito metálico de carga alta resistencia', 89.90, 80, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Transpaleta Manual 2500kg', 'Transpaleta hidráulica para almacén', 329.00, 25, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Film Transparente Industrial', 'Rollo de film transparente 23 micras', 9.90, 900, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987')),
('Etiquetas de Envío 1000u', 'Pack de etiquetas adhesivas térmicas', 14.50, 700, (SELECT idEntidad FROM ENTIDAD WHERE nif='L54321987'));

-- ===========================================================
-- PRODUCTOS EMPRESA 2 (Frutas del Sur + ElectroWorld)
-- ===========================================================
INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Caja de Naranjas Premium', 'Naranja valenciana calibre 1', 18.50, 400, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234')),
('Pack Manzanas Golden', 'Manzana dulce calidad extra', 12.90, 250, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234')),
('Caja de Plátanos Canarias', 'Plátano maduro categoría A', 15.40, 300, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234')),
('Melones Super Dulces', 'Melón piel de sapo primera selección', 9.99, 150, (SELECT idEntidad FROM ENTIDAD WHERE nif='F87651234'));

INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Cargador USB-C UltraFast', 'Cargador 65W carga rápida universal', 24.90, 600, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344')),
('Auriculares ProBass', 'Sonido HD con graves avanzados', 39.90, 350, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344')),
('Altavoz Bluetooth PowerMini', 'Resistente al agua, 12h batería', 29.90, 500, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344')),
('Teclado Mecánico IronKeys', 'Switches blue, iluminación RGB', 59.90, 180, (SELECT idEntidad FROM ENTIDAD WHERE nif='E11223344'));

-- ===========================================================
-- PRODUCTOS EMPRESA 3 (Consultora Nova)
-- ===========================================================
INSERT INTO ROLES_ENTIDAD (idEntidad, rol) VALUES ((SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'), 'PROVEEDOR');

INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, idProveedor) VALUES
('Paquete de Asesoría Empresarial', 'Consultoría estratégica completa', 499.00, 50,  (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788')),
('Estudio Financiero Avanzado', 'Análisis completo de balances', 350.00, 80, (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788')),
('Plan de Optimización Fiscal', 'Reducción de carga tributaria', 289.00, 40, (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788')),
('Consultoría Express 1h', 'Sesión rápida con especialista', 49.00, 200, (SELECT idEntidad FROM ENTIDAD WHERE nif='N55667788'));
