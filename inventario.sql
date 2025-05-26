-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-05-2025 a las 17:56:21
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
-- Estructura de tabla para la tabla `categoriaenty`
--

CREATE TABLE `categoriaenty` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoriaenty`
--

INSERT INTO `categoriaenty` (`id`, `nombre`) VALUES
(1, 'electrodomésticos'),
(2, 'Muebles'),
(3, 'herramientas'),
(4, 'vehículos'),
(5, 'alimentos'),
(6, 'licorería'),
(7, 'Celulares');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compraenty`
--

CREATE TABLE `compraenty` (
  `id_compra` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `estado` varchar(50) NOT NULL,
  `costo_total` decimal(10,0) NOT NULL,
  `id_proveedor` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `compraenty`
--

INSERT INTO `compraenty` (`id_compra`, `fecha`, `estado`, `costo_total`, `id_proveedor`, `id_usuario`) VALUES
(22, '2025-05-22 00:00:00', 'Pendiente', 30000000, 878923, 6),
(23, '2025-05-22 00:00:00', 'Pendiente', 38000000, 878923, 6),
(24, '2025-05-22 00:00:00', 'Pendiente', 7800, 860, 6),
(25, '2025-05-22 00:00:00', 'Pendiente', 3900, 860, 6),
(26, '2025-05-22 23:15:32', 'Pendiente', 12000000, 878923, 6),
(27, '2025-05-22 23:16:37', 'Pendiente', 6800000, 878923, 6),
(28, '2025-05-24 21:22:26', 'Pendiente', 3900, NULL, 13),
(29, '2025-05-24 21:22:35', 'Pendiente', 6000000, NULL, 13),
(30, '2025-05-24 21:30:45', 'Pendiente', 18003900, NULL, 13),
(31, '2025-05-26 07:06:00', 'Pendiente', 3000000, NULL, 6),
(32, '2025-05-26 07:53:50', 'Pendiente', 1000000, NULL, 6),
(33, '2025-05-26 07:55:23', 'Pendiente', 1000000, NULL, 6),
(34, '2025-05-26 08:17:37', 'Pendiente', 12003900, NULL, 6),
(35, '2025-05-26 08:18:28', 'Pendiente', 15600, NULL, 6),
(36, '2025-05-26 08:19:00', 'Pendiente', 30000000, NULL, 6),
(39, '2025-05-26 08:21:43', 'Pendiente', 60000000, NULL, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecompra`
--

CREATE TABLE `detallecompra` (
  `id` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_compra_proveedor` decimal(10,2) NOT NULL,
  `id_compra` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detallecompra`
--

INSERT INTO `detallecompra` (`id`, `idproducto`, `cantidad`, `precio_compra_proveedor`, `id_compra`) VALUES
(48, 18, 5, 30000000.00, 22),
(49, 18, 5, 30000000.00, 23),
(51, 17, 2, 7800.00, 24),
(52, 17, 1, 3900.00, 25),
(53, 18, 2, 12000000.00, 26),
(54, 18, 1, 6000000.00, 27),
(56, 17, 1, 3900.00, 28),
(57, 20, 1, 6000000.00, 29),
(58, 17, 1, 3900.00, 30),
(59, 18, 1, 6000000.00, 30),
(60, 20, 2, 12000000.00, 30),
(61, 18, 1, 3000000.00, 31),
(62, 18, 1, 1000000.00, 32),
(63, 18, 1, 1000000.00, 33),
(64, 17, 1, 3900.00, 34),
(65, 18, 1, 6000000.00, 34),
(66, 20, 1, 6000000.00, 34),
(67, 17, 4, 15600.00, 35),
(68, 18, 5, 30000000.00, 36),
(69, 20, 10, 60000000.00, 39);

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
(81, 5, 19500.00, 55, 17),
(82, 5, 19500.00, 56, 17),
(83, 1, 3000000.00, 56, 18),
(84, 1, 3900.00, 57, 17),
(85, 1, 3000000.00, 57, 18),
(87, 1, 3900.00, 58, 17),
(88, 2, 7800.00, 59, 17),
(89, 4, 12000000.00, 61, 18),
(90, 1, 3000000.00, 62, 18),
(91, 2, 7800.00, 63, 17),
(92, 1, 3000000.00, 64, 18),
(93, 1, 3000000.00, 65, 18),
(94, 2, 12000000.00, 65, 20),
(95, 1, 3900.00, 66, 17),
(96, 1, 6000000.00, 66, 18),
(97, 1, 6000000.00, 66, 20),
(98, 1, 3900.00, 67, 17),
(99, 1, 6000000.00, 67, 18),
(100, 1, 6000000.00, 67, 20),
(101, 5, 30000000.00, 68, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcaenty`
--

CREATE TABLE `marcaenty` (
  `id` int(10) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `marcaenty`
--

INSERT INTO `marcaenty` (`id`, `nombre`) VALUES
(1, 'lg'),
(2, 'samsumg'),
(3, 'Diana'),
(4, 'Gelvez');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidoenty`
--

CREATE TABLE `pedidoenty` (
  `id_pedido` int(11) NOT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT current_timestamp(),
  `estado_pedido` varchar(30) NOT NULL,
  `costo_total` decimal(10,0) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidoenty`
--

INSERT INTO `pedidoenty` (`id_pedido`, `fecha_creacion`, `estado_pedido`, `costo_total`, `id_usuario`) VALUES
(55, '2025-05-22 00:00:00', 'Pendiente', 19500, 13),
(56, '2025-05-22 00:00:00', 'Pendiente', 6019500, 6),
(57, '2025-05-22 00:00:00', 'Pendiente', 6803900, 6),
(58, '2025-05-22 00:00:00', 'Pendiente', 3901, 6),
(59, '2025-05-22 00:00:00', 'Pendiente', 7800, 6),
(60, '2025-05-22 23:03:37', 'Pendiente', 12002, 3),
(61, '2025-05-22 00:00:00', 'Pendiente', 24000000, 6),
(62, '2025-05-22 23:09:25', 'Pendiente', 6000000, 6),
(63, '2025-05-22 23:09:35', 'Pendiente', 7800, 6),
(64, '2025-05-24 21:13:19', 'Pendiente', 6000000, 13),
(65, '2025-05-26 07:55:47', 'Pendiente', 13000000, 6),
(66, '2025-05-26 08:16:09', 'Pendiente', 12003900, 6),
(67, '2025-05-26 08:18:18', 'Pendiente', 12003900, 6),
(68, '2025-05-26 08:19:10', 'Pendiente', 30000000, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoenty`
--

CREATE TABLE `productoenty` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `cantidad_en_stock` int(11) NOT NULL DEFAULT 0,
  `precio_venta_unitario` decimal(10,0) NOT NULL,
  `url_imagen` varchar(200) NOT NULL,
  `marca` int(11) NOT NULL,
  `categoría` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productoenty`
--

INSERT INTO `productoenty` (`id_producto`, `nombre`, `cantidad_en_stock`, `precio_venta_unitario`, `url_imagen`, `marca`, `categoría`) VALUES
(17, 'Arroz', 81, 3900, 'https://exitocol.vteximg.com.br/arquivos/ids/27182455/Arroz-Blanco-Bolsa-X-3000g-130407_a.jpg', 3, 5),
(18, 'Televisor 24', 7, 6000000, 'https://fullhogar.com.co/cdn/shop/files/50UT7300PSA.AWCQ-3.jpg?v=1718818028', 1, 1),
(20, 'CARRAZO', 10, 6000000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTS6G59YqQb8j9NhGKxGDHRLm4296H5QRhO5g&s', 2, 4);

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
(2, 'Distribuciones El Sol', 'Carlos Pérez', '3001234567', 'carlos@elsol.com', 'Calle 45 #12-34'),
(4, 'Suministros del Norte', 'Luis Ramírez', '3025558888', 'luis@suminorte.com', 'Av. 3 #45-67'),
(860, 'Diana', 'Jose', '3133391213', 'adjjasd@gmail.com', 'av3'),
(878923, 'LG', 'Juan Herrera', '3133391213', 'kevin.sneyde@gmail.com', 'av3'),
(90912313, 'Unisimon', 'Kevin', '3133391213', 'kevin.sneyder.hernandez@gmail.com', 'av3');

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
-- Indices de la tabla `categoriaenty`
--
ALTER TABLE `categoriaenty`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `compraenty`
--
ALTER TABLE `compraenty`
  ADD PRIMARY KEY (`id_compra`),
  ADD KEY `id_proveedor` (`id_proveedor`),
  ADD KEY `fk_compra_usuario` (`id_usuario`);

--
-- Indices de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idproducto` (`idproducto`),
  ADD KEY `fk_detallecompra_compra` (`id_compra`);

--
-- Indices de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD PRIMARY KEY (`id_detalle_pedido`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `marcaenty`
--
ALTER TABLE `marcaenty`
  ADD PRIMARY KEY (`id`);

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
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `FK2` (`categoría`),
  ADD KEY `FK1` (`marca`);

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
-- AUTO_INCREMENT de la tabla `categoriaenty`
--
ALTER TABLE `categoriaenty`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `compraenty`
--
ALTER TABLE `compraenty`
  MODIFY `id_compra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT de la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  MODIFY `id_detalle_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `marcaenty`
--
ALTER TABLE `marcaenty`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `pedidoenty`
--
ALTER TABLE `pedidoenty`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT de la tabla `productoenty`
--
ALTER TABLE `productoenty`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuarioenty`
--
ALTER TABLE `usuarioenty`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compraenty`
--
ALTER TABLE `compraenty`
  ADD CONSTRAINT `compraenty_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedorenty` (`id_proveedor`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_compra_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarioenty` (`id_usuario`);

--
-- Filtros para la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD CONSTRAINT `detallecompra_ibfk_3` FOREIGN KEY (`idproducto`) REFERENCES `productoenty` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detallecompra_compra` FOREIGN KEY (`id_compra`) REFERENCES `compraenty` (`id_compra`);

--
-- Filtros para la tabla `detallepedido`
--
ALTER TABLE `detallepedido`
  ADD CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidoenty` (`id_pedido`),
  ADD CONSTRAINT `detallepedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productoenty` (`id_producto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pedidoenty`
--
ALTER TABLE `pedidoenty`
  ADD CONSTRAINT `pedidoenty_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarioenty` (`id_usuario`);

--
-- Filtros para la tabla `productoenty`
--
ALTER TABLE `productoenty`
  ADD CONSTRAINT `FK1` FOREIGN KEY (`marca`) REFERENCES `marcaenty` (`id`),
  ADD CONSTRAINT `FK2` FOREIGN KEY (`categoría`) REFERENCES `categoriaenty` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
