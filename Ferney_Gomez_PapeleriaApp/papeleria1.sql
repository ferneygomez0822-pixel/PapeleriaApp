
CREATE SCHEMA IF NOT EXISTS papeleria;
SET search_path = papeleria;



CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS empleados (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    cedula VARCHAR(30) UNIQUE,
    telefono VARCHAR(30),
    correo VARCHAR(150),
    direccion TEXT,
    fecha_ingreso DATE DEFAULT CURRENT_DATE,
    rol_id INTEGER NOT NULL REFERENCES roles(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    cedula_ruc VARCHAR(50) UNIQUE,
    telefono VARCHAR(30),
    correo VARCHAR(150),
    direccion TEXT
);

CREATE TABLE IF NOT EXISTS proveedores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    contacto VARCHAR(150),
    telefono VARCHAR(30),
    correo VARCHAR(150),
    direccion TEXT
);

CREATE TABLE IF NOT EXISTS categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);



CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,
    sku VARCHAR(64) UNIQUE,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria_id INTEGER REFERENCES categorias(id) ON DELETE SET NULL,
    proveedor_id INTEGER REFERENCES proveedores(id) ON DELETE SET NULL,
    precio_costo NUMERIC(12,2) DEFAULT 0.00 CHECK (precio_costo >= 0),
    precio_venta NUMERIC(12,2) DEFAULT 0.00 CHECK (precio_venta >= 0),
    iva BOOLEAN DEFAULT TRUE,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS inventario (
    id SERIAL PRIMARY KEY,
    producto_id INTEGER NOT NULL REFERENCES productos(id) ON DELETE CASCADE,
    cantidad INTEGER NOT NULL DEFAULT 0 CHECK (cantidad >= 0),
    ubicacion VARCHAR(100),
    fecha_actualizacion TIMESTAMP WITH TIME ZONE DEFAULT now(),
    UNIQUE(producto_id, ubicacion)
);


CREATE TABLE IF NOT EXISTS compras (
    id SERIAL PRIMARY KEY,
    proveedor_id INTEGER REFERENCES proveedores(id) ON DELETE SET NULL,
    empleado_id INTEGER REFERENCES empleados(id) ON DELETE SET NULL,
    fecha TIMESTAMP WITH TIME ZONE DEFAULT now(),
    total NUMERIC(12,2) DEFAULT 0.00,
    estado VARCHAR(30) DEFAULT 'RECIBIDA'
);

