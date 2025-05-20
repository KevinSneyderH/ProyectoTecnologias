-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-05-2025 a las 05:12:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecompra`
--

CREATE TABLE `detallecompra` (
  `id` int(11) NOT NULL,
  `id_proveedor` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_compra_proveedor` decimal(10,2) NOT NULL,
  `FechaCompra` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detallecompra`
--

INSERT INTO `detallecompra` (`id`, `id_proveedor`, `idproducto`, `cantidad`, `precio_compra_proveedor`, `FechaCompra`) VALUES
(9, 4, 4, 150, 14.00, '2025-05-19'),
(13, 3, 3, 20, 120.00, '2025-05-19');

--
-- Disparadores `detallecompra`
--
DELIMITER $$
CREATE TRIGGER `trg_actualizar_stock_compra` AFTER INSERT ON `detallecompra` FOR EACH ROW BEGIN
    UPDATE productoenty
    SET cantidad_en_stock = cantidad_en_stock + NEW.cantidad
    WHERE id_producto = NEW.idproducto;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallepedido`
--

CREATE TABLE `detallepedido` (
  `id_detalle_pedido` int(11) NOT NULL,
  `cantidad_solicitada` int(11) NOT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detallepedido`
--

INSERT INTO `detallepedido` (`id_detalle_pedido`, `cantidad_solicitada`, `subtotal`, `id_pedido`, `id_producto`) VALUES
(3, 3, 600.00, 2, 3),
(4, 15, 225.00, 2, 4),
(8, 1, 200.00, 4, 3),
(9, 20, 300.00, 5, 4),
(13, 10, 300.00, 13, 4),
(31, 1, 0.00, 23, 3),
(32, 1, 0.00, 23, 4),
(33, 1, 0.00, 24, 3),
(34, 1, 0.00, 24, 4),
(35, 1, 200.00, 25, 3),
(36, 1, 30.00, 25, 4),
(37, 1, 200.00, 26, 3),
(38, 1, 30.00, 26, 4),
(39, 1, 200.00, 27, 3),
(40, 1, 200.00, 28, 3),
(41, 1, 200.00, 29, 3),
(42, 3, 90.00, 29, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidoenty`
--

CREATE TABLE `pedidoenty` (
  `id_pedido` int(11) NOT NULL,
  `fecha_creacion` date NOT NULL DEFAULT current_timestamp(),
  `estado_pedido` varchar(30) NOT NULL,
  `costo_total` decimal(10,0) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidoenty`
--

INSERT INTO `pedidoenty` (`id_pedido`, `fecha_creacion`, `estado_pedido`, `costo_total`, `id_usuario`) VALUES
(1, '2025-05-01', 'Entregado', 272, 3),
(2, '2025-05-02', 'En proceso', 825, 4),
(3, '2025-05-03', 'Pendiente', 218, 6),
(4, '2025-05-04', 'En proceso', 200, 5),
(5, '2025-05-05', 'Pendiente', 600, 9),
(6, '2025-05-06', 'Pendiente', 130, 11),
(7, '2025-05-07', 'Pendiente', 880, 12),
(8, '2025-05-08', 'Pendiente', 1000, 13),
(9, '2025-05-19', 'Pendiente', 12002, 3),
(10, '2025-05-19', 'Pendiente', 800000, 13),
(11, '2025-05-19', 'Pendiente', 8000, 13),
(12, '2025-05-19', 'Pendiente', 78, 13),
(13, '2025-05-19', 'Pendiente', 300, 13),
(23, '2025-05-19', 'Pendiente', 0, 13),
(24, '2025-05-19', 'Pendiente', 0, 13),
(25, '2025-05-19', 'Pendiente', 230, 13),
(26, '2025-05-19', 'Pendiente', 230, 13),
(27, '2025-05-19', 'Pendiente', 200, 13),
(28, '2025-05-19', 'Pendiente', 200, 13),
(29, '2025-05-19', 'Pendiente', 290, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoenty`
--

CREATE TABLE `productoenty` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `cantidad_en_stock` int(11) NOT NULL DEFAULT 0,
  `precio_venta_unitario` decimal(10,0) NOT NULL,
  `url_imagen` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productoenty`
--

INSERT INTO `productoenty` (`id_producto`, `nombre`, `cantidad_en_stock`, `precio_venta_unitario`, `url_imagen`) VALUES
(3, 'Monitor LED 24\"s', 50, 200, 'https://example.com/images/mon'),
(4, 'Auriculares Bluetooth', 200, 30, 'https://example.com/images/aur');

--
-- Disparadores `productoenty`
--
DELIMITER $$
CREATE TRIGGER `actualizar_subtotal_producto` AFTER UPDATE ON `productoenty` FOR EACH ROW BEGIN
    -- Actualiza los subtotales de todos los detalles relacionados con este producto
    UPDATE detallepedido 
    SET subtotal = NEW.precio_venta_unitario * cantidad_solicitada
    WHERE id_producto = NEW.id_producto;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedorenty`
--

CREATE TABLE `proveedorenty` (
  `id_proveedor` int(11) NOT NULL,
  `nombre_empresa` varchar(30) NOT NULL,
  `contacto_principal` varchar(30) DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `direccion` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedorenty`
--

INSERT INTO `proveedorenty` (`id_proveedor`, `nombre_empresa`, `contacto_principal`, `telefono`, `email`, `direccion`) VALUES
(1, 'Tech Supplies S.A', 'Carlos Ramírez', '0414-1234567', 'carlos@techsupplies.com', 'Av. Principal, Caracas, Venezu'),
(2, 'Distribuciones El Sol', 'Carlos Pérez', '3001234567', 'carlos@elsol.com', 'Calle 45 #12-34'),
(3, 'Insumos Industriales S.A.', 'María Gómez', '3019876543', 'maria@insumos.com', 'Carrera 7 #89-10'),
(4, 'Suministros del Norte', 'Luis Ramírez', '3025558888', 'luis@suminorte.com', 'Av. 3 #45-67'),
(5, 'TecnoInsumos Ltda', 'Juan Herrera', '3112345678', 'juan@tecnoinsumos.com', 'Carrera 50 #12-34');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarioenty`
--

CREATE TABLE `usuarioenty` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contrasena_encriptada` varchar(100) NOT NULL,
  `rol` varchar(30) NOT NULL,
  `fecha_creacion` datetime DEFAULT current_timestamp(),
  `fecha_actualizacion` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarioenty`
--

INSERT INTO `usuarioenty` (`id_usuario`, `nombre_usuario`, `email`, `contrasena_encriptada`, `rol`, `fecha_creacion`, `fecha_actualizacion`) VALUES
(3, 'Juan Pérez Santos', 'juan.perez@example.com', '1234abcd5678efgh', 'admin', '2025-05-08 17:30:56', '2025-05-08 17:31:25'),
(4, 'kevin', 'david@gmail', '$2a$10$W6el3WwvvHQJ5IQrx4Wv0OuCgrZs8m6Xl9UQVJp5Zg83gi1XoExMe', 'USER', NULL, NULL),
(5, 'kevin', 'kevin.sneyder.hernandez@gmail.com', '$2a$10$eBa4ocEzAbqreDX4z1YuKOyja5nNDVRHecmpjku59.2YjirlVW2JW', 'USER', NULL, NULL),
(6, 'yo', 'yo@gmail.com', '$2a$10$4z4eBovwWPKyjHWAaVi.le2EXsOrZ7AMLQNmfujdhSVRmTWY6vfk2', 'USER', NULL, NULL),
(9, 'tu', 'kevin.sneyder@gmail.com', '$2a$10$Xe14O6vLG4mbiW7wPhpQJu0uBBrH7iHAfeuxly9OQKhEDOVpjtOVS', 'USER', '2025-05-08 20:19:34', '2025-05-08 20:19:34'),
(11, 'kevin', 'tu@gmail.com', '$2a$10$qiuCnxPbcbPIIb.1rHmxH.Ce8ALia01MYQkPtozhtbdSvqV8db3SO', 'USER', '2025-05-08 20:33:45', '2025-05-08 20:33:45'),
(12, 'ka', 'ka@gmail.com', '$2a$10$DjTPRI2G1QM19b37JbWqauMtl23A5sngzkTtpuWAopuP9Hl13AvOO', 'ADMIN', '2025-05-08 23:04:19', '2025-05-08 23:04:19'),
(13, 'k_hernandez8', 'kevin400@gmail.com', '$2a$10$/kupfypwEFsllY7CJKrhGuJ/nweIo9OYDGrkK563kQ8ESf68JKOPa', 'ADMIN', '2025-05-12 16:01:20', '2025-05-12 16:01:20');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_proveedor` (`id_proveedor`),
  ADD KEY `idproducto` (`idproducto`);

--
-- Indices de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD PRIMARY KEY (`id_detalle_pedido`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `pedidoenty`
--
ALTER TABLE `pedidoenty`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `productoenty`
--
ALTER TABLE `productoenty`
  ADD PRIMARY KEY (`id_producto`);

--
-- Indices de la tabla `proveedorenty`
--
ALTER TABLE `proveedorenty`
  ADD PRIMARY KEY (`id_proveedor`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `usuarioenty`
--
ALTER TABLE `usuarioenty`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  MODIFY `id_detalle_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `pedidoenty`
--
ALTER TABLE `pedidoenty`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `productoenty`
--
ALTER TABLE `productoenty`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `usuarioenty`
--
ALTER TABLE `usuarioenty`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD CONSTRAINT `detallecompra_ibfk_2` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedorenty` (`id_proveedor`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detallecompra_ibfk_3` FOREIGN KEY (`idproducto`) REFERENCES `productoenty` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidoenty` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detallepedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productoenty` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pedidoenty`
--
ALTER TABLE `pedidoenty`
  ADD CONSTRAINT `pedidoenty_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarioenty` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
