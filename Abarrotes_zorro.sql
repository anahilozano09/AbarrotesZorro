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
    telefono VARCHAR(12) NOT NULL
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

INSERT INTO `cliente` VALUES
                          (1,'Jose Perez','123456789252','clientepruebaspring@gmail.com','228844317623'),
                          (2,'Alondra Ramos', '425459789252', 'anatrikilozano@gmail.com','5580104964'),
                          (3,'Diego Arreola', '227153789252', 'diego23arreola@gmail.com','5577540590');


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
    (11,'Uso domestico'),
    (12,'Botanas'),
    (13,'Frutas y verduras'),
    (14,'Congelados y helados');

INSERT INTO `producto` VALUES
                           (1,'Arroz',79.04,'Arroz.png',1),
                           (2,'Frijol',41.27,'Frijol.png',1),
                           (3,'Azúcar',77.07,'Azúcar.png',1),
                           (4,'Aceite',15.23,'Aceite.png',1),
                           (5,'Sal',90.13,'Sal.png',1),
                           (6,'Harina',63.83,'Harina.png',1),
                           (7,'Pasta',18.16,'Pasta.png',1),
                           (8,'Sopa Maruchan',38.21,'Sopa_Maruchan.png',7),
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
                           (50,'Fabuloso Lavanda',38.94,'Fabuloso_Lavanda.png',11),
                           (51,'Frijoles Bayos Isadora',18.5,'Frijoles_Bayos_Isadora.png',2),
                           (52,'Chícharos enlatados Herdez',15.75,'Chicharos_Herdez.png',2),
                           (53,'Elote en lata La Costeña',16.9,'Elote_La_Costena.png',2),
                           (54,'Atún en agua Dolores',21,'Atun_Dolores.png',2),
                           (55,'Sardinas en tomate Calmex',19.6,'Sardinas_Calmex.png',2),
                           (56,'Champiñones en lata La Costeña',23.4,'Champinones_La_Costena.png',2),
                           (57,'Verduras mixtas Herdez',17.8,'Verduras_Mixtas_Herdez.png',2),
                           (58,'Leche entera Lala 1L',21.5,'Leche_Lala.png',3),
                           (59,'Yogurt bebible Danone 220ml',10,'Yogurt_Danone.png',3),
                           (60,'Queso panela NocheBuena 200g',36.9,'Queso_Panela_NocheBuena.png',3),
                           (61,'Crema Alpura 250ml',18,'Crema_Alpura.png',3),
                           (62,'Leche deslactosada Santa Clara',23.4,'Leche_Santa_Clara.png',3),
                           (63,'Mantequilla Gloria 90g',15.7,'Mantequilla_Gloria.png',3),
                           (64,'Paleta Payaso',13,'Paleta_Payaso.png',4),
                           (65,'Gomitas Ricolino',9.5,'Gomitas_Ricolino.png',4),
                           (66,'Chocolate Carlos V',12,'CarlosV.png',4),
                           (67,'Mazapán De la Rosa',6,'Mazapan_DeLaRosa.png',4),
                           (68,'Pulparindo',5.5,'Pulparindo.png',4),
                           (69,'Paleta Vero Mango',4.5,'Paleta_Vero_Mango.png',4),
                           (70,'Gansito Marinela',11.5,'Gansito.png',4),
                           (71,'Rockaleta',6.5,'Rockaleta.png',4),
                           (72,'Pan Bimbo Blanco',38,'Pan_Bimbo.png',5),
                           (73,'Tortillas de maíz Maseca',19,'Tortillas_Maseca.png',5),
                           (74,'Harina Hot Cakes Gamesa',22.5,'Harina_Gamesa.png',5),
                           (75,'Pan Molido La Moderna',14,'Pan_Molido_Moderna.png',5),
                           (76,'Tostadas Charras',17,'Tostadas_Charras.png',5),
                           (77,'Pan de caja Great Value',25.5,'Pan_Great_Value.png',5),
                           (78,'Coca-Cola 600ml',17,'Coca_600ml.png',6),
                           (79,'Pepsi 2L',28,'Pepsi_2L.png',6),
                           (80,'Jugo Jumex Durazno 500ml',13.5,'Jugo_Jumex.png',6),
                           (81,'Agua Bonafont 1.5L',10,'Bonafont.png',6),
                           (82,'Red Bull 250ml',32,'RedBull.png',6),
                           (83,'Gatorade Naranja 500ml',14.5,'Gatorade.png',6),
                           (84,'Sopa Maruchan Camarón',13,'Maruchan.png',7),
                           (85,'Sándwich de jamón',24,'Sandwich_Jamon.png',7),
                           (86,'Ensalada de atún',29.5,'Ensalada_Atun.png',7),
                           (87,'Tamal de pollo',18,'Tamal_Pollo.png',7),
                           (88,'Pasta lista para calentar',21,'Pasta_Preparada.png',7),
                           (89,'Pizza individual congelada',34,'Pizza_Congelada.png',7),
                           (90,'Hot Dog listo',25,'HotDog_Listo.png',7),
                           (91,'Paracetamol 500mg',9,'Paracetamol.png',8),
                           (92,'Ibuprofeno 400mg',11,'Ibuprofeno.png',8),
                           (93,'Loratadina 10mg',13,'Loratadina.png',8),
                           (94,'Omeprazol 20mg',14,'Omeprazol.png',8),
                           (95,'Antigripal Next',17.5,'Next.png',8),
                           (96,'Alka-Seltzer',8.5,'Alka_Seltzer.png',8),
                           (97,'VapoRub Vicks',21,'VapoRub.png',8),
                           (98,'Pepto Bismol',27,'Pepto.png',8),
                           (99,'Curitas adhesivas',15,'Curitas.png',8),
                           (100,'Papel higiénico Regio 4 rollos',25,'Papel_Regio.png',9),
                           (101,'Shampoo Sedal 190ml',22,'Shampoo_Sedal.png',9),
                           (102,'Pasta dental Colgate 100ml',18.5,'Colgate.png',9),
                           (103,'Jabón Zote blanco',10,'Jabon_Zote.png',9),
                           (104,'Escoba económica',27,'Escoba.png',10),
                           (105,'Recogedor plástico',18,'Recogedor.png',10),
                           (106,'Trapeador tipo mechudo',36,'Trapeador.png',10),
                           (107,'Fibras para tallar',12.5,'Fibra_Tallar.png',10),
                           (108,'Cubeta 10L',39,'Cubeta.png',10),
                           (109,'Cepillo para baño',21,'Cepillo_Bano.png',10),
                           (110,'Guantes de limpieza',17.5,'Guantes_Limpieza.png',10),
                           (111,'Toalla multiusos',15,'Toalla_Multiusos.png',10),
                           (112,'Atomizador plástico',14,'Atomizador.png',10),
                           (113,'Cloro 1L',11,'Cloro.png',11),
                           (114,'Detergente Roma 1kg',24.5,'Detergente_Roma.png',11),
                           (115,'Suavitel 850ml',27,'Suavitel.png',11),
                           (116,'Limpiavidrios Windex',33,'Windex.png',11),
                           (117,'Papas Sabritas Original 45g',15,'Sabritas.png',12),
                           (118,'Doritos Nacho 65g',17,'Doritos.png',12),
                           (119,'Chetos Torciditos',16,'Chetos.png',12),
                           (120,'Rancheritos',15.5,'Rancheritos.png',12),
                           (121,'Takis Fuego',18,'Takis.png',12),
                           (122,'Tostitos Salsa Verde',19.5,'Tostitos.png',12),
                           (123,'Palanquetas de cacahuate',9,'Palanqueta.png',12),
                           (124,'Chicharrones botaneros',12,'Chicharrones.png',12),
                           (125,'Mazorca preparada',23,'Mazorca.png',12),
                           (126,'Cacahuates japoneses',14,'Cacahuates.png',12),
                           (127,'Plátano por kg',18,'Platano.png',13),
                           (128,'Manzana roja por kg',32,'Manzana.png',13),
                           (129,'Tomate saladet por kg',16,'Tomate.png',13),
                           (130,'Cebolla blanca por kg',14.5,'Cebolla.png',13),
                           (131,'Aguacate hass por kg',48,'Aguacate.png',13),
                           (132,'Zanahoria por kg',12,'Zanahoria.png',13),
                           (133,'Lechuga romana',10,'Lechuga.png',13),
                           (134,'Limón sin semilla por kg',24,'Limon.png',13),
                           (135,'Papaya maradol por kg',19,'Papaya.png',13),
                           (136,'Naranja para jugo por kg',14,'Naranja.png',13),
                           (137,'Helado Holanda Napolitano 1L',62,'Helado_Holanda.png',14),
                           (138,'Paleta Magnum Almendra',27,'Magnum.png',14),
                           (139,'Nieve de limón 1L',39,'Nieve_Limon.png',14),
                           (140,'Pizza congelada Bimbo',45,'Pizza_Bimbo.png',14),
                           (141,'Hamburguesas congeladas',48,'Hamburguesas.png',14),
                           (142,'Croquetas empanizadas',42,'Croquetas.png',14),
                           (143,'Pollo empanizado congelado',59,'Pollo_Empanizado.png',14),
                           (144,'Nuggets de pollo congelados',47,'Nuggets.png',14),
                           (145,'Hielo bolsa 3kg',22,'Hielo.png',14),
                           (146,'Paletas Fruttare',26,'Fruttare.png',14);