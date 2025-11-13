DROP DATABASE IF EXISTS GESFAC;
CREATE DATABASE GESFAC;

USE GESFAC;

CREATE TABLE Empresa (
    idEm BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombreEm VARCHAR(200) NOT NULL,
    nifEm VARCHAR(20) NOT NULL UNIQUE,
    direccionEm VARCHAR(250),
    cpEm VARCHAR(10),
    ciudadEm VARCHAR(100),
    provinciaEm VARCHAR(100),
    paisEm VARCHAR(100) DEFAULT 'ES',
    telefonoEm VARCHAR(30),
    emailEm VARCHAR(150),
    webEm VARCHAR(200),
    domicilio_fiscalEm VARCHAR(250),
    contactoEm VARCHAR(100)
);
CREATE TABLE Cli_Prov (
    idCP BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipoCP CHAR(1) NOT NULL,          
    codigoCP INT NOT NULL,            
    nombreCP VARCHAR(200) NOT NULL,
    nifCP VARCHAR(20) NOT NULL,
    emailCP VARCHAR(150),
    telefonoCP VARCHAR(30),
    idEm BIGINT
);
CREATE TABLE Direccion (
    idDi BIGINT PRIMARY KEY AUTO_INCREMENT,
    idCP BIGINT NOT NULL,          
    etiquetaDi VARCHAR(30) NOT NULL,        
    direccionDi VARCHAR(250) NOT NULL,
    cpDi VARCHAR(10),
    ciudadDi VARCHAR(100),
    provinciaDi VARCHAR(100),
    paisDi VARCHAR(100) DEFAULT 'ES'
);
CREATE TABLE Producto (
    idPRO BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigoPRO VARCHAR(13) NOT NULL UNIQUE,
    descripcionPRO VARCHAR(250) NOT NULL,
    referencia_proveedorPRO VARCHAR(50),
    idCP BIGINT,
    tipo_iva_idPRO SMALLINT NOT NULL,
    precio_costePRO DECIMAL(12,4) NOT NULL DEFAULT 0,
    precio_ventaPRO DECIMAL(12,4) NOT NULL DEFAULT 0,
    stockPRO DECIMAL NOT NULL
);
CREATE TABLE Factura (
    idF BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipoF CHAR(1) NOT NULL,                   
    numeroF VARCHAR(20) NOT NULL,
    fecha_emisionF DATE NOT NULL,
    idCP BIGINT NOT NULL,              
    conceptoF VARCHAR(200),
    base_imponibleF DECIMAL(14,2) NOT NULL,
    iva_totalF DECIMAL(14,2) NOT NULL,
    total_facturaF DECIMAL(14,2) NOT NULL,
    estadoF VARCHAR(15) DEFAULT 'PENDIENTE',
    idEm BIGINT,
    observacionesF VARCHAR(255)
);
CREATE TABLE Factura_Linea (
    idFL BIGINT PRIMARY KEY AUTO_INCREMENT,
    idF BIGINT NOT NULL,
    idPRO BIGINT NOT NULL,
    cantidad DECIMAL(12,4) NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(12,4) NOT NULL,
    importe DECIMAL(14,2) NOT NULL,
    CONSTRAINT fk_facturalinea_factura FOREIGN KEY (idF) REFERENCES Factura(idF),
    CONSTRAINT fk_facturalinea_producto FOREIGN KEY (idPRO) REFERENCES Producto(idPRO)
);


ALTER TABLE Direccion
ADD CONSTRAINT fk_direccion_cliente_proveedor
FOREIGN KEY (idCP) REFERENCES Cli_Prov(idCP);


ALTER TABLE Producto
ADD CONSTRAINT fk_producto_proveedor
FOREIGN KEY (idCP) REFERENCES Cli_Prov(idCP);


ALTER TABLE Factura
ADD CONSTRAINT fk_factura_cliente_proveedor
FOREIGN KEY (idCP) REFERENCES Cli_Prov(idCP);

ALTER TABLE Cli_Prov
ADD CONSTRAINT fk_cliprov_empresa
FOREIGN KEY (idEm) REFERENCES Empresa(idEm);

ALTER TABLE Factura
ADD CONSTRAINT fk_factura_empresa
FOREIGN KEY (idEm) REFERENCES Empresa(idEm);

-- insertamos algunos registros de prueba

-- 1. Insertar datos en Empresa (sin ID, se genera automáticamente)
INSERT INTO Empresa (nombreEm, nifEm, direccionEm, ciudadEm, provinciaEm, telefonoEm, emailEm, contactoEm) VALUES
('Empresa Uno', 'A12345678', 'Calle Principal 1', 'Madrid', 'Madrid', '912345678', 'contacto@empresauno.com', 'Juan Perez'),
('Empresa Dos', 'B87654321', 'Avenida Secundaria 45', 'Barcelona', 'Barcelona', '934567890', 'info@empresados.com', 'Ana Garcia');

-- 2. Insertar en Cli_Prov (referenciando a Empresa con idEm 1 y 2)
INSERT INTO Cli_Prov (tipoCP, codigoCP, nombreCP, nifCP, emailCP, telefonoCP, idEm) VALUES
('C', 1001, 'Cliente Alpha', 'C11111111', 'alpha@clientes.com', '600111222', 1),
('P', 2001, 'Proveedor Beta', 'P22222222', 'beta@proveedores.com', '611222333', 2);

-- 3. Insertar en Direccion (usando idCP 1 y 2)
INSERT INTO Direccion (idCP, etiquetaDi, direccionDi, cpDi, ciudadDi, provinciaDi, paisDi) VALUES
(1, 'fiscal', 'Calle Fiscal 10', '28001', 'Madrid', 'Madrid', 'ES'),
(2, 'envio', 'Polígono Industrial 5', '08010', 'Barcelona', 'Barcelona', 'ES');

-- 4. Insertar en Producto (usando idCP 2 que es proveedor)
INSERT INTO Producto (codigoPRO, descripcionPRO, referencia_proveedorPRO, idCP, tipo_iva_idPRO, precio_costePRO, precio_ventaPRO, stockPRO) VALUES
('1234567890123', 'Producto A', 'REF123', 2, 21, 10.00, 15.00, 100),
('9876543210987', 'Producto B', 'REF456', 2, 21, 20.00, 30.00, 50);

-- 5. Insertar en Factura (usando idCP 1 y idEm 1)
INSERT INTO Factura (tipoF, numeroF, fecha_emisionF, idCP, conceptoF, base_imponibleF, iva_totalF, total_facturaF, estadoF, idEm, observacionesF) VALUES
('V', 'FAC001', '2025-11-13', 1, 'Venta productos A y B', 100.00, 21.00, 121.00, 'PENDIENTE', 1, 'Sin observaciones'),
('C', 'FAC002', '2025-11-14', 2, 'Compra productos A', 200.00, 42.00, 242.00, 'PENDIENTE', 2, 'Urgente');

-- 6. Insertar en Factura_Linea (usando idF 1 y 2, y idPRO 1 y 2)
INSERT INTO Factura_Linea (idF, idPRO, cantidad, precio_unitario, importe) VALUES
(1, 1, 2, 15.00, 30.00),
(1, 2, 1, 30.00, 30.00),
(2, 1, 5, 10.00, 50.00),
(2, 2, 3, 20.00, 60.00);

