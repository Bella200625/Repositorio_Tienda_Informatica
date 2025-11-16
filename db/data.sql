USE tienda_informatica_final;

-- ========================================================
-- 1. DATOS DE INFRAESTRUCTURA (Proveedores y Fabricantes)
-- ========================================================

-- INSERCIÓN EN Empresa_fabricante (Necesaria para Detalle_alta_tecnologia)
INSERT INTO Empresa_fabricante (nombre, numreo_empleados) VALUES
('Intel Corp', 110000),
('HP Inc', 55000),
('Samsung Electronics', 267000);

-- INSERCIÓN EN proveedor (Necesaria para Adquisicion)
INSERT INTO proveedor (NIF) VALUES
('P70000001'),
('P70000002'),
('P70000003');

-- ========================================================
-- 2. CATEGORÍAS Y PRODUCTOS (Foco en HU1)
-- ========================================================

-- INSERCIÓN EN categoria_producto
INSERT INTO categoria_producto (nombre) VALUES
('CPU'),              -- idcategoria = 1
('Impresora'),        -- idcategoria = 2
('Monitor'),          -- idcategoria = 3
('Disco Duro'),       -- idcategoria = 4
('Otro');             -- idcategoria = 5

-- ENLACE A CATEGORÍAS ESPECIALES (PARA PRUEBAS FUTURAS)
-- La tabla categoria_alquilables y categoria_alta_tecnologia usan la ID de categoria_producto
INSERT INTO Categoria_alta_tecnologia (categoria_producto_idcategoria) VALUES (1), (4); -- CPU, Disco Duro
INSERT INTO categoria_servicios_tecnicos (categoria_producto_idcategoria) VALUES (2); -- Impresora

-- INSERCIÓN EN Detalle_alta_tecnologia (Necesario para CPU y DD)
-- Detalle 1: para el CPU
INSERT INTO Detalle_alta_tecnologia (pais_origen, fecha_fabricacion, Empresa_fabricante_idEmpresa_fabricante) VALUES
('Malasia', '2023-01-15', 1); -- Fabricante ID 1 (Intel Corp)

-- Detalle 2: para el Disco Duro
INSERT INTO Detalle_alta_tecnologia (pais_origen, fecha_fabricacion, Empresa_fabricante_idEmpresa_fabricante) VALUES
('Corea del Sur', '2023-05-20', 3); -- Fabricante ID 3 (Samsung)


-- INSERCIÓN EN producto
INSERT INTO producto (nombre_producto, modelo, descripcion, Detalle_alta_tecnologia_idDetalle_alta_tecnologia, categoria_producto_idcategoria) VALUES
-- ID 1: CPU (Alta Tecnología)
('Procesador Core i7', 'i7-12700K', 'CPU de alto rendimiento para gaming.', 1, 1),
-- ID 2: Impresora (Servicio Técnico)
('Impresora Láser', 'LaserJet Pro M404', 'Impresora monocromática rápida.', NULL, 2),
-- ID 3: Monitor
('Monitor UltraWide', 'UltraWide 34W', 'Monitor 34 pulgadas, resolución QHD.', NULL, 3),
-- ID 4: Disco Duro (Alta Tecnología)
('SSD NVMe 1TB', '970 EVO', 'Unidad de estado sólido de 1TB.', 2, 4),
-- ID 5: Otro Producto
('Mouse Óptico', 'MX3', 'Mouse ergonómico inalámbrico.', NULL, 5);


-- ========================================================
-- 3. ADQUISICIONES (Foco en HU2)
-- ========================================================

-- INSERCIÓN EN Producto_proveedor (Adquisiciones)
INSERT INTO Producto_proveedor (fecha_adquisicion, producto_idproducto, proveedor_idproveedor) VALUES
-- CPU (ID 1) adquirido por Proveedor NIF P70000001 (ID 1)
('2023-10-01', 1, 1), 
-- Impresora (ID 2) adquirida por Proveedor NIF P70000002 (ID 2)
('2023-10-05', 2, 2), 
-- Monitor (ID 3) adquirido por Proveedor NIF P70000001 (ID 1)
('2023-10-10', 3, 1),
-- Disco Duro (ID 4) adquirido por Proveedor NIF P70000003 (ID 3)
('2023-10-15', 4, 3);