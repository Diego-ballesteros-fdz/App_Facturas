CLAVES DE LA BASE DE DATOS:
ROOT:
USER ROOT
PSW ROOT.

para que el script funcione, y podamos practicar todos lo mismo.
La base de datos no se sincronizara entre dispositivos pero si almacenara los datos.
En la raíz del proyecto teneis el script BDFacturas.sql
debéis configurar la base de datos en el puerto 3306 el de serie, y tener los ususarios de arriba con el comando
añadir la carpeta programs_files/MySQL/mysqlserver/bin en la variable de entorno
abrir el cmd e ir al repositorio
MySQL -u root -p 
source BDFacturas.sql;
y la tendríamos lista.

Para ejecutar la base de datos y poder conectarnos debemos usar
net start MySQL80 
en una cmd como administrador

Está es la estructura de la BD:
                         +---------------------------+
                         |         ENTIDAD           |
                         |---------------------------|
                         | *idEntidad (PK)           |
                         |  nombre (NVARCHAR 200)    |
                         |  nif (NVARCHAR 20)        |
                         |  email (NVARCHAR 150)     |
                         |  telefono (NVARCHAR 30)   |
                         |  observaciones (MAX)      |
                         +--------------+------------+
                                        |
                      .-----------------+-------------------.
                      | (1:N)                              | (1:N)
                      |                                     |
             +--------+----------+                 +--------+----------+
             |    ROLES_ENTIDAD  |                 |      DIRECCION     |
             |--------------------|                 |---------------------|
             | *idRol (PK)        |                 | *idDireccion (PK)   |
             |  idEntidad (FK)    |<--------------->|  idEntidad (FK)     |
             |  rol (CLIENTE/     |                 |  via (NVARCHAR 200) |
             |       PROVEEDOR)   |                 |  numero (NVARCHAR50)|
             +--------------------+                 |  ciudad (NVARCHAR100)|
                                                    |  provincia (NVARCHAR100)|
                                                    |  cp (NVARCHAR10)     |
                                                    |  pais (NVARCHAR100)  |
                                                    +----------+-----------+
                                                               |
                                                               |
                                                               | (1:N)
                                                               |
                                                 +-------------+-------------+
                                                 |           FACTURA         |
                                                 |---------------------------|
                                                 | *idFactura (PK)           |
                                                 |  fecha (DATE)             |
                                                 |  idEntidad (FK - CLIENTE) |
                                                 |  total (DECIMAL)          |
                                                 +--------------+------------+
                                                                |
                                                                | (1:N)
                                                                |
                                            +-------------------+----------------------+
                                            |               LINEA_FACTURA              |
                                            |------------------------------------------|
                                            | *idLinea (PK)                            |
                                            |  idFactura (FK)                          |
                                            |  idProducto (FK)                         |
                                            |  cantidad (INT)                          |
                                            |  precioUnitario (DECIMAL)                |
                                            |  subtotal = cantidad * precioUnitario    |
                                            +-------------------+----------------------+
                                                                ^
                                                                |
                                                                | (N:1)
                                       +------------------------+-------------------------+
                                       |                        PRODUCTO                  |
                                       |--------------------------------------------------|
                                       | *idProducto (PK)                                  |
                                       |  nombre (NVARCHAR 200)                            |
                                       |  descripcion (MAX)                                |
                                       |  precio (DECIMAL)                                 |
                                       |  stock (INT)                                      |
                                       |  idProveedor (FK → ENTIDAD con rol PROVEEDOR)     |
                                       +---------------------------------------------------+
