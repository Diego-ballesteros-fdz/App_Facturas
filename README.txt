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

Está es la estructura de la BD
              +---------------+
              |   Empresa     |
              |---------------|
              | *idEm (PK)    |
              |   nombreEm    |
              |   nifEm       |
              +-------+-------+
                      |
      .---------------+---------------.
      | (1:N)                         | (1:N)
      | idEm                          | empresa_idEm
+-----+---------+                     |
|   Cli_Prov    |                     |
|---------------|                     |
| *idCP (PK)    |                     |
|  idEm (FK)    |                     |
+-------+-------+                     |
        |                             |
        | (idCP)                      |
.-------+-------.---------------------+ (idCP)
| (1:N)         | (1:N)               |
|               |                     |
+-------+   +-------+           +-----+---------+
|Direccion|   |Producto |           |    Factura    |
|---------|   |---------|           |---------------|
|*idDi(PK)|   |*idPRO(PK|           | *idF (PK)     |
| idCP(FK)|   | idCP(FK)|           |  idCP (FK)    |
+---------+   | ...     |           |  empresa_idEm(FK)
              +----+----+           +-------+-------+
                   |                         |
                   | (idPRO)                 | (idF)
                   |                         |
                   '--------(N:1)------+ (1:N)
                                    |
                               +----+----------+
                               | Factura_Linea |
                               |---------------|
                               | *idFL (PK)    |
                               |  idF (FK)     |
                               |  idPRO (FK)   |
                               +---------------+