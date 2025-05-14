-- Crear la base de datos
DROP DATABASE IF EXISTS Abarrotes_zorro;
CREATE DATABASE Abarrotes_zorro;
USE Abarrotes_zorro;

-- Tabla de roles de empleados
CREATE TABLE rol_empleado (
    id_rol INT PRIMARY KEY NOT NULL auto_increment,
    rol VARCHAR(50) NOT NULL
);

-- Tabla de empleados
CREATE TABLE empleado (
    id_emp INT PRIMARY KEY NOT NULL auto_increment,
    nombre VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL ,
    password VARCHAR(100) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    telefono VARCHAR(100) NOT NULL,
    id_rol INT NOT NULL ,
    FOREIGN KEY (id_rol) REFERENCES rol_empleado(id_rol)
);

-- Tabla de clientes
CREATE TABLE cliente (
    id_clien INT PRIMARY KEY auto_increment,
    nombre VARCHAR(100) NOT NULL ,
    num_cuenta VARCHAR(12) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    telefono VARCHAR(100) NOT NULL
);

-- Tabla de tipo del producto
CREATE TABLE tipo_producto (
    id_tprod INT PRIMARY KEY NOT NULL auto_increment,
    tipo VARCHAR(255)
);

-- Tabla de productos
CREATE TABLE producto (
    id_pro INT PRIMARY KEY NOT NULL auto_increment,
    nombre VARCHAR(100) NOT NULL ,
    precio DOUBLE NOT NULL,
    img_pro VARCHAR(255) NOT NULL ,
    id_tprod INT NOT NULL ,
    FOREIGN KEY (id_tprod) REFERENCES tipo_producto(id_tprod)
);


-- Tabla de productos en almacén
CREATE TABLE cantidad_producto_almacen (
    id_cad INT PRIMARY KEY NOT NULL auto_increment,
    id_pro INT NOT NULL ,
    cantidad INT NOT NULL ,
    FOREIGN KEY (id_pro) REFERENCES producto(id_pro)
);

-- Tabla de productos comprados
CREATE TABLE producto_comprado (
    id_prodcomp INT PRIMARY KEY NOT NULL auto_increment,
    id_pro INT NOT NULL ,
    cantidad INT NOT NULL ,
    FOREIGN KEY (id_pro) REFERENCES producto(id_pro)
);

-- Tabla de compras de clientes
CREATE TABLE compra_cliente (
    id_compra INT PRIMARY KEY NOT NULL auto_increment,
    id_cliente INT NOT NULL ,
    total DOUBLE NOT NULL,
    id_emp INT NOT NULL ,
    id_prodcomp INT NOT NULL ,
    fecha DATE NOT NULL ,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_clien),
    FOREIGN KEY (id_emp) REFERENCES empleado(id_emp),
    FOREIGN KEY (id_prodcomp) REFERENCES producto_comprado(id_prodcomp)
);

-- Tabla de proveedores
CREATE TABLE proveedor (
    id_prov INT PRIMARY KEY NOT NULL auto_increment,
    nombre VARCHAR(100) NOT NULL ,
    id_pro INT NOT NULL ,
    cantidad INT NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    telefono VARCHAR(100) NOT NULL ,
    FOREIGN KEY (id_pro) REFERENCES producto(id_pro)
);

-- Tabla de histórico de productos
CREATE TABLE historico_productos (
    id_histo INT PRIMARY KEY NOT NULL auto_increment,
    id_cad INT NOT NULL ,
    id_prodcomp INT NOT NULL ,
    cantidad INT NOT NULL ,
    fecha DATE NOT NULL ,
    FOREIGN KEY (id_cad) REFERENCES cantidad_producto_almacen(id_cad),
    FOREIGN KEY (id_prodcomp) REFERENCES producto_comprado(id_prodcomp)
);

INSERT INTO `rol_empleado` VALUES
    (1,'Administrador'),
    (2,'Cajero');

INSERT INTO `tipo_producto` VALUES
    (1,'Abarrotes'),
    (2,'Productos enlatados'),
    (3,'Lacteos'),
    (4,'Dulceria'),
    (5,'Harinas y pan'),
    (6,'Bebidas'),
    (7,'Alimentos preparados'),
    (8,'Automedicacion'),
    (9,'Higiene personal'),
    (10,'Jarceria'),
    (11,'Uso domestico');

