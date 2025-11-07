-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         11.6.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para bdsvtecno
CREATE DATABASE IF NOT EXISTS `bdsvtecno` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `bdsvtecno`;

-- Volcando estructura para tabla bdsvtecno.bancos
CREATE TABLE IF NOT EXISTS `bancos` (
  `cod_banco` int(5) NOT NULL AUTO_INCREMENT,
  `nombre_banco` varbinary(50) NOT NULL,
  `tipo_cuenta_banco` enum('CUENTA CORRIENTE','CAJA DE AHORRO') NOT NULL,
  `telefono_banco` varchar(20) DEFAULT NULL,
  `correo_banco` varchar(100) DEFAULT NULL,
  `direccion_banco` varchar(255) DEFAULT NULL,
  `usuario_insercion` varchar(50) NOT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  `estado_banco` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `uso_registro` enum('ACTIVO','INACTIVO') DEFAULT 'INACTIVO',
  PRIMARY KEY (`cod_banco`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.bancos: ~8 rows (aproximadamente)
REPLACE INTO `bancos` (`cod_banco`, `nombre_banco`, `tipo_cuenta_banco`, `telefono_banco`, `correo_banco`, `direccion_banco`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`, `estado_banco`, `uso_registro`) VALUES
	(1, _binary 0x42414e434f204e4143494f4e, 'CUENTA CORRIENTE', '0987001001', 'contacto@bancounion.com', 'Av. Libertador 345', 'admin', 'VLOPEZ', '2024-10-01 10:00:00', '2024-11-08 13:13:09', 'INACTIVO', 'ACTIVO'),
	(2, _binary 0x42414e434f2043454e5452414c, 'CAJA DE AHORRO', '0987001002', 'info@bancocentral.com', 'Calle Principal 456', 'admin', NULL, '2024-10-01 10:15:00', NULL, 'ACTIVO', 'INACTIVO'),
	(3, _binary 0x42414e434f204649524d45, 'CUENTA CORRIENTE', '0987001003', 'servicio@bancofirme.com', 'Av. Solano López 789', 'admin', 'VLOPEZ', '2024-10-01 10:30:00', '2025-01-02 22:53:04', 'ACTIVO', 'INACTIVO'),
	(4, _binary 0x42414e434f20534f4c, 'CAJA DE AHORRO', '0987001004', 'clientes@bancosol.com', 'Calle 7 de Agosto 123', 'admin', NULL, '2024-10-01 10:45:00', NULL, 'ACTIVO', 'INACTIVO'),
	(5, _binary 0x42414e434f2042415341, 'CUENTA CORRIENTE', '0987001005', 'info@bancoreal.com', 'Av. Colon 987', 'admin', '', '2024-10-01 11:00:00', '2024-10-28 21:20:46', 'INACTIVO', 'INACTIVO'),
	(6, _binary 0x42414e434f2050524f475245534f, 'CAJA DE AHORRO', '0987001006', 'atencion@bancoprogreso.com', 'Calle Patiño 246', 'admin', NULL, '2024-10-01 11:15:00', NULL, 'ACTIVO', 'INACTIVO'),
	(8, _binary 0x42414e434f204d455441, 'CAJA DE AHORRO', '0987001008', 'contacto@banco meta.com', 'Calle Herrera 123', 'admin', 'VLOPEZ', '2024-10-01 11:45:00', '2025-01-02 22:53:30', 'ACTIVO', 'INACTIVO'),
	(10, _binary 0x42414e434f204e4143, 'CUENTA CORRIENTE', '0987001010', 'contact@bancosiglo.com', 'Av. Santa Cruz 321', 'admin', 'VLOPEZ', '2024-10-01 12:15:00', '2025-01-02 22:53:37', 'ACTIVO', 'ACTIVO');

-- Volcando estructura para tabla bdsvtecno.cajas
CREATE TABLE IF NOT EXISTS `cajas` (
  `cod_caja` int(10) NOT NULL AUTO_INCREMENT,
  `cod_usuario` int(5) NOT NULL,
  `fecha_apertura_caja` datetime NOT NULL,
  `fecha_cierre_caja` datetime DEFAULT NULL,
  `usuario_reapertura` varchar(50) DEFAULT NULL,
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_caja`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.cajas: ~0 rows (aproximadamente)
REPLACE INTO `cajas` (`cod_caja`, `cod_usuario`, `fecha_apertura_caja`, `fecha_cierre_caja`, `usuario_reapertura`, `usuario_insercion`, `fecha_insercion`, `usuario_modificacion`, `fecha_modificacion`) VALUES
	(1, 3, '2025-01-19 17:37:21', '2025-01-27 09:12:23', 'VLOPEZ', 'INDEFINIDO', '2025-01-19 17:37:25', 'LOPEZZ', '2025-01-27 09:12:26');

-- Volcando estructura para tabla bdsvtecno.clasificacion
CREATE TABLE IF NOT EXISTS `clasificacion` (
  `cod_clasificacion` int(5) NOT NULL AUTO_INCREMENT,
  `nombre_clasificacion` varchar(50) NOT NULL,
  `estado_clasificacion` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `usuario_insercion` varbinary(50) DEFAULT NULL,
  `usuario_modificacion` varbinary(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_clasificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.clasificacion: ~10 rows (aproximadamente)
REPLACE INTO `clasificacion` (`cod_clasificacion`, `nombre_clasificacion`, `estado_clasificacion`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`) VALUES
	(1, 'ELECTRODOMESTICOS', 'ACTIVO', _binary 0x61646d696e, _binary 0x6e756c6c, '2024-10-01 09:00:00', '2025-01-19 19:24:09'),
	(2, 'INFORMATICA', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 09:15:00', NULL),
	(3, 'TELEFONIA', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 09:30:00', NULL),
	(4, 'AUDIO Y VIDEO', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 09:45:00', NULL),
	(5, 'COCINA', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 10:00:00', NULL),
	(6, 'CLIMATIZACION', 'INACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 10:15:00', NULL),
	(7, 'FOTOGRAFIA', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 10:30:00', NULL),
	(8, 'HOGAR', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 10:45:00', NULL),
	(9, 'MOVILIDAD', 'ACTIVO', _binary 0x61646d696e, NULL, '2024-10-01 11:00:00', NULL),
	(10, 'GADGATSLÑLL', 'ACTIVO', _binary 0x61646d696e, _binary 0x6e756c6c, '2024-10-01 11:15:00', '2025-01-19 19:22:27');

-- Volcando estructura para tabla bdsvtecno.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `cod_cliente` int(5) NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(50) NOT NULL,
  `apellido_cliente` varchar(50) DEFAULT NULL,
  `ruc_cliente` varchar(20) NOT NULL,
  `sexo_cliente` enum('INDEFINIDO','MASCULINO','FEMENINO') DEFAULT NULL,
  `fecha_nacimiento_cliente` date DEFAULT NULL,
  `direccion_cliente` varbinary(255) DEFAULT NULL,
  `telefono_cliente` varbinary(20) DEFAULT NULL,
  `estado_cliente` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `uso_cliente` enum('ACTIVO','INACTIVO') DEFAULT 'INACTIVO',
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.clientes: ~10 rows (aproximadamente)
REPLACE INTO `clientes` (`cod_cliente`, `nombre_cliente`, `apellido_cliente`, `ruc_cliente`, `sexo_cliente`, `fecha_nacimiento_cliente`, `direccion_cliente`, `telefono_cliente`, `estado_cliente`, `uso_cliente`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`) VALUES
	(1, 'VICTOR MANUEL', 'JARA LOPEZ', '6741475', 'MASCULINO', '2001-07-24', _binary 0x434f4e43455043494f4e, _binary 0x30393731373732373534, 'ACTIVO', 'INACTIVO', 'admin', 'INDEFINIDO', '2024-10-01 08:30:00', '2024-11-20 07:46:40'),
	(2, 'ANA', 'GOMEZ', '98765432-1', 'FEMENINO', '1990-05-10', _binary 0x43616c6c6520536563756e646172696120343536, _binary 0x30393833303032303032, 'ACTIVO', 'INACTIVO', 'admin', 'INDEFINIDO', '2024-10-01 08:40:00', '2024-11-20 07:46:49'),
	(3, 'LUIS', 'RODRIGUEZ', '87654321-2', 'MASCULINO', '1987-12-20', _binary 0x43616c6c65205465726365726120373839, _binary 0x30393833303033303033, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 08:50:00', NULL),
	(4, 'MARIA', 'SOTO', '76543210-3', 'FEMENINO', '1992-04-15', _binary 0x43616c6c652043756172746120313031, _binary 0x30393833303034303034, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:00:00', NULL),
	(5, 'CARLOS', 'FERNANDEZ', '65432109-4', 'MASCULINO', '1989-07-25', _binary 0x43616c6c65205175696e746120323032, _binary 0x30393833303035303035, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:10:00', NULL),
	(6, 'LAURA', 'RUIZ', '54321098-5', 'FEMENINO', '1988-03-30', _binary 0x41762e20536578746120333033, _binary 0x30393833303036303036, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:20:00', NULL),
	(7, 'PEDRO', 'MARTINEZ', '43210987-6', 'MASCULINO', '1991-09-05', _binary 0x43616c6c652053c3a97074696d6120343034, _binary 0x30393833303037303037, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:30:00', NULL),
	(8, 'ANDREA', 'ROJAS', '32109876-7', 'FEMENINO', '1986-11-10', _binary 0x41762e204f637461766120353035, _binary 0x30393833303038303038, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:40:00', NULL),
	(9, 'MIGUEL', 'DIAZ', '21098765-8', 'MASCULINO', '1993-02-20', _binary 0x43616c6c65204e6f76656e6120363036, _binary 0x30393833303039303039, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:50:00', NULL),
	(10, 'CARLOS JAVIER', 'romero', '80078946-8', 'MASCULINO', '2024-11-22', _binary 0x464753444647, _binary 0x353935323331313634, 'ACTIVO', 'INACTIVO', 'null', 'VLOPEZ', '2024-11-04 07:43:09', '2025-01-19 19:31:12');

-- Volcando estructura para tabla bdsvtecno.compras_cabecera
CREATE TABLE IF NOT EXISTS `compras_cabecera` (
  `cod_compra_cabecera` int(10) NOT NULL AUTO_INCREMENT,
  `tipo_movimiento` enum('COMPRA CONTADO','COMPRA CREDITO','PAGO CUOTA') NOT NULL,
  `cod_proveedor` int(5) NOT NULL,
  `nom_proveedor` varchar(50) NOT NULL,
  `ruc_proveedor` varchar(15) NOT NULL,
  `fecha_comprobante` date NOT NULL,
  `timbrado_compra` varchar(10) DEFAULT NULL,
  `num_comprobante_compra` varchar(15) DEFAULT NULL,
  `estado_compra` enum('ACTIVO','INACTIVO') NOT NULL,
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_compra_cabecera`),
  KEY `FK_compras_proveedor` (`cod_proveedor`),
  CONSTRAINT `FK_compras_proveedor` FOREIGN KEY (`cod_proveedor`) REFERENCES `proveedores` (`cod_proveedor`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.compras_cabecera: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.compras_detalle
CREATE TABLE IF NOT EXISTS `compras_detalle` (
  `cod_compra_detalle` int(10) NOT NULL AUTO_INCREMENT,
  `cod_compra_cabecera` int(10) NOT NULL,
  `cod_producto` int(5) DEFAULT NULL,
  `cant_producto` float DEFAULT NULL,
  `prec_producto` float DEFAULT NULL,
  `iva_producto` enum('EXENTA','5%','10%') DEFAULT NULL,
  PRIMARY KEY (`cod_compra_detalle`),
  KEY `FK_detalle_cabecera_compras` (`cod_compra_cabecera`),
  KEY `FK_compras_productos` (`cod_producto`),
  CONSTRAINT `FK_compras_productos` FOREIGN KEY (`cod_producto`) REFERENCES `productos` (`cod_producto`) ON UPDATE CASCADE,
  CONSTRAINT `FK_detalle_cabecera_compras` FOREIGN KEY (`cod_compra_cabecera`) REFERENCES `compras_cabecera` (`cod_compra_cabecera`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.compras_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.credito_cliente
CREATE TABLE IF NOT EXISTS `credito_cliente` (
  `cod_credito_cliente` int(5) NOT NULL AUTO_INCREMENT,
  `cod_venta` int(10) NOT NULL,
  `numero_cuotas_credito` int(3) NOT NULL,
  `cuotas_pagadas_credito` int(3) NOT NULL,
  `plazo_cuotas_credito` enum('SEMANAL','QUINCENAL','MENSUAL','TRIMESTRAL','SEMESTRAL','ANUAL') NOT NULL,
  `fecha_inicio_credito` date NOT NULL,
  `fecha_vencimiento_credito` date NOT NULL,
  `saldo_restante_credito` float NOT NULL,
  `estado_credito` enum('ACTIVO','INACTIVO') NOT NULL,
  PRIMARY KEY (`cod_credito_cliente`),
  KEY `FK_credito_venta` (`cod_venta`),
  CONSTRAINT `FK_credito_venta` FOREIGN KEY (`cod_venta`) REFERENCES `ventas_cabecera` (`cod_venta_cabecera`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.credito_cliente: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.credito_proveedor
CREATE TABLE IF NOT EXISTS `credito_proveedor` (
  `cod_credito_proveedor` int(10) NOT NULL AUTO_INCREMENT,
  `cod_compra` int(10) NOT NULL,
  `fecha_inicio_credito` date NOT NULL,
  `fecha_vencimiento_credito` date NOT NULL,
  `num_cuotas_credito` int(3) NOT NULL,
  `plazo_credito` enum('semanal','quincenal','mensual','trimestral','semestral','anual') NOT NULL,
  `saldo_restante_credito` float NOT NULL,
  `estado_credito` enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`cod_credito_proveedor`),
  KEY `FK_credito_compra` (`cod_compra`),
  CONSTRAINT `FK_credito_compra` FOREIGN KEY (`cod_compra`) REFERENCES `compras_cabecera` (`cod_compra_cabecera`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.credito_proveedor: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.cuotas_credito_cliente
CREATE TABLE IF NOT EXISTS `cuotas_credito_cliente` (
  `cod_cuotas_credito` int(10) NOT NULL,
  `cod_credito_cliente` int(10) NOT NULL,
  `numero_cuota` int(3) NOT NULL,
  `monto_cuota` float NOT NULL,
  `fecha_vencimiento_cuota` date NOT NULL,
  `estado_cuota` enum('ACTIVO','INACTIVO') NOT NULL,
  `fecha_pago_cuota` datetime DEFAULT NULL,
  `num_movimiento` int(10) DEFAULT NULL,
  PRIMARY KEY (`cod_cuotas_credito`),
  KEY `FK_cuota_credito_cliente` (`cod_credito_cliente`),
  CONSTRAINT `FK_cuota_credito_cliente` FOREIGN KEY (`cod_credito_cliente`) REFERENCES `credito_cliente` (`cod_credito_cliente`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.cuotas_credito_cliente: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.cuotas_credito_proveedor
CREATE TABLE IF NOT EXISTS `cuotas_credito_proveedor` (
  `cod_cuota_credito_proveedor` int(10) NOT NULL,
  `cod_credito_proveedor` int(10) NOT NULL,
  `numero_cuota` int(3) NOT NULL,
  `monto_cuota` float NOT NULL,
  `fecha_vencimiento_cuota` date NOT NULL,
  `estado_cuota` enum('ACTIVO','INACTIVO') NOT NULL,
  `fecha_pago_cuota` datetime DEFAULT NULL,
  `num_movimiento` int(10) DEFAULT NULL,
  PRIMARY KEY (`cod_cuota_credito_proveedor`),
  KEY `FK_credito_cuota_proveedor` (`cod_credito_proveedor`),
  CONSTRAINT `FK_credito_cuota_proveedor` FOREIGN KEY (`cod_credito_proveedor`) REFERENCES `credito_proveedor` (`cod_credito_proveedor`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.cuotas_credito_proveedor: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.detalle_egreso_venta
CREATE TABLE IF NOT EXISTS `detalle_egreso_venta` (
  `cod_detalle_egreso` int(10) NOT NULL,
  `cod_venta_cabecera` int(5) DEFAULT NULL,
  `concepto_egreso` varchar(255) DEFAULT NULL,
  `monto_egreso` float DEFAULT NULL,
  PRIMARY KEY (`cod_detalle_egreso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.detalle_egreso_venta: ~4 rows (aproximadamente)
REPLACE INTO `detalle_egreso_venta` (`cod_detalle_egreso`, `cod_venta_cabecera`, `concepto_egreso`, `monto_egreso`) VALUES
	(1, 10, 'DEVOLUCION EN EFECTIVO', 10000),
	(2, 10, 'DEVOLUCION EN EFECTIVO', 10000),
	(3, 2, 'PAGOS POR SERVICIOS', 1000000),
	(4, 3, 'GASTOS VARIOS', 500000);

-- Volcando estructura para tabla bdsvtecno.lineas_credito
CREATE TABLE IF NOT EXISTS `lineas_credito` (
  `cod_linea_credito` int(11) NOT NULL AUTO_INCREMENT,
  `cod_cliente` int(11) DEFAULT NULL,
  `monto_total` decimal(10,2) DEFAULT NULL,
  `saldo_disponible` decimal(10,2) DEFAULT NULL,
  `fecha_asignacion` date DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `estado_linea_credito` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  PRIMARY KEY (`cod_linea_credito`),
  KEY `cod_cliente` (`cod_cliente`),
  CONSTRAINT `lineas_credito_ibfk_1` FOREIGN KEY (`cod_cliente`) REFERENCES `clientes` (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.lineas_credito: ~2 rows (aproximadamente)
REPLACE INTO `lineas_credito` (`cod_linea_credito`, `cod_cliente`, `monto_total`, `saldo_disponible`, `fecha_asignacion`, `fecha_vencimiento`, `estado_linea_credito`) VALUES
	(1, 1, 2000000.00, 2000000.00, '2025-01-15', '2025-12-31', 'ACTIVO'),
	(2, 10, 10000000.00, 10000000.00, '2025-01-19', '2025-12-31', 'ACTIVO');

-- Volcando estructura para tabla bdsvtecno.marcas
CREATE TABLE IF NOT EXISTS `marcas` (
  `cod_marca` int(5) NOT NULL AUTO_INCREMENT,
  `nombre_marca` varbinary(50) NOT NULL,
  `estado_marca` enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  `uso_marca` enum('ACTIVO','INACTIVO') DEFAULT NULL,
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_marca`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.marcas: ~12 rows (aproximadamente)
REPLACE INTO `marcas` (`cod_marca`, `nombre_marca`, `estado_marca`, `uso_marca`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`) VALUES
	(1, _binary 0x53414d53554e4764, 'ACTIVO', 'INACTIVO', 'admin', 'INDEFINIDO', '2024-10-26 08:11:41', '2024-11-19 08:16:41'),
	(2, _binary 0x4c47, 'ACTIVO', 'ACTIVO', 'admin', 'VLOPEZ', '2024-10-26 08:11:41', '2025-01-22 16:44:02'),
	(3, _binary 0x534f4e59, 'ACTIVO', 'ACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(4, _binary 0x50414e41534f4e59, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(6, _binary 0x4f53544552, 'INACTIVO', 'ACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(7, _binary 0x424c41434b2059204445434b45, 'ACTIVO', 'INACTIVO', 'admin', 'admin', '2024-10-26 08:11:41', '2024-10-28 11:12:02'),
	(8, _binary 0x4d4f554c494e4558, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(9, _binary 0x48414d494c54484f4e204245414348, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(10, _binary 0x456c656374726f6c7578, 'ACTIVO', 'ACTIVO', 'admin', NULL, '2024-10-26 08:11:41', NULL),
	(13, _binary 0x454c454354524f53544152, 'ACTIVO', 'ACTIVO', 'INDEFINIDO', 'VLOPEZ', '2024-11-18 17:17:54', '2025-01-19 19:09:52'),
	(14, _binary 0x4b4f4c4b45, 'ACTIVO', 'ACTIVO', 'INDEFINIDO', 'VLOPEZ', '2024-11-18 17:18:08', '2025-01-19 19:10:04'),
	(18, _binary 0x5048494c4946, 'ACTIVO', NULL, 'VLOPEZ', 'VLOPEZ', '2025-01-19 19:08:18', '2025-01-19 19:08:26');

-- Volcando estructura para tabla bdsvtecno.metodo_pago_venta
CREATE TABLE IF NOT EXISTS `metodo_pago_venta` (
  `cod_metodo_pago_venta` int(5) NOT NULL,
  `cod_venta` int(10) NOT NULL,
  `metodo_pago_venta` enum('EFECTIVO','TARJETA DEBITO','TARJETA CREDITO','PAGO QR','TRANSFERENCIA','CHEQUE') NOT NULL,
  `monto_venta` float NOT NULL,
  `cod_banco` int(5) DEFAULT NULL,
  PRIMARY KEY (`cod_metodo_pago_venta`),
  KEY `FR_metodo_pago_venta` (`cod_venta`),
  KEY `FK_metodo_pago_banco` (`cod_banco`),
  CONSTRAINT `FK_metodo_pago_banco` FOREIGN KEY (`cod_banco`) REFERENCES `bancos` (`cod_banco`) ON UPDATE CASCADE,
  CONSTRAINT `FR_metodo_pago_venta` FOREIGN KEY (`cod_venta`) REFERENCES `ventas_cabecera` (`cod_venta_cabecera`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.metodo_pago_venta: ~0 rows (aproximadamente)

-- Volcando estructura para procedimiento bdsvtecno.obtenerDetallesVenta
DELIMITER //
CREATE PROCEDURE `obtenerDetallesVenta`(IN cod_venta VARCHAR(255))
BEGIN
IF (SELECT if(vent.tipo_movimiento ='INGRESO','1','2') AS tipo FROM ventas_cabecera vent WHERE vent.cod_venta_cabecera = cod_venta) = '1' THEN
IF (SELECT if(vent.concepto_movimiento ='PAGO CUOTA','1','2') AS tipo FROM ventas_cabecera vent WHERE vent.cod_venta_cabecera = cod_venta) = '1' THEN
SELECT
ccc.cod_cuotas_credito as codigo,
CONCAT(cl.nombre_cliente,', ',cl.apellido_cliente) AS concepto,
'1' AS cantidad,
ccc.monto_cuota as precio,
'' AS EXENTAS,
'' AS "5%",
ccc.monto_cuota AS "10%"
FROM
cuotas_credito_cliente ccc
JOIN credito_cliente cc ON ccc.cod_credito_cliente=cc.cod_credito_cliente
JOIN ventas_cabecera vc ON cc.cod_venta=vc.cod_venta_cabecera
JOIN clientes cl ON vc.cod_cliente=cl.cod_cliente
WHERE
ccc.num_movimiento = cod_venta;
ELSE
SELECT
vd.cod_producto AS codigo,
p.nombre_producto as concepto,
vd.cantidad_producto AS cantidad,
vd.precio_producto as precio,
IF(vd.iva_producto='EXENTA', (vd.cantidad_producto * vd.precio_producto), '') AS EXENTAS,
IF(vd.iva_producto='5%', (vd.cantidad_producto * vd.precio_producto), '') AS "5%",
IF(vd.iva_producto='10%', (vd.cantidad_producto * vd.precio_producto), '') AS "10%"
FROM
ventas_detalle vd
JOIN productos p ON vd.cod_producto = p.cod_producto
WHERE
cod_venta_cabecera = cod_venta;
END IF;
ELSE
SET @contador = 0;
SELECT 
    @contador := @contador + 1 AS codigo,
    concepto_egreso AS concepto,
    '1' AS cantidad,
    monto_egreso AS precio,
    '' AS EXENTAS,
    '' AS '5%',
    concat('-',monto_egreso) AS '10%'
FROM 
    detalle_egreso_venta
WHERE 
    cod_venta_cabecera = cod_venta;
END IF;
END//
DELIMITER ;

-- Volcando estructura para tabla bdsvtecno.presupuestos
CREATE TABLE IF NOT EXISTS `presupuestos` (
  `cod_presupuesto` int(10) NOT NULL,
  `cod_cliente` int(5) NOT NULL,
  `fecha_presupuesto` date NOT NULL,
  `estado_presupuesto` enum('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  `num_facturacion` int(5) DEFAULT 0,
  `usuario_insercion` varchar(50) NOT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_presupuesto`,`cod_cliente`,`fecha_presupuesto`,`estado_presupuesto`,`usuario_insercion`,`fecha_insercion`),
  KEY `FK_presupuesto_cliente` (`cod_cliente`),
  CONSTRAINT `FK_presupuesto_cliente` FOREIGN KEY (`cod_cliente`) REFERENCES `clientes` (`cod_cliente`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.presupuestos: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.presupuesto_detalle
CREATE TABLE IF NOT EXISTS `presupuesto_detalle` (
  `cod_presupuesto_detalle` int(10) NOT NULL AUTO_INCREMENT,
  `cod_presupuesto_cabecera` int(10) NOT NULL,
  `cod_producto` int(5) NOT NULL,
  `precio_producto` float NOT NULL,
  `cantidad_producto` float NOT NULL,
  `iva_producto` enum('5%','10%','EXENTA') DEFAULT NULL,
  PRIMARY KEY (`cod_presupuesto_detalle`),
  KEY `FK_presupuesto` (`cod_presupuesto_cabecera`),
  KEY `FK_presupuesto_producto` (`cod_producto`),
  CONSTRAINT `FK_presupuesto` FOREIGN KEY (`cod_presupuesto_cabecera`) REFERENCES `presupuestos` (`cod_presupuesto`) ON UPDATE CASCADE,
  CONSTRAINT `FK_presupuesto_producto` FOREIGN KEY (`cod_producto`) REFERENCES `productos` (`cod_producto`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.presupuesto_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `cod_producto` int(5) NOT NULL,
  `cod_barras` varbinary(50) NOT NULL,
  `nombre_producto` varbinary(50) NOT NULL,
  `img1_producto` mediumblob DEFAULT NULL,
  `img2_producto` mediumblob DEFAULT NULL,
  `cod_clasificacion` int(5) DEFAULT NULL,
  `cod_marca` int(5) DEFAULT NULL,
  `precio_producto` decimal(10,2) DEFAULT NULL,
  `existencia_producto` decimal(10,2) DEFAULT 0.00,
  `iva_producto` enum('EXENTAS','5%','10%') DEFAULT '10%',
  `estado_producto` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `coment_producto` varchar(255) DEFAULT NULL,
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_producto`),
  KEY `FK_producto_clasificacion` (`cod_clasificacion`),
  KEY `FK_producto_marca` (`cod_marca`),
  CONSTRAINT `FK_producto_clasificacion` FOREIGN KEY (`cod_clasificacion`) REFERENCES `clasificacion` (`cod_clasificacion`) ON UPDATE CASCADE,
  CONSTRAINT `FK_producto_marca` FOREIGN KEY (`cod_marca`) REFERENCES `marcas` (`cod_marca`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.productos: ~4 rows (aproximadamente)
REPLACE INTO `productos` (`cod_producto`, `cod_barras`, `nombre_producto`, `img1_producto`, `img2_producto`, `cod_clasificacion`, `cod_marca`, `precio_producto`, `existencia_producto`, `iva_producto`, `estado_producto`, `coment_producto`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`) VALUES
	(1, _binary 0x303030303031, _binary 0x4c41564152524f504153204155544f4d4154494341, _binary 0x89504e470d0a1a0a0000000d494844520000002000000020080300000044a48ac6000000d8504c544547704c6bb80076c3086ab70062ae0062ae006cb90071be0275c2078ad24e73c00477c40c74c10677c30a72bf0375c20779c71178c50f69b60070be027ac7137bc81668b50071be027cc9197dc91c7eca207fcb2367b4006fbd0180cb2767b3006fbc0181cc2b66b2006ebb006dba0082cd2e65b200f8ffedf7ffea83cd3265b100f9fff0f5ffe884ce3664b000fafff3f4ffe585cf3a63b000f3ffe2fbfff586cf3d63af00f2ffe0fcfff887d041f1ffddfdfffa87d144f0ffdbfefffc88d148efffd9fefffe89d24befffd7ffffffc9eb958ad24ed8efbbf4a430a60000000a74524e5300ffffff7fffff7f7f7f57370065000000f04944415438cbad93c776c2301405a9a187127a091d02a1b7849240a8ffff47982bcb57e6606fc26c67644b7a470ec75308ecb79be57cdcef34abef857c2e9b4ac6e2a188cb2ffdf9b18f8683b6eb35ffea4360ed336f086c7c0901fde57834fb0a02aeffd330f93a027efff786ea1b08f8ff3550fc0b02eeef5b40ef44c0fdcf740cef46c0f30d25d22710f0fc9f06ba4f23e0fdb489f04504bcbf0f227c1901efb767a0fb1a02deff48227d0b01e733d5317c1701e7f325a01f20e0fc5640f113049cdfcf0dd52f1070be3b0d93f722e07c4f8783d98b409dff9d17818d1781dfda7bc4c3b0f45ef9b47cb6ebffcb154dd05599d40f04180000000049454e44ae426082, _binary 0x89504e470d0a1a0a0000000d494844520000002000000020080300000044a48ac6000000017352474200aece1ce90000000467414d410000b18f0bfc6105000000d8504c544547704c6bb80076c3086ab70062ae0062ae006cb90071be0275c2078ad24e73c00477c40c74c10677c30a72bf0375c20779c71178c50f69b60070be027ac7137bc81668b50071be027cc9197dc91c7eca207fcb2367b4006fbd0180cb2767b3006fbc0181cc2b66b2006ebb006dba0082cd2e65b200f8ffedf7ffea83cd3265b100f9fff0f5ffe884ce3664b000fafff3f4ffe585cf3a63b000f3ffe2fbfff586cf3d63af00f2ffe0fcfff887d041f1ffddfdfffa87d144f0ffdbfefffc88d148efffd9fefffe89d24befffd7ffffffc9eb958ad24ed8efbbf4a430a60000000a74524e5300ffffff7fffff7f7f7f57370065000000097048597300000ec300000ec301c76fa864000000f349444154384fadd1d75642311484e1082a28bd09824815b0172c74a528efff46e64c9239c95a861b99dbefdf2b1711fbd9e191dcfbcbc34dafddb838cb470f8ebb9d66bd10d32c02fec3cbe953e59e7be9c93802bf672226f0794a078eafd7dff4840a1cff92a3e710383e0f46cf22b07d82d18b082cff50a39710843ed4a35710d09fcce8e7088cdf71f42a02ed8370f41a02e5dd7e38fa2502e59de62d476f21d05e2f3c9ad1af10182fa75ff5e8d708acff1ba9d1ef11d8ff3bc5e8cf082c4f2516c1e86f086ccf659772f43102c78ba5cd664b9f21703d7c5ffa27821dbe4210f3fb0902e1f51fe542c477deff6f42fc02e21055992f86cfa30000000049454e44ae426082, 1, 1, 10000000.00, 0.00, '10%', 'ACTIVO', 'CARGADO EL \nSDFS\n\n\n\n\n\nSSDDDD', 'null', 'null', '2025-02-05 07:22:04', '2025-02-05 10:56:12'),
	(2, _binary 0x303030303032, _binary 0x544f535441444f5241, NULL, NULL, 1, 1, 5000000.00, 0.00, '10%', 'ACTIVO', 'hou', 'null', 'null', '2025-02-05 07:26:42', '2025-02-05 08:33:59'),
	(3, _binary 0x303030303033, _binary 0x4445434f444946494341444f5220, NULL, NULL, 2, 1, 30000.00, 0.00, '10%', 'ACTIVO', 'ssssss', 'null', NULL, '2025-02-05 07:27:06', NULL),
	(4, _binary 0x303030303034, _binary 0x434f4d50555441444f524120, NULL, NULL, 1, 1, 10000.00, 0.00, '10%', 'ACTIVO', '30\n10\n10\n10\n10\n10\n1\n010\n', 'null', 'null', '2025-02-05 07:27:40', '2025-02-05 08:33:37');

-- Volcando estructura para tabla bdsvtecno.proveedores
CREATE TABLE IF NOT EXISTS `proveedores` (
  `cod_proveedor` int(5) NOT NULL AUTO_INCREMENT,
  `ruc_proveedor` varchar(15) NOT NULL,
  `nombre_proveedor` varchar(50) NOT NULL,
  `telefono_proveedor` varchar(20) DEFAULT NULL,
  `correo_proveedor` varchar(100) DEFAULT NULL,
  `direccion_proveedor` varbinary(255) DEFAULT NULL,
  `estado_proveedor` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `uso_proveedor` enum('ACTIVO','INACTIVO') DEFAULT 'INACTIVO',
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.proveedores: ~11 rows (aproximadamente)
REPLACE INTO `proveedores` (`cod_proveedor`, `ruc_proveedor`, `nombre_proveedor`, `telefono_proveedor`, `correo_proveedor`, `direccion_proveedor`, `estado_proveedor`, `uso_proveedor`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`) VALUES
	(1, '12345678-0', 'ELECTRO MAYOR', '0987001111', 'contacto@electromayor.com', _binary 0x41762e2043656e7472616c20313233, 'ACTIVO', 'INACTIVO', 'admin', 'INDEFINIDO', '2024-10-01 09:00:00', '2024-11-21 09:59:33'),
	(2, '23456789-1', 'DISTRIBUIDORA GOMEZ', '0987002222', 'ventas@distribuidoragomez.com', _binary 0x43616c6c6520536563756e646172696120343536, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:15:00', NULL),
	(3, '34567890-2', 'TECNO SHOP', '0987003333', 'info@tecnoshop.com', _binary 0x41762e20546572636572204d696c656e696f20373839, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:30:00', NULL),
	(4, '45678901-3', 'CASA ELECTRO', '0987004444', 'soporte@casaelectro.com', _binary 0x43616c6c652043756172746120313031, 'INACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 09:45:00', NULL),
	(5, '56789012-4', 'SURTIDOR TOTAL', '0987005555', 'contacto@surtidortotal.com', _binary 0x41762e205175696e746120323032, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 10:00:00', NULL),
	(6, '67890123-5', 'DISTRIBUICIONES MODERNAS', '0987006666', 'info@distribucionesmoderna.com', _binary 0x43616c6c6520536578746120333033, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 10:15:00', NULL),
	(7, '78901234-6', 'TECNOLOGIA AVANZADA', '0987007777', 'atencion@tecnologiaavanzada.com', _binary 0x41762e2053c3a97074696d6120343034, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 10:30:00', NULL),
	(8, '89012345-7', 'ELECTRO HOGAR', '0987008888', 'clientes@electrohogar.com', _binary 0x43616c6c65204f637461766120353035, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 10:45:00', NULL),
	(9, '90123456-8', 'DISTRIBUIDORA GLOBAL', '0987009999', 'contact@distribuidoraglobal.com', _binary 0x41762e204e6f76656e6120363036, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 11:00:00', NULL),
	(10, '01234567-9', 'ELECTRO MUNDO', '0987010000', 'servicio@electromundo.com', _binary 0x43616c6c652044c3a963696d6120373037, 'ACTIVO', 'INACTIVO', 'admin', NULL, '2024-10-01 11:15:00', NULL),
	(11, '80078946-6', 'GRUPO SANTA ISABEL Y CIA SA', '0972656272', 'SANTAISABEL100@GMAIL.COM', _binary 0x5341524d49454e544f205920434150204c455a43414e4f, 'ACTIVO', 'INACTIVO', 'INDEFINIDO', 'INDEFINIDO', '2024-11-21 07:55:52', '2025-01-19 19:38:33');

-- Volcando estructura para tabla bdsvtecno.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `cod_usuario` int(5) NOT NULL AUTO_INCREMENT,
  `nom_usuario` varchar(20) NOT NULL,
  `cont_usuario` varbinary(255) NOT NULL,
  `tel_usuario` varchar(15) DEFAULT NULL,
  `correo_usuario` varchar(50) DEFAULT NULL,
  `rol_usuario` enum('ADMIN','SECRETARIO','ENCARGADO STOCK','CAJERO') DEFAULT NULL,
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  `estado_usuario` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `uso_registro` enum('ACTIVO','INACTIVO') DEFAULT 'INACTIVO',
  PRIMARY KEY (`cod_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.usuarios: ~7 rows (aproximadamente)
REPLACE INTO `usuarios` (`cod_usuario`, `nom_usuario`, `cont_usuario`, `tel_usuario`, `correo_usuario`, `rol_usuario`, `usuario_insercion`, `usuario_modificacion`, `fecha_insercion`, `fecha_modificacion`, `estado_usuario`, `uso_registro`) VALUES
	(1, 'VLOPEZ', _binary 0xefbfbdefbfbd78efbfbdefbfbdefbfbdefbfbd6a61131512efbfbd314914, '0971772754', 'VICTORMANUELJARALOPEZ@GMAIL.COM', 'ADMIN', 'VLOPEZ', 'indefinido', '2024-11-24 11:39:53', '2025-01-19 15:56:45', 'ACTIVO', 'INACTIVO'),
	(2, 'VLOP', _binary 0x203439543f207e7d721a205d207ad8bd, '0971772754', 'DFASDF', 'SECRETARIO', 'VLOPEZ', 'indefinido', '2024-12-27 11:07:35', '2025-01-02 14:12:30', 'ACTIVO', 'INACTIVO'),
	(3, 'LOPEZZ', _binary 0x92b07886be97a76a61131512b2314914, '23453', '', 'CAJERO', 'VLOPEZ', NULL, '2025-01-02 19:07:52', NULL, 'ACTIVO', 'INACTIVO'),
	(4, 'SDFASD', _binary 0xefbfbd4fefbfbdefbfbdefbfbd1b6cefbfbdefbfbdefbfbdefbfbd411befbfbd3d2c, '', '', 'ADMIN', 'indefinido', NULL, '2025-01-21 19:25:38', NULL, 'INACTIVO', 'INACTIVO'),
	(5, 'DSDGSSS', _binary 0xefbfbd4fefbfbdefbfbdefbfbd1b6cefbfbdefbfbdefbfbdefbfbd411befbfbd3d2c, '', '', 'SECRETARIO', 'indefinido', NULL, '2025-01-21 19:26:27', NULL, 'INACTIVO', 'INACTIVO'),
	(6, '<ZC', _binary 0xefbfbd4fefbfbdefbfbdefbfbd1b6cefbfbdefbfbdefbfbdefbfbd411befbfbd3d2c, '', '', 'CAJERO', 'indefinido', NULL, '2025-01-21 19:26:37', NULL, 'INACTIVO', 'INACTIVO'),
	(7, 'EWWW', _binary 0xefbfbd4fefbfbdefbfbdefbfbd1b6cefbfbdefbfbdefbfbdefbfbd411befbfbd3d2c, '', '', 'ENCARGADO STOCK', 'indefinido', NULL, '2025-01-21 19:26:45', NULL, 'INACTIVO', 'INACTIVO');

-- Volcando estructura para tabla bdsvtecno.ventas_cabecera
CREATE TABLE IF NOT EXISTS `ventas_cabecera` (
  `cod_venta_cabecera` int(10) NOT NULL AUTO_INCREMENT,
  `tipo_movimiento` enum('INGRESO','EGRESO') NOT NULL,
  `concepto_movimiento` enum('VENTA CONTADO','VENTA CREDITO','PAGO CUOTA') DEFAULT NULL,
  `cod_caja` int(10) NOT NULL,
  `cod_cliente` int(5) NOT NULL,
  `ruc_cliente` varchar(15) NOT NULL,
  `razon_social_cliente` varchar(50) NOT NULL,
  `fecha_venta` date NOT NULL,
  `timbrado_venta` int(10) DEFAULT NULL,
  `num_comprobante_venta` varchar(20) DEFAULT NULL,
  `estado_venta` enum('ACTIVO','INACTIVO') NOT NULL,
  `anulacion_fisica` enum('ACTIVO','INACTIVO') DEFAULT 'INACTIVO',
  `usuario_insercion` varchar(50) DEFAULT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_venta_cabecera`),
  KEY `FK_venta_cliente` (`cod_cliente`),
  KEY `FK_ventas_caja` (`cod_caja`),
  CONSTRAINT `FK_venta_cliente` FOREIGN KEY (`cod_cliente`) REFERENCES `clientes` (`cod_cliente`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ventas_caja` FOREIGN KEY (`cod_caja`) REFERENCES `cajas` (`cod_caja`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.ventas_cabecera: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bdsvtecno.ventas_detalle
CREATE TABLE IF NOT EXISTS `ventas_detalle` (
  `cod_venta_detalle` int(10) NOT NULL,
  `cod_venta_cabecera` int(10) NOT NULL,
  `cod_producto` int(5) DEFAULT NULL,
  `precio_producto` float DEFAULT NULL,
  `cantidad_producto` float DEFAULT NULL,
  `iva_producto` enum('5%','10%','EXENTA') DEFAULT NULL,
  PRIMARY KEY (`cod_venta_detalle`),
  KEY `FK_detalle_cabecera_venta` (`cod_venta_cabecera`),
  KEY `FK_venta_producto` (`cod_producto`),
  CONSTRAINT `FK_detalle_cabecera_venta` FOREIGN KEY (`cod_venta_cabecera`) REFERENCES `ventas_cabecera` (`cod_venta_cabecera`) ON UPDATE CASCADE,
  CONSTRAINT `FK_venta_producto` FOREIGN KEY (`cod_producto`) REFERENCES `productos` (`cod_producto`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bdsvtecno.ventas_detalle: ~0 rows (aproximadamente)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
