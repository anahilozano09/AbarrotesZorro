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

