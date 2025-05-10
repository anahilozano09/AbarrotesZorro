-- Crear la base de datos
DROP DATABASE IF EXISTS Abarrotes_zorro;
CREATE DATABASE Abarrotes_zorro;
USE Abarrotes_zorro;

-- Tabla de roles de empleados
CREATE TABLE Rol_Empleado (
    id_rol INT PRIMARY KEY,
    Rol VARCHAR(50)
);

-- Tabla de empleados
CREATE TABLE Empleado (
    id_emp INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Usuario VARCHAR(50),
    Password VARCHAR(100),
    Email VARCHAR(100),
    id_rol INT,
    FOREIGN KEY (id_rol) REFERENCES Rol_Empleado(id_rol)
);

-- Tabla de clientes
CREATE TABLE Cliente (
    id_clien INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Usuario VARCHAR(50),
    Password VARCHAR(100),
    Email VARCHAR(100),
    Telefono VARCHAR(100)
);

-- Tabla de productos
CREATE TABLE Producto (
    id_pro INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Precio DOUBLE,
    Img_pro VARCHAR(255)
);
INSERT INTO `Producto` VALUES (1,'Arroz',79.04,'/img/Arroz.png'),(2,'Frijol',41.27,'/img/Frijol.png'),(3,'Azúcar',77.07,'/img/Azúcar.png'),(4,'Aceite',15.23,'/img/Aceite.png'),(5,'Sal',90.13,'/img/Sal.png'),(6,'Harina',63.83,'/img/Harina.png'),(7,'Pasta',18.16,'/img/Pasta.png'),(8,'Sopa Maruchan',38.21,'/img/Sopa_Maruchan.png'),(9,'Pan Bimbo',44.98,'/img/Pan_Bimbo.png'),(10,'Leche Lala',77.33,'/img/Leche_Lala.png'),(11,'Coca Cola',38.62,'/img/Coca_Cola.png'),(12,'Pepsi',73.08,'/img/Pepsi.png'),(13,'Agua Bonafont',9.77,'/img/Agua_Bonafont.png'),(14,'Galletas María',63.68,'/img/Galletas_María.png'),(15,'Chocolates Hershey',59.79,'/img/Chocolates_Hershey.png'),(16,'Cereal Zucaritas',24.38,'/img/Cereal_Zucaritas.png'),(17,'Yogur Danone',40.85,'/img/Yogur_Danone.png'),(18,'Queso Oaxaca',97.2,'/img/Queso_Oaxaca.png'),(19,'Jabón Zote',76.92,'/img/Jabón_Zote.png'),(20,'Papel Higiénico Suavel',34.04,'/img/Papel_Higiénico_Suavel.png'),(21,'Detergente Ariel',83.56,'/img/Detergente_Ariel.png'),(22,'Cloro Cloralex',88.5,'/img/Cloro_Cloralex.png'),(23,'Shampoo Sedal',83.04,'/img/Shampoo_Sedal.png'),(24,'Crema Nivea',52.7,'/img/Crema_Nivea.png'),(25,'Servilletas Pétalo',93.31,'/img/Servilletas_Pétalo.png'),(26,'Jamón FUD',78.83,'/img/Jamón_FUD.png'),(27,'Salchichas San Rafael',23.28,'/img/Salchichas_San_Rafael.png'),(28,'Mantequilla Gloria',50.2,'/img/Mantequilla_Gloria.png'),(29,'Pan Molido',85.16,'/img/Pan_Molido.png'),(30,'Mayonesa McCormick',17.94,'/img/Mayonesa_McCormick.png'),(31,'Catsup Heinz',72.65,'/img/Catsup_Heinz.png'),(32,'Chiles La Costeña',39.83,'/img/Chiles_La_Costeña.png'),(33,'Salsa Valentina',92.88,'/img/Salsa_Valentina.png'),(34,'Refresco Jarritos',32.86,'/img/Refresco_Jarritos.png'),(35,'Gansito',79.35,'/img/Gansito.png'),(36,'Tortillas Tía Rosa',88.5,'/img/Tortillas_Tía_Rosa.png'),(37,'Chicles Trident',25.68,'/img/Chicles_Trident.png'),(38,'Café Legal',94.06,'/img/Café_Legal.png'),(39,'Chocolate Abuelita',37.19,'/img/Chocolate_Abuelita.png'),(40,'Mermelada McCormick',45.77,'/img/Mermelada_McCormick.png'),(41,'Gelatina D Gari',23.17,'/img/Gelatina_DGari.png'),(42,'Atún Dolores',57.31,'/img/Atún_Dolores.png'),(43,'Sardinas La Sirena',82.74,'/img/Sardinas_La_Sirena.png'),(44,'Suero Electrolit',60.91,'/img/Suero_Electrolit.png'),(45,'Toallas Always',85.53,'/img/Toallas_Always.png'),(46,'Desodorante Rexona',14.6,'/img/Desodorante_Rexona.png'),(47,'Cubrebocas Desechable',70.01,'/img/Cubrebocas_Desechable.png'),(48,'Encendedor Clipper',11.97,'/img/Encendedor_Clipper.png'),(49,'Bolsa de Basura',89.69,'/img/Bolsa_de_Basura.png'),(50,'Fabuloso Lavanda',38.94,'/img/Fabuloso_Lavanda.png');


-- Tabla de productos en almacén
CREATE TABLE cantidad_producto_almacen (
    id_cad INT PRIMARY KEY,
    id_pro INT,
    cantidad INT,
    precio_unit DOUBLE,
    FOREIGN KEY (id_pro) REFERENCES Producto(id_pro)
);

-- Tabla de productos comprados
CREATE TABLE producto_comprado (
    id_prodcomp INT PRIMARY KEY,
    id_pro INT,
    cantidad INT,
    precio_unit DOUBLE,
    FOREIGN KEY (id_pro) REFERENCES Producto(id_pro)
);

-- Tabla de compras de clientes
CREATE TABLE compra_cliente (
    id_compra INT PRIMARY KEY,
    id_cliente INT,
    total DOUBLE,
    id_emp INT,
    id_prodcomp INT,
    Fecha DATE,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_clien),
    FOREIGN KEY (id_emp) REFERENCES Empleado(id_emp),
    FOREIGN KEY (id_prodcomp) REFERENCES producto_comprado(id_prodcomp)
);

-- Tabla de proveedores
CREATE TABLE Proveedor (
    id_prov INT PRIMARY KEY,
    Nombre VARCHAR(100),
    id_pro INT,
    Cantidad INT,
    Email VARCHAR(100),
    Telefono VARCHAR(100),
    FOREIGN KEY (id_pro) REFERENCES Producto(id_pro)
);

-- Tabla de histórico de productos
CREATE TABLE Historico_productos (
    Id_Histo INT PRIMARY KEY,
    id_cad INT,
    id_prodcomp INT,
    Cantidad INT,
    Fecha DATE,
    FOREIGN KEY (id_cad) REFERENCES cantidad_producto_almacen(id_cad),
    FOREIGN KEY (id_prodcomp) REFERENCES producto_comprado(id_prodcomp)
);