INSERT INTO `producto` VALUES
    (1,'Arroz',79.04,'Arroz.png',1),
    (2,'Frijol',41.27,'Frijol.png',1),
    (3,'Azúcar',77.07,'Azúcar.png',1),
    (4,'Aceite',15.23,'Aceite.png',1),
    (5,'Sal',90.13,'Sal.png',1),
    (6,'Harina',63.83,'Harina.png',1),
    (7,'Pasta',18.16,'Pasta.png',1),
    (8,'Sopa Maruchan',38.21,'Sopa_Maruchan.png',1),
    (9,'Pan Bimbo',44.98,'Pan_Bimbo.png',5),
    (10,'Leche Lala',77.33,'Leche_Lala.png',3),
    (11,'Coca Cola',38.62,'Coca_Cola.png',6),
    (12,'Pepsi',73.08,'Pepsi.png',6),
    (13,'Agua Bonafont',9.77,'Agua_Bonafont.png',6),
    (14,'Galletas María',63.68,'Galletas_María.png',5),
    (15,'Chocolates Hershey',59.79,'Chocolates_Hershey.png',4),
    (16,'Cereal Zucaritas',24.38,'Cereal_Zucaritas.png',1),
    (17,'Yogur Danone',40.85,'Yogur_Danone.png',3),
    (18,'Queso Oaxaca',97.2,'Queso_Oaxaca.png',3),
    (19,'Jabón Zote',76.92,'Jabón_Zote.png',11),
    (20,'Papel Higiénico Suavel',34.04,'Papel_Higiénico_Suavel.png',9),
    (21,'Detergente Ariel',83.56,'Detergente_Ariel.png',11),
    (22,'Cloro Cloralex',88.5,'Cloro_Cloralex.png',11),
    (23,'Shampoo Sedal',83.04,'Shampoo_Sedal.png',9),
    (24,'Crema Nivea',52.7,'Crema_Nivea.png',9),
    (25,'Servilletas Pétalo',93.31,'Servilletas_Pétalo.png',11),
    (26,'Jamón FUD',78.83,'Jamón_FUD.png',7),
    (27,'Salchichas San Rafael',23.28,'Salchichas_San_Rafael.png',7),
    (28,'Mantequilla Gloria',50.2,'Mantequilla_Gloria.png',3),
    (29,'Pan Molido',85.16,'Pan_Molido.png',1),
    (30,'Mayonesa McCormick',17.94,'Mayonesa_McCormick.png',1),
    (31,'Catsup Heinz',72.65,'Catsup_Heinz.png',1),
    (32,'Chiles La Costeña',39.83,'Chiles_La_Costeña.png',2),
    (33,'Salsa Valentina',92.88,'Salsa_Valentina.png',1),
    (34,'Refresco Jarritos',32.86,'Refresco_Jarritos.png',6),
    (35,'Gansito',79.35,'Gansito.png',5),
    (36,'Tortillas Tía Rosa',88.5,'Tortillas_Tía_Rosa.png',5),
    (37,'Chicles Trident',25.68,'Chicles_Trident.png',4),
    (38,'Café Legal',94.06,'Café_Legal.png',1),
    (39,'Chocolate Abuelita',37.19,'Chocolate_Abuelita.png',1),
    (40,'Mermelada McCormick',45.77,'Mermelada_McCormick.png',1),
    (41,'Gelatina D Gari',23.17,'Gelatina_DGari.png',1),
    (42,'Atún Dolores',57.31,'Atún_Dolores.png',2),
    (43,'Sardinas La Sirena',82.74,'Sardinas_La_Sirena.png',2),
    (44,'Suero Electrolit',60.91,'Suero_Electrolit.png',8),
    (45,'Toallas Always',85.53,'Toallas_Always.png',9),
    (46,'Desodorante Rexona',14.6,'Desodorante_Rexona.png',9),
    (47,'Cubrebocas Desechable',70.01,'Cubrebocas_Desechable.png',9),
    (48,'Encendedor Clipper',11.97,'Encendedor_Clipper.png',11),
    (49,'Bolsa de Basura',89.69,'Bolsa_de_Basura.png',10),
    (50,'Fabuloso Lavanda',38.94,'Fabuloso_Lavanda.png',11);
