CREATE TABLE IF NOT EXISTS `adelantos` (
  `id_adelanto` smallint(6) NOT NULL AUTO_INCREMENT,
  `id_personal` smallint(6) NOT NULL,
  `montoAdelanto` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  PRIMARY KEY (`id_adelanto`),
  KEY `id_personal` (`id_personal`),
  CONSTRAINT `adelantos_ibfk_1` FOREIGN KEY (`id_personal`) REFERENCES `personal` (`id_personal`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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

CREATE TABLE IF NOT EXISTS `detalle_egreso_venta` (
  `cod_detalle_egreso` int(10) NOT NULL,
  `cod_venta_cabecera` int(5) DEFAULT NULL,
  `concepto_egreso` varchar(255) DEFAULT NULL,
  `monto_egreso` float DEFAULT NULL,
  PRIMARY KEY (`cod_detalle_egreso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE IF NOT EXISTS `presupuesto` (
  `cod_presupuesto` int(10) NOT NULL,
  `cod_cliente` int(5) NOT NULL,
  `fecha_presupuesto` date NOT NULL,
  `estado_presupuesto` enum('pendiente','finalizada') NOT NULL,
  `usuario_insercion` varchar(50) NOT NULL,
  `usuario_modificacion` varchar(50) DEFAULT NULL,
  `fecha_insercion` datetime NOT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`cod_presupuesto`,`cod_cliente`,`fecha_presupuesto`,`estado_presupuesto`,`usuario_insercion`,`fecha_insercion`),
  KEY `FK_presupuesto_cliente` (`cod_cliente`),
  CONSTRAINT `FK_presupuesto_cliente` FOREIGN KEY (`cod_cliente`) REFERENCES `clientes` (`cod_cliente`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `presupuesto_detalle` (
  `cod_presupuesto_detalle` int(10) NOT NULL AUTO_INCREMENT,
  `cod_presupuesto_cabecera` int(10) NOT NULL,
  `cod_producto` int(5) NOT NULL,
  `precio_producto` float NOT NULL,
  `cantidad_producto` float NOT NULL,
  PRIMARY KEY (`cod_presupuesto_detalle`),
  KEY `FK_presupuesto` (`cod_presupuesto_cabecera`),
  KEY `FK_presupuesto_producto` (`cod_producto`),
  CONSTRAINT `FK_presupuesto` FOREIGN KEY (`cod_presupuesto_cabecera`) REFERENCES `presupuesto` (`cod_presupuesto`) ON UPDATE CASCADE,
  CONSTRAINT `FK_presupuesto_producto` FOREIGN KEY (`cod_producto`) REFERENCES `productos` (`cod_producto`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `productos` (
  `cod_producto` int(5) NOT NULL,
  `nombre_producto` varbinary(50) NOT NULL,
  `img1_producto` mediumblob DEFAULT NULL,
  `img2_producto` mediumblob DEFAULT NULL,
  `cod_clasificacion` int(5) DEFAULT NULL,
  `cod_marca` int(5) DEFAULT NULL,
  `precio_producto` decimal(10,2) DEFAULT NULL,
  `existencia_producto` decimal(10,2) DEFAULT 0.00,
  `iva_producto` enum('EXENTAS','5%','10%') DEFAULT '10%',
  `estado_producto` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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