CREATE TABLE IF NOT EXISTS compra_items (
    id SERIAL PRIMARY KEY,
    compra_id INTEGER NOT NULL REFERENCES compras(id) ON DELETE CASCADE,
    producto_id INTEGER NOT NULL REFERENCES productos(id) ON DELETE RESTRICT,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(12,2) NOT NULL CHECK (precio_unitario >= 0),
    subtotal NUMERIC(12,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED
);



CREATE TABLE IF NOT EXISTS ventas (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER REFERENCES clientes(id) ON DELETE SET NULL,
    empleado_id INTEGER REFERENCES empleados(id) ON DELETE SET NULL,
    fecha TIMESTAMP WITH TIME ZONE DEFAULT now(),
    subtotal NUMERIC(12,2) DEFAULT 0.00,
    impuesto NUMERIC(12,2) DEFAULT 0.00,
    total NUMERIC(12,2) DEFAULT 0.00,
    estado VARCHAR(30) DEFAULT 'EMITIDA'
);

CREATE TABLE IF NOT EXISTS venta_items (
    id SERIAL PRIMARY KEY,
    venta_id INTEGER NOT NULL REFERENCES ventas(id) ON DELETE CASCADE,
    producto_id INTEGER NOT NULL REFERENCES productos(id) ON DELETE RESTRICT,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(12,2) NOT NULL CHECK (precio_unitario >= 0),
    descuento NUMERIC(5,2) DEFAULT 0.00 CHECK (descuento >= 0),
    subtotal NUMERIC(12,2) GENERATED ALWAYS AS ((precio_unitario * cantidad) * (1 - (descuento/100))) STORED
);


CREATE TABLE IF NOT EXISTS pagos (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    referencia_id INTEGER,
    medio_pago VARCHAR(50),
    fecha TIMESTAMP WITH TIME ZONE DEFAULT now(),
    monto NUMERIC(12,2) NOT NULL CHECK (monto >= 0)
);

CREATE TABLE IF NOT EXISTS papeleria.facturas (
    id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATE NOT NULL,
    total NUMERIC(10,2) NOT NULL,

    CONSTRAINT facturas_cliente_id_fkey
        FOREIGN KEY (cliente_id) REFERENCES papeleria.clientes(id)
);


DROP TABLE IF EXISTS facturas CASCADE;

CREATE TABLE facturas (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    fecha DATE NOT NULL,
    total NUMERIC(10,2) NOT NULL
);
CREATE TABLE papeleria.facturas (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES papeleria.clientes(id),
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    total NUMERIC(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS papeleria.usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL
);


CREATE INDEX IF NOT EXISTS idx_productos_nombre ON productos (nombre);
CREATE INDEX IF NOT EXISTS idx_inventario_producto ON inventario (producto_id);
CREATE INDEX IF NOT EXISTS idx_ventas_fecha ON ventas (fecha);


CREATE OR REPLACE VIEW vw_stock_resumen AS
SELECT p.id AS producto_id, p.sku, p.nombre, COALESCE(i.cantidad,0) AS cantidad, p.precio_venta
FROM productos p
LEFT JOIN inventario i ON p.id = i.producto_id;



CREATE OR REPLACE FUNCTION actualizar_total_compra() RETURNS TRIGGER AS $$
BEGIN
    UPDATE compras SET total = (
        SELECT COALESCE(SUM(subtotal),0) FROM compra_items WHERE compra_id = NEW.compra_id
    ) WHERE id = NEW.compra_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_actualizar_total_compra ON compra_items;
CREATE TRIGGER trg_actualizar_total_compra
AFTER INSERT OR UPDATE OR DELETE ON compra_items
FOR EACH ROW EXECUTE FUNCTION actualizar_total_compra();

CREATE OR REPLACE FUNCTION actualizar_total_venta() RETURNS TRIGGER AS $$
BEGIN
    UPDATE ventas SET subtotal = (
        SELECT COALESCE(SUM(subtotal),0) FROM venta_items WHERE venta_id = NEW.venta_id
    ),
    impuesto = (SELECT COALESCE(SUM(subtotal),0) FROM venta_items WHERE venta_id = NEW.venta_id) * 0.12,
    total = (SELECT COALESCE(SUM(subtotal),0) FROM venta_items WHERE venta_id = NEW.venta_id) * 1.12
    WHERE id = NEW.venta_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_actualizar_total_venta ON venta_items;
CREATE TRIGGER trg_actualizar_total_venta
AFTER INSERT OR UPDATE OR DELETE ON venta_items
FOR EACH ROW EXECUTE FUNCTION actualizar_total_venta();



INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Administrador'),
('CAJERO', 'Encargado de caja')
ON CONFLICT DO NOTHING;

INSERT INTO categorias (nombre, descripcion) VALUES
('Papel','Artículos de papel'),
('Útiles de Escritura','Bolígrafos, lápices, etc'),
('Mochilas','Mochilas y estuches')
ON CONFLICT DO NOTHING;

INSERT INTO proveedores (nombre, contacto) VALUES
('Distribuciones ABC','Contacto ABC')
ON CONFLICT DO NOTHING;

INSERT INTO papeleria.facturas (cliente_id, fecha, total)
VALUES
(1, '2025-01-15', 45.00),   -- Danna
(2, '2025-01-16', 89.50),   -- Ferney
(3, '2025-01-18', 12.25),   -- Junior
(4, '2025-01-18', 120.00),  -- Cristian
(5, '2025-01-19', 6.50),    -- Axel
(6, '2025-01-20', 78.99);   -- Brayan


INSERT INTO productos (sku, nombre, categoria_id, proveedor_id, precio_costo, precio_venta)
VALUES
('PAP-001','Cuaderno A4 100 hojas', 1, 1, 3.00, 6.50),
('BOL-001','Bolígrafo Azul', 2, 1, 0.20, 0.80)
ON CONFLICT DO NOTHING;

INSERT INTO inventario (producto_id, cantidad, ubicacion)
VALUES
(1,50,'Tienda'),
(2,200,'Tienda')
ON CONFLICT DO NOTHING;


INSERT INTO clientes (nombre, cedula_ruc, telefono, correo, direccion)
VALUES
('Danna Torres', '1102345678', '0987654321', 'danna.torres@example.com', 'Calle 10 #15-22'),
('Ferney Gómez', '1109876543', '0998765432', 'ferney.gomez@example.com', 'Cra 23 #45-67'),
('Junior Martínez', '1105566778', '0976543210', 'junior.martinez@example.com', 'Av. Libertad 120'),
('Cristian López', '1102233445', '0965432109', 'cristian.lopez@example.com', 'Calle 80 #12-09'),
('Axel Ramírez', '1108899001', '0954321098', 'axel.ramirez@example.com', 'Barrio Central, Mz 4, Casa 18'),
('Brayan Castillo', '1106677889', '0943210987', 'brayan.castillo@example.com', 'Urbanización Los Pinos, Casa 12');


SELECT * FROM facturas;

INSERT INTO papeleria.facturas (cliente_id, fecha, total)
VALUES
(1, CURRENT_DATE, 45.00),
(2, CURRENT_DATE, 89.50),
(5, CURRENT_DATE, 120.00);
SELECT id, nombre FROM papeleria.clientes ORDER BY id;
SELECT * FROM papeleria.facturas;
SELECT schema_name
FROM information_schema.schemata;


SELECT * FROM usuarios;
SELECT table_schema, table_name
FROM information_schema.tables
WHERE table_name ILIKE '%usuario%';
SELECT schema_name 
FROM information_schema.schemata;

SELECT table_schema, table_name
FROM information_schema.tables
WHERE table_name = 'usuarios';

INSERT INTO papeleria.usuarios (nombre, usuario, contraseña) VALUES
('Ferney Gómez', 'ferney', '1234'),
('Danna Torres', 'danna', '1234'),
('Axel Ramírez', 'axel', '1234'),
('Junior Martínez', 'junior', '1234');


SELECT * FROM papeleria.usuarios;

